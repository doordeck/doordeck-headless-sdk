package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.GetAuditForUserData
import com.doordeck.multiplatform.sdk.model.data.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.model.data.GetLocksForUserData
import com.doordeck.multiplatform.sdk.model.data.GetSingleLockData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephonesData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForLockData
import com.doordeck.multiplatform.sdk.model.data.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.ShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.UnlockOperationData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.model.data.toBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toTimeRequirementList
import com.doordeck.multiplatform.sdk.model.data.toUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.handleCallback

actual object LockOperationsApi {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    @CName("getSingleLock")
    fun getSingleLock(data: String, callback: CStringCallback) = callback.handleCallback {
        val getSingleLockData = data.fromJson<GetSingleLockData>()
        LockOperationsClient.getSingleLockRequest(getSingleLockData.lockId)
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    @CName("getLockAuditTrail")
    fun getLockAuditTrail(data: String, callback: CStringCallback) = callback.handleCallback {
        val getLockAuditTrailData = data.fromJson<GetLockAuditTrailData>()
        LockOperationsClient.getLockAuditTrailRequest(
            lockId = getLockAuditTrailData.lockId,
            start = getLockAuditTrailData.start,
            end = getLockAuditTrailData.end
        )
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    @CName("getAuditForUser")
    fun getAuditForUser(data: String, callback: CStringCallback) = callback.handleCallback {
        val getAuditForUserData = data.fromJson<GetAuditForUserData>()
        LockOperationsClient.getAuditForUserRequest(
            userId = getAuditForUserData.userId,
            start = getAuditForUserData.start,
            end = getAuditForUserData.end
        )
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    @CName("getUsersForLock")
    fun getUsersForLock(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUsersForLockData = data.fromJson<GetUsersForLockData>()
        LockOperationsClient.getUsersForLockRequest(getUsersForLockData.lockId)
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    @CName("getLocksForUser")
    fun getLocksForUser(data: String, callback: CStringCallback) = callback.handleCallback {
        val getLocksForUserData = data.fromJson<GetLocksForUserData>()
        LockOperationsClient.getLocksForUserRequest(getLocksForUserData.userId)
    }

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockName")
    fun updateLockName(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateLockNameData = data.fromJson<UpdateLockNameData>()
        LockOperationsClient.updateLockNameRequest(
            lockId = updateLockNameData.lockId,
            name = updateLockNameData.name
        )
    }

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockFavourite")
    fun updateLockFavourite(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateLockFavouriteData = data.fromJson<UpdateLockFavouriteData>()
        LockOperationsClient.updateLockFavouriteRequest(
            lockId = updateLockFavouriteData.lockId,
            favourite = updateLockFavouriteData.favourite
        )
    }

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingDefaultName")
    fun updateLockSettingDefaultName(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateLockSettingDefaultNameData = data.fromJson<UpdateLockSettingDefaultNameData>()
        LockOperationsClient.updateLockSettingDefaultNameRequest(
            lockId = updateLockSettingDefaultNameData.lockId,
            name = updateLockSettingDefaultNameData.name
        )
    }

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("setLockSettingPermittedAddresses")
    fun setLockSettingPermittedAddresses(data: String, callback: CStringCallback) = callback.handleCallback {
        val setLockSettingPermittedAddressesData = data.fromJson<SetLockSettingPermittedAddressesData>()
        LockOperationsClient.setLockSettingPermittedAddressesRequest(
            lockId = setLockSettingPermittedAddressesData.lockId,
            permittedAddresses = setLockSettingPermittedAddressesData.permittedAddresses
        )
    }

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingHidden")
    fun updateLockSettingHidden(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateLockSettingHiddenData = data.fromJson<UpdateLockSettingHiddenData>()
        LockOperationsClient.updateLockSettingHiddenRequest(
            lockId = updateLockSettingHiddenData.lockId,
            hidden = updateLockSettingHiddenData.hidden
        )
    }

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("setLockSettingTimeRestrictions")
    fun setLockSettingTimeRestrictions(data: String, callback: CStringCallback) = callback.handleCallback {
        val setLockSettingTimeRestrictionsData = data.fromJson<SetLockSettingTimeRestrictionsData>()
        LockOperationsClient.setLockSettingTimeRestrictionsRequest(
            lockId = setLockSettingTimeRestrictionsData.lockId,
            times = setLockSettingTimeRestrictionsData.times.toTimeRequirementList()
        )
    }

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingLocationRestrictions")
    fun updateLockSettingLocationRestrictions(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateLockSettingLocationRestrictionsData = data.fromJson<UpdateLockSettingLocationRestrictionsData>()
        LockOperationsClient.updateLockSettingLocationRestrictionsRequest(
            lockId = updateLockSettingLocationRestrictionsData.lockId,
            location = updateLockSettingLocationRestrictionsData.location?.toLocationRequirement()
        )
    }

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @CName("getUserPublicKey")
    fun getUserPublicKey(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyData = data.fromJson<GetUserPublicKeyData>()
        LockOperationsClient.getUserPublicKeyRequest(
            userEmail = getUserPublicKeyData.userEmail,
            visitor = getUserPublicKeyData.visitor
        )
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByEmail")
    fun getUserPublicKeyByEmail(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyData = data.fromJson<GetUserPublicKeyByEmailData>()
        LockOperationsClient.getUserPublicKeyByEmailRequest(getUserPublicKeyData.email)
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByTelephone")
    fun getUserPublicKeyByTelephone(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByTelephoneData = data.fromJson<GetUserPublicKeyByTelephoneData>()
        LockOperationsClient.getUserPublicKeyByTelephoneRequest(getUserPublicKeyByTelephoneData.telephone)
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByLocalKey")
    fun getUserPublicKeyByLocalKey(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByLocalKeyData = data.fromJson<GetUserPublicKeyByLocalKeyData>()
        LockOperationsClient.getUserPublicKeyByLocalKeyRequest(getUserPublicKeyByLocalKeyData.localKey)
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByForeignKey")
    fun getUserPublicKeyByForeignKey(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByForeignKeyData = data.fromJson<GetUserPublicKeyByForeignKeyData>()
        LockOperationsClient.getUserPublicKeyByForeignKeyRequest(getUserPublicKeyByForeignKeyData.foreignKey)
    }

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByIdentity")
    fun getUserPublicKeyByIdentity(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByIdentityData = data.fromJson<GetUserPublicKeyByIdentityData>()
        LockOperationsClient.getUserPublicKeyByIdentityRequest(getUserPublicKeyByIdentityData.identity)
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByEmails")
    fun getUserPublicKeyByEmails(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByEmailsData = data.fromJson<GetUserPublicKeyByEmailsData>()
        LockOperationsClient.getUserPublicKeyByEmailsRequest(getUserPublicKeyByEmailsData.emails)
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByTelephones")
    fun getUserPublicKeyByTelephones(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByTelephonesData = data.fromJson<GetUserPublicKeyByTelephonesData>()
        LockOperationsClient.getUserPublicKeyByTelephonesRequest(getUserPublicKeyByTelephonesData.telephones)
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByLocalKeys")
    fun getUserPublicKeyByLocalKeys(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByLocalKeysData = data.fromJson<GetUserPublicKeyByLocalKeysData>()
        LockOperationsClient.getUserPublicKeyByLocalKeysRequest(getUserPublicKeyByLocalKeysData.localKeys)
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByForeignKeys")
    fun getUserPublicKeyByForeignKeys(data: String, callback: CStringCallback) = callback.handleCallback {
        val getUserPublicKeyByForeignKeysData = data.fromJson<GetUserPublicKeyByForeignKeysData>()
        LockOperationsClient.getUserPublicKeyByForeignKeysRequest(
            foreignKeys = getUserPublicKeyByForeignKeysData.foreignKeys
        )
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    @CName("unlock")
    fun unlock(data: String, callback: CStringCallback) = callback.handleCallback {
        val unlockOperationData = data.fromJson<UnlockOperationData>()
        LockOperationsClient.unlockRequest(unlockOperationData.toUnlockOperation())
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    @CName("shareLock")
    fun shareLock(data: String, callback: CStringCallback) = callback.handleCallback {
        val shareLockOperationData = data.fromJson<ShareLockOperationData>()
        LockOperationsClient.shareLockRequest(shareLockOperationData.toShareLockOperation())
    }

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    @CName("batchShareLock")
    fun batchShareLock(data: String, callback: CStringCallback) = callback.handleCallback {
        val batchShareLockOperationData = data.fromJson<BatchShareLockOperationData>()
        LockOperationsClient.batchShareLockRequest(
            batchShareLockOperation = batchShareLockOperationData.toBatchShareLockOperation()
        )
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    @CName("revokeAccessToLock")
    fun revokeAccessToLock(data: String, callback: CStringCallback) = callback.handleCallback {
        val revokeAccessToLockOperationData = data.fromJson<RevokeAccessToLockOperationData>()
        LockOperationsClient.revokeAccessToLockRequest(
            revokeAccessToLockOperation = revokeAccessToLockOperationData.toRevokeAccessToLockOperation()
        )
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    @CName("updateSecureSettingUnlockDuration")
    fun updateSecureSettingUnlockDuration(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateSecureSettingUnlockDurationData = data.fromJson<UpdateSecureSettingUnlockDurationData>()
        LockOperationsClient.updateSecureSettingUnlockDurationRequest(
            updateSecureSettingUnlockDuration = updateSecureSettingUnlockDurationData
                .toUpdateSecureSettingUnlockDuration()
        )
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    @CName("updateSecureSettingUnlockBetween")
    fun updateSecureSettingUnlockBetween(data: String, callback: CStringCallback) = callback.handleCallback {
        val updateSecureSettingUnlockBetweenData = data.fromJson<UpdateSecureSettingUnlockBetweenData>()
        LockOperationsClient.updateSecureSettingUnlockBetweenRequest(
            updateSecureSettingUnlockBetween = updateSecureSettingUnlockBetweenData
                .toUpdateSecureSettingUnlockBetween()
        )
    }

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    @CName("getPinnedLocks")
    fun getPinnedLocks(callback: CStringCallback) = callback.handleCallback {
        LockOperationsClient.getPinnedLocksRequest()
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    @CName("getShareableLocks")
    fun getShareableLocks(callback: CStringCallback) = callback.handleCallback {
        LockOperationsClient.getShareableLocksRequest()
    }
}

actual fun lockOperations(): LockOperationsApi = LockOperationsApi