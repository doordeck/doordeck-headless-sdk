package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.UserRole
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
    fun updateLockProperties(lockId: String) // TODO
    fun pairWithNewLock(key: String, name: String)
    fun getADoordeckUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse
    fun lock(userId: String, x5c: Array<String>, lockId: String, privateKey: ByteArray, trackId: String? = null)
    fun unlock(userId: String, x5c: Array<String>, lockId: String, privateKey: ByteArray, trackId: String? = null)
    fun shareALock(userId: String, x5c: Array<String>, lockId: String, targetUserId: String,
                   targetUserRole: UserRole, targetUserPublicKey: ByteArray, privateKey: ByteArray,
                   start: Int? = null, end: Int? = null, trackId: String? = null)
    fun revokeAccessToALock(userId: String, x5c: Array<String>, lockId: String, users: Array<String>,
                            privateKey: ByteArray, trackId: String?)
    fun removeSecureSettings(userId: String, x5c: Array<String>, lockId: String,
                             privateKey: ByteArray, trackId: String?)
    fun updateSecureSettings(lockId: String) // TODO
    fun getPinnedLocks(): Array<LockResponse>
    fun getShareableLocks(): Array<ShareableLockResponse>
}