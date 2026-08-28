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
    suspend fun getSingleLock(lockId: String): LockResponse = LockOperationsClient
        .getSingleLockRequest(lockId)
        .toLockResponse()

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    suspend fun getLockAuditTrail(
        lockId: String,
        start: Long? = null,
        end: Long? = null
    ): JsArray<AuditResponse> = LockOperationsClient
        .getLockAuditTrailRequest(
            lockId = lockId,
            start = start,
            end = end
        )
        .toAuditResponse()

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    suspend fun getAuditForUser(
        userId: String,
        start: Long? = null,
        end: Long? = null
    ): JsArray<AuditResponse> = LockOperationsClient
        .getAuditForUserRequest(
            userId = userId,
            start = start,
            end = end
        )
        .toAuditResponse()

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    suspend fun getUsersForLock(lockId: String): JsArray<UserLockResponse> = LockOperationsClient
        .getUsersForLockRequest(lockId)
        .toUserLockResponse()

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    suspend fun getLocksForUser(userId: String): LockUserResponse = LockOperationsClient
        .getLocksForUserRequest(userId)
        .toLockUserResponse()

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    suspend fun updateLockName(lockId: String, name: String? = null): dynamic = LockOperationsClient
        .updateLockNameRequest(
            lockId = lockId,
            name = name
        )

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    suspend fun updateLockFavourite(lockId: String, favourite: Boolean): dynamic = LockOperationsClient
        .updateLockFavouriteRequest(
            lockId = lockId,
            favourite = favourite
        )

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    suspend fun updateLockSettingDefaultName(lockId: String, name: String): dynamic = LockOperationsClient
        .updateLockSettingDefaultNameRequest(
            lockId = lockId,
            name = name
        )

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    suspend fun setLockSettingPermittedAddresses(
        lockId: String,
        permittedAddresses: JsArray<String>
    ): dynamic = LockOperationsClient
        .setLockSettingPermittedAddressesRequest(
            lockId = lockId,
            permittedAddresses = permittedAddresses.toList()
        )

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean): dynamic = LockOperationsClient
        .updateLockSettingHiddenRequest(
            lockId = lockId,
            hidden = hidden
        )

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    suspend fun setLockSettingTimeRestrictions(
        lockId: String,
        times: JsArray<LockOperations.TimeRequirement>
    ): dynamic = LockOperationsClient
        .setLockSettingTimeRestrictionsRequest(
            lockId = lockId,
            times = times.toBasicTimeRequirement()
        )

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    suspend fun updateLockSettingLocationRestrictions(
        lockId: String,
        location: LockOperations.LocationRequirement? = null
    ): dynamic = LockOperationsClient
        .updateLockSettingLocationRestrictionsRequest(
            lockId = lockId,
            location = location?.toBasicLocationRequirement()
        )

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyRequest(
            userEmail = userEmail,
            visitor = visitor
        )
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByEmailRequest(email)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByTelephoneRequest(telephone)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByLocalKeyRequest(localKey)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByForeignKeyRequest(foreignKey)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByIdentityRequest(identity)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    suspend fun getUserPublicKeyByEmails(emails: JsArray<String>): JsArray<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByEmailsRequest(emails.toList())
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    suspend fun getUserPublicKeyByTelephones(
        telephones: JsArray<String>
    ): JsArray<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByTelephonesRequest(telephones.toList())
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    suspend fun getUserPublicKeyByLocalKeys(localKeys: JsArray<String>): JsArray<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByLocalKeysRequest(localKeys.toList())
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    suspend fun getUserPublicKeyByForeignKeys(
        foreignKeys: JsArray<String>
    ): JsArray<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByForeignKeysRequest(foreignKeys.toList())
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.unlockRequest
     */
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation): dynamic = LockOperationsClient
        .unlockRequest(unlockOperation.toBasicUnlockOperation())

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): dynamic = LockOperationsClient
        .shareLockRequest(shareLockOperation.toBasicShareLockOperation())

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation): dynamic = LockOperationsClient
        .batchShareLockRequest(
            batchShareLockOperation = batchShareLockOperation.toBasicBatchShareLockOperation()
        )

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    suspend fun revokeAccessToLock(
        revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation
    ): dynamic = LockOperationsClient
        .revokeAccessToLockRequest(
            revokeAccessToLockOperation = revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation()
        )

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    suspend fun updateSecureSettingUnlockDuration(
        updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration
    ): dynamic = LockOperationsClient
        .updateSecureSettingUnlockDurationRequest(
            updateSecureSettingUnlockDuration = updateSecureSettingUnlockDuration
                .toBasicUpdateSecureSettingUnlockDuration()
        )

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    suspend fun updateSecureSettingUnlockBetween(
        updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween
    ): dynamic = LockOperationsClient
        .updateSecureSettingUnlockBetweenRequest(
            updateSecureSettingUnlockBetween = updateSecureSettingUnlockBetween
                .toBasicUpdateSecureSettingUnlockBetween()
        )

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    suspend fun getPinnedLocks(): JsArray<LockResponse> = LockOperationsClient
        .getPinnedLocksRequest()
        .toLockResponse()

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    suspend fun getShareableLocks(): JsArray<ShareableLockResponse> = LockOperationsClient
        .getShareableLocksRequest()
        .toShareableLockResponse()
}

private val lockOperations = LockOperationsApi

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = lockOperations