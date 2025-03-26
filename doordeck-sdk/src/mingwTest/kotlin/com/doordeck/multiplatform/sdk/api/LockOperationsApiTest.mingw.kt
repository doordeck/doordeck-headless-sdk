package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.AUDIT_RESPONSE
import com.doordeck.multiplatform.sdk.BATCH_USER_PUBLIC_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.LOCK_RESPONSE
import com.doordeck.multiplatform.sdk.LOCK_USER_RESPONSE
import com.doordeck.multiplatform.sdk.PINNED_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.SHAREABLE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.USER_LOCK_RESPONSE
import com.doordeck.multiplatform.sdk.USER_PUBLIC_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.model.data.BaseOperationData
import com.doordeck.multiplatform.sdk.model.data.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.GetAuditForUserData
import com.doordeck.multiplatform.sdk.model.data.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.model.data.GetLocksForUserData
import com.doordeck.multiplatform.sdk.model.data.GetSingleLockData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephonesData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForLockData
import com.doordeck.multiplatform.sdk.model.data.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.ShareLockData
import com.doordeck.multiplatform.sdk.model.data.ShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.UnlockOperationData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockColourData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsApiTest : CallbackTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(DEFAULT_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = LOCK_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getLockAuditTrail(
                    data = GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = AUDIT_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getAuditForUser(
                    data = GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = AUDIT_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUsersForLock(
                    data = GetUsersForLockData(DEFAULT_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_LOCK_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(DEFAULT_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = LOCK_USER_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockName(
                    data = UpdateLockNameData(DEFAULT_LOCK_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockFavourite(
                    data = UpdateLockFavouriteData(DEFAULT_LOCK_ID, false).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockColour(
                    data = UpdateLockColourData(DEFAULT_LOCK_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockSettingDefaultName(
                    data = UpdateLockSettingDefaultNameData(DEFAULT_LOCK_ID, "").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.setLockSettingPermittedAddresses(
                    data = SetLockSettingPermittedAddressesData(DEFAULT_LOCK_ID, listOf("1.1.1.1")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockSettingHidden(
                    data = UpdateLockSettingHiddenData(DEFAULT_LOCK_ID, true).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.setLockSettingTimeRestrictions(
                    data = SetLockSettingTimeRestrictionsData(DEFAULT_LOCK_ID, emptyList()).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateLockSettingLocationRestrictions(
                    data = UpdateLockSettingLocationRestrictionsData(DEFAULT_LOCK_ID, null).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKey(
                    data = GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByEmail(
                    data = GetUserPublicKeyByEmailData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByTelephone(
                    data = GetUserPublicKeyByTelephoneData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByLocalKey(
                    data = GetUserPublicKeyByLocalKeyData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByForeignKey(
                    data = GetUserPublicKeyByForeignKeyData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByIdentity(
                    data = GetUserPublicKeyByIdentityData("").toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByEmails(
                    data = GetUserPublicKeyByEmailsData(listOf("", "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByTelephones(
                    data = GetUserPublicKeyByTelephonesData(listOf("", "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByLocalKeys(
                    data = GetUserPublicKeyByLocalKeysData(listOf("", "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getUserPublicKeyByForeignKeys(
                    data = GetUserPublicKeyByForeignKeysData(listOf("", "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            },
            expectedResponse = BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.unlock(
                    data = UnlockOperationData(BaseOperationData(lockId = DEFAULT_LOCK_ID)).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUnlock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.unlock(
                    data = UnlockOperationData(BaseOperationData("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, "")).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.shareLock(
                    data = ShareLockOperationData(
                        baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                        shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.batchShareLock(
                    data = BatchShareLockOperationData(
                        baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                        users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldShareLock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.shareLock(
                    data = ShareLockOperationData(
                        baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                        shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.batchShareLock(
                    data = BatchShareLockOperationData(
                        baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                        users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.revokeAccessToLock(
                    data = RevokeAccessToLockOperationData(
                        baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                        users = emptyList()
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.revokeAccessToLock(
                    data = RevokeAccessToLockOperationData(
                        baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                        users = emptyList()
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateSecureSettingUnlockDuration(
                    data = UpdateSecureSettingUnlockDurationData(
                        baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                        unlockDuration = 0
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateSecureSettingUnlockDuration(
                    data = UpdateSecureSettingUnlockDurationData(
                        baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0 , 0, ""),
                        unlockDuration = 0
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateSecureSettingUnlockBetween(
                    data = UpdateSecureSettingUnlockBetweenData(
                        baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                        unlockBetween = null
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.updateSecureSettingUnlockBetween(
                    data = UpdateSecureSettingUnlockBetweenData(
                        baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                        unlockBetween = null
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        )
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getPinnedLocks(staticCFunction(::testCallback))
            },
            expectedResponse = PINNED_LOCKS_RESPONSE.toResultDataJson()
        )
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        callbackTest(
            apiCall = {
                LockOperationsApi.getShareableLocks(staticCFunction(::testCallback))
            },
            expectedResponse = SHAREABLE_LOCKS_RESPONSE.toResultDataJson()
        )
    }
}