package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import io.ktor.client.HttpClient as KtorHttpClient

/**
 * What signing in does to the key material the device is already holding.
 *
 * Signing in as somebody else used to leave the previous user's key pair, certificate chain and
 * "verified" mark exactly where they were: the login replaced the tokens and the email and nothing
 * else. The entry checks then found a key, an unexpired chain and a verified mark — never asking
 * whose they were — and concluded that nothing had to be registered, so the second user was let in
 * with no verification at all, holding the first user's certificate chain.
 */
class AccountlessClientTest : IntegrationTest() {

    @Test
    fun signingInAsSomebodyElseDropsThePreviousUsersKeyMaterial() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertEquals(SECOND_USER_EMAIL, Context.getUserEmail())
        assertNull(Context.getCertificateChain(), "the new user inherited the certificate chain")
        assertNull(Context.getUserId(), "the new user inherited the previous one's user id")
        assertFalse(Context.isKeyPairVerified(), "the new user inherited a verified key, so no code is asked for")
        val keyPair = assertNotNull(Context.getKeyPair(), "there is no key left to register")
        assertFalse(keyPair.public.contentEquals(PUBLIC_KEY), "the new user inherited the previous one's key pair")
    }

    /**
     * What integrators — the React Native bridge among them — do on a fresh install: set a key
     * pair, sign in, then register that key. Nothing in the context can let anybody in yet, so the
     * key they just set must still be there to register.
     */
    @Test
    fun aKeyPairSetJustBeforeTheFirstSignInIsKept() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertContentEquals(PUBLIC_KEY, Context.getKeyPair()?.public)
        assertEquals(FIRST_USER_EMAIL, Context.getUserEmail())
    }

    /**
     * A device that was let through before this was fixed is still holding somebody else's chain,
     * under its own email. The user id stored with that chain says whose it is, so it heals on the
     * next sign-in.
     */
    @Test
    fun aChainIssuedToSomebodyElseGoesEvenWhenTheEmailMatches() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(SECOND_USER_EMAIL)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertNull(Context.getUserId())
        assertFalse(Context.isKeyPairVerified())
    }

    /** The session being opened, and what describes the installation, survive it. */
    @Test
    fun theNewUsersOwnSessionAndTheInstallationSurviveIt() = runTest {
        val tokens = tokensFor(SECOND_USER_ID)
        CloudHttpClient.setupMockClient(tokens)
        givenAVerifiedUser(FIRST_USER_EMAIL)
        Context.setFusionHost(FUSION_HOST)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertEquals(tokens.authToken, Context.getCloudAuthToken())
        assertEquals(tokens.refreshToken, Context.getCloudRefreshToken())
        assertEquals(TEST_ENVIRONMENT, Context.getApiEnvironment(), "the environment was lost")
        assertEquals(FUSION_HOST, Context.getFusionHost(), "the Fusion host was lost")
    }

    /**
     * The ordinary case: the same person signing in again keeps what they have, and is not asked
     * for a code they have already answered on this device.
     */
    @Test
    fun signingInAgainAsTheSameUserKeepsIt() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertEquals(listOf(FIRST_USER_CERTIFICATE), Context.getCertificateChain())
        assertEquals(FIRST_USER_ID, Context.getUserId())
        assertTrue(Context.isKeyPairVerified())
    }

    /** Typing the same address with different capitals is still the same person. */
    @Test
    fun theEmailIsComparedRegardlessOfCase() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL.uppercase(), PASSWORD)

        assertEquals(listOf(FIRST_USER_CERTIFICATE), Context.getCertificateChain())
        assertTrue(Context.isKeyPairVerified())
    }

    /**
     * A token that does not say who it is for cannot contradict the stored user id, so the email
     * alone decides, and it matches.
     */
    @Test
    fun aTokenWithNoSubjectLeavesTheSameEmailsMaterialAlone() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(userId = null))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertEquals(listOf(FIRST_USER_CERTIFICATE), Context.getCertificateChain())
        assertTrue(Context.isKeyPairVerified())
    }

    /**
     * Key material with no email beside it cannot be shown to belong to whoever is signing in —
     * storage written before the email was kept, or restored by an integrator who never set one.
     * Unknown is treated as somebody else.
     */
    @Test
    fun keyMaterialWithNoEmailBesideItIsNotInherited() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        Context.setCertificateChain(listOf(FIRST_USER_CERTIFICATE))
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    /** A chain with no user id stored beside it cannot say whose it is either. */
    @Test
    fun aChainWithNoUserIdBesideItIsNotInherited() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        Context.setUserEmail(FIRST_USER_EMAIL)
        Context.setCertificateChain(listOf(FIRST_USER_CERTIFICATE))
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    /** Registering over somebody else's session leaves the same material behind. */
    @Test
    fun registeringAsSomebodyElseDropsItToo() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        AccountlessClient.registrationRequest(
            email = SECOND_USER_EMAIL,
            password = PASSWORD,
            displayName = null,
            force = false,
            publicKey = null,
        )

        assertEquals(SECOND_USER_EMAIL, Context.getUserEmail())
        assertNull(Context.getCertificateChain())
        assertNull(Context.getUserId())
        assertFalse(Context.isKeyPairVerified())
    }

    /**
     * The whole path the apps take. The login has to come before reading the key material:
     * read first, it finds the previous user's verified key and unexpired chain, decides nothing
     * has to be registered, and the second user is in without a code.
     */
    @Test
    fun anAssistedLoginAsSomebodyElseRegistersAKeyOfTheirOwn() = runTest {
        answerWith(tokensFor(SECOND_USER_ID), registrationFor(SECOND_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL)

        HelperClient.assistedLoginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertEquals(2, CloudHttpClient.client.requestHistory().size, "no key was registered for the new user")
        assertEquals(SECOND_USER_ID, Context.getUserId())
        assertEquals(listOf(SECOND_USER_CERTIFICATE), Context.getCertificateChain())
        val keyPair = assertNotNull(Context.getKeyPair())
        assertFalse(keyPair.public.contentEquals(PUBLIC_KEY), "the new user is using the previous one's key")
    }

    /** And the same person signing in again is not made to register, nor asked for a code. */
    @Test
    fun anAssistedLoginAsTheSameUserRegistersNothing() = runTest {
        answerWith(tokensFor(FIRST_USER_ID), registrationFor(FIRST_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL, certificate = TEST_VALID_CERTIFICATE)

        val result = HelperClient.assistedLoginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertEquals(1, CloudHttpClient.client.requestHistory().size)
        assertFalse(result.requiresVerification)
        assertContentEquals(PUBLIC_KEY, Context.getKeyPair()?.public)
        assertTrue(Context.isKeyPairVerified())
    }

    private fun givenAVerifiedUser(email: String, certificate: String = FIRST_USER_CERTIFICATE) {
        Context.setUserEmail(email)
        Context.setUserId(FIRST_USER_ID)
        Context.setCertificateChain(listOf(certificate))
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)
    }

    /** Answers the login with [tokens], then the key registration with [registration]. */
    private fun answerWith(tokens: BasicTokenResponse, registration: BasicRegisterEphemeralKeyResponse) {
        val engine = MockEngine.config {
            addHandler { respondContent(tokens) }
            addHandler { respondContent(registration) }
        }
        val client = KtorHttpClient(engine) {
            installResponseValidator()
            installContentNegotiation()
            installAuth()
        }.also { it.addExceptionInterceptor() }
        CloudHttpClient.overrideClient(client)
    }

    /**
     * Only the claims are read, never the signature, so this is as much of a JWT as it needs to be.
     * The expiry is far enough away that the token reads as live.
     */
    private fun tokensFor(userId: String?): BasicTokenResponse {
        val subject = userId?.let { "\"sub\":\"$it\"," } ?: ""
        val claims = """{$subject"exp":4102444800}"""
        val token = "header.${claims.encodeToByteArray().encodeByteArrayToBase64()}.signature"
        return BasicTokenResponse(authToken = token, refreshToken = "a-refresh-token")
    }

    private fun registrationFor(userId: String) = BasicRegisterEphemeralKeyResponse(
        certificateChain = listOf(if (userId == FIRST_USER_ID) FIRST_USER_CERTIFICATE else SECOND_USER_CERTIFICATE),
        userId = userId,
    )

    private companion object {
        const val FIRST_USER_EMAIL = "first@doordeck.com"
        const val SECOND_USER_EMAIL = "second@doordeck.com"
        const val PASSWORD = "password"
        const val FIRST_USER_ID = "11111111-1111-4111-8111-111111111111"
        const val SECOND_USER_ID = "22222222-2222-4222-8222-222222222222"
        const val FUSION_HOST = "http://fusion.local:27700"

        /** Nothing on the sign-in path parses a chain, so any string stands in for one. */
        const val FIRST_USER_CERTIFICATE = "first-user-certificate"
        const val SECOND_USER_CERTIFICATE = "second-user-certificate"

        val PUBLIC_KEY = ByteArray(32) { it.toByte() }
        val PRIVATE_KEY = ByteArray(64) { (64 - it).toByte() }
    }
}
