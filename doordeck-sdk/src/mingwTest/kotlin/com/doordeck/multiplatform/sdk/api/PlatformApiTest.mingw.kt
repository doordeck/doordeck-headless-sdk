package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_APPLICATION_NAME
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.ApplicationIdData
import com.doordeck.multiplatform.sdk.model.data.ApplicationJwtBody
import com.doordeck.multiplatform.sdk.model.data.ApplicationJwtHeader
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.ApplicationUserData
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
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationUserResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEd25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRsaKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

class PlatformApiTest : CallbackTest() {

    @AfterTest
    fun cleanUp() {
        val applicationsResponse = callbackApiCall<ResultData<List<BasicApplicationResponse>>> {
            PlatformApi.listApplications(callback = TestCallback)
        }
        applicationsResponse.success?.result?.filter { application ->
            application.name.startsWith(TEST_MAIN_APPLICATION_NAME) &&
                    application.owners.any { it == PLATFORM_TEST_MAIN_USER_ID }
        }?.forEach { application ->
            runCatching {
                callbackApiCall<ResultData<Unit>> {
                    PlatformApi.deleteApplication(
                        data = ApplicationIdData(application.applicationId).toJson(),
                        callback = TestCallback
                    )
                }.unwrap()
            }
        }
    }

    private fun withApplication(block: (TestApplication) -> Unit) {
        val authTokens = callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        val newApplication = CreateApplicationData(
            name = "$TEST_MAIN_APPLICATION_NAME - ${randomUuidString()}",
            companyName = randomString(),
            mailingAddress = randomEmail(),
            privacyPolicy = randomUrlString(),
            supportContact = randomUrlString()
        )
        val applicationId = callbackApiCall<ResultData<String>> {
            PlatformApi.createApplication(
                data = newApplication.toJson(),
                callback = TestCallback
            )
        }.unwrap()

        try {
            block(TestApplication(newApplication, applicationId, authTokens.authToken))
        } finally {
            runCatching {
                callbackApiCall<ResultData<Unit>> {
                    PlatformApi.deleteApplication(
                        data = ApplicationIdData(applicationId).toJson(),
                        callback = TestCallback
                    )
                }.unwrap()
            }
        }
    }

    private data class TestApplication(
        val request: CreateApplicationData,
        val applicationId: String,
        val authToken: String
    )

    private fun getApplication(applicationId: String): BasicApplicationResponse =
        callbackApiCall<ResultData<BasicApplicationResponse>> {
            PlatformApi.getApplication(
                data = ApplicationIdData(applicationId).toJson(),
                callback = TestCallback
            )
        }.unwrap()

    private fun listApplications(): List<BasicApplicationResponse> =
        callbackApiCall<ResultData<List<BasicApplicationResponse>>> {
            PlatformApi.listApplications(callback = TestCallback)
        }.unwrap()

