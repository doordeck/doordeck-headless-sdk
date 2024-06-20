package com.doordeck.sdk.api

import com.doordeck.sdk.KDoordeckFactory
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.model.Platform
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformResourceTest {

    // Initial application
    private val application = Platform.CreateApplication(
        name = "TEST",
        companyName = "TEST",
        mailingAddress = "TEST@doordeck.com",
        privacyPolicy = "https://www.doordeck.com/privacy",
        supportContact = "https://www.doordeck.com"
    )

    // Updated application
    private val updatedApplicationName = "TEST2"
    private val updatedApplicationCompanyName = "TEST2"
    private val updatedApplicationMailingAddress = "TEST2@doordeck.com"
    private val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"
    private val updatedApplicationSupportContact = "https://www.doordeck2.com"
    private val updatedApplicationAppLink = "https://www.doordeck.com"
    private val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/TEST"
    private val updatedApplicationEmailPreferences = Platform.EmailPreferences(
        senderEmail = "TEST@TEST.com",
        senderName = "TEST",
        primaryColour = "#640b74",
        secondaryColour = "#000000",
        callToAction = Platform.EmailCallToAction(
            actionTarget = "TEST",
            headline = "TEST",
            actionText = "TEST"
        )
    )
    private val updatedApplicationAuthIssuer = "https://TEST.com"
    private val updatedApplicationCorsDomain = "https://TEST.com"

    @Test
    fun shouldTestPlatform() {
        // Initialize the SDK
        val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, "")

        // Create a new application
        sdk.platform().createApplication(application)

        // Retrieve the applications
        val applications = sdk.platform().listApplications()
        assertTrue { applications.isNotEmpty() }
        var actualApplication = applications.firstOrNull {
            it.name == application.name && it.companyName == application.companyName && it.mailingAddress == application.mailingAddress
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

        // Retrieve the application
        actualApplication = sdk.platform().getApplication(actualApplication.applicationId)
        assertEquals(updatedApplicationName, actualApplication.name)
        assertEquals(updatedApplicationCompanyName, actualApplication.companyName)
        assertEquals(updatedApplicationMailingAddress, actualApplication.mailingAddress)
        assertEquals(updatedApplicationPrivacyPolicy, actualApplication.privacyPolicy)
        assertEquals(updatedApplicationSupportContact, actualApplication.supportContact)
        assertEquals(updatedApplicationAppLink, actualApplication.appLink)
        //assertEquals(updatedApplicationEmailPreferences.senderEmail, actualApplication.emailPreferences.senderEmail)
        //assertEquals(updatedApplicationEmailPreferences.senderName, actualApplication.emailPreferences.senderName)
        //assertEquals(updatedApplicationEmailPreferences.primaryColour, actualApplication.emailPreferences.primaryColour)
        //assertEquals(updatedApplicationEmailPreferences.secondaryColour, actualApplication.emailPreferences.secondaryColour)
        //assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, actualApplication.emailPreferences.callToAction?.actionTarget)
        //assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, actualApplication.emailPreferences.callToAction?.headline)
        //assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, actualApplication.emailPreferences.callToAction?.actionText)
        assertEquals(updatedApplicationLogoUrl, actualApplication.logoUrl)
        assertTrue { actualApplication.authDomains.any { it.equals(updatedApplicationAuthIssuer, true) } }
        assertTrue { actualApplication.corsDomains.any { it.equals(updatedApplicationCorsDomain, true) } }

        // Delete auth issuer
        sdk.platform().deleteAuthIssuer(actualApplication.applicationId, updatedApplicationAuthIssuer)

        // Delete cors domain
        sdk.platform().removeCorsDomain(actualApplication.applicationId, updatedApplicationCorsDomain)

        // Delete the application
        sdk.platform().deleteApplication(actualApplication.applicationId)
    }
}