package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAuditResponse
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
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
    fun getSingleLock(lockId: String): Promise<LockResponse> {
        return promise { LockOperationsClient.getSingleLockRequest(lockId).toLockResponse() }
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<JsArray<AuditResponse>> {
        return promise { LockOperationsClient.getLockAuditTrailRequest(lockId, start.toLong(), end.toLong()).toAuditResponse() }
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): Promise<JsArray<AuditResponse>> {
        return promise { LockOperationsClient.getAuditForUserRequest(userId, start.toLong(), end.toLong()).toAuditResponse() }
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    fun getUsersForLock(lockId: String): Promise<JsArray<UserLockResponse>> {
        return promise { LockOperationsClient.getUsersForLockRequest(lockId).toUserLockResponse() }
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    fun getLocksForUser(userId: String): Promise<LockUserResponse> {
        return promise { LockOperationsClient.getLocksForUserRequest(userId).toLockUserResponse() }
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
    fun updateLockFavourite(lockId: String, favourite: Boolean): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    fun updateLockSettingDefaultName(lockId: String, name: String): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: JsArray<String>): Promise<dynamic> {
        return promise { LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses.toList()) }
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
    fun setLockSettingTimeRestrictions(lockId: String, times: JsArray<LockOperations.TimeRequirement>): Promise<dynamic> {
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
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailRequest(email).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByIdentityRequest(identity).toUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    fun getUserPublicKeyByEmails(emails: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailsRequest(emails.toList()).toBatchUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    fun getUserPublicKeyByTelephones(telephones: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones.toList()).toBatchUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    fun getUserPublicKeyByLocalKeys(localKeys: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys.toList()).toBatchUserPublicKeyResponse() }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: JsArray<String>): Promise<JsArray<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys.toList()).toBatchUserPublicKeyResponse() }
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
    fun getPinnedLocks(): Promise<JsArray<LockResponse>> {
        return promise { LockOperationsClient.getPinnedLocksRequest().toLockResponse() }
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    fun getShareableLocks(): Promise<JsArray<ShareableLockResponse>> {
        return promise { LockOperationsClient.getShareableLocksRequest().toShareableLockResponse() }
    }
}

private val lockOperations = LockOperationsApi

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
@JsExport
actual fun lockOperations(): LockOperationsApi = lockOperations