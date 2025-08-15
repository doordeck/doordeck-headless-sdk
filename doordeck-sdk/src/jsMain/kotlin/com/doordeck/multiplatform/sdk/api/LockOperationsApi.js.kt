package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.toBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.toShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise
import kotlin.js.collections.JsArray
import kotlin.js.collections.toList

/**
 * Platform-specific implementations of lock-related API calls.
 */
@JsExport
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    fun getSingleLock(lockId: String): Promise<LockResponse> = promise {
        LockOperationsClient.getSingleLockRequest(lockId)
            .toLockResponse()
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<JsArray<AuditResponse>> = promise {
        LockOperationsClient
            .getLockAuditTrailRequest(
                lockId = lockId,
                start = start.toLong(),
                end = end.toLong()
            ).toAuditResponse()
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): Promise<JsArray<AuditResponse>> = promise {
        LockOperationsClient
            .getAuditForUserRequest(
                userId = userId,
                start = start.toLong(),
                end = end.toLong()
            )
            .toAuditResponse()
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    fun getUsersForLock(lockId: String): Promise<JsArray<UserLockResponse>> = promise {
        LockOperationsClient.getUsersForLockRequest(lockId)
            .toUserLockResponse()
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    fun getLocksForUser(userId: String): Promise<LockUserResponse> = promise {
        LockOperationsClient.getLocksForUserRequest(userId)
            .toLockUserResponse()
    }

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    fun updateLockName(lockId: String, name: String? = null): Promise<dynamic> = promise {
        LockOperationsClient.updateLockNameRequest(
            lockId = lockId,
            name = name
        )
    }

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean): Promise<dynamic> = promise {
        LockOperationsClient.updateLockFavouriteRequest(
            lockId = lockId,
            favourite = favourite
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    fun updateLockSettingDefaultName(lockId: String, name: String): Promise<dynamic> = promise {
        LockOperationsClient.updateLockSettingDefaultNameRequest(
            lockId = lockId,
            name = name
        )
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    fun setLockSettingPermittedAddresses(
        lockId: String,
        permittedAddresses: JsArray<String>
    ): Promise<dynamic> = promise {
        LockOperationsClient.setLockSettingPermittedAddressesRequest(
            lockId = lockId,
            permittedAddresses = permittedAddresses.toList()
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<dynamic> = promise {
        LockOperationsClient.updateLockSettingHiddenRequest(
            lockId = lockId,
            hidden = hidden
        )
    }

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    fun setLockSettingTimeRestrictions(
        lockId: String,
        times: JsArray<LockOperations.TimeRequirement>
    ): Promise<dynamic> = promise {
        LockOperationsClient.setLockSettingTimeRestrictionsRequest(
            lockId = lockId,
            times = times.toBasicTimeRequirement()
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    fun updateLockSettingLocationRestrictions(
        lockId: String,
        location: LockOperations.LocationRequirement? = null
    ): Promise<dynamic> = promise {
        LockOperationsClient.updateLockSettingLocationRestrictionsRequest(
            lockId = lockId,
            location = location?.toBasicLocationRequirement()
        )
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient
            .getUserPublicKeyRequest(
                userEmail = userEmail,
                visitor = visitor
            )
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient.getUserPublicKeyByEmailRequest(email)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> = promise {
        LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    fun getUserPublicKeyByEmails(emails: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> = promise {
        LockOperationsClient.getUserPublicKeyByEmailsRequest(emails.toList())
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    fun getUserPublicKeyByTelephones(
        telephones: JsArray<String>
    ): Promise<JsArray<BatchUserPublicKeyResponse>> = promise {
        LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones.toList())
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    fun getUserPublicKeyByLocalKeys(localKeys: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> =
        promise {
            LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys.toList())
                .toBatchUserPublicKeyResponse()
        }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    fun getUserPublicKeyByForeignKeys(
        foreignKeys: JsArray<String>
    ): Promise<JsArray<BatchUserPublicKeyResponse>> = promise {
        LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys.toList())
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<dynamic> = promise {
        LockOperationsClient.unlockRequest(unlockOperation.toBasicUnlockOperation())
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<dynamic> = promise {
        LockOperationsClient.shareLockRequest(shareLockOperation.toBasicShareLockOperation())
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation): Promise<dynamic> = promise {
        LockOperationsClient
            .batchShareLockRequest(
                batchShareLockOperation = batchShareLockOperation.toBasicBatchShareLockOperation()
            )
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    fun revokeAccessToLock(
        revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation
    ): Promise<dynamic> = promise {
        LockOperationsClient
            .revokeAccessToLockRequest(
                revokeAccessToLockOperation = revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation()
            )
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    fun updateSecureSettingUnlockDuration(
        updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration
    ): Promise<dynamic> = promise {
        LockOperationsClient
            .updateSecureSettingUnlockDurationRequest(
                updateSecureSettingUnlockDuration = updateSecureSettingUnlockDuration
                    .toBasicUpdateSecureSettingUnlockDuration()
            )
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    fun updateSecureSettingUnlockBetween(
        updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween
    ): Promise<dynamic> = promise {
        LockOperationsClient
            .updateSecureSettingUnlockBetweenRequest(
                updateSecureSettingUnlockBetween = updateSecureSettingUnlockBetween
                    .toBasicUpdateSecureSettingUnlockBetween()
            )
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    fun getPinnedLocks(): Promise<JsArray<LockResponse>> = promise {
        LockOperationsClient.getPinnedLocksRequest()
            .toLockResponse()
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    fun getShareableLocks(): Promise<JsArray<ShareableLockResponse>> = promise {
        LockOperationsClient.getShareableLocksRequest()
            .toShareableLockResponse()
    }
}

private val lockOperations = LockOperationsApi

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
@JsExport
actual fun lockOperations(): LockOperationsApi = lockOperations