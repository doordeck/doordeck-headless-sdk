package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformResourceTest : SystemTest() {

    @Test
    fun shouldTestPlatform() = runBlocking {
        val applicationId = shouldCreateApplication()
        shouldUpdateApplicationName(applicationId)
        shouldUpdateApplicationCompanyName(applicationId)
        shouldUpdateApplicationMailingAddress(applicationId)
        shouldUpdateApplicationPrivacyPolicy(applicationId)
        shouldUpdateApplicationSupportContact(applicationId)
        shouldUpdateApplicationAppLink(applicationId)
        shouldUpdateApplicationEmailPreferences(applicationId)
        shouldUpdateApplicationLogoUrl(applicationId)
        shouldAddAuthIssuer(applicationId)
        shouldDeleteAuthIssuer(applicationId)
        shouldAddCorsDomain(applicationId)
        shouldDeleteCorsDomain(applicationId)
        shouldAddAuthKey(applicationId)
        shouldGetApplicationOwnersDetails(applicationId)
        shouldAddApplicationOwner(applicationId)
        shouldRemoveApplicationOwner(applicationId)
        shouldGetLogoUploadUrl(applicationId)
        shouldDeleteApplication(applicationId)
    }

    private fun shouldCreateApplication(): String {
        // Given
        val newApplication = Platform.CreateApplication(
            name = "Test Application ${uuid4()}",
            companyName = uuid4().toString(),
            mailingAddress = "test@doordeck.com",
            privacyPolicy = "https://www.doordeck.com/privacy",
            supportContact = "https://www.doordeck.com"
        )

        // When
        PLATFORM_RESOURCE.createApplication(newApplication)

        // Then
        val application = shouldListApplications().firstOrNull { it.name.equals(newApplication.name, true) }
        assertNotNull(application)

        return application.applicationId
    }

    private fun shouldUpdateApplicationName(applicationId: String) {
        // Given
        val updatedApplicationName = "Test Application ${uuid4()}"

        // When
        PLATFORM_RESOURCE.updateApplicationName(applicationId, updatedApplicationName)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationName, application.name)
    }

    private fun shouldUpdateApplicationCompanyName(applicationId: String) {
        // Given
        val updatedApplicationCompanyName = uuid4().toString()

        // When
        PLATFORM_RESOURCE.updateApplicationCompanyName(applicationId, updatedApplicationCompanyName)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationCompanyName, application.companyName)
    }

    private fun shouldUpdateApplicationMailingAddress(applicationId: String) {
        // Given
        val updatedApplicationMailingAddress = "test2@doordeck.com"

        // When
        PLATFORM_RESOURCE.updateApplicationMailingAddress(applicationId, updatedApplicationMailingAddress)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)
    }

    private fun shouldUpdateApplicationPrivacyPolicy(applicationId: String) {
        // Given
        val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"

        // When
        PLATFORM_RESOURCE.updateApplicationPrivacyPolicy(applicationId, updatedApplicationPrivacyPolicy)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)
    }

    private fun shouldUpdateApplicationSupportContact(applicationId: String) {
        // Given
        val updatedApplicationSupportContact = "https://www.doordeck2.com"

        // When
        PLATFORM_RESOURCE.updateApplicationSupportContact(applicationId, updatedApplicationSupportContact)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationSupportContact, application.supportContact)
    }

    private fun shouldUpdateApplicationAppLink(applicationId: String) {
        // Given
        val updatedApplicationAppLink = "https://www.doordeck.com"

        // When
        PLATFORM_RESOURCE.updateApplicationAppLink(applicationId, updatedApplicationAppLink)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationAppLink, application.appLink)
    }

    private fun shouldUpdateApplicationEmailPreferences(applicationId: String) {
        // Given
        val updatedApplicationEmailPreferences = Platform.EmailPreferences(
            senderEmail = "test@test.com",
            senderName = "test",
            primaryColour = "#000000",
            secondaryColour = "#000000",
            onlySendEssentialEmails = true,
            callToAction = Platform.EmailCallToAction(
                actionTarget = "test",
                headline = "test",
                actionText = "test"
            )
        )

        // When
        PLATFORM_RESOURCE.updateApplicationEmailPreferences(applicationId, updatedApplicationEmailPreferences)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationEmailPreferences.senderEmail, application.emailPreferences.senderEmail)
        assertEquals(updatedApplicationEmailPreferences.senderName, application.emailPreferences.senderName)
        assertEquals(updatedApplicationEmailPreferences.primaryColour, application.emailPreferences.primaryColour)
        assertEquals(updatedApplicationEmailPreferences.secondaryColour, application.emailPreferences.secondaryColour)
        assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, application.emailPreferences.onlySendEssentialEmails)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, application.emailPreferences.callToAction?.actionTarget)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, application.emailPreferences.callToAction?.headline)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, application.emailPreferences.callToAction?.actionText)
    }

    private fun shouldUpdateApplicationLogoUrl(applicationId: String) {
        // Given
        val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"

        // When
        PLATFORM_RESOURCE.updateApplicationLogoUrl(applicationId, updatedApplicationLogoUrl)

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)
    }

    private fun shouldAddAuthIssuer(applicationId: String) {
        // Given
        val updatedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_RESOURCE.addAuthIssuer(applicationId, updatedApplicationAuthIssuer)

        // Then
        val application = shouldGetApplication(applicationId)
        assertTrue { application.authDomains.any { it.equals(updatedApplicationAuthIssuer, true) } }
    }

    private fun shouldDeleteAuthIssuer(applicationId: String) {
        // Given
        val updatedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_RESOURCE.deleteAuthIssuer(applicationId, updatedApplicationAuthIssuer)

        // Then
        val application = shouldGetApplication(applicationId)
        assertFalse { application.authDomains.any { it.equals(updatedApplicationAuthIssuer, true) } }
    }

    private fun shouldAddCorsDomain(applicationId: String) {
        // Given
        val updatedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_RESOURCE.addCorsDomain(applicationId, updatedApplicationCorsDomain)

        // Then
        val application = shouldGetApplication(applicationId)
        assertTrue { application.corsDomains.any { it.equals(updatedApplicationCorsDomain, true) } }
    }

    private fun shouldDeleteCorsDomain(applicationId: String) {
        // Given
        val updatedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_RESOURCE.removeCorsDomain(applicationId, updatedApplicationCorsDomain)

        // Then
        val application = shouldGetApplication(applicationId)
        assertFalse { application.corsDomains.any { it.equals(updatedApplicationCorsDomain, true) } }
    }

    private fun shouldAddAuthKey(applicationId: String) {
        // Given
        val updatedApplicationAuthKey = Platform.Ed25519Key(
            kty = "OKP",
            use = "sig",
            kid = uuid4().toString(),
            alg = "EdDSA",
            d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        PLATFORM_RESOURCE.addAuthKey(applicationId, updatedApplicationAuthKey)

        // Then
        val application = shouldGetApplication(applicationId)
        // TODO
    }

    private fun shouldGetApplicationOwnersDetails(applicationId: String): Array<ApplicationOwnerDetailsResponse> {
        // When
        val result = PLATFORM_RESOURCE.getApplicationOwnersDetails(applicationId)

        // Then
        assertTrue { result.isNotEmpty() }
        assertTrue { result.any { it.userId == TEST_MAIN_USER_ID } }

        return result
    }

    private fun shouldAddApplicationOwner(applicationId: String) {
        // When
        PLATFORM_RESOURCE.addApplicationOwner(applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        val result = shouldGetApplicationOwnersDetails(applicationId)
        assertTrue { result.isNotEmpty() }
        assertTrue { result.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }
    }

    private fun shouldRemoveApplicationOwner(applicationId: String) {
        // When
        PLATFORM_RESOURCE.removeApplicationOwner(applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        val result = shouldGetApplicationOwnersDetails(applicationId)
        assertTrue { result.isNotEmpty() }
        assertFalse { result.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }
    }

    private fun shouldGetLogoUploadUrl(applicationId: String) {
        // Given
        val contentType = "image/png"

        // When
        val url = PLATFORM_RESOURCE.getLogoUploadUrl(applicationId, contentType)

        // Then
        assertTrue { url.uploadUrl.isNotEmpty() }
    }

    private fun shouldListApplications(): Array<ApplicationResponse> {
        return PLATFORM_RESOURCE.listApplications()
    }

    private fun shouldGetApplication(applicationId: String): ApplicationResponse {
        return PLATFORM_RESOURCE.getApplication(applicationId)
    }

    private fun shouldDeleteApplication(applicationId: String) {
        // When
        PLATFORM_RESOURCE.deleteApplication(applicationId)

        // Then
        val application = shouldListApplications()
        assertFalse { application.any { it.applicationId == applicationId } }
    }
}