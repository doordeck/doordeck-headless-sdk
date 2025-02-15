package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.model.GetAuditForUserData
import com.doordeck.multiplatform.sdk.model.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.model.GetLocksForUserData
import com.doordeck.multiplatform.sdk.model.GetSingleLockData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByForeignKeysData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyByTelephonesData
import com.doordeck.multiplatform.sdk.model.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.model.GetUsersForLockData
import com.doordeck.multiplatform.sdk.model.LockOperations
import com.doordeck.multiplatform.sdk.model.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.model.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.model.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.model.ShareLockOperationData
import com.doordeck.multiplatform.sdk.model.UnlockOperationData
import com.doordeck.multiplatform.sdk.model.UpdateLockColourData
import com.doordeck.multiplatform.sdk.model.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.model.UpdateLockNameData
import com.doordeck.multiplatform.sdk.model.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.model.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.model.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.model.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.model.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.toBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.toLocationRequirement
import com.doordeck.multiplatform.sdk.model.toRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.toShareLockOperation
import com.doordeck.multiplatform.sdk.model.toTimeRequirementList
import com.doordeck.multiplatform.sdk.model.toUnlockOperation
import com.doordeck.multiplatform.sdk.model.toUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.toUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

actual object LockOperationsApi {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    fun getSingleLock(lockId: String): LockResponse {
        return runBlocking { LockOperationsClient.getSingleLockRequest(lockId) }
    }

    @CName("getSingleLockJson")
    fun getSingleLockJson(data: String): String {
        return resultData {
            val getSingleLockData = data.fromJson<GetSingleLockData>()
            getSingleLock(getSingleLockData.lockId)
        }
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return runBlocking { LockOperationsClient.getLockAuditTrailRequest(lockId, start, end) }
    }

    @CName("getLockAuditTrailJson")
    fun getLockAuditTrailJson(data: String): String {
        return resultData {
            val getLockAuditTrailData = data.fromJson<GetLockAuditTrailData>()
            getLockAuditTrail(getLockAuditTrailData.lockId, getLockAuditTrailData.start, getLockAuditTrailData.end)
        }
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse> {
        return runBlocking { LockOperationsClient.getAuditForUserRequest(userId, start, end) }
    }

    @CName("getAuditForUserJson")
    fun getAuditForUserJson(data: String): String {
        return resultData {
            val getAuditForUserData = data.fromJson<GetAuditForUserData>()
            getAuditForUser(getAuditForUserData.userId, getAuditForUserData.start, getAuditForUserData.end)
        }
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return runBlocking { LockOperationsClient.getUsersForLockRequest(lockId) }
    }

    @CName("getUsersForLockJson")
    fun getUsersForLockJson(data: String): String {
        return resultData {
            val getUsersForLockData = data.fromJson<GetUsersForLockData>()
            getUsersForLock(getUsersForLockData.lockId)
        }
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    fun getLocksForUser(userId: String): LockUserResponse {
        return runBlocking { LockOperationsClient.getLocksForUserRequest(userId) }
    }

    @CName("getLocksForUserJson")
    fun getLocksForUserJson(data: String): String {
        return resultData {
            val getLocksForUserData = data.fromJson<GetLocksForUserData>()
            getLocksForUser(getLocksForUserData.userId)
        }
    }

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockName(lockId: String, name: String? = null) {
        return runBlocking { LockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    @CName("updateLockNameJson")
    fun updateLockNameJson(data: String): String {
        return resultData {
            val updateLockNameData = data.fromJson<UpdateLockNameData>()
            updateLockName(updateLockNameData.lockId, updateLockNameData.name)
        }
    }

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null) {
        return runBlocking { LockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    @CName("updateLockFavouriteJson")
    fun updateLockFavouriteJson(data: String): String {
        return resultData {
            val updateLockFavouriteData = data.fromJson<UpdateLockFavouriteData>()
            updateLockFavourite(updateLockFavouriteData.lockId, updateLockFavouriteData.favourite)
        }
    }

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockColour(lockId: String, colour: String? = null) {
        return runBlocking { LockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    @CName("updateLockColourJson")
    fun updateLockColourJson(data: String): String {
        return resultData {
            val updateLockColourData = data.fromJson<UpdateLockColourData>()
            updateLockColour(updateLockColourData.lockId, updateLockColourData.colour)
        }
    }

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null) {
        return runBlocking { LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    @CName("updateLockSettingDefaultNameJson")
    fun updateLockSettingDefaultNameJson(data: String): String {
        return resultData {
            val updateLockSettingDefaultNameData = data.fromJson<UpdateLockSettingDefaultNameData>()
            updateLockSettingDefaultName(updateLockSettingDefaultNameData.lockId, updateLockSettingDefaultNameData.name)
        }
    }

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return runBlocking { LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    @CName("setLockSettingPermittedAddressesJson")
    fun setLockSettingPermittedAddressesJson(data: String): String {
        return resultData {
            val setLockSettingPermittedAddressesData = data.fromJson<SetLockSettingPermittedAddressesData>()
            setLockSettingPermittedAddresses(setLockSettingPermittedAddressesData.lockId, setLockSettingPermittedAddressesData.permittedAddresses)
        }
    }

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return runBlocking { LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    @CName("updateLockSettingHiddenJson")
    fun updateLockSettingHiddenJson(data: String): String {
        return resultData {
            val updateLockSettingHiddenData = data.fromJson<UpdateLockSettingHiddenData>()
            updateLockSettingHidden(updateLockSettingHiddenData.lockId, updateLockSettingHiddenData.hidden)
        }
    }

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return runBlocking { LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    @CName("setLockSettingTimeRestrictionsJson")
    fun setLockSettingTimeRestrictionsJson(data: String): String {
        return resultData {
            val setLockSettingTimeRestrictionsData = data.fromJson<SetLockSettingTimeRestrictionsData>()
            setLockSettingTimeRestrictions(setLockSettingTimeRestrictionsData.lockId, setLockSettingTimeRestrictionsData.times.toTimeRequirementList())
        }
    }

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null) {
        return runBlocking { LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    @CName("updateLockSettingLocationRestrictionsJson")
    fun updateLockSettingLocationRestrictionsJson(data: String): String {
        return resultData {
            val updateLockSettingLocationRestrictionsData = data.fromJson<UpdateLockSettingLocationRestrictionsData>()
            updateLockSettingLocationRestrictions(updateLockSettingLocationRestrictionsData.lockId, updateLockSettingLocationRestrictionsData.location?.toLocationRequirement())
        }
    }

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor) }
    }

    @DoordeckOnly
    @CName("getUserPublicKeyJson")
    fun getUserPublicKeyJson(data: String): String {
        return resultData {
            val getUserPublicKeyData = data.fromJson<GetUserPublicKeyData>()
            getUserPublicKey(getUserPublicKeyData.userEmail, getUserPublicKeyData.visitor)
        }
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyByEmailRequest(email) }
    }

    @CName("getUserPublicKeyByEmailJson")
    fun getUserPublicKeyByEmailJson(data: String): String {
        return resultData {
            val getUserPublicKeyData = data.fromJson<GetUserPublicKeyByEmailData>()
            getUserPublicKeyByEmail(getUserPublicKeyData.email)
        }
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone) }
    }

    @CName("getUserPublicKeyByTelephoneJson")
    fun getUserPublicKeyByTelephoneJson(data: String): String {
        return resultData {
            val getUserPublicKeyByTelephoneData = data.fromJson<GetUserPublicKeyByTelephoneData>()
            getUserPublicKeyByTelephone(getUserPublicKeyByTelephoneData.telephone)
        }
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    @CName("getUserPublicKeyByLocalKeyJson")
    fun getUserPublicKeyByLocalKeyJson(data: String): String {
        return resultData {
            val getUserPublicKeyByLocalKeyData = data.fromJson<GetUserPublicKeyByLocalKeyData>()
            getUserPublicKeyByLocalKey(getUserPublicKeyByLocalKeyData.localKey)
        }
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    @CName("getUserPublicKeyByForeignKeyJson")
    fun getUserPublicKeyByForeignKeyJson(data: String): String {
        return resultData {
            val getUserPublicKeyByForeignKeyData = data.fromJson<GetUserPublicKeyByForeignKeyData>()
            getUserPublicKeyByForeignKey(getUserPublicKeyByForeignKeyData.foreignKey)
        }
    }

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return runBlocking { LockOperationsClient.getUserPublicKeyByIdentityRequest(identity) }
    }

    @CName("getUserPublicKeyByIdentityJson")
    fun getUserPublicKeyByIdentityJson(data: String): String {
        return resultData {
            val getUserPublicKeyByIdentityData = data.fromJson<GetUserPublicKeyByIdentityData>()
            getUserPublicKeyByIdentity(getUserPublicKeyByIdentityData.identity)
        }
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> {
        return runBlocking { LockOperationsClient.getUserPublicKeyByEmailsRequest(emails) }
    }

    @CName("getUserPublicKeyByEmailsJson")
    fun getUserPublicKeyByEmailsJson(data: String): String {
        return resultData {
            val getUserPublicKeyByEmailsData = data.fromJson<GetUserPublicKeyByEmailsData>()
            getUserPublicKeyByEmails(getUserPublicKeyByEmailsData.emails)
        }
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse> {
        return runBlocking { LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones) }
    }

    @CName("getUserPublicKeyByTelephonesJson")
    fun getUserPublicKeyByTelephonesJson(data: String): String {
        return resultData {
            val getUserPublicKeyByTelephonesData = data.fromJson<GetUserPublicKeyByTelephonesData>()
            getUserPublicKeyByTelephones(getUserPublicKeyByTelephonesData.telephones)
        }
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return runBlocking { LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys) }
    }

    @CName("getUserPublicKeyByLocalKeysJson")
    fun getUserPublicKeyByLocalKeysJson(data: String): String {
        return resultData {
            val getUserPublicKeyByLocalKeysData = data.fromJson<GetUserPublicKeyByLocalKeysData>()
            getUserPublicKeyByLocalKeys(getUserPublicKeyByLocalKeysData.localKeys)
        }
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return runBlocking { LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys) }
    }

    @CName("getUserPublicKeyByForeignKeysJson")
    fun getUserPublicKeyByForeignKeysJson(data: String): String {
        return resultData {
            val getUserPublicKeyByForeignKeysData = data.fromJson<GetUserPublicKeyByForeignKeysData>()
            getUserPublicKeyByForeignKeys(getUserPublicKeyByForeignKeysData.foreignKeys)
        }
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return runBlocking { LockOperationsClient.unlockRequest(unlockOperation) }
    }

    @CName("unlockJson")
    fun unlockJson(data: String): String {
        return resultData {
            val unlockOperationData = data.fromJson<UnlockOperationData>()
            unlock(unlockOperationData.toUnlockOperation())
        }
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return runBlocking { LockOperationsClient.shareLockRequest(shareLockOperation) }
    }

    @CName("shareLockJson")
    fun shareLockJson(data: String): String {
        return resultData {
            val shareLockOperationData = data.fromJson<ShareLockOperationData>()
            shareLock(shareLockOperationData.toShareLockOperation())
        }
    }

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        return runBlocking { LockOperationsClient.batchShareLockRequest(batchShareLockOperation) }
    }

    @CName("batchShareLockJson")
    fun batchShareLockJson(data: String): String {
        return resultData {
            val batchShareLockOperationData = data.fromJson<BatchShareLockOperationData>()
            batchShareLock(batchShareLockOperationData.toBatchShareLockOperation())
        }
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return runBlocking { LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    @CName("revokeAccessToLockJson")
    fun revokeAccessToLockJson(data: String): String {
        return resultData {
            val revokeAccessToLockOperationData = data.fromJson<RevokeAccessToLockOperationData>()
            revokeAccessToLock(revokeAccessToLockOperationData.toRevokeAccessToLockOperation())
        }
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return runBlocking { LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    @CName("updateSecureSettingUnlockDurationJson")
    fun updateSecureSettingUnlockDurationJson(data: String): String {
        return resultData {
            val updateSecureSettingUnlockDurationData = data.fromJson<UpdateSecureSettingUnlockDurationData>()
            updateSecureSettingUnlockDuration(updateSecureSettingUnlockDurationData.toUpdateSecureSettingUnlockDuration())
        }
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return runBlocking { LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    @CName("updateSecureSettingUnlockBetweenJson")
    fun updateSecureSettingUnlockBetweenJson(data: String): String {
        return resultData {
            val updateSecureSettingUnlockBetweenData = data.fromJson<UpdateSecureSettingUnlockBetweenData>()
            updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetweenData.toUpdateSecureSettingUnlockBetween())
        }
    }

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    fun getPinnedLocks(): List<LockResponse> {
        return runBlocking { LockOperationsClient.getPinnedLocksRequest() }
    }

    @CName("getPinnedLocksJson")
    fun getPinnedLocksJson(): String {
        return resultData {
            getPinnedLocks()
        }
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    fun getShareableLocks(): List<ShareableLockResponse> {
        return runBlocking { LockOperationsClient.getShareableLocksRequest() }
    }

    @CName("getShareableLocksJson")
    fun getShareableLocksJson(): String {
        return resultData {
            getShareableLocks()
        }
    }
}

actual fun lockOperations(): LockOperationsApi = LockOperationsApi