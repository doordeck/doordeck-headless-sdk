package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.GetAuditForUserData
import com.doordeck.multiplatform.sdk.api.model.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.api.model.GetLocksForUserData
import com.doordeck.multiplatform.sdk.api.model.GetSingleLockData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForLockData
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.api.model.RevokeAccessToLockWithContextData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.ShareLockOperationData
import com.doordeck.multiplatform.sdk.api.model.ShareLockWithContextData
import com.doordeck.multiplatform.sdk.api.model.UnlockOperationData
import com.doordeck.multiplatform.sdk.api.model.UnlockWithContextData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockColourData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockBetweenWithContextData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockDurationWithContextData
import com.doordeck.multiplatform.sdk.api.model.toLocationRequirement
import com.doordeck.multiplatform.sdk.api.model.toRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.api.model.toShareLock
import com.doordeck.multiplatform.sdk.api.model.toShareLockOperation
import com.doordeck.multiplatform.sdk.api.model.toTimeRequirementList
import com.doordeck.multiplatform.sdk.api.model.toUnlockBetween
import com.doordeck.multiplatform.sdk.api.model.toUnlockOperation
import com.doordeck.multiplatform.sdk.api.model.toUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.api.model.toUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

internal class LockOperationsResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl,
    localUnlock: LocalUnlockClient
) : LockOperationsClient(httpClient, contextManager, localUnlock), LockOperationsResource {

    override fun getSingleLock(lockId: String): LockResponse {
        return runBlocking { getSingleLockRequest(lockId) }
    }

    override fun getSingleLockJson(data: String): String {
        val getSingleLockData = data.fromJson<GetSingleLockData>()
        return getSingleLock(getSingleLockData.lockId).toJson()
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return runBlocking { getLockAuditTrailRequest(lockId, start, end) }
    }

    override fun getLockAuditTrailJson(data: String): String {
        val getLockAuditTrailData = data.fromJson<GetLockAuditTrailData>()
        return getLockAuditTrail(getLockAuditTrailData.lockId, getLockAuditTrailData.start, getLockAuditTrailData.end).toJson()
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse> {
        return runBlocking { getAuditForUserRequest(userId, start, end) }
    }

    override fun getAuditForUserJson(data: String): String {
        val getAuditForUserData = data.fromJson<GetAuditForUserData>()
        return getAuditForUser(getAuditForUserData.userId, getAuditForUserData.start, getAuditForUserData.end).toJson()
    }

    override fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return runBlocking { getUsersForLockRequest(lockId) }
    }

    override fun getUsersForLockJson(data: String): String {
        val getUsersForLockData = data.fromJson<GetUsersForLockData>()
        return getUsersForLock(getUsersForLockData.lockId).toJson()
    }

    override fun getLocksForUser(userId: String): LockUserResponse {
        return runBlocking { getLocksForUserRequest(userId) }
    }

    override fun getLocksForUserJson(data: String): String {
        val getLocksForUserData = data.fromJson<GetLocksForUserData>()
        return getLocksForUser(getLocksForUserData.userId).toJson()
    }

    override fun updateLockName(lockId: String, name: String?) {
        return runBlocking { updateLockNameRequest(lockId, name) }
    }

    override fun updateLockNameJson(data: String) {
        val updateLockNameData = data.fromJson<UpdateLockNameData>()
        return updateLockName(updateLockNameData.lockId, updateLockNameData.name)
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return runBlocking { updateLockFavouriteRequest(lockId, favourite) }
    }

    override fun updateLockFavouriteJson(data: String) {
        val updateLockFavouriteData = data.fromJson<UpdateLockFavouriteData>()
        return updateLockFavourite(updateLockFavouriteData.lockId, updateLockFavouriteData.favourite)
    }

    override fun updateLockColour(lockId: String, colour: String?) {
        return runBlocking { updateLockColourRequest(lockId, colour) }
    }

    override fun updateLockColourJson(data: String) {
        val updateLockColourData = data.fromJson<UpdateLockColourData>()
        return updateLockColour(updateLockColourData.lockId, updateLockColourData.colour)
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return runBlocking { updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override fun updateLockSettingDefaultNameJson(data: String) {
        val updateLockSettingDefaultNameData = data.fromJson<UpdateLockSettingDefaultNameData>()
        return updateLockSettingDefaultName(updateLockSettingDefaultNameData.lockId, updateLockSettingDefaultNameData.name)
    }

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return runBlocking { setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override fun setLockSettingPermittedAddressesJson(data: String) {
        val setLockSettingPermittedAddressesData = data.fromJson<SetLockSettingPermittedAddressesData>()
        return setLockSettingPermittedAddresses(setLockSettingPermittedAddressesData.lockId, setLockSettingPermittedAddressesData.permittedAddresses)
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return runBlocking { updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override fun updateLockSettingHiddenJson(data: String) {
        val updateLockSettingHiddenData = data.fromJson<UpdateLockSettingHiddenData>()
        return updateLockSettingHidden(updateLockSettingHiddenData.lockId, updateLockSettingHiddenData.hidden)
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return runBlocking { setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override fun setLockSettingTimeRestrictionsJson(data: String) {
        val setLockSettingTimeRestrictionsData = data.fromJson<SetLockSettingTimeRestrictionsData>()
        return setLockSettingTimeRestrictions(setLockSettingTimeRestrictionsData.lockId, setLockSettingTimeRestrictionsData.times.toTimeRequirementList())
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return runBlocking { updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override fun updateLockSettingLocationRestrictionsJson(data: String) {
        val updateLockSettingLocationRestrictionsData = data.fromJson<UpdateLockSettingLocationRestrictionsData>()
        return updateLockSettingLocationRestrictions(updateLockSettingLocationRestrictionsData.lockId, updateLockSettingLocationRestrictionsData.location?.toLocationRequirement())
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyRequest(userEmail, visitor) }
    }

    override fun getUserPublicKeyJson(data: String): String {
        val getUserPublicKeyData = data.fromJson<GetUserPublicKeyData>()
        return getUserPublicKey(getUserPublicKeyData.userEmail, getUserPublicKeyData.visitor).toJson()
    }

    override fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByEmailRequest(email) }
    }

    override fun getUserPublicKeyByEmailJson(data: String): String {
        val getUserPublicKeyData = data.fromJson<GetUserPublicKeyByEmailData>()
        return getUserPublicKeyByEmail(getUserPublicKeyData.email).toJson()
    }

    override fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override fun getUserPublicKeyByTelephoneJson(data: String): String {
        val getUserPublicKeyByTelephoneData = data.fromJson<GetUserPublicKeyByTelephoneData>()
        return getUserPublicKeyByTelephone(getUserPublicKeyByTelephoneData.telephone).toJson()
    }

    override fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override fun getUserPublicKeyByLocalKeyJson(data: String): String {
        val getUserPublicKeyByLocalKeyData = data.fromJson<GetUserPublicKeyByLocalKeyData>()
        return getUserPublicKeyByLocalKey(getUserPublicKeyByLocalKeyData.localKey).toJson()
    }

    override fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override fun getUserPublicKeyByForeignKeyJson(data: String): String {
        val getUserPublicKeyByForeignKeyData = data.fromJson<GetUserPublicKeyByForeignKeyData>()
        return getUserPublicKeyByForeignKey(getUserPublicKeyByForeignKeyData.foreignKey).toJson()
    }

    override fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByIdentityRequest(identity) }
    }

    override fun getUserPublicKeyByIdentityJson(data: String): String {
        val getUserPublicKeyByIdentityData = data.fromJson<GetUserPublicKeyByIdentityData>()
        return getUserPublicKeyByIdentity(getUserPublicKeyByIdentityData.identity).toJson()
    }

    override fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return runBlocking { unlockWithContextRequest(lockId, directAccessEndpoints) }
    }

    override fun unlockWithContextJson(data: String) {
        val unlockWithContextData = data.fromJson<UnlockWithContextData>()
        return unlockWithContext(unlockWithContextData.lockId, unlockWithContextData.directAccessEndpoints)
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return runBlocking { unlockRequest(unlockOperation) }
    }

    override fun unlockJson(data: String) {
        val unlockOperationData = data.fromJson<UnlockOperationData>()
        return unlock(unlockOperationData.toUnlockOperation())
    }

    override fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return runBlocking { shareLockWithContextRequest(lockId, shareLock) }
    }

    override fun shareLockWithContextJson(data: String) {
        val shareLockWithContextData = data.fromJson<ShareLockWithContextData>()
        return shareLockWithContext(shareLockWithContextData.lockId, shareLockWithContextData.shareLock.toShareLock())
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return runBlocking { shareLockRequest(shareLockOperation) }
    }

    override fun shareLockJson(data: String) {
        val shareLockOperationData = data.fromJson<ShareLockOperationData>()
        return shareLock(shareLockOperationData.toShareLockOperation())
    }

    override fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return runBlocking { revokeAccessToLockWithContextRequest(lockId, users) }
    }

    override fun revokeAccessToLockWithContextJson(data: String) {
        val revokeAccessToLockWithContextData = data.fromJson<RevokeAccessToLockWithContextData>()
        return revokeAccessToLockWithContext(revokeAccessToLockWithContextData.lockId, revokeAccessToLockWithContextData.users)
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return runBlocking { revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override fun revokeAccessToLockJson(data: String) {
        val revokeAccessToLockOperationData = data.fromJson<RevokeAccessToLockOperationData>()
        return revokeAccessToLock(revokeAccessToLockOperationData.toRevokeAccessToLockOperation())
    }

    override fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return runBlocking { updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration) }
    }

    override fun updateSecureSettingUnlockDurationWithContextJson(data: String) {
        val updateSecureSettingUnlockDurationWithContextData = data.fromJson<UpdateSecureSettingUnlockDurationWithContextData>()
        return updateSecureSettingUnlockDurationWithContext(updateSecureSettingUnlockDurationWithContextData.lockId, updateSecureSettingUnlockDurationWithContextData.unlockDuration)
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return runBlocking { updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override fun updateSecureSettingUnlockDurationJson(data: String) {
        val updateSecureSettingUnlockDurationData = data.fromJson<UpdateSecureSettingUnlockDurationData>()
        return updateSecureSettingUnlockDuration(updateSecureSettingUnlockDurationData.toUpdateSecureSettingUnlockDuration())
    }

    override fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return runBlocking { updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween) }
    }

    override fun updateSecureSettingUnlockBetweenWithContextJson(data: String) {
        val updateSecureSettingUnlockBetweenWithContextData = data.fromJson<UpdateSecureSettingUnlockBetweenWithContextData>()
        return updateSecureSettingUnlockBetweenWithContext(updateSecureSettingUnlockBetweenWithContextData.lockId, updateSecureSettingUnlockBetweenWithContextData.unlockBetween?.toUnlockBetween())
    }

    override fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return runBlocking { updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override fun updateSecureSettingUnlockBetweenJson(data: String) {
        val updateSecureSettingUnlockBetweenData = data.fromJson<UpdateSecureSettingUnlockBetweenData>()
        return updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetweenData.toUpdateSecureSettingUnlockBetween())
    }

    override fun getPinnedLocks(): List<LockResponse> {
        return runBlocking { getPinnedLocksRequest() }
    }

    override fun getPinnedLocksJson(): String {
        return getPinnedLocks().toJson()
    }

    override fun getShareableLocks(): List<ShareableLockResponse> {
        return runBlocking { getShareableLocksRequest() }
    }

    override fun getShareableLocksJson(): String {
        return getShareableLocks().toJson()
    }
}