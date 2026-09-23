package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
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
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL, FIRST_USER_CERTIFICATE)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertEquals(SECOND_USER_EMAIL, Context.getUserEmail())
        assertNull(Context.getKeyPair(), "the new user inherited the previous one's key pair")
        assertNull(Context.getCertificateChain(), "the new user inherited the certificate chain")
        assertNull(Context.getUserId(), "the new user inherited the previous one's user id")
        assertFalse(
            Context.isKeyPairVerified(),
            "the new user inherited a verified key, so no code is asked for",
        )
    }

    /**
     * A device that was let through before this was fixed is still holding somebody else's chain,
     * under its own email. The chain says who it belongs to, so that heals on the next sign-in.
     */
    @Test
    fun aChainIssuedToSomebodyElseGoesEvenWhenTheEmailMatches() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(SECOND_USER_EMAIL, FIRST_USER_CERTIFICATE)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    /** The session being opened is the one thing the wipe must not take with it. */
    @Test
    fun theNewUsersOwnSessionSurvivesIt() = runTest {
        val tokens = tokensFor(SECOND_USER_ID)
        CloudHttpClient.setupMockClient(tokens)
        givenAVerifiedUser(FIRST_USER_EMAIL, FIRST_USER_CERTIFICATE)

        AccountlessClient.loginRequest(SECOND_USER_EMAIL, PASSWORD)

        assertEquals(tokens.authToken, Context.getCloudAuthToken())
        assertEquals(tokens.refreshToken, Context.getCloudRefreshToken())
        assertEquals(TEST_ENVIRONMENT, Context.getApiEnvironment(), "the environment was lost")
    }

    /**
     * The ordinary case: the same person signing in again keeps what they have, and is not asked
     * for a code they have already answered on this device.
     */
    @Test
    fun signingInAgainAsTheSameUserKeepsIt() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL, FIRST_USER_CERTIFICATE)

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

    /** Registering over somebody else's session leaves the same material behind. */
    @Test
    fun registeringAsSomebodyElseDropsItToo() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(SECOND_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL, FIRST_USER_CERTIFICATE)

        AccountlessClient.registrationRequest(
            email = SECOND_USER_EMAIL,
            password = PASSWORD,
            displayName = null,
            force = false,
            publicKey = null,
        )

        assertEquals(SECOND_USER_EMAIL, Context.getUserEmail())
        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    /**
     * A certificate that does not say who it belongs to cannot be shown to be ours, and an
     * unreadable chain is no use to anybody, so it goes the same way.
     */
    @Test
    fun aChainThatDoesNotSayWhoItBelongsToGoesToo() = runTest {
        CloudHttpClient.setupMockClient(tokensFor(FIRST_USER_ID))
        givenAVerifiedUser(FIRST_USER_EMAIL, TEST_VALID_CERTIFICATE)

        AccountlessClient.loginRequest(FIRST_USER_EMAIL, PASSWORD)

        assertNull(Context.getCertificateChain())
        assertFalse(Context.isKeyPairVerified())
    }

    private fun givenAVerifiedUser(email: String, certificate: String) {
        Context.setUserEmail(email)
        Context.setUserId(FIRST_USER_ID)
        Context.setCertificateChain(listOf(certificate))
        Context.setKeyPair(publicKey = PUBLIC_KEY, privateKey = PRIVATE_KEY)
        Context.setKeyPairVerified(PUBLIC_KEY)
    }

    /**
     * Only the claims are read, never the signature, so this is as much of a JWT as it needs to be.
     * The expiry is far enough away that the token reads as live.
     */
    private fun tokensFor(userId: String): BasicTokenResponse {
        val claims = """{"sub":"$userId","exp":4102444800}"""
        val token = "header.${claims.encodeToByteArray().encodeByteArrayToBase64()}.signature"
        return BasicTokenResponse(authToken = token, refreshToken = "a-refresh-token")
    }

    private companion object {
        const val FIRST_USER_EMAIL = "first@doordeck.com"
        const val SECOND_USER_EMAIL = "second@doordeck.com"
        const val PASSWORD = "password"
        const val FIRST_USER_ID = "11111111-1111-4111-8111-111111111111"
        const val SECOND_USER_ID = "22222222-2222-4222-8222-222222222222"

        /**
         * Self-signed certificates whose issuer is a single userId attribute, the shape the backend
         * gives a user's master certificate and therefore the issuer of every ephemeral
         * certificate it signs. Made with:
         *
         * `openssl req -x509 -newkey rsa:2048 -nodes -keyout key.pem -out cert.pem -days 30 -subj "/UID=<the user id>"`
         */
        const val FIRST_USER_CERTIFICATE =
            "MIIDTTCCAjWgAwIBAgIUNI+gHZxda1i2CGpX/oArAZdc8nswDQYJKoZIhvcNAQELBQAwNjE0MDIGCgmSJomT8ixkAQEMJDEx" +
            "MTExMTExLTExMTEtNDExMS04MTExLTExMTExMTExMTExMTAeFw0yNjA5MjMyMTU3MTZaFw0yNjEwMjMyMTU3MTZaMDYxNDAy" +
            "BgoJkiaJk/IsZAEBDCQxMTExMTExMS0xMTExLTQxMTEtODExMS0xMTExMTExMTExMTEwggEiMA0GCSqGSIb3DQEBAQUAA4IB" +
            "DwAwggEKAoIBAQDQPLX/pTo9hvTwzQdIp7uj6VKrWplPgYtMhuUV6YbDh0SSjNcAmqw4oubGckxuGiclh3/7cW8hgGkUeZ0y" +
            "iXskCi6jT6VIE/rwqHwjLX2tc/AqQkChmkoCjC9V+FoWEyYD99eX2B+7OW2tTdc6iJDpwn8hSma+vOeESxiy0G1vnmkmWcFM" +
            "ZxAnGHBQ1J4mLzSsKI6ZXFyPVJ1ejRPLMLPt5Ls1AkvXvB5dE9dYnvg70bISV5i6JMwbrwLOwtubg/6cmAoY+r9T5kncaSAe" +
            "hnVey7L2uXNp5kTXrOku2iQeIAQLxOBEP9cs0OLZFr2xkU4uHTYFK8ZHWSY39XX3DkWTAgMBAAGjUzBRMB0GA1UdDgQWBBSH" +
            "o009Y+TDqgajkSvUvqJJp1WDpjAfBgNVHSMEGDAWgBSHo009Y+TDqgajkSvUvqJJp1WDpjAPBgNVHRMBAf8EBTADAQH/MA0G" +
            "CSqGSIb3DQEBCwUAA4IBAQDQGjwBQybWa4cfOG2D14Dc+xL43PHcYI+CJ2ItJ2VKEY+dq7WmfUGNB6NUSOjvTFXPQYoyUSEh" +
            "k30gQ6+K/g1zZHQ+w520M4M1d+FrYDVoPlrEKoUrlOaGaB9eVB36r6hJOS5HA6qQ0/q9FDhzBk07YDOf4mlImxFzmyblSFwL" +
            "mpZLntIIedE6A9qmKRnx99Evho+J5NfJInoA0L78Ra2s+1V4p71Ecvv96Ng0SklBTe/ayGPaBQhGMuEgxZtYWF4pbw7U0c1E" +
            "rELRqAuz665whBUF0GCZXqkaTqs3G8n+zMSP51MvkrdyoTNuPgctgn+ltJrAvCDx785ctyhVk85v"

        val PUBLIC_KEY = ByteArray(32) { it.toByte() }
        val PRIVATE_KEY = ByteArray(64) { (64 - it).toByte() }
    }
}
