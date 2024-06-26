package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.model.Platform
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.internal.api.PlatformResourceImpl
import com.doordeck.sdk.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformResourceTest : SystemTest() {

    // Initial application
    private val application = Platform.CreateApplication(
        name = "Test Application ${uuid4()}",
        companyName = uuid4().toString(),
        mailingAddress = "test@doordeck.com",
        privacyPolicy = "https://www.doordeck.com/privacy",
        supportContact = "https://www.doordeck.com"
    )

    // Updated application
    private val updatedApplicationName = "Test Application ${uuid4()}"
    private val updatedApplicationCompanyName = uuid4().toString()
    private val updatedApplicationMailingAddress = "test2@doordeck.com"
    private val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"
    private val updatedApplicationSupportContact = "https://www.doordeck2.com"
    private val updatedApplicationAppLink = "https://www.doordeck.com"
    private val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"
    private val updatedApplicationEmailPreferences = Platform.EmailPreferences(
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
    private val updatedApplicationAuthIssuer = "https://test.com"
    private val updatedApplicationCorsDomain = "https://test.com"
    private val updatedApplicationAuthKey = Platform.Ed25519Key(
        kty = "OKP",
        use = "sig",
        kid = uuid4().toString(),
        alg = "EdDSA",
        d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
        crv = "Ed25519",
        x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
    )

    @Test
    fun shouldTestPlatform() = runBlocking {
        // Initialize the resource
        val resource = PlatformResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

        // Create a new application
        resource.createApplication(application)

        // Retrieve the applications
        val applications = resource.listApplications()
        assertTrue { applications.isNotEmpty() }
        var actualApplication = applications.firstOrNull {
            it.name == application.name && it.companyName == application.companyName
        }
        assertNotNull(actualApplication)

        // Update the application name
        resource.updateApplicationName(actualApplication.applicationId, updatedApplicationName)

        // Update the application company name
        resource.updateApplicationCompanyName(actualApplication.applicationId, updatedApplicationCompanyName)

        // Update the application mailing address
        resource.updateApplicationMailingAddress(actualApplication.applicationId, updatedApplicationMailingAddress)

        // Update the application privacy policy
        resource.updateApplicationPrivacyPolicy(actualApplication.applicationId, updatedApplicationPrivacyPolicy)

        //Update the application support contact
        resource.updateApplicationSupportContact(actualApplication.applicationId, updatedApplicationSupportContact)

        // Update the application app link
        resource.updateApplicationAppLink(actualApplication.applicationId, updatedApplicationAppLink)

        // Update the application email preferences
        resource.updateApplicationEmailPreferences(actualApplication.applicationId, updatedApplicationEmailPreferences)

        // Update the application logo
        resource.updateApplicationLogoUrl(actualApplication.applicationId, updatedApplicationLogoUrl)

        // Add auth issuer
        resource.addAuthIssuer(actualApplication.applicationId, updatedApplicationAuthIssuer)

        // Add cors domain
        resource.addCorsDomain(actualApplication.applicationId, updatedApplicationCorsDomain)

        // Add auth key
        resource.addAuthKey(actualApplication.applicationId, updatedApplicationAuthKey)

        // Retrieve the application
        actualApplication = resource.getApplication(actualApplication.applicationId)
        assertEquals(updatedApplicationName, actualApplication.name)
        assertEquals(updatedApplicationCompanyName, actualApplication.companyName)
        assertEquals(updatedApplicationMailingAddress, actualApplication.mailingAddress)
        assertEquals(updatedApplicationPrivacyPolicy, actualApplication.privacyPolicy)
        assertEquals(updatedApplicationSupportContact, actualApplication.supportContact)
        assertEquals(updatedApplicationAppLink, actualApplication.appLink)
        assertEquals(updatedApplicationEmailPreferences.senderEmail, actualApplication.emailPreferences.senderEmail)
        assertEquals(updatedApplicationEmailPreferences.senderName, actualApplication.emailPreferences.senderName)
        assertEquals(updatedApplicationEmailPreferences.primaryColour, actualApplication.emailPreferences.primaryColour)
        assertEquals(updatedApplicationEmailPreferences.secondaryColour, actualApplication.emailPreferences.secondaryColour)
        assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, actualApplication.emailPreferences.onlySendEssentialEmails)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, actualApplication.emailPreferences.callToAction?.actionTarget)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, actualApplication.emailPreferences.callToAction?.headline)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, actualApplication.emailPreferences.callToAction?.actionText)
        assertEquals(updatedApplicationLogoUrl, actualApplication.logoUrl)
        assertTrue { actualApplication.authDomains.any { it.equals(updatedApplicationAuthIssuer, true) } }
        assertTrue { actualApplication.corsDomains.any { it.equals(updatedApplicationCorsDomain, true) } }

        // Delete auth issuer
        resource.deleteAuthIssuer(actualApplication.applicationId, updatedApplicationAuthIssuer)

        // Delete cors domain
        resource.removeCorsDomain(actualApplication.applicationId, updatedApplicationCorsDomain)

        // Add application owner
        resource.addApplicationOwner(actualApplication.applicationId, TEST_NEW_APPLICATION_OWNER)

        // Retrieve the application owner details
        val applicationOwnerDetails = resource.getApplicationOwnersDetails(actualApplication.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_NEW_APPLICATION_OWNER } }

        // Remove application owner
        resource.removeApplicationOwner(actualApplication.applicationId, TEST_NEW_APPLICATION_OWNER)

        // Generate logo upload url
        val url = resource.getLogoUploadUrl(actualApplication.applicationId, "image/png")
        assertTrue { url.uploadUrl.isNotEmpty() }

        // Delete the application
        resource.deleteApplication(actualApplication.applicationId)
    }
}