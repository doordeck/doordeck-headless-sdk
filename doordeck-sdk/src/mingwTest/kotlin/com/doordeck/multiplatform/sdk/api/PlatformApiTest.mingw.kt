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
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PlatformApiTest : CallbackTest() {

    @Test
    fun shouldTestPlatform() = runTest {
        runBlocking {
            // Given - shouldCreateApplication
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val newApplication = CreateApplicationData(
                name = "Test Application $platformType ${randomUuidString()}",
                companyName = randomString(),
                mailingAddress = randomEmail(),
                privacyPolicy = randomUrlString(),
                supportContact = randomUrlString()
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
            val updatedApplicationName = "Test Application $platformType ${randomUuidString()}"

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
            val updatedApplicationCompanyName = randomString()

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
            assertEquals(updatedApplicationCompanyName, applicationResponse.success.result.companyName)

            // Given - shouldUpdateApplicationMailingAddress
            val updatedApplicationMailingAddress = randomEmail()

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
            val updatedApplicationPrivacyPolicy = randomUrlString()

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
            val updatedApplicationSupportContact = randomUrlString()

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
            val updatedApplicationAppLink = randomUrlString()

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
                senderEmail = randomEmail(),
                senderName = "test",
                primaryColour = "#000000",
                secondaryColour = "#000000",
                onlySendEssentialEmails = true,
                callToAction = EmailCallToActionData(
                    actionTarget = randomUrlString(),
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
            val addApplicationAuthIssuer = randomUrlString()

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
            assertNotEquals(0, applicationResponse.success.result.authDomains.size)
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
            assertEquals(0, applicationResponse.success.result.authDomains.size)
            assertFalse { applicationResponse.success.result.authDomains.any { it == removedApplicationAuthIssuer } }

            // Given - shouldAddCorsDomain
            val addedApplicationCorsDomain = randomUrlString()

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
            assertNotEquals(0, applicationResponse.success.result.corsDomains.size)
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
            assertEquals(0, applicationResponse.success.result.corsDomains.size)
            assertFalse { applicationResponse.success.result.corsDomains.any { it == removedApplicationCorsDomain } }

            // Given - shouldAddEd25519AuthKey
            val ed25519Key = Ed25519KeyData(
                kid = randomUuidString(),
                use = "sig",
                alg = "EdDSA",
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
                kid = randomUuidString(),
                use = "sig",
                alg = "RS256",
                e = "AQAB",
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
                kid = randomUuidString(),
                use = "sig",
                alg = "ES256",
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
}