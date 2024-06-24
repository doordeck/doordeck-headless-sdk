package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformResourceTest : SystemTest() {

    // Initial application
    private val application = Platform.CreateApplication(
        name = uuid4().toString(),
        companyName = uuid4().toString(),
        mailingAddress = "test@doordeck.com",
        privacyPolicy = "https://www.doordeck.com/privacy",
        supportContact = "https://www.doordeck.com"
    )

    // Updated application
    private val updatedApplicationName = uuid4().toString()
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
    fun shouldTestPlatform() {
        // Create a new application
        sdk.platform().createApplication(application)

        // Retrieve the applications
        val applications = sdk.platform().listApplications()
        assertTrue { applications.isNotEmpty() }
        var actualApplication = applications.firstOrNull {
            it.name == application.name && it.companyName == application.companyName
        }
        assertNotNull(actualApplication)

        // Update the application name
        sdk.platform().updateApplicationName(actualApplication.applicationId, updatedApplicationName)

        // Update the application company name
        sdk.platform().updateApplicationCompanyName(actualApplication.applicationId, updatedApplicationCompanyName)

        // Update the application mailing address
        sdk.platform().updateApplicationMailingAddress(actualApplication.applicationId, updatedApplicationMailingAddress)

        // Update the application privacy policy
        sdk.platform().updateApplicationPrivacyPolicy(actualApplication.applicationId, updatedApplicationPrivacyPolicy)

        //Update the application support contact
        sdk.platform().updateApplicationSupportContact(actualApplication.applicationId, updatedApplicationSupportContact)

        // Update the application app link
        sdk.platform().updateApplicationAppLink(actualApplication.applicationId, updatedApplicationAppLink)

        // Update the application email preferences
        sdk.platform().updateApplicationEmailPreferences(actualApplication.applicationId, updatedApplicationEmailPreferences)

        // Update the application logo
        sdk.platform().updateApplicationLogoUrl(actualApplication.applicationId, updatedApplicationLogoUrl)

        // Add auth issuer
        sdk.platform().addAuthIssuer(actualApplication.applicationId, updatedApplicationAuthIssuer)

        // Add cors domain
        sdk.platform().addCorsDomain(actualApplication.applicationId, updatedApplicationCorsDomain)

        // Add auth key
        sdk.platform().addAuthKey(actualApplication.applicationId, updatedApplicationAuthKey)

        // Retrieve the application
        actualApplication = sdk.platform().getApplication(actualApplication.applicationId)
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
        sdk.platform().deleteAuthIssuer(actualApplication.applicationId, updatedApplicationAuthIssuer)

        // Delete cors domain
        sdk.platform().removeCorsDomain(actualApplication.applicationId, updatedApplicationCorsDomain)

        // Add application owner
        sdk.platform().addApplicationOwner(actualApplication.applicationId, TEST_NEW_APPLICATION_OWNER)

        // Retrieve the application owner details
        val applicationOwnerDetails = sdk.platform().getApplicationOwnersDetails(actualApplication.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_NEW_APPLICATION_OWNER } }

        // Remove application owner
        sdk.platform().removeApplicationOwner(actualApplication.applicationId, TEST_NEW_APPLICATION_OWNER)

        // Generate logo upload url
        val url = sdk.platform().getLogoUploadUrl(actualApplication.applicationId, "image/png")
        assertTrue { url.uploadUrl.isNotEmpty() }

        // Delete the application
        sdk.platform().deleteApplication(actualApplication.applicationId)
    }
}