package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.any
import com.doordeck.multiplatform.sdk.isNotEmpty
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.model.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.size
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.js.collections.toList
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformApiTest : IntegrationTest() {
    
    @Test
    fun shouldTestPlatform() = runTest {
        // Given - shouldCreateApplication
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val newApplication = PlatformOperations.CreateApplication(
            name = "Test Application $platformType ${randomUuidString()}",
            companyName = randomString(),
            mailingAddress = randomEmail(),
            privacyPolicy = randomUrlString(),
            supportContact = randomUrlString()
        )

        // When
        val applicationId = PlatformApi.createApplication(newApplication).await()

        // Then
        var application = PlatformApi.listApplications().await().toList().firstOrNull {
            it.name.equals(newApplication.name, true)
        }
        assertNotNull(application)
        assertEquals(applicationId, application.applicationId)
        assertEquals(newApplication.name, application.name)
        assertEquals(newApplication.companyName, application.companyName)
        assertEquals(newApplication.mailingAddress, application.mailingAddress)
        assertEquals(newApplication.privacyPolicy, application.privacyPolicy)
        assertEquals(newApplication.supportContact, application.supportContact)

        // Given - shouldUpdateApplicationName
        val updatedApplicationName = "Test Application $platformType ${randomUuidString()}"

        // When
        PlatformApi.updateApplicationName(application.applicationId, updatedApplicationName).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationName, application.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = randomString()

        // When
        PlatformApi.updateApplicationCompanyName(application.applicationId, updatedApplicationCompanyName).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationCompanyName, application.companyName)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = randomEmail()

        // When
        PlatformApi.updateApplicationMailingAddress(application.applicationId, updatedApplicationMailingAddress).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = randomUrlString()

        // When
        PlatformApi.updateApplicationPrivacyPolicy(application.applicationId, updatedApplicationPrivacyPolicy).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = randomUrlString()

        // When
        PlatformApi.updateApplicationSupportContact(application.applicationId, updatedApplicationSupportContact).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationSupportContact, application.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = randomUrlString()

        // When
        PlatformApi.updateApplicationAppLink(application.applicationId, updatedApplicationAppLink).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationAppLink, application.appLink)

        // Given - shouldUpdateApplicationEmailPreferences
        val updatedApplicationEmailPreferences = PlatformOperations.EmailPreferences(
            senderEmail = randomEmail(),
            senderName = "test",
            primaryColour = "#000000",
            secondaryColour = "#000000",
            onlySendEssentialEmails = true,
            callToAction = PlatformOperations.EmailCallToAction(
                actionTarget = randomUrlString(),
                headline = "test",
                actionText = "test"
            )
        )

        // When
        PlatformApi.updateApplicationEmailPreferences(application.applicationId, updatedApplicationEmailPreferences).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationEmailPreferences.senderEmail, application.emailPreferences.senderEmail)
        assertEquals(updatedApplicationEmailPreferences.senderName, application.emailPreferences.senderName)
        assertEquals(updatedApplicationEmailPreferences.primaryColour, application.emailPreferences.primaryColour)
        assertEquals(updatedApplicationEmailPreferences.secondaryColour, application.emailPreferences.secondaryColour)
        assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, application.emailPreferences.onlySendEssentialEmails)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, application.emailPreferences.callToAction?.actionTarget)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, application.emailPreferences.callToAction?.headline)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, application.emailPreferences.callToAction?.actionText)

        // Given - shouldUpdateApplicationLogoUrl
        val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"

        // When
        PlatformApi.updateApplicationLogoUrl(application.applicationId, updatedApplicationLogoUrl).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)

        // Given - shouldAddAuthIssuer
        val addApplicationAuthIssuer = randomUrlString()

        // When
        PlatformApi.addAuthIssuer(application.applicationId, addApplicationAuthIssuer).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertNotEquals(0, application.authDomains.size())
        assertTrue { application.authDomains.any { it == addApplicationAuthIssuer } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = addApplicationAuthIssuer

        // When
        PlatformApi.deleteAuthIssuer(application.applicationId, removedApplicationAuthIssuer).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(0, application.authDomains.size())
        assertFalse { application.authDomains.any { it == removedApplicationAuthIssuer } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = randomUrlString()

        // When
        PlatformApi.addCorsDomain(application.applicationId, addedApplicationCorsDomain).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertNotEquals(0, application.corsDomains.size())
        assertTrue { application.corsDomains.any { it == addedApplicationCorsDomain } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = addedApplicationCorsDomain

        // When
        PlatformApi.removeCorsDomain(application.applicationId, removedApplicationCorsDomain).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
        assertEquals(0, application.corsDomains.size())
        assertFalse { application.corsDomains.any { it == removedApplicationCorsDomain } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = PlatformOperations.Ed25519Key(
            kid = randomUuidString(),
            use = "sig",
            alg = "EdDSA",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        PlatformApi.addAuthKey(application.applicationId, ed25519Key).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
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
        PlatformApi.addAuthKey(application.applicationId, rsaKey).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
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
        PlatformApi.addAuthKey(application.applicationId, ecKey).await()

        // Then
        application = PlatformApi.getApplication(application.applicationId).await()
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

        // Given - shouldGetApplicationOwnersDetails
        // When
        var applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(application.applicationId).await()

        // Then
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PlatformApi.addApplicationOwner(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        applicationOwnerDetails  = PlatformApi.getApplicationOwnersDetails(application.applicationId).await()
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        PlatformApi.removeApplicationOwner(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(application.applicationId).await()
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertFalse { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val url = PlatformApi.getLogoUploadUrl(application.applicationId, contentType).await()

        // Then
        assertTrue { url.uploadUrl.contains("doordeck-upload") }

        // Given - shouldDeleteApplication
        // When
        PlatformApi.deleteApplication(application.applicationId).await()

        // Then
        val applications = PlatformApi.listApplications().await()
        assertFalse { applications.any { it.applicationId == application.applicationId } }
    }
}