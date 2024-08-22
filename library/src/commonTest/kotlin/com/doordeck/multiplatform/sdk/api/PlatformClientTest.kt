package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
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
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformClientTest : IntegrationTest() {

    @Test
    fun shouldTestPlatform() = runTest {
        // Given - shouldCreateApplication
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val newApplication = Platform.CreateApplication(
            name = "Test Application ${getPlatform()} ${uuid4()}",
            companyName = uuid4().toString(),
            mailingAddress = "test@doordeck.com",
            privacyPolicy = "https://www.doordeck.com/privacy",
            supportContact = "https://www.doordeck.com"
        )

        // When
        PLATFORM_CLIENT.createApplicationRequest(newApplication)

        // Then
        var application = PLATFORM_CLIENT.listApplicationsRequest().firstOrNull {
            it.name.equals(newApplication.name, true)
        }
        assertNotNull(application)

        // Given - shouldUpdateApplicationName
        val updatedApplicationName = "Test Application ${uuid4()}"

        // When
        PLATFORM_CLIENT.updateApplicationNameRequest(application.applicationId, updatedApplicationName)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationName, application.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = uuid4().toString()

        // When
        PLATFORM_CLIENT.updateApplicationCompanyNameRequest(application.applicationId, updatedApplicationCompanyName)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationCompanyName, application.companyName)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = "test2@doordeck.com"

        // When
        PLATFORM_CLIENT.updateApplicationMailingAddressRequest(application.applicationId, updatedApplicationMailingAddress)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"

        // When
        PLATFORM_CLIENT.updateApplicationPrivacyPolicyRequest(application.applicationId, updatedApplicationPrivacyPolicy)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = "https://www.doordeck2.com"

        // When
        PLATFORM_CLIENT.updateApplicationSupportContactRequest(application.applicationId, updatedApplicationSupportContact)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationSupportContact, application.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = "https://www.doordeck.com"

        // When
        PLATFORM_CLIENT.updateApplicationAppLinkRequest(application.applicationId, updatedApplicationAppLink)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
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
        PLATFORM_CLIENT.updateApplicationEmailPreferencesRequest(application.applicationId, updatedApplicationEmailPreferences)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
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
        PLATFORM_CLIENT.updateApplicationLogoUrlRequest(application.applicationId, updatedApplicationLogoUrl)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)

        // Given - shouldAddAuthIssuer
        val addedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_CLIENT.addAuthIssuerRequest(application.applicationId, addedApplicationAuthIssuer)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertNotNull(application.authDomains)
        assertTrue { application!!.authDomains!!.any { it.equals(addedApplicationAuthIssuer, true) } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_CLIENT.deleteAuthIssuerRequest(application.applicationId, removedApplicationAuthIssuer)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertNotNull(application.authDomains)
        assertFalse { application!!.authDomains!!.any { it.equals(removedApplicationAuthIssuer, true) } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_CLIENT.addCorsDomainRequest(application.applicationId, addedApplicationCorsDomain)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertNotNull(application.corsDomains)
        assertTrue { application!!.corsDomains!!.any { it.equals(addedApplicationCorsDomain, true) } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_CLIENT.removeCorsDomainRequest(application.applicationId, removedApplicationCorsDomain)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        assertNotNull(application.corsDomains)
        assertFalse { application!!.corsDomains!!.any { it.equals(removedApplicationCorsDomain, true) } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = Platform.Ed25519Key(
            kid = uuid4().toString(),
            use = "sig",
            alg = "EdDSA",
            d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        PLATFORM_CLIENT.addAuthKeyRequest(application.applicationId, ed25519Key)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        val actualEd25519Key = application.authKeys.values.firstOrNull {
            it.key == ed25519Key.kid
        }?.value as? Ed25519KeyResponse
        assertNotNull(actualEd25519Key)
        assertEquals(ed25519Key.kty, actualEd25519Key.kty)
        assertEquals(ed25519Key.use, actualEd25519Key.use)
        assertEquals(ed25519Key.kid, actualEd25519Key.kid)
        assertEquals(ed25519Key.alg, actualEd25519Key.alg)
        assertEquals(ed25519Key.crv, actualEd25519Key.crv)
        assertEquals(ed25519Key.x, actualEd25519Key.x)

        // Given - shouldAddRsaAuthKey
        val rsaKey = Platform.RsaKey(
            kid = uuid4().toString(),
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
        PLATFORM_CLIENT.addAuthKeyRequest(application.applicationId, rsaKey)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        val actualRsaKey = application.authKeys.values.firstOrNull {
            it.key == rsaKey.kid
        }?.value as? RsaKeyResponse
        assertNotNull(actualRsaKey)
        assertEquals(rsaKey.kty, actualRsaKey.kty)
        assertEquals(rsaKey.use, actualRsaKey.use)
        assertEquals(rsaKey.kid, actualRsaKey.kid)
        assertEquals(rsaKey.alg, actualRsaKey.alg)
        assertEquals(rsaKey.e, actualRsaKey.e)
        assertEquals(rsaKey.n, actualRsaKey.n)

        // Given - shouldAddEcAuthKey
        val ecKey = Platform.EcKey(
            kid = uuid4().toString(),
            use = "sig",
            alg = "ES256",
            d = "6BCNLf3Qdkle4DRLzqocD_58yIQLkaCT-EiWVThFf1s",
            crv = "secp256k1",
            x = "L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA",
            y = "ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"
        )

        // When
        PLATFORM_CLIENT.addAuthKeyRequest(application.applicationId, ecKey)

        // Then
        application = PLATFORM_CLIENT.getApplicationRequest(application.applicationId)
        val actualKeyEcKey = application.authKeys.values.firstOrNull {
            it.key == ecKey.kid
        }?.value as? EcKeyResponse
        assertNotNull(actualKeyEcKey)
        assertEquals(ecKey.kty, actualKeyEcKey.kty)
        assertEquals(ecKey.use, actualKeyEcKey.use)
        assertEquals(ecKey.kid, actualKeyEcKey.kid)
        assertEquals(ecKey.alg, actualKeyEcKey.alg)
        assertEquals(ecKey.crv, actualKeyEcKey.crv)
        assertEquals(ecKey.x, actualKeyEcKey.x)
        assertEquals(ecKey.y, actualKeyEcKey.y)

        // Given - shouldGetApplicationOwnersDetails
        // When
        var applicationOwnerDetails = PLATFORM_CLIENT.getApplicationOwnersDetailsRequest(application.applicationId)

        // Then
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PLATFORM_CLIENT.addApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails  = PLATFORM_CLIENT.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        PLATFORM_CLIENT.addApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails = PLATFORM_CLIENT.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertTrue { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        PLATFORM_CLIENT.removeApplicationOwnerRequest(application.applicationId, TEST_SUPPLEMENTARY_USER_ID)

        // Then
        applicationOwnerDetails = PLATFORM_CLIENT.getApplicationOwnersDetailsRequest(application.applicationId)
        assertTrue { applicationOwnerDetails.isNotEmpty() }
        assertFalse { applicationOwnerDetails.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val url = PLATFORM_CLIENT.getLogoUploadUrlRequest(application.applicationId, contentType)

        // Then
        assertTrue { url.uploadUrl.isNotEmpty() }

        // Given - shouldDeleteApplication
        // When
        PLATFORM_CLIENT.deleteApplicationRequest(application.applicationId)

        // Then
        val applications = PLATFORM_CLIENT.listApplicationsRequest()
        assertFalse { applications.any { it.applicationId == application.applicationId } }
    }
}