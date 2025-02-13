package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class PlatformClientTest : IntegrationTest() {

    @Test
    fun shouldTestPlatform() = runTest {
        // Given - shouldCreateApplication
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val newApplication = Platform.CreateApplication(
            name = "Test Application ${getPlatform()} ${Uuid.random()}",
            companyName = Uuid.random().toString(),
            mailingAddress = "test@doordeck.com",
            privacyPolicy = "https://www.doordeck.com/privacy",
            supportContact = "https://www.doordeck.com"
        )

        // When
        PlatformClient.createApplicationRequest(newApplication)

        // Then
        var application = PlatformClient.listApplicationsRequest().firstOrNull {
            it.name.equals(newApplication.name, true)
        }
        assertNotNull(application)

        // Given - shouldUpdateApplicationName
        val updatedApplicationName = "Test Application ${Uuid.random()}"

        // When
        PlatformClient.updateApplicationNameRequest(application.applicationId, updatedApplicationName)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationName, application.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = Uuid.random().toString()

        // When
        PlatformClient.updateApplicationCompanyNameRequest(application.applicationId, updatedApplicationCompanyName)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationCompanyName, application.companyName)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = "test2@doordeck.com"

        // When
        PlatformClient.updateApplicationMailingAddressRequest(application.applicationId, updatedApplicationMailingAddress)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"

        // When
        PlatformClient.updateApplicationPrivacyPolicyRequest(application.applicationId, updatedApplicationPrivacyPolicy)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = "https://www.doordeck2.com"

        // When
        PlatformClient.updateApplicationSupportContactRequest(application.applicationId, updatedApplicationSupportContact)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationSupportContact, application.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = "https://www.doordeck.com"

        // When
        PlatformClient.updateApplicationAppLinkRequest(application.applicationId, updatedApplicationAppLink)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationAppLink, application.appLink)

        // Given - shouldUpdateApplicationEmailPreferences
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
        PlatformClient.updateApplicationEmailPreferencesRequest(application.applicationId, updatedApplicationEmailPreferences)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
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
        PlatformClient.updateApplicationLogoUrlRequest(application.applicationId, updatedApplicationLogoUrl)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)

        // Given - shouldAddAuthIssuer
        val addedApplicationAuthIssuer = "https://test.com"

        // When
        PlatformClient.addAuthIssuerRequest(application.applicationId, addedApplicationAuthIssuer)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertNotNull(application.authDomains)
        assertTrue { application!!.authDomains!!.any { it.equals(addedApplicationAuthIssuer, true) } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = "https://test.com"

        // When
        PlatformClient.deleteAuthIssuerRequest(application.applicationId, removedApplicationAuthIssuer)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertNotNull(application.authDomains)
        assertFalse { application!!.authDomains!!.any { it.equals(removedApplicationAuthIssuer, true) } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = "https://test.com"

        // When
        PlatformClient.addCorsDomainRequest(application.applicationId, addedApplicationCorsDomain)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertNotNull(application.corsDomains)
        assertTrue { application!!.corsDomains!!.any { it.equals(addedApplicationCorsDomain, true) } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = "https://test.com"

        // When
        PlatformClient.removeCorsDomainRequest(application.applicationId, removedApplicationCorsDomain)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
        assertNotNull(application.corsDomains)
        assertFalse { application!!.corsDomains!!.any { it.equals(removedApplicationCorsDomain, true) } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = Platform.Ed25519Key(
            kid = Uuid.random().toString(),
            use = "sig",
            alg = "EdDSA",
            d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        PlatformClient.addAuthKeyRequest(application.applicationId, ed25519Key)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
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
        val rsaKey = Platform.RsaKey(
            kid = Uuid.random().toString(),
            use = "sig",
            alg = "RS256",
            p = "_86RLMHT_HrvYGzRLA8K2gEzUh3UADy5xMatYY7nh6KLBWY2USMn5nD93adcT0u6FKE6cEBjNRhWpq_11ai4UuJmLI88Cpf3HoN-eVBqpf0aULsLMNPJK88MWNRvLR5_54-NrAqZOUDLGi32te-CWd4Uq4ly6D_MS_p7G1z7zdk",
            q = "7Sjz_MDitX1csDsqr6mJBGd9p4hzmwKRxjbdJ02i1oh2F2pio70x9qVBMKIybQgjkPYK7W4dPbGkDu95ld0P2mdwfPsFCInxJH9zHhskHQkK3kA7-euMTpIN2zmvcCndZpRceubCL7xzavWFsoEDPENB8w_Wy5zSih2O9Z9Hk4E",
            d = "JsKBgWqM43I10aVLXYApmCBPh_FGb7SEtvCS0vh6BFDj_0KgJC3o1tZjgeBZZe-IHk6xqDIS89K1umZnzYPGeSuQNtoWiG8OtxR1GdIimfdrgZF5lIjUNRS0HOsIRWJcHlPidSCUcBOlj1hQ6nx0LPuguRCnb18Z08Oh39cSY9ajA8wPJLudTiZ91A4UTTNnrYKRNoW-9DQZeY0_z5w9EP4diFxTCbz7JSVSG8EjCs7hTLQi2ktJc9sy8VUTwZethCCH7bn5t3ylUGEgdHaWWqq92pkOLqbSkvBYU4nTt-oh7pJXvAfg74BJZahBrwzLoWj_lA-LPQ8F24488caMAQ",
            e = "AQAB",
            qi = "vWorZMpENBF1nSW-WjCPI-vbvOypfaVGd-Kf7HkNb1LLIx3m4UsoiWFBagqjqO9EdF5vNDxqr6PQYSjDyICm8kDf9j1o88-KK6JgIdZlWLjPq2CN2KUDiMz5aas4gKkFuJmSvDxOQhRurXA_tvsPiqt7Ad8bk_yGKmSuPDAng4w",
            dp = "mUPIk4pmWqXFen54LO-uTsPdXdvlQ2ce3pkzFHqsmgV3SfrdnGt14onccMtvcUsr6GRZQRwy1IMKl8BhiGwYVAC1uwjurmIye6PJSSI3Y9BrzebjY5PgulDJUwekvOHDPJg0B9opx7XceokDgipIbVO0CrrFkAV5gCRJUjG55LE",
            dq = "S84PTvcIgCJ2Ag6nckaqeTHrRCWlbiLAHa9juTBjoFc2B_4FUXkkA0aHM9hkbd1wIOHEVGgiCJpDalK5dmGWs6Tkm85QqY4N-jCSx0i9nlpJkwjNIvFbg7HDpBMoNJ3tGuDJPq-L2l5ONh4MgiYitpx49AxYB_U0htkz3ObwpgE",
            n = "7PsoesJRZIBUKN3AlhGCJPflQd08U9n9EsdeQS70Dbr8ce-aIpVjNAWxPaNdddYQJBUcj6wy3jKe8Vzu04tCrfafjBR6Db8pZGhTEjRQP6wQKxuo7GbnqUeCgrbT2cE5W-zRJGX4ImSuaoOyNXuDjpmDA4stWqXrMeDZIUqXcFpcOTMfi-cbSZ0A4fgX43bTCef-noprBtBAig-kaz3W7NFcBSkA3faUdlaJ6Bj9DHpqkQYpUR-MuqmAyGUOli0JY0x6QhoVrNGFQ1ejivbvMH3lkuhrJwJlJEt0wD3JoH0Q03XBKcJSBeUl6pzZV0oD2lNrQIrQdsQ1_0yLUEVVWQ"
        )

        // When
        PlatformClient.addAuthKeyRequest(application.applicationId, rsaKey)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
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
        val ecKey = Platform.EcKey(
            kid = Uuid.random().toString(),
            use = "sig",
            alg = "ES256",
            d = "6BCNLf3Qdkle4DRLzqocD_58yIQLkaCT-EiWVThFf1s",
            crv = "secp256k1",
            x = "L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA",
            y = "ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"
        )

        // When
        PlatformClient.addAuthKeyRequest(application.applicationId, ecKey)

        // Then
        application = PlatformClient.getApplicationRequest(application.applicationId)
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
        var applicationOwnerDetails = PlatformClient.getApplicationOwnersDetailsRequest(application.applicationId)

        // Then
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PlatformClient.addApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails  = PlatformClient.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PlatformClient.addApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails = PlatformClient.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        PlatformClient.removeApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails = PlatformClient.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertFalse { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val url = PlatformClient.getLogoUploadUrlRequest(application.applicationId, contentType)

        // Then
        assertTrue { url.uploadUrl.isNotEmpty() }

        // Given - shouldDeleteApplication
        // When
        PlatformClient.deleteApplicationRequest(application.applicationId)

        // Then
        val applications = PlatformClient.listApplicationsRequest()
        assertFalse { applications.any { it.applicationId == application.applicationId } }
    }
}