package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.randomCreateApplication
import com.doordeck.multiplatform.sdk.randomEmailCallToAction
import com.doordeck.multiplatform.sdk.randomEmailPreferences
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformTest {

    @Test
    fun shouldBuildCreateApplication() = runTest {
        // Given
        val createApplication = randomCreateApplication()

        // When
        val result = PlatformOperations.CreateApplication.Builder()
            .setName(createApplication.name)
            .setCompanyName(createApplication.companyName)
            .setMailingAddress(createApplication.mailingAddress)
            .setPrivacyPolicy(createApplication.privacyPolicy)
            .setSupportContact(createApplication.supportContact)
            .setAppLink(createApplication.appLink)
            .setEmailPreferences(createApplication.emailPreferences)
            .setLogoUrl(createApplication.logoUrl)
            .build()

        // Then
        assertEquals(createApplication, result)
    }

    @Test
    fun shouldBuildEmailPreferences() = runTest {
        // Given
        val emailPreferences = randomEmailPreferences()

        // When
        val result = PlatformOperations.EmailPreferences.Builder()
            .setSenderEmail(emailPreferences.senderEmail)
            .setSenderName(emailPreferences.senderName)
            .setPrimaryColour(emailPreferences.primaryColour)
            .setSecondaryColour(emailPreferences.secondaryColour)
            .setOnlySendEssentialEmails(emailPreferences.onlySendEssentialEmails)
            .setCallToAction(emailPreferences.callToAction)
            .build()

        // Then
        assertEquals(emailPreferences, result)
    }

    @Test
    fun shouldBuildEmailCallToAction() = runTest {
        // Given
        val emailCallToAction = randomEmailCallToAction()

        // When
        val result = PlatformOperations.EmailCallToAction.Builder()
            .setActionTarget(emailCallToAction.actionTarget)
            .setHeadline(emailCallToAction.headline)
            .setActionText(emailCallToAction.actionText)
            .build()

        // Then
        assertEquals(emailCallToAction, result)
    }
}