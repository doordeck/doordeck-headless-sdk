package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

internal object LockOperationsResourceImpl : LockOperationsResource {
    
    override fun getSingleLock(lockId: String): Promise<LockResponse> {
        return promise { LockOperationsClient.getSingleLockRequest(lockId) }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return promise { LockOperationsClient.getLockAuditTrailRequest(lockId, start, end) }
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return promise { LockOperationsClient.getAuditForUserRequest(userId, start, end) }
    }

    override fun getUsersForLock(lockId: String): Promise<List<UserLockResponse>> {
        return promise { LockOperationsClient.getUsersForLockRequest(lockId) }
    }

    override fun getLocksForUser(userId: String): Promise<LockUserResponse> {
        return promise { LockOperationsClient.getLocksForUserRequest(userId) }
    }

    override fun updateLockName(lockId: String, name: String?): Promise<Unit> {
        return promise { LockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?): Promise<Unit> {
        return promise { LockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    override fun updateLockColour(lockId: String, colour: String?): Promise<Unit> {
        return promise { LockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?): Promise<Unit> {
        return promise { LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<Unit> {
        return promise { LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<Unit> {
        return promise { LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<Unit> {
        return promise { LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?): Promise<Unit> {
        return promise { LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor) }
    }

    override fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailRequest(email) }
    }

    override fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByIdentityRequest(identity) }
    }

    override fun getUserPublicKeyByEmails(emails: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailsRequest(emails) }
    }

    override fun getUserPublicKeyByTelephones(telephones: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones) }
    }

    override fun getUserPublicKeyByLocalKeys(localKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys) }
    }

    override fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys) }
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<Unit> {
        return promise { LockOperationsClient.unlockRequest(unlockOperation) }
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<Unit> {
        return promise { LockOperationsClient.shareLockRequest(shareLockOperation) }
    }

    override fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation): Promise<dynamic> {
        return GlobalScope.promise { LockOperationsClient.batchShareLockRequest(batchShareLockOperation) }
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<Unit> {
        return promise { LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<Unit> {
        return promise { LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<Unit> {
        return promise { LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override fun getPinnedLocks(): Promise<List<LockResponse>> {
        return promise { LockOperationsClient.getPinnedLocksRequest() }
    }

    override fun getShareableLocks(): Promise<List<ShareableLockResponse>> {
        return promise { LockOperationsClient.getShareableLocksRequest() }
    }
}