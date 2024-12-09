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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class LockOperationsResourceImpl(
    private val lockOperationsClient: LockOperationsClient
) : LockOperationsResource {
    
    override fun getSingleLock(lockId: String): Promise<LockResponse> {
        return GlobalScope.promise { lockOperationsClient.getSingleLockRequest(lockId) }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return GlobalScope.promise { lockOperationsClient.getLockAuditTrailRequest(lockId, start, end) }
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return GlobalScope.promise { lockOperationsClient.getAuditForUserRequest(userId, start, end) }
    }

    override fun getUsersForLock(lockId: String): Promise<List<UserLockResponse>> {
        return GlobalScope.promise { lockOperationsClient.getUsersForLockRequest(lockId) }
    }

    override fun getLocksForUser(userId: String): Promise<LockUserResponse> {
        return GlobalScope.promise { lockOperationsClient.getLocksForUserRequest(userId) }
    }

    override fun updateLockName(lockId: String, name: String?): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    override fun updateLockColour(lockId: String, colour: String?): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyRequest(userEmail, visitor) }
    }

    override fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByEmailRequest(email) }
    }

    override fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByIdentityRequest(identity) }
    }

    override fun getUserPublicKeyByEmails(emails: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByEmailsRequest(emails) }
    }

    override fun getUserPublicKeyByTelephones(telephones: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones) }
    }

    override fun getUserPublicKeyByLocalKeys(localKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys) }
    }

    override fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.promise { lockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys) }
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.unlockRequest(unlockOperation) }
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.shareLockRequest(shareLockOperation) }
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<Unit> {
        return GlobalScope.promise { lockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override fun getPinnedLocks(): Promise<List<LockResponse>> {
        return GlobalScope.promise { lockOperationsClient.getPinnedLocksRequest() }
    }

    override fun getShareableLocks(): Promise<List<ShareableLockResponse>> {
        return GlobalScope.promise { lockOperationsClient.getShareableLocksRequest() }
    }
}