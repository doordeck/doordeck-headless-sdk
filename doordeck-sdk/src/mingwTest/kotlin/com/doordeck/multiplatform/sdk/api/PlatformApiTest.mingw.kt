package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.APPLICATION_LIST_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_OWNER_DETAILS_RESPONSE
import com.doordeck.multiplatform.sdk.APPLICATION_RESPONSE
import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.LOGO_UPLOAD_URL_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_APPLICATION_ID
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
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class PlatformApiTest : CallbackTest() {

    @Test
    fun shouldCreateApplication() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.createApplication(
                    data = CreateApplicationData("name", "companyName", "mailingAddress").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldListApplications() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.listApplications(staticCFunction(::testCallback))
            },
            expectedResponse = APPLICATION_LIST_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetApplication() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.getApplication(
                    data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = APPLICATION_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldUpdateApplicationName() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationName(
                    data = UpdateApplicationNameData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationCompanyName() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationCompanyName(
                    UpdateApplicationCompanyNameData(DEFAULT_APPLICATION_ID, "").toJson(),
                    staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationMailingAddress() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationMailingAddress(
                    data = UpdateApplicationMailingAddressData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationPrivacyPolicy() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationPrivacyPolicy(
                    data = UpdateApplicationPrivacyPolicyData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationSupportContact() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationSupportContact(
                    data = UpdateApplicationSupportContactData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationAppLink() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationAppLink(
                    data = UpdateApplicationAppLinkData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationEmailPreferences() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationEmailPreferences(
                    data = UpdateApplicationEmailPreferencesData(DEFAULT_APPLICATION_ID, EmailPreferencesData()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateApplicationLogoUrl() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.updateApplicationLogoUrl(
                    data = UpdateApplicationLogoUrlData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldDeleteApplication() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.deleteApplication(
                    data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetLogoUploadUrl() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.getLogoUploadUrl(
                    data = GetLogoUploadUrlData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = LOGO_UPLOAD_URL_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldAddAuthKey() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.addAuthKey(
                    data = AddAuthKeyData(DEFAULT_APPLICATION_ID, EcKeyData(use = "", kid = "", d = "", crv = "", x = "", y = "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldAddAuthIssuer() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.addAuthIssuer(
                    data = AuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldDeleteAuthIssuer() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.deleteAuthIssuer(
                    data = AuthIssuerData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldAddCorsDomain() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.addCorsDomain(
                    data = CorsDomainData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldRemoveCorsDomain() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.removeCorsDomain(
                    data = CorsDomainData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldAddApplicationOwner() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.addApplicationOwner(
                    data = ApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldRemoveApplicationOwner() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.removeApplicationOwner(
                    data = ApplicationOwnerData(DEFAULT_APPLICATION_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetApplicationOwnersDetails() = runTest {
        callbackTest(
            apiCall = {
                PlatformApi.getApplicationOwnersDetails(
                    data = ApplicationIdData(DEFAULT_APPLICATION_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = APPLICATION_OWNER_DETAILS_RESPONSE.toResultDataJson()
        )
    }
}