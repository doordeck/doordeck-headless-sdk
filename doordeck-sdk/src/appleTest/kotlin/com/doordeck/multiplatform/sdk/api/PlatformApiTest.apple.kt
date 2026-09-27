package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_APPLICATION_NAME
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.ApplicationJwtBody
import com.doordeck.multiplatform.sdk.model.data.ApplicationJwtHeader
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.doordeck.multiplatform.sdk.util.toNsUrlComponents
import com.doordeck.multiplatform.sdk.util.toUrlString
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import platform.Foundation.NSUUID
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class PlatformApiTest : IntegrationTest() {

    @AfterTest
    fun cleanUp() = runBlocking {
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        PlatformApi.listApplications().filter { application ->
            application.name.startsWith(TEST_MAIN_APPLICATION_NAME) &&
                    application.owners.any { it == PLATFORM_TEST_MAIN_USER_ID }
        }.forEach { application ->
            runCatching {
                PlatformApi.deleteApplication(application.applicationId)
            }
        }
    }

    private suspend fun withApplication(block: suspend (TestApplication) -> Unit) {
        val authTokens = AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val newApplication = PlatformOperations.CreateApplication(
            name = "$TEST_MAIN_APPLICATION_NAME - ${randomUuidString()}",
            companyName = randomString(),
            mailingAddress = randomEmail(),
            privacyPolicy = randomUri(),
            supportContact = randomUri()
        )
        val applicationId = PlatformApi.createApplication(newApplication)
        try {
            block(TestApplication(newApplication, applicationId, authTokens.authToken))
        } finally {
            runCatching {
                PlatformApi.deleteApplication(applicationId)
            }
        }
    }

    private data class TestApplication(
        val request: PlatformOperations.CreateApplication,
        val applicationId: NSUUID,
        val authToken: String
    )

    @Test
    fun shouldCreateApplication() = runTest {
        withApplication { (newApplication, applicationId, _) ->
            // When
            val application = PlatformApi.listApplications().firstOrNull {
                it.name.equals(newApplication.name, true)
            }

            // Then
            assertNotNull(application)
            assertEquals(applicationId, application.applicationId)
            assertEquals(newApplication.name, application.name)
            assertEquals(newApplication.companyName, application.companyName)
            assertEquals(newApplication.mailingAddress, application.mailingAddress)
            assertEquals(newApplication.privacyPolicy, application.privacyPolicy)
            assertEquals(newApplication.supportContact, application.supportContact)
        }
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        withApplication { (_, applicationId, _) ->
            // When
            PlatformApi.deleteApplication(applicationId)

            // Then
            val applications = PlatformApi.listApplications()
            assertFalse { applications.any { it.applicationId == applicationId } }
        }
    }

    @Test
    fun shouldUpdateApplicationDetails() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldUpdateApplicationName
            val updatedApplicationName = "$TEST_MAIN_APPLICATION_NAME - ${randomUuidString()}"

            // When
            PlatformApi.updateApplicationName(applicationId, updatedApplicationName)

            // Then
            var application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationName, application.name)

            // Given - shouldUpdateApplicationCompanyName
            val updatedApplicationCompanyName = randomString()

            // When
            PlatformApi.updateApplicationCompanyName(applicationId, updatedApplicationCompanyName)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationCompanyName, application.companyName)

            // Given - shouldUpdateApplicationMailingAddress
            val updatedApplicationMailingAddress = randomEmail()

            // When
            PlatformApi.updateApplicationMailingAddress(applicationId, updatedApplicationMailingAddress)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationMailingAddress, application.mailingAddress)
        }
    }

    @Test
    fun shouldUpdateApplicationLinks() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldUpdateApplicationPrivacyPolicy
            val updatedApplicationPrivacyPolicy = randomUri()

            // When
            PlatformApi.updateApplicationPrivacyPolicy(applicationId, updatedApplicationPrivacyPolicy)

            // Then
            var application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

            // Given - shouldUpdateApplicationSupportContact
            val updatedApplicationSupportContact = randomUri()

            // When
            PlatformApi.updateApplicationSupportContact(applicationId, updatedApplicationSupportContact)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationSupportContact, application.supportContact)

            // Given - shouldUpdateApplicationAppLink
            val updatedApplicationAppLink = randomUri()

            // When
            PlatformApi.updateApplicationAppLink(applicationId, updatedApplicationAppLink)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationAppLink, application.appLink)

            // Given - shouldUpdateApplicationLogoUrl
            val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test".toNsUrlComponents()

            // When
            PlatformApi.updateApplicationLogoUrl(applicationId, updatedApplicationLogoUrl)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationLogoUrl, application.logoUrl)
        }
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given
            val updatedApplicationEmailPreferences = PlatformOperations.EmailPreferences(
                senderEmail = randomEmail(),
                senderName = "test",
                primaryColour = "#000000",
                secondaryColour = "#000000",
                onlySendEssentialEmails = true,
                callToAction = PlatformOperations.EmailCallToAction(
                    actionTarget = randomUri(),
                    headline = "test",
                    actionText = "test"
                )
            )

            // When
            PlatformApi.updateApplicationEmailPreferences(applicationId, updatedApplicationEmailPreferences)

            // Then
            val application = PlatformApi.getApplication(applicationId)
            assertEquals(updatedApplicationEmailPreferences.senderEmail, application.emailPreferences.senderEmail)
            assertEquals(updatedApplicationEmailPreferences.senderName, application.emailPreferences.senderName)
            assertEquals(updatedApplicationEmailPreferences.primaryColour, application.emailPreferences.primaryColour)
            assertEquals(updatedApplicationEmailPreferences.secondaryColour, application.emailPreferences.secondaryColour)
            assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, application.emailPreferences.onlySendEssentialEmails)
            assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, application.emailPreferences.callToAction?.actionTarget)
            assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, application.emailPreferences.callToAction?.headline)
            assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, application.emailPreferences.callToAction?.actionText)
        }
    }

    @Test
    fun shouldAddAndDeleteAuthIssuer() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddAuthIssuer
            val addedApplicationAuthIssuer = randomUri()

            // When
            PlatformApi.addAuthIssuer(applicationId, addedApplicationAuthIssuer)

            // Then
            var application = PlatformApi.getApplication(applicationId)
            assertNotEquals(0, application.authDomains.size)
            assertTrue { application.authDomains.any { it == addedApplicationAuthIssuer } }

            // Given - shouldDeleteAuthIssuer
            val removedApplicationAuthIssuer = addedApplicationAuthIssuer

            // When
            PlatformApi.deleteAuthIssuer(applicationId, removedApplicationAuthIssuer)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(0, application.authDomains.size)
            assertFalse { application.authDomains.any { it == removedApplicationAuthIssuer } }
        }
    }

    @Test
    fun shouldAddAndRemoveCorsDomain() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddCorsDomain
            val addedApplicationCorsDomain = randomUri()

            // When
            PlatformApi.addCorsDomain(applicationId, addedApplicationCorsDomain)

            // Then
            var application = PlatformApi.getApplication(applicationId)
            assertNotEquals(0, application.corsDomains.size)
            assertTrue { application.corsDomains.any { it == addedApplicationCorsDomain } }

            // Given - shouldDeleteCorsDomain
            val removedApplicationCorsDomain = addedApplicationCorsDomain

            // When
            PlatformApi.removeCorsDomain(applicationId, removedApplicationCorsDomain)

            // Then
            application = PlatformApi.getApplication(applicationId)
            assertEquals(0, application.corsDomains.size)
            assertFalse { application.corsDomains.any { it == removedApplicationCorsDomain } }
        }
    }

    @Test
    fun shouldAddAuthKeys() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddEd25519AuthKey
            val ed25519KeyPair = CryptoManager.generateKeyPair()
            val ed25519Key = PlatformOperations.Ed25519Key(
                kid = randomUuidString(),
                use = "sig",
                alg = "EdDSA",
                crv = "Ed25519",
                x = ed25519KeyPair.public.encodeByteArrayToBase64()
            )

            // When
            PlatformApi.addAuthKey(applicationId, ed25519Key)

            // Then
            var application = PlatformApi.getApplication(applicationId)
            val actualEd25519Key = application.authKeys.entries.firstOrNull {
                it.key == ed25519Key.kid
            }?.value as? Ed25519KeyResponse
            assertNotNull(actualEd25519Key)
            assertEquals(ed25519Key.use, actualEd25519Key.use)
            assertEquals(ed25519Key.kid, actualEd25519Key.kid)
            assertEquals(ed25519Key.alg, actualEd25519Key.alg)
            assertEquals(ed25519Key.crv, actualEd25519Key.crv)
            assertEquals(ed25519Key.x, actualEd25519Key.x)

            // Given - shouldAddRsaAuthKey
            val rsaKey = PlatformOperations.RsaKey(
                kid = randomUuidString(),
                use = "sig",
                alg = "RS256",
                e = "AQAB",
                n = "7PsoesJRZIBUKN3AlhGCJPflQd08U9n9EsdeQS70Dbr8ce-aIpVjNAWxPaNdddYQJBUcj6wy3jKe8Vzu04tCrfafjBR6Db8pZGhTEjRQP6wQKxuo7GbnqUeCgrbT2cE5W-zRJGX4ImSuaoOyNXuDjpmDA4stWqXrMeDZIUqXcFpcOTMfi-cbSZ0A4fgX43bTCef-noprBtBAig-kaz3W7NFcBSkA3faUdlaJ6Bj9DHpqkQYpUR-MuqmAyGUOli0JY0x6QhoVrNGFQ1ejivbvMH3lkuhrJwJlJEt0wD3JoH0Q03XBKcJSBeUl6pzZV0oD2lNrQIrQdsQ1_0yLUEVVWQ"
            )

            // When
            PlatformApi.addAuthKey(applicationId, rsaKey)

            // Then
            application = PlatformApi.getApplication(applicationId)
            val actualRsaKey = application.authKeys.entries.firstOrNull {
                it.key == rsaKey.kid
            }?.value as? RsaKeyResponse
            assertNotNull(actualRsaKey)
            assertEquals(rsaKey.use, actualRsaKey.use)
            assertEquals(rsaKey.kid, actualRsaKey.kid)
            assertEquals(rsaKey.alg, actualRsaKey.alg)
            assertEquals(rsaKey.e, actualRsaKey.e)
            assertEquals(rsaKey.n, actualRsaKey.n)

            // Given - shouldAddEcAuthKey
            val ecKey = PlatformOperations.EcKey(
                kid = randomUuidString(),
                use = "sig",
                alg = "ES256",
                crv = "secp256k1",
                x = "L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA",
                y = "ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"
            )

            // When
            PlatformApi.addAuthKey(applicationId, ecKey)

            // Then
            application = PlatformApi.getApplication(applicationId)
            val actualKeyEcKey = application.authKeys.entries.firstOrNull {
                it.key == ecKey.kid
            }?.value as? EcKeyResponse
            assertNotNull(actualKeyEcKey)
            assertEquals(ecKey.use, actualKeyEcKey.use)
            assertEquals(ecKey.kid, actualKeyEcKey.kid)
            assertEquals(ecKey.alg, actualKeyEcKey.alg)
            assertEquals(ecKey.crv, actualKeyEcKey.crv)
            assertEquals(ecKey.x, actualKeyEcKey.x)
            assertEquals(ecKey.y, actualKeyEcKey.y)
        }
    }

    @Test
    fun shouldGetApplicationUsers() = runTest {
        withApplication { (_, applicationId, authToken) ->
            // Given - an auth issuer and an Ed25519 auth key to sign the application user token with
            val addedApplicationAuthIssuer = randomUri()
            PlatformApi.addAuthIssuer(applicationId, addedApplicationAuthIssuer)

            val ed25519KeyPair = CryptoManager.generateKeyPair()
            val ed25519KeyId = randomUuidString()
            PlatformApi.addAuthKey(
                applicationId,
                PlatformOperations.Ed25519Key(
                    kid = ed25519KeyId,
                    use = "sig",
                    alg = "EdDSA",
                    crv = "Ed25519",
                    x = ed25519KeyPair.public.encodeByteArrayToBase64()
                )
            )

            val applicationUserEmail = "training+${randomUuidString()}@doordeck.com"
            val applicationUserId = randomUuidString()
            val applicationJwtHeader = ApplicationJwtHeader("Ed25519", ed25519KeyId)
            val applicationJwtBody = ApplicationJwtBody(
                iss = addedApplicationAuthIssuer.toString(),
                exp = Clock.System.now().epochSeconds + 1.days.inWholeSeconds,
                iat = Clock.System.now().epochSeconds,
                aud = ApiEnvironment.PROD.cloudHost,
                sub = applicationUserId,
                email = applicationUserEmail,
                emailVerified = true,
                name = "Training Training"
            )
            val headerB64 = applicationJwtHeader.toJson().encodeToByteArray().encodeByteArrayToBase64()
            val bodyB64 = applicationJwtBody.toJson().encodeToByteArray().encodeByteArrayToBase64()
            val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(ed25519KeyPair.private).encodeByteArrayToBase64()
            val applicationAuthToken = "$headerB64.$bodyB64.$signatureB64"

            // When
            ContextManager.clearContext()
            ContextManager.setCloudAuthToken(applicationAuthToken) // Override the context auth token with the application auth token
            AccountApi.getUserDetails() // Perform a request to create the new user and attach it to the application
            ContextManager.clearContext()
            ContextManager.setCloudAuthToken(authToken) // Restore the context token

            // Then
            val applicationUsers = PlatformApi.getApplicationUsers(applicationId)
            assertEquals(1, applicationUsers.size)
            assertEquals(applicationUserEmail, applicationUsers.first().email)
            assertEquals(applicationJwtBody.name, applicationUsers.first().displayName)
            assertEquals(applicationUserId, applicationUsers.first().foreignKey)

            ContextManager.clearContext()
            ContextManager.setCloudAuthToken(applicationAuthToken) // Override the context auth token with the application auth token
            AccountApi.deleteAccount() // Cleanup the application user
            ContextManager.clearContext()
            ContextManager.setCloudAuthToken(authToken) // Restore the context token
        }
    }

    @Test
    fun shouldAddAndRemoveApplicationOwner() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldGetApplicationOwnersDetails
            // When
            var applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(applicationId)

            // Then
            assertTrue { applicationOwnerDetails.isNotEmpty() }
            assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

            // Given - shouldAddApplicationOwner
            // When
            PlatformApi.addApplicationOwner(applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID)

            // Then
            applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(applicationId)
            assertTrue { applicationOwnerDetails.isNotEmpty() }
            assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

            // Given - shouldRemoveApplicationOwner
            // When
            PlatformApi.removeApplicationOwner(applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID)

            // Then
            applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(applicationId)
            assertTrue { applicationOwnerDetails.isNotEmpty() }
            assertFalse { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }
        }
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given
            val contentType = "image/png"

            // When
            val url = PlatformApi.getLogoUploadUrl(applicationId, contentType)

            // Then
            assertTrue { url.uploadUrl.toUrlString().contains("doordeck-upload") }
        }
    }
}