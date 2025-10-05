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
import com.doordeck.multiplatform.sdk.util.toEpochSeconds
import platform.Foundation.NSDate
import platform.Foundation.NSUUID

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    @Throws(Exception::class)
    suspend fun getSingleLock(lockId: NSUUID): LockResponse = LockOperationsClient
        .getSingleLockRequest(lockId.UUIDString)
        .toLockResponse()

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    @Throws(Exception::class)
    suspend fun getLockAuditTrail(
        lockId: NSUUID,
        start: NSDate? = null,
        end: NSDate? = null
    ): List<AuditResponse> = LockOperationsClient
        .getLockAuditTrailRequest(
            lockId = lockId.UUIDString,
            start = start?.toEpochSeconds(),
            end = end?.toEpochSeconds()
        )
        .toAuditResponse()

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getAuditForUser(
        userId: NSUUID,
        start: NSDate? = null,
        end: NSDate? = null
    ): List<AuditResponse> = LockOperationsClient
        .getAuditForUserRequest(
            userId = userId.UUIDString,
            start = start?.toEpochSeconds(),
            end = end?.toEpochSeconds()
        )
        .toAuditResponse()

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForLock(lockId: NSUUID): List<UserLockResponse> = LockOperationsClient
        .getUsersForLockRequest(lockId.UUIDString)
        .toUserLockResponse()

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForUser(userId: NSUUID): LockUserResponse = LockOperationsClient
        .getLocksForUserRequest(userId.UUIDString)
        .toLockUserResponse()

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockName(lockId: NSUUID, name: String? = null) = LockOperationsClient
        .updateLockNameRequest(
            lockId = lockId.UUIDString,
            name = name
        )

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockFavourite(lockId: NSUUID, favourite: Boolean) = LockOperationsClient
        .updateLockFavouriteRequest(
            lockId = lockId.UUIDString,
            favourite = favourite
        )

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingDefaultName(lockId: NSUUID, name: String) = LockOperationsClient
        .updateLockSettingDefaultNameRequest(
            lockId = lockId.UUIDString,
            name = name
        )

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    @Throws(Exception::class)
    suspend fun setLockSettingPermittedAddresses(
        lockId: NSUUID,
        permittedAddresses: List<String>
    ) = LockOperationsClient
        .setLockSettingPermittedAddressesRequest(
            lockId = lockId.UUIDString,
            permittedAddresses = permittedAddresses
        )

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingHidden(lockId: NSUUID, hidden: Boolean) = LockOperationsClient
        .updateLockSettingHiddenRequest(
            lockId = lockId.UUIDString,
            hidden = hidden
        )

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    @Throws(Exception::class)
    suspend fun setLockSettingTimeRestrictions(
        lockId: NSUUID,
        times: List<LockOperations.TimeRequirement>
    ) = LockOperationsClient
        .setLockSettingTimeRestrictionsRequest(
            lockId = lockId.UUIDString,
            times = times.toBasicTimeRequirement()
        )

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingLocationRestrictions(
        lockId: NSUUID,
        location: LockOperations.LocationRequirement? = null
    ) = LockOperationsClient
        .updateLockSettingLocationRestrictionsRequest(
            lockId = lockId.UUIDString,
            location = location?.toBasicLocationRequirement()
        )

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getUserPublicKey(
        userEmail: String,
        visitor: Boolean = false
    ): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyRequest(
            userEmail = userEmail,
            visitor = visitor
        )
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByEmailRequest(email)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByTelephoneRequest(telephone)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByLocalKeyRequest(localKey)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByForeignKeyRequest(foreignKey)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByIdentityRequest(identity)
        .toUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByEmailsRequest(emails)
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephones(
        telephones: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByTelephonesRequest(telephones)
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKeys(
        localKeys: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByLocalKeysRequest(localKeys)
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKeys(
        foreignKeys: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByForeignKeysRequest(foreignKeys)
        .toBatchUserPublicKeyResponse()

    /**
     * @see LockOperationsClient.unlockRequest
     */
    @Throws(Exception::class)
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) = LockOperationsClient
        .unlockRequest(unlockOperation.toBasicUnlockOperation())

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    @Throws(Exception::class)
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) = LockOperationsClient
        .shareLockRequest(shareLockOperation.toBasicShareLockOperation())

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    @Throws(Exception::class)
    suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) = LockOperationsClient
        .batchShareLockRequest(batchShareLockOperation.toBasicBatchShareLockOperation())

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    @Throws(Exception::class)
    suspend fun revokeAccessToLock(
        revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation
    ) = LockOperationsClient
        .revokeAccessToLockRequest(
            revokeAccessToLockOperation = revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation()
        )

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockDuration(
        updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration
    ) = LockOperationsClient
        .updateSecureSettingUnlockDurationRequest(
            updateSecureSettingUnlockDuration = updateSecureSettingUnlockDuration
                .toBasicUpdateSecureSettingUnlockDuration()
        )

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockBetween(
        updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween
    ) = LockOperationsClient
        .updateSecureSettingUnlockBetweenRequest(
            updateSecureSettingUnlockBetween = updateSecureSettingUnlockBetween
                .toBasicUpdateSecureSettingUnlockBetween()
        )

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getPinnedLocks(): List<LockResponse> = LockOperationsClient
        .getPinnedLocksRequest()
        .toLockResponse()

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getShareableLocks(): List<ShareableLockResponse> = LockOperationsClient
        .getShareableLocksRequest()
        .toShareableLockResponse()
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi