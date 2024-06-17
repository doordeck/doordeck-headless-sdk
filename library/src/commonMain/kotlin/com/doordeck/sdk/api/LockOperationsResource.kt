package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.api.responses.LockAuditTrail
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserAuditResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import kotlin.js.JsExport

@JsExport
interface LockOperationsResource {

    fun getSingleLock(lockId: String): LockResponse
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrail>
    fun getAuditForUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse>
    fun getUsersForLock(lockId: String): Array<UserLockResponse>
    fun getLocksForUser(userId: String): LockUserResponse
    fun updateLockName(lockId: String, name: String? = null): EmptyResponse
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null): EmptyResponse
    fun updateLockColour(lockId: String, colour: String? = null): EmptyResponse
    fun updateLockSettingDefaultName(lockId: String, name: String? = null): EmptyResponse
    fun updateLockSettingPermittedAddresses(lockId: String, permittedAddress: Array<String>? = null): EmptyResponse
    fun updateLockSettingDelay(lockId: String, delay: Int? = null): EmptyResponse
    fun updateLockSettingHidden(lockId: String, hidden: Boolean? = null): EmptyResponse
    fun updateLockSettingUsageRequirementTime(lockId: String, time: LockOperations.TimeRequirement? = null): EmptyResponse
    fun updateLockSettingUsageRequirementLocationCoordinates(lockId: String, latitude: Double, longitude: Double): EmptyResponse
    fun updateLockSettingUsageRequirementLocationEnabled(lockId: String, enabled: Boolean? = null): EmptyResponse
    fun updateLockSettingUsageRequirementLocationRadius(lockId: String, radius: Int? = null): EmptyResponse
    fun updateLockSettingUsageRequirementLocationAccuracy(lockId: String, accuracy: Int? = null): EmptyResponse
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse
    fun unlock(unlockOperation: LockOperations.UnlockOperation): EmptyResponse
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): EmptyResponse
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): EmptyResponse
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): EmptyResponse
    fun uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): EmptyResponse
    fun getPinnedLocks(): Array<LockResponse>
    fun getShareableLocks(): Array<ShareableLockResponse>
}