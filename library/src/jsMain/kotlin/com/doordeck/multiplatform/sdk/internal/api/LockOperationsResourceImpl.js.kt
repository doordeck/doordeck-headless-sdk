package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserAuditResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class LockOperationsResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl,
    localUnlock: LocalUnlockClient
) : LockOperationsClient(httpClient, contextManager, localUnlock), LockOperationsResource {
    
    override fun getSingleLock(lockId: String): Promise<LockResponse> {
        return GlobalScope.promise { getSingleLockRequest(lockId) }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<LockAuditTrailResponse>> {
        return GlobalScope.promise { getLockAuditTrailRequest(lockId, start, end) }
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<UserAuditResponse>> {
        return GlobalScope.promise { getAuditForUserRequest(userId, start, end) }
    }

    override fun getUsersForLock(lockId: String): Promise<List<UserLockResponse>> {
        return GlobalScope.promise { getUsersForLockRequest(lockId) }
    }

    override fun getLocksForUser(userId: String): Promise<LockUserResponse> {
        return GlobalScope.promise { getLocksForUserRequest(userId) }
    }

    override fun updateLockName(lockId: String, name: String?): Promise<Unit> {
        return GlobalScope.promise { updateLockNameRequest(lockId, name) }
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?): Promise<Unit> {
        return GlobalScope.promise { updateLockFavouriteRequest(lockId, favourite) }
    }

    override fun updateLockColour(lockId: String, colour: String?): Promise<Unit> {
        return GlobalScope.promise { updateLockColourRequest(lockId, colour) }
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?): Promise<Unit> {
        return GlobalScope.promise { updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<Unit> {
        return GlobalScope.promise { setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<Unit> {
        return GlobalScope.promise { updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<Unit> {
        return GlobalScope.promise { setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?): Promise<Unit> {
        return GlobalScope.promise { updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyRequest(userEmail, visitor) }
    }

    override fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyByEmailRequest(email) }
    }

    override fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> {
        return GlobalScope.promise { getUserPublicKeyByIdentityRequest(identity) }
    }

    override fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?): Promise<Unit> {
        return GlobalScope.promise { unlockWithContextRequest(lockId, directAccessEndpoints) }
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<Unit> {
        return GlobalScope.promise { unlockRequest(unlockOperation) }
    }

    override fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock): Promise<Unit> {
        return GlobalScope.promise { shareLockWithContextRequest(lockId, shareLock) }
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<Unit> {
        return GlobalScope.promise { shareLockRequest(shareLockOperation) }
    }

    override fun revokeAccessToLockWithContext(lockId: String, users: List<String>): Promise<Unit> {
        return GlobalScope.promise { revokeAccessToLockWithContextRequest(lockId, users) }
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<Unit> {
        return GlobalScope.promise { revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int): Promise<Unit> {
        return GlobalScope.promise { updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration) }
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<Unit> {
        return GlobalScope.promise { updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?): Promise<Unit> {
        return GlobalScope.promise { updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween) }
    }

    override fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<Unit> {
        return GlobalScope.promise { updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override fun getPinnedLocks(): Promise<List<LockResponse>> {
        return GlobalScope.promise { getPinnedLocksRequest() }
    }

    override fun getShareableLocks(): Promise<List<ShareableLockResponse>> {
        return GlobalScope.promise { getShareableLocksRequest() }
    }
}