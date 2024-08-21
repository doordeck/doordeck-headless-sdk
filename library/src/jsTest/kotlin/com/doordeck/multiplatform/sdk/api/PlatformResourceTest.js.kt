package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.api.model.Platform
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.getPlatform
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformResourceTest : IntegrationTest() {

    /*@Test
    fun shouldTestPlatform() = runTest {
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
        shouldAddEd25519AuthKey(applicationId)
        shouldAddRsaAuthKey(applicationId)
        shouldAddEcAuthKey(applicationId)
        shouldGetApplicationOwnersDetails(applicationId)
        shouldAddApplicationOwner(applicationId)
        shouldRemoveApplicationOwner(applicationId)
        shouldGetLogoUploadUrl(applicationId)
        shouldDeleteApplication(applicationId)
    }

    private fun shouldCreateApplication(): String {
        // Given
        val newApplication = Platform.CreateApplication(
            name = "Test Application ${getPlatform()} ${uuid4()}",
            companyName = uuid4().toString(),
            mailingAddress = "test@doordeck.com",
            privacyPolicy = "https://www.doordeck.com/privacy",
            supportContact = "https://www.doordeck.com"
        )

        // When
        PLATFORM_RESOURCE.createApplication(newApplication).await()

        // Then
        val application = shouldListApplications().firstOrNull { it.name.equals(newApplication.name, true) }
        assertNotNull(application)

        return application.applicationId
    }

    private fun shouldUpdateApplicationName(applicationId: String) = runTest {
        // Given
        val updatedApplicationName = "Test Application ${uuid4()}"

        // When
        PLATFORM_RESOURCE.updateApplicationName(applicationId, updatedApplicationName).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationName, application.name)
    }

    private fun shouldUpdateApplicationCompanyName(applicationId: String) = runTest {
        // Given
        val updatedApplicationCompanyName = uuid4().toString()

        // When
        PLATFORM_RESOURCE.updateApplicationCompanyName(applicationId, updatedApplicationCompanyName).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationCompanyName, application.companyName)
    }

    private fun shouldUpdateApplicationMailingAddress(applicationId: String) = runTest {
        // Given
        val updatedApplicationMailingAddress = "test2@doordeck.com"

        // When
        PLATFORM_RESOURCE.updateApplicationMailingAddress(applicationId, updatedApplicationMailingAddress).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationMailingAddress, application.mailingAddress)
    }

    private fun shouldUpdateApplicationPrivacyPolicy(applicationId: String) = runTest {
        // Given
        val updatedApplicationPrivacyPolicy = "https://www.doordeck.com/privacy2"

        // When
        PLATFORM_RESOURCE.updateApplicationPrivacyPolicy(applicationId, updatedApplicationPrivacyPolicy).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationPrivacyPolicy, application.privacyPolicy)
    }

    private fun shouldUpdateApplicationSupportContact(applicationId: String) = runTest {
        // Given
        val updatedApplicationSupportContact = "https://www.doordeck2.com"

        // When
        PLATFORM_RESOURCE.updateApplicationSupportContact(applicationId, updatedApplicationSupportContact).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationSupportContact, application.supportContact)
    }

    private fun shouldUpdateApplicationAppLink(applicationId: String) = runTest {
        // Given
        val updatedApplicationAppLink = "https://www.doordeck.com"

        // When
        PLATFORM_RESOURCE.updateApplicationAppLink(applicationId, updatedApplicationAppLink).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationAppLink, application.appLink)
    }

    private fun shouldUpdateApplicationEmailPreferences(applicationId: String) = runTest {
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
        PLATFORM_RESOURCE.updateApplicationEmailPreferences(applicationId, updatedApplicationEmailPreferences).await()

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

    private fun shouldUpdateApplicationLogoUrl(applicationId: String) = runTest {
        // Given
        val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"

        // When
        PLATFORM_RESOURCE.updateApplicationLogoUrl(applicationId, updatedApplicationLogoUrl).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertEquals(updatedApplicationLogoUrl, application.logoUrl)
    }

    private fun shouldAddAuthIssuer(applicationId: String) = runTest {
        // Given
        val updatedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_RESOURCE.addAuthIssuer(applicationId, updatedApplicationAuthIssuer).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertNotNull(application.authDomains)
        assertTrue { application.authDomains!!.any { it.equals(updatedApplicationAuthIssuer, true) } }
    }

    private fun shouldDeleteAuthIssuer(applicationId: String) = runTest {
        // Given
        val updatedApplicationAuthIssuer = "https://test.com"

        // When
        PLATFORM_RESOURCE.deleteAuthIssuer(applicationId, updatedApplicationAuthIssuer).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertNotNull(application.authDomains)
        assertFalse { application.authDomains!!.any { it.equals(updatedApplicationAuthIssuer, true) } }
    }

    private fun shouldAddCorsDomain(applicationId: String) = runTest {
        // Given
        val updatedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_RESOURCE.addCorsDomain(applicationId, updatedApplicationCorsDomain).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertNotNull(application.corsDomains)
        assertTrue { application.corsDomains!!.any { it.equals(updatedApplicationCorsDomain, true) } }
    }

    private fun shouldDeleteCorsDomain(applicationId: String) = runTest {
        // Given
        val updatedApplicationCorsDomain = "https://test.com"

        // When
        PLATFORM_RESOURCE.removeCorsDomain(applicationId, updatedApplicationCorsDomain).await()

        // Then
        val application = shouldGetApplication(applicationId)
        assertNotNull(application.corsDomains)
        assertFalse { application.corsDomains!!.any { it.equals(updatedApplicationCorsDomain, true) } }
    }

    private fun shouldAddEd25519AuthKey(applicationId: String) = runTest {
        // Given
        val key = Platform.Ed25519Key(
            kid = uuid4().toString(),
            use = "sig",
            alg = "EdDSA",
            d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        PLATFORM_RESOURCE.addAuthKey(applicationId, key).await()

        // Then
        val application = shouldGetApplication(applicationId)
        val actualKey = application.authKeys.values.firstOrNull {
            it.key == key.kid
        }?.value as? Ed25519KeyResponse
        assertNotNull(actualKey)
        assertEquals(key.kty, actualKey.kty)
        assertEquals(key.use, actualKey.use)
        assertEquals(key.kid, actualKey.kid)
        assertEquals(key.alg, actualKey.alg)
        assertEquals(key.crv, actualKey.crv)
        assertEquals(key.x, actualKey.x)
    }

    private fun shouldAddRsaAuthKey(applicationId: String) = runTest {
        // Given
        val key = Platform.RsaKey(
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
        PLATFORM_RESOURCE.addAuthKey(applicationId, key).await()

        // Then
        val application = shouldGetApplication(applicationId)
        val actualKey = application.authKeys.values.firstOrNull {
            it.key == key.kid
        }?.value as? RsaKeyResponse
        assertNotNull(actualKey)
        assertEquals(key.kty, actualKey.kty)
        assertEquals(key.use, actualKey.use)
        assertEquals(key.kid, actualKey.kid)
        assertEquals(key.alg, actualKey.alg)
        assertEquals(key.e, actualKey.e)
        assertEquals(key.n, actualKey.n)
    }

    private fun shouldAddEcAuthKey(applicationId: String) = runTest {
        // Given
        val key = Platform.EcKey(
            kid = uuid4().toString(),
            use = "sig",
            alg = "ES256",
            d = "6BCNLf3Qdkle4DRLzqocD_58yIQLkaCT-EiWVThFf1s",
            crv = "secp256k1",
            x = "L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA",
            y = "ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"
        )

        // When
        PLATFORM_RESOURCE.addAuthKey(applicationId, key).await()

        // Then
        val application = shouldGetApplication(applicationId)
        val actualKey = application.authKeys.values.firstOrNull {
            it.key == key.kid
        }?.value as? EcKeyResponse
        assertNotNull(actualKey)
        assertEquals(key.kty, actualKey.kty)
        assertEquals(key.use, actualKey.use)
        assertEquals(key.kid, actualKey.kid)
        assertEquals(key.alg, actualKey.alg)
        assertEquals(key.crv, actualKey.crv)
        assertEquals(key.x, actualKey.x)
        assertEquals(key.y, actualKey.y)
    }

    private fun shouldGetApplicationOwnersDetails(applicationId: String): Array<ApplicationOwnerDetailsResponse> {
        // When
        val result = PLATFORM_RESOURCE.getApplicationOwnersDetails(applicationId).await()

        // Then
        assertTrue { result.isNotEmpty() }
        assertTrue { result.any { it.userId == TEST_MAIN_USER_ID } }

        return result
    }

    private fun shouldAddApplicationOwner(applicationId: String) = runTest {
        // When
        PLATFORM_RESOURCE.addApplicationOwner(applicationId, TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        val result = shouldGetApplicationOwnersDetails(applicationId)
        assertTrue { result.isNotEmpty() }
        assertTrue { result.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }
    }

    private fun shouldRemoveApplicationOwner(applicationId: String) = runTest {
        // When
        PLATFORM_RESOURCE.removeApplicationOwner(applicationId, TEST_SUPPLEMENTARY_USER_ID).await()

        // Then
        val result = shouldGetApplicationOwnersDetails(applicationId)
        assertTrue { result.isNotEmpty() }
        assertFalse { result.any { it.userId == TEST_SUPPLEMENTARY_USER_ID } }
    }

    private fun shouldGetLogoUploadUrl(applicationId: String) = runTest {
        // Given
        val contentType = "image/png"

        // When
        val url = PLATFORM_RESOURCE.getLogoUploadUrl(applicationId, contentType).await()

        // Then
        assertTrue { url.uploadUrl.isNotEmpty() }
    }

    private fun shouldListApplications(): Array<ApplicationResponse> {
        return PLATFORM_RESOURCE.listApplications().await()
    }

    private fun shouldGetApplication(applicationId: String): ApplicationResponse {
        return PLATFORM_RESOURCE.getApplication(applicationId).await()
    }

    private fun shouldDeleteApplication(applicationId: String) = runTest {
        // When
        PLATFORM_RESOURCE.deleteApplication(applicationId).await()

        // Then
        val application = shouldListApplications()
        assertFalse { application.any { it.applicationId == applicationId } }
    }*/
}