    @Test
    fun shouldCreateApplication() = runTest {
        withApplication { (newApplication, applicationId, _) ->
            // When
            val application = listApplications().first {
                it.name.equals(newApplication.name, true)
            }

            // Then
            assertNotNull(application)
            assertEquals(applicationId, application.applicationId)
            assertEquals(newApplication.name, application.name)
            assertEquals(newApplication.companyName, application.companyName)
            assertEquals(newApplication.mailingAddress, application.mailingAddress)
            assertEquals(newApplication.privacyPolicy, application.privacyPolicy)
            assertEquals(newApplication.supportContact, application.supportContact)
        }
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        withApplication { (_, applicationId, _) ->
            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.deleteApplication(
                    data = ApplicationIdData(applicationId).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            val applications = listApplications()
            assertFalse { applications.any { it.applicationId == applicationId } }
        }
    }

    @Test
    fun shouldUpdateApplicationDetails() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldUpdateApplicationName
            val updatedApplicationName = "$TEST_MAIN_APPLICATION_NAME - ${randomUuidString()}"

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationName(
                    data = UpdateApplicationNameData(applicationId, updatedApplicationName).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationName, getApplication(applicationId).name)

            // Given - shouldUpdateApplicationCompanyName
            val updatedApplicationCompanyName = randomString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationCompanyName(
                    data = UpdateApplicationCompanyNameData(applicationId, updatedApplicationCompanyName).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationCompanyName, getApplication(applicationId).companyName)

            // Given - shouldUpdateApplicationMailingAddress
            val updatedApplicationMailingAddress = randomEmail()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationMailingAddress(
                    data = UpdateApplicationMailingAddressData(applicationId, updatedApplicationMailingAddress).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationMailingAddress, getApplication(applicationId).mailingAddress)
        }
    }

    @Test
    fun shouldUpdateApplicationLinks() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldUpdateApplicationPrivacyPolicy
            val updatedApplicationPrivacyPolicy = randomUrlString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationPrivacyPolicy(
                    data = UpdateApplicationPrivacyPolicyData(applicationId, updatedApplicationPrivacyPolicy).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationPrivacyPolicy, getApplication(applicationId).privacyPolicy)

            // Given - shouldUpdateApplicationSupportContact
            val updatedApplicationSupportContact = randomUrlString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationSupportContact(
                    data = UpdateApplicationSupportContactData(applicationId, updatedApplicationSupportContact).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationSupportContact, getApplication(applicationId).supportContact)

            // Given - shouldUpdateApplicationAppLink
            val updatedApplicationAppLink = randomUrlString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationAppLink(
                    data = UpdateApplicationAppLinkData(applicationId, updatedApplicationAppLink).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationAppLink, getApplication(applicationId).appLink)

            // Given - shouldUpdateApplicationLogoUrl
            val updatedApplicationLogoUrl = "https://cdn.doordeck.com/application/test"

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.updateApplicationLogoUrl(
                    data = UpdateApplicationLogoUrlData(applicationId, updatedApplicationLogoUrl).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(updatedApplicationLogoUrl, getApplication(applicationId).logoUrl)
        }
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given
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
                    data = UpdateApplicationEmailPreferencesData(
                        applicationId,
                        updatedApplicationEmailPreferences
                    ).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            val applicationResponse = getApplication(applicationId)
            assertEquals(
                updatedApplicationEmailPreferences.senderEmail,
                applicationResponse.emailPreferences.senderEmail
            )
            assertEquals(
                updatedApplicationEmailPreferences.senderName,
                applicationResponse.emailPreferences.senderName
            )
            assertEquals(
                updatedApplicationEmailPreferences.primaryColour,
                applicationResponse.emailPreferences.primaryColour
            )
            assertEquals(
                updatedApplicationEmailPreferences.secondaryColour,
                applicationResponse.emailPreferences.secondaryColour
            )
            assertEquals(
                updatedApplicationEmailPreferences.onlySendEssentialEmails,
                applicationResponse.emailPreferences.onlySendEssentialEmails
            )
            assertEquals(
                updatedApplicationEmailPreferences.callToAction?.actionTarget,
                applicationResponse.emailPreferences.callToAction?.actionTarget
            )
            assertEquals(
                updatedApplicationEmailPreferences.callToAction?.headline,
                applicationResponse.emailPreferences.callToAction?.headline
            )
            assertEquals(
                updatedApplicationEmailPreferences.callToAction?.actionText,
                applicationResponse.emailPreferences.callToAction?.actionText
            )
        }
    }

    @Test
    fun shouldAddAndDeleteAuthIssuer() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddAuthIssuer
            val addedApplicationAuthIssuer = randomUrlString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addAuthIssuer(
                    data = AuthIssuerData(applicationId, addedApplicationAuthIssuer).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            var applicationResponse = getApplication(applicationId)
            assertNotEquals(0, applicationResponse.authDomains.size)
            assertTrue { applicationResponse.authDomains.any { it == addedApplicationAuthIssuer } }

            // Given - shouldDeleteAuthIssuer
            val removedApplicationAuthIssuer = addedApplicationAuthIssuer

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.deleteAuthIssuer(
                    data = AuthIssuerData(applicationId, removedApplicationAuthIssuer).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationResponse = getApplication(applicationId)
            assertEquals(0, applicationResponse.authDomains.size)
            assertFalse { applicationResponse.authDomains.any { it == removedApplicationAuthIssuer } }
        }
    }

    @Test
    fun shouldAddAndRemoveCorsDomain() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddCorsDomain
            val addedApplicationCorsDomain = randomUrlString()

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addCorsDomain(
                    data = CorsDomainData(applicationId, addedApplicationCorsDomain).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            var applicationResponse = getApplication(applicationId)
            assertNotEquals(0, applicationResponse.corsDomains.size)
            assertTrue { applicationResponse.corsDomains.any { it == addedApplicationCorsDomain } }

            // Given - shouldDeleteCorsDomain
            val removedApplicationCorsDomain = addedApplicationCorsDomain

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.removeCorsDomain(
                    data = CorsDomainData(applicationId, removedApplicationCorsDomain).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationResponse = getApplication(applicationId)
            assertEquals(0, applicationResponse.corsDomains.size)
            assertFalse { applicationResponse.corsDomains.any { it == removedApplicationCorsDomain } }
        }
    }

    @Test
    fun shouldAddAuthKeys() = runTest {
        CryptoManager.initialize() // Initialize
        withApplication { (_, applicationId, _) ->
            // Given - shouldAddEd25519AuthKey
            val ed25519KeyPair = CryptoManager.generateRawKeyPair()
            val ed25519Key = Ed25519KeyData(
                kid = randomUuidString(),
                use = "sig",
                alg = "EdDSA",
                crv = "Ed25519",
                x = ed25519KeyPair.public.encodeByteArrayToBase64()
            )

            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addAuthKey(
                    data = AddAuthKeyData(applicationId, ed25519Key).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            var applicationResponse = getApplication(applicationId)
            val actualEd25519Key = applicationResponse.authKeys.entries.firstOrNull {
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
                    data = AddAuthKeyData(applicationId, rsaKey).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationResponse = getApplication(applicationId)
            val actualRsaKey = applicationResponse.authKeys.entries.firstOrNull {
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
                    data = AddAuthKeyData(applicationId, ecKey).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationResponse = getApplication(applicationId)
            val actualKeyEcKey = applicationResponse.authKeys.entries.firstOrNull {
                it.key == ecKey.kid
            }?.value as? BasicEcKeyResponse
            assertNotNull(actualKeyEcKey)
            assertEquals(ecKey.use, actualKeyEcKey.use)
            assertEquals(ecKey.kid, actualKeyEcKey.kid)
            assertEquals(ecKey.alg, actualKeyEcKey.alg)
            assertEquals(ecKey.crv, actualKeyEcKey.crv)
            assertEquals(ecKey.x, actualKeyEcKey.x)
            assertEquals(ecKey.y, actualKeyEcKey.y)
        }
    }

    @Test
    fun shouldGetApplicationUsers() = runTest {
        CryptoManager.initialize() // Initialize
        withApplication { (_, applicationId, authToken) ->
            // Given - an auth issuer and an Ed25519 auth key to sign the application user token with
            val addedApplicationAuthIssuer = randomUrlString()
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addAuthIssuer(
                    data = AuthIssuerData(applicationId, addedApplicationAuthIssuer).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            val ed25519KeyPair = CryptoManager.generateRawKeyPair()
            val ed25519KeyId = randomUuidString()
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addAuthKey(
                    data = AddAuthKeyData(
                        applicationId,
                        Ed25519KeyData(
                            kid = ed25519KeyId,
                            use = "sig",
                            alg = "EdDSA",
                            crv = "Ed25519",
                            x = ed25519KeyPair.public.encodeByteArrayToBase64()
                        )
                    ).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            val applicationUserEmail = "training+${randomUuidString()}@doordeck.com"
            val applicationUserId = randomUuidString()
            val applicationJwtHeader = ApplicationJwtHeader("Ed25519", ed25519KeyId)
            val applicationJwtBody = ApplicationJwtBody(
                iss = addedApplicationAuthIssuer,
                exp = Clock.System.now().epochSeconds + 1.days.inWholeSeconds,
                iat = Clock.System.now().epochSeconds,
                aud = ApiEnvironment.PROD.cloudHost,
                sub = applicationUserId,
                email = applicationUserEmail,
                emailVerified = true,
                name = "Training Training"
            )
            val headerB64 = applicationJwtHeader.toJson().encodeToByteArray().encodeByteArrayToBase64()
            val bodyB64 = applicationJwtBody.toJson().encodeToByteArray().encodeByteArrayToBase64()
            val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(ed25519KeyPair.private).encodeByteArrayToBase64()
            val applicationAuthToken = "$headerB64.$bodyB64.$signatureB64"

            // When
            callbackApiCall<ResultData<Unit>> { ContextManager.clearContext(callback = TestCallback)}.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.setCloudAuthToken(applicationAuthToken, callback = TestCallback) }.unwrap() // Override the context auth token with the application auth token
            // Perform a request to create the new user and attach it to the application
            callbackApiCall<ResultData<BasicUserDetailsResponse>> {
                AccountApi.getUserDetails(callback = TestCallback)
            }.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.clearContext(callback = TestCallback)}.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.setCloudAuthToken(authToken, callback = TestCallback) }.unwrap() // Restore the context token

            // Then
            val applicationUsersResponse = callbackApiCall<ResultData<List<BasicApplicationUserResponse>>> {
                PlatformApi.getApplicationUsers(
                    data = ApplicationUserData(applicationId).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            assertEquals(1, applicationUsersResponse.size)
            assertEquals(applicationUserEmail, applicationUsersResponse.first().email)
            assertEquals(applicationJwtBody.name, applicationUsersResponse.first().displayName)
            assertEquals(applicationUserId, applicationUsersResponse.first().foreignKey)

            callbackApiCall<ResultData<Unit>> { ContextManager.clearContext(callback = TestCallback)}.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.setCloudAuthToken(applicationAuthToken, callback = TestCallback) }.unwrap() // Override the context auth token with the application auth token
            // Cleanup the application user
            callbackApiCall<ResultData<Unit>> {
                AccountApi.deleteAccount(callback = TestCallback)
            }.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.clearContext(callback = TestCallback)}.unwrap()
            callbackApiCall<ResultData<Unit>> { ContextManager.setCloudAuthToken(authToken, callback = TestCallback) }.unwrap() // Restore the context token
        }
    }

    @Test
    fun shouldAddAndRemoveApplicationOwner() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given - shouldGetApplicationOwnersDetails
            // When
            var applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
                PlatformApi.getApplicationOwnersDetails(
                    data = ApplicationIdData(applicationId).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertTrue { applicationOwnerDetailsResponse.isNotEmpty() }
            assertTrue { applicationOwnerDetailsResponse.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }

            // Given - shouldAddApplicationOwner
            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.addApplicationOwner(
                    data = ApplicationOwnerData(applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
                PlatformApi.getApplicationOwnersDetails(
                    data = ApplicationIdData(applicationId).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            assertTrue { applicationOwnerDetailsResponse.isNotEmpty() }
            assertTrue { applicationOwnerDetailsResponse.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }

            // Given - shouldRemoveApplicationOwner
            // When
            callbackApiCall<ResultData<Unit>> {
                PlatformApi.removeApplicationOwner(
                    data = ApplicationOwnerData(applicationId, PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            applicationOwnerDetailsResponse = callbackApiCall<ResultData<List<BasicApplicationOwnerDetailsResponse>>> {
                PlatformApi.getApplicationOwnersDetails(
                    data = ApplicationIdData(applicationId).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            assertTrue { applicationOwnerDetailsResponse.isNotEmpty() }
            assertFalse { applicationOwnerDetailsResponse.any { it.userId == PLATFORM_TEST_SUPPLEMENTARY_USER_ID } }
        }
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        withApplication { (_, applicationId, _) ->
            // Given
            val contentType = "image/png"

            // When
            val uploadUrlResponse = callbackApiCall<ResultData<BasicGetLogoUploadUrlResponse>> {
                PlatformApi.getLogoUploadUrl(
                    data = GetLogoUploadUrlData(applicationId, contentType).toJson(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertTrue { uploadUrlResponse.uploadUrl.contains("doordeck-upload") }
        }
    }
}