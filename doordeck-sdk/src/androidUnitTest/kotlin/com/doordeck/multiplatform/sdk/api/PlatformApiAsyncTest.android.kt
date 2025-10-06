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
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.OctetKeyPair
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.util.Base64URL
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldTestPlatformAsync() = runTest {
        // Given - shouldCreateApplication
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val newApplication = PlatformOperations.CreateApplication(
            name = "Test Application $platformType ${randomUuidString()}",
            companyName = randomString(),
            mailingAddress = randomEmail(),
            privacyPolicy = randomUri(),
            supportContact = randomUri()
        )

        // When
        val applicationId = PlatformApi.createApplicationAsync(newApplication).await()

        // Then
        var application = PlatformApi.listApplicationsAsync().await().firstOrNull {
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
        PlatformApi.updateApplicationNameAsync(application.applicationId, updatedApplicationName).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationName, application.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = randomString()

        // When
        PlatformApi.updateApplicationCompanyNameAsync(application.applicationId, updatedApplicationCompanyName).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationCompanyName, application.companyName)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = randomEmail()

        // When
        PlatformApi.updateApplicationMailingAddressAsync(application.applicationId, updatedApplicationMailingAddress).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = randomUri()

        // When
        PlatformApi.updateApplicationPrivacyPolicyAsync(application.applicationId, updatedApplicationPrivacyPolicy).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = randomUri()

        // When
        PlatformApi.updateApplicationSupportContactAsync(application.applicationId, updatedApplicationSupportContact).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationSupportContact, application.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = randomUri()

        // When
        PlatformApi.updateApplicationAppLinkAsync(application.applicationId, updatedApplicationAppLink).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
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
        PlatformApi.updateApplicationEmailPreferencesAsync(application.applicationId, updatedApplicationEmailPreferences).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
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
        PlatformApi.updateApplicationLogoUrlAsync(application.applicationId, updatedApplicationLogoUrl).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)

        // Given - shouldAddAuthIssuer
        val addApplicationAuthIssuer = randomUri()

        // When
        PlatformApi.addAuthIssuerAsync(application.applicationId, addApplicationAuthIssuer).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertNotEquals(0, application.authDomains.size)
        assertTrue { application.authDomains.any { it == addApplicationAuthIssuer } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = addApplicationAuthIssuer

        // When
        PlatformApi.deleteAuthIssuerAsync(application.applicationId, removedApplicationAuthIssuer).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(0, application.authDomains.size)
        assertFalse { application.authDomains.any { it == removedApplicationAuthIssuer } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = randomUri()

        // When
        PlatformApi.addCorsDomainAsync(application.applicationId, addedApplicationCorsDomain).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertNotEquals(0, application.corsDomains.size)
        assertTrue { application.corsDomains.any { it == addedApplicationCorsDomain } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = addedApplicationCorsDomain

        // When
        PlatformApi.removeCorsDomainAsync(application.applicationId, removedApplicationCorsDomain).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
        assertEquals(0, application.corsDomains.size)
        assertFalse { application.corsDomains.any { it == removedApplicationCorsDomain } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = OctetKeyPair.Builder(Curve.Ed25519, Base64URL("vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"))
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(Algorithm.parse("EdDSA"))
            .keyID(randomUuidString())
            .build()

        // When
        PlatformApi.addAuthKeyAsync(application.applicationId, ed25519Key).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
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
        val rsaKey = RSAKey.Builder(
            Base64URL("7PsoesJRZIBUKN3AlhGCJPflQd08U9n9EsdeQS70Dbr8ce-aIpVjNAWxPaNdddYQJBUcj6wy3jKe8Vzu04tCrfafjBR6Db8pZGhTEjRQP6wQKxuo7GbnqUeCgrbT2cE5W-zRJGX4ImSuaoOyNXuDjpmDA4stWqXrMeDZIUqXcFpcOTMfi-cbSZ0A4fgX43bTCef-noprBtBAig-kaz3W7NFcBSkA3faUdlaJ6Bj9DHpqkQYpUR-MuqmAyGUOli0JY0x6QhoVrNGFQ1ejivbvMH3lkuhrJwJlJEt0wD3JoH0Q03XBKcJSBeUl6pzZV0oD2lNrQIrQdsQ1_0yLUEVVWQ"),
            Base64URL("AQAB"))
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(Algorithm.parse("RS256"))
            .keyID(randomUuidString())
            .build()

        // When
        PlatformApi.addAuthKeyAsync(application.applicationId, rsaKey).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
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
        val ecKey = ECKey.Builder(Curve.SECP256K1, Base64URL("L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA"), Base64URL("ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"))
            .keyUse(KeyUse.SIGNATURE)
            .algorithm(Algorithm.parse("ES256"))
            .keyID(randomUuidString())
            .build()

        // When
        PlatformApi.addAuthKeyAsync(application.applicationId, ecKey).await()

        // Then
        application = PlatformApi.getApplicationAsync(application.applicationId).await()
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
        var applicationOwnerDetails = PlatformApi.getApplicationOwnersDetailsAsync(application.applicationId).await()

        // Then
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PlatformApi.addApplicationOwnerAsync(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        applicationOwnerDetails  = PlatformApi.getApplicationOwnersDetailsAsync(application.applicationId).await()
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        PlatformApi.removeApplicationOwnerAsync(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        applicationOwnerDetails = PlatformApi.getApplicationOwnersDetailsAsync(application.applicationId).await()
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertFalse { applicationOwnerDetails.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val url = PlatformApi.getLogoUploadUrlAsync(application.applicationId, contentType).await()

        // Then
        assertTrue { url.uploadUrl.host.contains("doordeck-upload") }

        // Given - shouldDeleteApplication
        // When
        PlatformApi.deleteApplicationAsync(application.applicationId).await()

        // Then
        val applications = PlatformApi.listApplicationsAsync().await()
        assertFalse { applications.any { it.applicationId == application.applicationId } }
    }
}