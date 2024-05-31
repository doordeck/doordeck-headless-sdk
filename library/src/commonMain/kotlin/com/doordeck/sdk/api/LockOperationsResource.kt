package com.doordeck.sdk.api

import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.OperationBodyRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import kotlin.js.JsExport

@JsExport
interface LockOperationsResource {

    fun getAllLocks(): Array<LockResponse>
    fun getSingleLock(lockId: String): LockResponse
    fun getAuditForAUser(lockId: String, start: Int, end: Int) // TODO
    fun getUsersForALock(lockId: String): Array<UserLockResponse>
    fun getLocksForAUser(userId: String): LockUserResponse
    fun updateLockProperties(lockId: String) // TODO
    fun pairWithNewLock() // TODO
    fun getADoordeckUserPublicKey(userEmail: String) // TODO
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse
    fun unlock(userId: String, x5c: Array<String>, lockId: String, locked: Boolean, privateKey: ByteArray,
               trackId: String? = null)
    fun shareALock(lockId: String) // TODO
    fun revokeAccessToALock(lockId: String) // TODO
    fun updateSecureSettings(lockId: String) // TODO
    fun getPinnedLocks(): Array<LockResponse>
    fun getShareableLocks(): Array<ShareableLockResponse>
}