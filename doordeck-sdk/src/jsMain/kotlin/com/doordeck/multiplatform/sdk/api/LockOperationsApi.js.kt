package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.Audit
import com.doordeck.multiplatform.sdk.model.data.BatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.data.Lock
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.LockUser
import com.doordeck.multiplatform.sdk.model.data.ShareableLock
import com.doordeck.multiplatform.sdk.model.data.UserLock
import com.doordeck.multiplatform.sdk.model.data.UserPublicKey
import com.doordeck.multiplatform.sdk.model.data.toAudit
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.data.toBatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.data.toLock
import com.doordeck.multiplatform.sdk.model.data.toLockUser
import com.doordeck.multiplatform.sdk.model.data.toShareableLock
import com.doordeck.multiplatform.sdk.model.data.toUserLock
import com.doordeck.multiplatform.sdk.model.data.toUserPublicKey
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of lock-related API calls.
 */
@JsExport
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    fun getSingleLock(lockId: String): Promise<Lock> {
        return promise { LockOperationsClient.getSingleLockRequest(lockId).toLock() }
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<Audit>> {
        return promise { LockOperationsClient.getLockAuditTrailRequest(lockId, start.toLong(), end.toLong()).toAudit() }
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<Audit>> {
        return promise { LockOperationsClient.getAuditForUserRequest(userId, start.toLong(), end.toLong()).toAudit() }
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    fun getUsersForLock(lockId: String): Promise<List<UserLock>> {
        return promise { LockOperationsClient.getUsersForLockRequest(lockId).toUserLock() }
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    fun getLocksForUser(userId: String): Promise<LockUser> {
        return promise { LockOperationsClient.getLocksForUserRequest(userId).toLockUser() }
    }

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    fun updateLockName(lockId: String, name: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    /**
     * @see LockOperationsClient.updateLockColourRequest
     */
    fun updateLockColour(lockId: String, colour: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<dynamic> {
        return promise { LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<dynamic> {
        return promise { LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times.toBasicTimeRequirement()) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location?.toBasicLocationRequirement()) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailRequest(email).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKey> {
        return promise { LockOperationsClient.getUserPublicKeyByIdentityRequest(identity).toUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    fun getUserPublicKeyByEmails(emails: List<String>): Promise<List<BatchUserPublicKey>> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailsRequest(emails).toBatchUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    fun getUserPublicKeyByTelephones(telephones: List<String>): Promise<List<BatchUserPublicKey>> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones).toBatchUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    fun getUserPublicKeyByLocalKeys(localKeys: List<String>): Promise<List<BatchUserPublicKey>> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys).toBatchUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): Promise<List<BatchUserPublicKey>> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys).toBatchUserPublicKey() }
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.unlockRequest(unlockOperation.toBasicUnlockOperation()) }
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.shareLockRequest(shareLockOperation.toBasicShareLockOperation()) }
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.batchShareLockRequest(batchShareLockOperation.toBasicBatchShareLockOperation()) }
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation()) }
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<dynamic> {
        return promise { LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration.toBasicUpdateSecureSettingUnlockDuration()) }
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<dynamic> {
        return promise { LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween.toBasicUpdateSecureSettingUnlockBetween()) }
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    fun getPinnedLocks(): Promise<List<Lock>> {
        return promise { LockOperationsClient.getPinnedLocksRequest().toLock() }
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    fun getShareableLocks(): Promise<List<ShareableLock>> {
        return promise { LockOperationsClient.getShareableLocksRequest().toShareableLock() }
    }
}

private val lockOperations = LockOperationsApi

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
@JsExport
actual fun lockOperations(): LockOperationsApi = lockOperations