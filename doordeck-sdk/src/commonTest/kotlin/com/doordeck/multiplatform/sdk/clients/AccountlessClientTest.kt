package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.setupMockClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

/**
 * What signing in does to the key material the device is already holding.
 *
 * Signing in as somebody else used to leave the previous user's key pair, certificate chain and
 * "verified" mark exactly where they were: the login replaced the tokens and the email and nothing
 * else. The entry checks then found a key, an unexpired chain and a verified mark — never asking
 * whose they were — and concluded that nothing had to be registered, so the second user was let in
 * with no verification at all, holding the first user's certificate chain.
 *
 * Found by hand: register one account, go back, sign in as another, and no code is ever asked for.
 */
class AccountlessClientTest : IntegrationTest() {

    @Test
    fun signingInAsSomebodyElseDropsThePreviousUsersKeyMaterial() = runTest {
        CloudHttpClient.setupMockClient(TOKENS)
        givenAVerifiedUser(FIRST_USER)

        AccountlessClient.loginRequest(SECOND_USER, PASSWORD)

        assertEquals(SECOND_USER, Context.getUserEmail())
        assertNull(Context.getKeyPair(), "the new user inherited the previous one's key pair")
        assertNull(Context.getCertificateChain(), "the new user inherited the certificate chain")
        assertNull(Context.getUserId(), "the new user inherited the previous one's user id")
        assertFalse(
            Context.isKeyPairVerified(),
            "the new user inherited a verified key, so no code is asked for",
        )
    }

    /** The session being opened is the one thing the wipe must not take with it. */
    @Test
    fun theNewUsersOwnSessionSurvivesIt() = runTest {
        CloudHttpClient.setupMockClient(TOKENS)
        givenAVerifiedUser(FIRST_USER)

        AccountlessClient.loginRequest(SECOND_USER, PASSWORD)

        assertEquals(TOKENS.authToken, Context.getCloudAuthToken())
        assertEquals(TOKENS.refreshToken, Context.getCloudRefreshToken())
        assertEquals(TEST_ENVIRONMENT, Context.getApiEnvironment(), "the environment was lost")
    }

    /**
     * The ordinary case: the same person signing in again keeps what they have, and is not asked
     * for a code they have already answered on this device.
     */
    @Test
    fun signingInAgainAsTheSameUserKeepsIt() = runTest {
        CloudHttpClient.setupMockClient(TOKENS)
        givenAVerifiedUser(FIRST_USER)

        AccountlessClient.loginRequest(FIRST_USER, PASSWORD)

        assertEquals(CERTIFICATE_CHAIN, Context.getCertificateChain())
        assertTrue(Context.isKeyPairVerified())
    }

    /**
     * Key material with no email beside it cannot be shown to belong to whoever is signing in —
     * storage written before the email was kept, or restored by an integrator who never set one.
     * Unknown is treated as somebody else.
     */
    @Test
    fun keyMaterialWithNoEmailBesideItIsNotInherited() = runTest {
        CloudHttpClient.setupMockClient(TOKENS)
        Context.setCertificateChain(CERTIFICATE_CHAIN)
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)

        AccountlessClient.loginRequest(SECOND_USER, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    /** Registering over somebody else's session leaves the same material behind. */
    @Test
    fun registeringAsSomebodyElseDropsItToo() = runTest {
        CloudHttpClient.setupMockClient(TOKENS)
        givenAVerifiedUser(FIRST_USER)

        AccountlessClient.registrationRequest(
            email = SECOND_USER,
            password = PASSWORD,
            displayName = null,
            force = false,
            publicKey = null,
        )

        assertEquals(SECOND_USER, Context.getUserEmail())
        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    private fun givenAVerifiedUser(email: String) {
        Context.setUserEmail(email)
        Context.setUserId(USER_ID)
        Context.setCertificateChain(CERTIFICATE_CHAIN)
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)
    }

    private companion object {
        const val FIRST_USER = "first@doordeck.com"
        const val SECOND_USER = "second@doordeck.com"
        const val PASSWORD = "password"
        const val USER_ID = "9e6d8a5a-8b1e-4b4a-9c58-2b4f7a4a1f11"

        /**
         * Nothing here is parsed: the context stores the chain as given, and the checks under test
         * only ask whether it is there and whether the verified mark matches the stored public key.
         */
        val CERTIFICATE_CHAIN = listOf("a-certificate", "an-intermediate")
        val PUBLIC_KEY = ByteArray(32) { it.toByte() }
        val PRIVATE_KEY = ByteArray(64) { (64 - it).toByte() }
        val TOKENS = BasicTokenResponse(authToken = "an-auth-token", refreshToken = "a-refresh-token")
    }
}
