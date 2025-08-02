package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.ApplicationIdData
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.AuthIssuerData
import com.doordeck.multiplatform.sdk.model.data.CorsDomainData
import com.doordeck.multiplatform.sdk.model.data.CreateApplicationData
import com.doordeck.multiplatform.sdk.model.data.EcKeyData
import com.doordeck.multiplatform.sdk.model.data.Ed25519KeyData
import com.doordeck.multiplatform.sdk.model.data.EmailCallToActionData
import com.doordeck.multiplatform.sdk.model.data.EmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.RsaKeyData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEd25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRsaKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUri
import com.doordeck.multiplatform.sdk.randomUrl
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class PlatformApiTest : CallbackTest() {

    @Test
    fun shouldTestPlatform() = runTest {
        // Given - shouldCreateApplication
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val newApplication = CreateApplicationData(
            name = "Test Application $platformType ${Uuid.random()}",
            companyName = Uuid.random().toString(),
            mailingAddress = "test@doordeck.com",
            privacyPolicy = randomUrl(),
            supportContact = randomUrl()
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.createApplication(
                data = newApplication.toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val applicationsResponse = callbackApiCall<ResultData<List<BasicApplicationResponse>>> {
            PlatformApi.listApplications(
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationsResponse.success)
        assertNotNull(applicationsResponse.success.result)

        val application: BasicApplicationResponse = applicationsResponse.success.result.first {
            it.name.equals(newApplication.name, true)
        }
        assertNotNull(application)
        assertEquals(newApplication.name, application.name)
        assertEquals(newApplication.companyName, application.companyName)
        assertEquals(newApplication.mailingAddress, application.mailingAddress)
        assertEquals(newApplication.privacyPolicy, application.privacyPolicy)
        assertEquals(newApplication.supportContact, application.supportContact)

        // Given - shouldUpdateApplicationName
        val updatedApplicationName = "Test Application $platformType ${Uuid.random()}"

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationName(
                data = UpdateApplicationNameData(application.applicationId, updatedApplicationName).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        var applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationName, applicationResponse.success.result.name)

        // Given - shouldUpdateApplicationCompanyName
        val updatedApplicationCompanyName = Uuid.random().toString()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationCompanyName(
                data = UpdateApplicationCompanyNameData(application.applicationId, updatedApplicationCompanyName).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationCompanyName, applicationResponse.success.result.name)

        // Given - shouldUpdateApplicationMailingAddress
        val updatedApplicationMailingAddress = "test2@doordeck.com"

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationMailingAddress(
                data = UpdateApplicationMailingAddressData(application.applicationId, updatedApplicationMailingAddress).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationMailingAddress, applicationResponse.success.result.mailingAddress)

        // Given - shouldUpdateApplicationPrivacyPolicy
        val updatedApplicationPrivacyPolicy = randomUri()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationPrivacyPolicy(
                data = UpdateApplicationPrivacyPolicyData(application.applicationId, updatedApplicationPrivacyPolicy).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationPrivacyPolicy, applicationResponse.success.result.privacyPolicy)

        // Given - shouldUpdateApplicationSupportContact
        val updatedApplicationSupportContact = randomUri()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationSupportContact(
                data = UpdateApplicationSupportContactData(application.applicationId, updatedApplicationSupportContact).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationSupportContact, applicationResponse.success.result.supportContact)

        // Given - shouldUpdateApplicationAppLink
        val updatedApplicationAppLink = randomUri()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationAppLink(
                data = UpdateApplicationAppLinkData(application.applicationId, updatedApplicationAppLink).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationAppLink, applicationResponse.success.result.appLink)

        // Given - shouldUpdateApplicationEmailPreferences
        val updatedApplicationEmailPreferences = EmailPreferencesData(
            senderEmail = "test@test.com",
            senderName = "test",
            primaryColour = "#000000",
            secondaryColour = "#000000",
            onlySendEssentialEmails = true,
            callToAction = EmailCallToActionData(
                actionTarget = randomUri(),
                headline = "test",
                actionText = "test"
            )
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationEmailPreferences(
                data = UpdateApplicationEmailPreferencesData(application.applicationId, updatedApplicationEmailPreferences).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationEmailPreferences.senderEmail, applicationResponse.success.result.emailPreferences.senderEmail)
        assertEquals(updatedApplicationEmailPreferences.senderName, applicationResponse.success.result.emailPreferences.senderName)
        assertEquals(updatedApplicationEmailPreferences.primaryColour, applicationResponse.success.result.emailPreferences.primaryColour)
        assertEquals(updatedApplicationEmailPreferences.secondaryColour, applicationResponse.success.result.emailPreferences.secondaryColour)
        assertEquals(updatedApplicationEmailPreferences.onlySendEssentialEmails, applicationResponse.success.result.emailPreferences.onlySendEssentialEmails)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionTarget, applicationResponse.success.result.emailPreferences.callToAction?.actionTarget)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.headline, applicationResponse.success.result.emailPreferences.callToAction?.headline)
        assertEquals(updatedApplicationEmailPreferences.callToAction?.actionText, applicationResponse.success.result.emailPreferences.callToAction?.actionText)

        // Given - shouldUpdateApplicationLogoUrl
        val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.updateApplicationLogoUrl(
                data = UpdateApplicationLogoUrlData(application.applicationId, updatedApplicationLogoUrl).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertEquals(updatedApplicationLogoUrl, applicationResponse.success.result.logoUrl)

        // Given - shouldAddAuthIssuer
        val addApplicationAuthIssuer = randomUri()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addAuthIssuer(
                data = AuthIssuerData(application.applicationId, addApplicationAuthIssuer).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertNotNull(applicationResponse.success.result.authDomains)
        assertTrue { applicationResponse.success.result.authDomains.any { it == addApplicationAuthIssuer } }

        // Given - shouldDeleteAuthIssuer
        val removedApplicationAuthIssuer = addApplicationAuthIssuer

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.deleteAuthIssuer(
                data = AuthIssuerData(application.applicationId, removedApplicationAuthIssuer).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertNotNull(applicationResponse.success.result.authDomains)
        assertFalse { applicationResponse.success.result.authDomains.any { it == removedApplicationAuthIssuer } }

        // Given - shouldAddCorsDomain
        val addedApplicationCorsDomain = randomUri()

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addCorsDomain(
                data = CorsDomainData(application.applicationId, addedApplicationCorsDomain).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertNotNull(applicationResponse.success.result.corsDomains)
        assertTrue { applicationResponse.success.result.corsDomains.any { it == addedApplicationCorsDomain } }

        // Given - shouldDeleteCorsDomain
        val removedApplicationCorsDomain = addedApplicationCorsDomain

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.removeCorsDomain(
                data = CorsDomainData(application.applicationId, removedApplicationCorsDomain).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)
        assertNotNull(applicationResponse.success.result.corsDomains)
        assertFalse { applicationResponse.success.result.corsDomains.any { it == removedApplicationCorsDomain } }

        // Given - shouldAddEd25519AuthKey
        val ed25519Key = Ed25519KeyData(
            kid = Uuid.random().toString(),
            use = "sig",
            alg = "EdDSA",
            d = "NUTwZGmCu7zQ5tNRXqBGBnZCTYqDci3GMlLCg8qw0J4",
            crv = "Ed25519",
            x = "vG0Xdtks-CANqLj2wYw7c72wd848QponNTyKr_xA_cg"
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addAuthKey(
                data = AddAuthKeyData(application.applicationId, ed25519Key).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)

        val actualEd25519Key = applicationResponse.success.result.authKeys.entries.firstOrNull {
            it.key == ed25519Key.kid
        }?.value as? BasicEd25519KeyResponse
        assertNotNull(actualEd25519Key)
        assertEquals(ed25519Key.use, actualEd25519Key.use)
        assertEquals(ed25519Key.kid, actualEd25519Key.kid)
        assertEquals(ed25519Key.alg, actualEd25519Key.alg)
        assertEquals(ed25519Key.crv, actualEd25519Key.crv)
        assertEquals(ed25519Key.x, actualEd25519Key.x)

        // Given - shouldAddRsaAuthKey
        val rsaKey = RsaKeyData(
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
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addAuthKey(
                data = AddAuthKeyData(application.applicationId, rsaKey).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)

        val actualRsaKey = applicationResponse.success.result.authKeys.entries.firstOrNull {
            it.key == rsaKey.kid
        }?.value as? BasicRsaKeyResponse
        assertNotNull(actualRsaKey)
        assertEquals(rsaKey.use, actualRsaKey.use)
        assertEquals(rsaKey.kid, actualRsaKey.kid)
        assertEquals(rsaKey.alg, actualRsaKey.alg)
        assertEquals(rsaKey.e, actualRsaKey.e)
        assertEquals(rsaKey.n, actualRsaKey.n)

        // Given - shouldAddEcAuthKey
        val ecKey = EcKeyData(
            kid = Uuid.random().toString(),
            use = "sig",
            alg = "ES256",
            d = "6BCNLf3Qdkle4DRLzqocD_58yIQLkaCT-EiWVThFf1s",
            crv = "secp256k1",
            x = "L9Oy_4lde8GqwXyF9rRtkkTOr9iZF65S02JToBFzuPA",
            y = "ac69MlrUIJQXlSEsp1lBG6erAZjBwSA6M3dT7pBOtMU"
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addAuthKey(
                data = AddAuthKeyData(application.applicationId, ecKey).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationResponse = callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationResponse.success)
        assertNotNull(applicationResponse.success.result)

        val actualKeyEcKey = applicationResponse.success.result.authKeys.entries.firstOrNull {
            it.key == ecKey.kid
        }?.value as? BasicEcKeyResponse
        assertNotNull(actualKeyEcKey)
        assertEquals(ecKey.use, actualKeyEcKey.use)
        assertEquals(ecKey.kid, actualKeyEcKey.kid)
        assertEquals(ecKey.alg, actualKeyEcKey.alg)
        assertEquals(ecKey.crv, actualKeyEcKey.crv)
        assertEquals(ecKey.x, actualKeyEcKey.x)
        assertEquals(ecKey.y, actualKeyEcKey.y)

        // Given - shouldGetApplicationOwnersDetails
        // When
        var applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
            PlatformApi.getApplicationOwnersDetails(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(applicationOwnerDetailsResponse.success)
        assertNotNull(applicationOwnerDetailsResponse.success.result)
        assertTrue { applicationOwnerDetailsResponse.success.result.isNotEmpty() }
        assertTrue { applicationOwnerDetailsResponse.success.result.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

        // Given - shouldAddApplicationOwner
        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.addApplicationOwner(
                data = ApplicationOwnerData(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
            PlatformApi.getApplicationOwnersDetails(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationOwnerDetailsResponse.success)
        assertNotNull(applicationOwnerDetailsResponse.success.result)
        assertTrue { applicationOwnerDetailsResponse.success.result.isNotEmpty() }
        assertTrue { applicationOwnerDetailsResponse.success.result.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldRemoveApplicationOwner
        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.removeApplicationOwner(
                data = ApplicationOwnerData(application.applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
            PlatformApi.getApplicationOwnersDetails(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(applicationOwnerDetailsResponse.success)
        assertNotNull(applicationOwnerDetailsResponse.success.result)
        assertTrue { applicationOwnerDetailsResponse.success.result.isNotEmpty() }
        assertFalse { applicationOwnerDetailsResponse.success.result.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

        // Given - shouldGetLogoUploadUrl
        val contentType = "image/png"

        // When
        val uploadUrlResponse = callbackApiCall<ResultData<BasicGetLogoUploadUrlResponse>> {
            PlatformApi.getLogoUploadUrl(
                data = GetLogoUploadUrlData(application.applicationId, contentType).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(uploadUrlResponse.success)
        assertNotNull(uploadUrlResponse.success.result)
        assertTrue { uploadUrlResponse.success.result.uploadUrl.contains("doordeck-upload") }

        // Given - shouldDeleteApplication
        // When
        callbackApiCall<ResultData<Unit>> {
            PlatformApi.deleteApplication(
                data = ApplicationIdData(application.applicationId).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val applications = callbackApiCall<ResultData<List<BasicApplicationResponse>>> {
            PlatformApi.listApplications(
                callback = staticCFunction(::testCallback)
            )
        }

        assertNotNull(applications.success)
        assertNotNull(applications.success.result)
        assertFalse { applications.success.result.any { it.applicationId == application.applicationId } }
    }
}