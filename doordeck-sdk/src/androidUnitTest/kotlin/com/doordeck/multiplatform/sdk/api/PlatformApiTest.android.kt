package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.util.toUri
import com.nimbusds.jose.Algorithm
import com.nimbusds.jose.jwk.Curve
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jose.jwk.KeyOperation
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.OctetKeyPair
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.util.Base64URL
import kotlinx.coroutines.test.runTest
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
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val newApplication = PlatformOperations.CreateApplication(
            name = "Test Application $platformType ${randomUuidString()}",
            companyName = randomString(),
            mailingAddress = randomEmail(),
            privacyPolicy = randomUri(),
            supportContact = randomUri()
        )

        // When
        val applicationId = PlatformApi.createApplication(newApplication)

        // Then
        var application = PlatformApi.listApplications().firstOrNull {
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
        PlatformApi.updateApplicationName(application.applicationId, updatedApplicationName)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationName, application.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = randomString()

        // When
        PlatformApi.updateApplicationCompanyName(application.applicationId, updatedApplicationCompanyName)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationCompanyName, application.companyName)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = randomEmail()

        // When
        PlatformApi.updateApplicationMailingAddress(application.applicationId, updatedApplicationMailingAddress)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = randomUri()

        // When
        PlatformApi.updateApplicationPrivacyPolicy(application.applicationId, updatedApplicationPrivacyPolicy)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = randomUri()

        // When
        PlatformApi.updateApplicationSupportContact(application.applicationId, updatedApplicationSupportContact)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationSupportContact, application.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = randomUri()

        // When
        PlatformApi.updateApplicationAppLink(application.applicationId, updatedApplicationAppLink)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationAppLink, application.appLink)

        // Given - shouldUpdateApplicationEmailPreferences
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
        PlatformApi.updateApplicationEmailPreferences(application.applicationId, updatedApplicationEmailPreferences)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationEmailPreferences.senderEmail, application.emailPreferences.senderEmail)
        assertEquals(updatedApplicationEmailPreferences.senderName, application.emailPreferences.senderName)
        assertEquals(updatedApplicationEmailPreferences.primaryColour, application.emailPreferences.primaryColour)
        assertEquals(updatedApplicationEmailPreferences.secondaryColour, application.emailPreferences.secondaryColour)
        assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, application.emailPreferences.onlySendEssentialEmails)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, application.emailPreferences.callToAction?.actionTarget)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, application.emailPreferences.callToAction?.headline)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, application.emailPreferences.callToAction?.actionText)

        // Given - shouldUpdateApplicationLogoUrl
        val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test".toUri()

        // When
        PlatformApi.updateApplicationLogoUrl(application.applicationId, updatedApplicationLogoUrl)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)

        // Given - shouldAddAuthIssuer
        val addApplicationAuthIssuer = randomUri()

        // When
        PlatformApi.addAuthIssuer(application.applicationId, addApplicationAuthIssuer)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertNotEquals(0, application.authDomains.size)
        assertTrue { application.authDomains.any { it == addApplicationAuthIssuer } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = addApplicationAuthIssuer

        // When
        PlatformApi.deleteAuthIssuer(application.applicationId, removedApplicationAuthIssuer)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(0, application.authDomains.size)
        assertFalse { application.authDomains.any { it == removedApplicationAuthIssuer } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = randomUri()

        // When
        PlatformApi.addCorsDomain(application.applicationId, addedApplicationCorsDomain)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertNotEquals(0, application.corsDomains.size)
        assertTrue { application.corsDomains.any { it == addedApplicationCorsDomain } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = addedApplicationCorsDomain

        // When
        PlatformApi.removeCorsDomain(application.applicationId, removedApplicationCorsDomain)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        assertEquals(0, application.corsDomains.size)
        assertFalse { application.corsDomains.any { it == removedApplicationCorsDomain } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = OctetKeyPair(
            Curve.Ed25519,
            Base64URL("vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"),
            KeyUse.SIGNATURE,
            emptySet<KeyOperation>(),
            Algorithm.parse("EdDSA"),
            randomUuidString(),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        // When
        PlatformApi.addAuthKey(application.applicationId, ed25519Key)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        val actualEd25519Key = application.authKeys.entries.firstOrNull {
            it.key == ed25519Key.keyID
        }?.value as? OctetKeyPair
        assertNotNull(actualEd25519Key)
        assertEquals(ed25519Key.keyUse, actualEd25519Key.keyUse)
        assertEquals(ed25519Key.keyID, actualEd25519Key.keyID)
        assertEquals(ed25519Key.algorithm, actualEd25519Key.algorithm)
        assertEquals(ed25519Key.curve, actualEd25519Key.curve)
        assertEquals(ed25519Key.x, actualEd25519Key.x)

        // Given - shouldAddRsaAuthKey
        val rsaKey = RSAKey(
            Base64URL("7PsoesJRZIBUKN3AlhGCJPflQd08U9n9EsdeQS70Dbr8ce-aIpVjNAWxPaNdddYQJBUcj6wy3jKe8Vzu04tCrfafjBR6Db8pZGhTEjRQP6wQKxuo7GbnqUeCgrbT2cE5W-zRJGX4ImSuaoOyNXuDjpmDA4stWqXrMeDZIUqXcFpcOTMfi-cbSZ0A4fgX43bTCef-noprBtBAig-kaz3W7NFcBSkA3faUdlaJ6Bj9DHpqkQYpUR-MuqmAyGUOli0JY0x6QhoVrNGFQ1ejivbvMH3lkuhrJwJlJEt0wD3JoH0Q03XBKcJSBeUl6pzZV0oD2lNrQIrQdsQ1_0yLUEVVWQ"),
            Base64URL("AQAB"),
            null,
            null,
            null,
            null,
            null,
            null,
            null, null,
            KeyUse.SIGNATURE, emptySet<KeyOperation>(), Algorithm.parse("RS256"), randomUuidString(),
            null, null, null, null, null, null, null, null, null,
        )

        // When
        PlatformApi.addAuthKey(application.applicationId, rsaKey)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        val actualRsaKey = application.authKeys.entries.firstOrNull {
            it.key == rsaKey.keyID
        }?.value as? RSAKey
        assertNotNull(actualRsaKey)
        assertEquals(rsaKey.keyUse, actualRsaKey.keyUse)
        assertEquals(rsaKey.keyID, actualRsaKey.keyID)
        assertEquals(rsaKey.algorithm, actualRsaKey.algorithm)
        assertEquals(rsaKey.publicExponent, actualRsaKey.publicExponent)
        assertEquals(rsaKey.modulus, actualRsaKey.modulus)

        // Given - shouldAddEcAuthKey
        val ecKey = ECKey(
            Curve.SECP256K1,
            Base64URL("L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA"),
            Base64URL("ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"),
            KeyUse.SIGNATURE,
            emptySet<KeyOperation>(),
            Algorithm.parse("ES256"),
            randomUuidString(),
            null, null, null, null, null, null, null, null, null
        )

        // When
        PlatformApi.addAuthKey(application.applicationId, ecKey)

        // Then
        application = PlatformApi.getApplication(application.applicationId)
        val actualKeyEcKey = application.authKeys.entries.firstOrNull {
            it.key == ecKey.keyID
        }?.value as? ECKey
        assertNotNull(actualKeyEcKey)
        assertEquals(ecKey.keyUse, actualKeyEcKey.keyUse)
        assertEquals(ecKey.keyID, actualKeyEcKey.keyID)
        assertEquals(ecKey.algorithm, actualKeyEcKey.algorithm)
        assertEquals(ecKey.curve, actualKeyEcKey.curve)
        assertEquals(ecKey.x, actualKeyEcKey.x)
        assertEquals(ecKey.y, actualKeyEcKey.y)

        // Given - shouldGetApplicationOwnersDetails
        // When
        var applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(application.applicationId)

        // Then
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PlatformApi.addApplicationOwner(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails  = PlatformApi.getApplicationOwnersDetails(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        PlatformApi.removeApplicationOwner(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails = PlatformApi.getApplicationOwnersDetails(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertFalse { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val url = PlatformApi.getLogoUploadUrl(application.applicationId, contentType)

        // Then
        assertTrue { url.uploadUrl.host.contains("doordeck-upload") }

        // Given - shouldDeleteApplication
        // When
        PlatformApi.deleteApplication(application.applicationId)

        // Then
        val applications = PlatformApi.listApplications()
        assertFalse { applications.any { it.applicationId == application.applicationId } }
    }
}