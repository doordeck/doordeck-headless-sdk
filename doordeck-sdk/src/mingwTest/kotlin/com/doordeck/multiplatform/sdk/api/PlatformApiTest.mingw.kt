package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
import com.doordeck.multiplatform.sdk.UNIT_RESULT_DATA
import com.doordeck.multiplatform.sdk.capturedCallback
import com.doordeck.multiplatform.sdk.model.data.AddAuthKeyData
import com.doordeck.multiplatform.sdk.model.data.ApplicationIdData
import com.doordeck.multiplatform.sdk.model.data.ApplicationOwnerData
import com.doordeck.multiplatform.sdk.model.data.AuthIssuerData
import com.doordeck.multiplatform.sdk.model.data.CorsDomainData
import com.doordeck.multiplatform.sdk.model.data.CreateApplicationData
import com.doordeck.multiplatform.sdk.model.data.EcKeyData
import com.doordeck.multiplatform.sdk.model.data.EmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.GetLogoUploadUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationAppLinkData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationCompanyNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationEmailPreferencesData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationLogoUrlData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationMailingAddressData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationPrivacyPolicyData
import com.doordeck.multiplatform.sdk.model.data.UpdateApplicationSupportContactData
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class PlatformApiTest : CallbackTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.createApplication(
                data = CreateApplicationData("name", "companyName", "mailingAddress").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldListApplications() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.listApplications(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(APPLICATION_LIST_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetApplication() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.getApplication(
                data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(APPLICATION_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationName(
                data = UpdateApplicationNameData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationCompanyName(
                UpdateApplicationCompanyNameData(DEFAULT_APPLICATION_ID, "").toJson(),
                callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationMailingAddress(
                data = UpdateApplicationMailingAddressData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationPrivacyPolicy(
                data = UpdateApplicationPrivacyPolicyData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationSupportContact(
                data = UpdateApplicationSupportContactData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationAppLink(
                data = UpdateApplicationAppLinkData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationEmailPreferences(
                data = UpdateApplicationEmailPreferencesData(DEFAULT_APPLICATION_ID, EmailPreferencesData()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.updateApplicationLogoUrl(
                data = UpdateApplicationLogoUrlData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.deleteApplication(
                data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.getLogoUploadUrl(
                data = GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(LOGO_UPLOAD_URL_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.addAuthKey(
                data = AddAuthKeyData(DEFAULT_APPLICATION_ID, EcKeyData(use = "", kid = "", d = "", crv = "", x = "", y = "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.addAuthIssuer(
                data = AuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.deleteAuthIssuer(
                data = AuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.addCorsDomain(
                data = CorsDomainData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.removeCorsDomain(
                data = CorsDomainData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.addApplicationOwner(
                data = ApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.removeApplicationOwner(
                data = ApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            PlatformApi.getApplicationOwnersDetails(
                data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(APPLICATION_OWNER_DETAILS_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }
}