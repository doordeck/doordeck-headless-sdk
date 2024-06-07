package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserAuditResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import kotlin.js.JsExport

@JsExport
interface LockOperationsResource {

    fun getAllLocks(): Array<LockResponse>
    fun getSingleLock(lockId: String): LockResponse
    fun getAuditForAUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse>
    fun getUsersForALock(lockId: String): Array<UserLockResponse>
    fun getLocksForAUser(userId: String): LockUserResponse
    fun updateLockProperties(lockId: String, lockProperties: LockOperations.LockProperties)
    fun pairWithNewLock(key: String, name: String)
    fun getADoordeckUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse
    fun lock(lockOperation: LockOperations.LockOperation)
    fun unlock(unlockOperation: LockOperations.UnlockOperation)
    fun shareALock(shareALockOperation: LockOperations.ShareALockOperation)
    fun revokeAccessToALock(revokeAccessToALockOperation: LockOperations.RevokeAccessToALockOperation)
    fun removeSecureSettings(removeSecureSettingsOperation: LockOperations.RemoveSecureSettingsOperation)
    fun updateSecureSettings(updateSecureSettingsOperation: LockOperations.UpdateSecureSettingsOperation)
    fun getPinnedLocks(): Array<LockResponse>
    fun getShareableLocks(): Array<ShareableLockResponse>
}