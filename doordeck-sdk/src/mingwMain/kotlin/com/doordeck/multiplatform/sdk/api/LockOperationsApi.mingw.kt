package com.doordeck.multiplatform.sdk.api

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
import com.doordeck.multiplatform.sdk.model.data.UpdateLockColourData
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
import com.doordeck.multiplatform.sdk.util.launchCallback
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual object LockOperationsApi {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    @CName("getSingleLock")
    fun getSingleLock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getSingleLockData = data.fromJson<GetSingleLockData>()
                LockOperationsClient.getSingleLockRequest(getSingleLockData.lockId)
            },
            callback = callback
        )
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    @CName("getLockAuditTrail")
    fun getLockAuditTrail(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getLockAuditTrailData = data.fromJson<GetLockAuditTrailData>()
                LockOperationsClient.getLockAuditTrailRequest(getLockAuditTrailData.lockId, getLockAuditTrailData.start, getLockAuditTrailData.end)
            },
            callback = callback
        )
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    @CName("getAuditForUser")
    fun getAuditForUser(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getAuditForUserData = data.fromJson<GetAuditForUserData>()
                LockOperationsClient.getAuditForUserRequest(getAuditForUserData.userId, getAuditForUserData.start, getAuditForUserData.end)
            },
            callback = callback
        )
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    @CName("getUsersForLock")
    fun getUsersForLock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUsersForLockData = data.fromJson<GetUsersForLockData>()
                LockOperationsClient.getUsersForLockRequest(getUsersForLockData.lockId)
            },
            callback = callback
        )
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    @CName("getLocksForUser")
    fun getLocksForUser(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getLocksForUserData = data.fromJson<GetLocksForUserData>()
                LockOperationsClient.getLocksForUserRequest(getLocksForUserData.userId)
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockName")
    fun updateLockName(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockNameData = data.fromJson<UpdateLockNameData>()
                LockOperationsClient.updateLockNameRequest(updateLockNameData.lockId, updateLockNameData.name)
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockFavourite")
    fun updateLockFavourite(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockFavouriteData = data.fromJson<UpdateLockFavouriteData>()
                LockOperationsClient.updateLockFavouriteRequest(updateLockFavouriteData.lockId, updateLockFavouriteData.favourite)
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockColour")
    fun updateLockColour(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockColourData = data.fromJson<UpdateLockColourData>()
                LockOperationsClient.updateLockColourRequest(updateLockColourData.lockId, updateLockColourData.colour)
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingDefaultName")
    fun updateLockSettingDefaultName(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockSettingDefaultNameData = data.fromJson<UpdateLockSettingDefaultNameData>()
                LockOperationsClient.updateLockSettingDefaultNameRequest(updateLockSettingDefaultNameData.lockId, updateLockSettingDefaultNameData.name)
            },
            callback = callback
        )
    }

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("setLockSettingPermittedAddresses")
    fun setLockSettingPermittedAddresses(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val setLockSettingPermittedAddressesData = data.fromJson<SetLockSettingPermittedAddressesData>()
                LockOperationsClient.setLockSettingPermittedAddressesRequest(setLockSettingPermittedAddressesData.lockId, setLockSettingPermittedAddressesData.permittedAddresses)
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingHidden")
    fun updateLockSettingHidden(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockSettingHiddenData = data.fromJson<UpdateLockSettingHiddenData>()
                LockOperationsClient.updateLockSettingHiddenRequest(updateLockSettingHiddenData.lockId, updateLockSettingHiddenData.hidden)
            },
            callback = callback
        )
    }

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("setLockSettingTimeRestrictions")
    fun setLockSettingTimeRestrictions(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val setLockSettingTimeRestrictionsData = data.fromJson<SetLockSettingTimeRestrictionsData>()
                LockOperationsClient.setLockSettingTimeRestrictionsRequest(setLockSettingTimeRestrictionsData.lockId, setLockSettingTimeRestrictionsData.times.toTimeRequirementList())
            },
            callback = callback
        )
    }

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    @CName("updateLockSettingLocationRestrictions")
    fun updateLockSettingLocationRestrictions(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateLockSettingLocationRestrictionsData = data.fromJson<UpdateLockSettingLocationRestrictionsData>()
                LockOperationsClient.updateLockSettingLocationRestrictionsRequest(updateLockSettingLocationRestrictionsData.lockId, updateLockSettingLocationRestrictionsData.location?.toLocationRequirement())
            },
            callback = callback
        )
    }

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @CName("getUserPublicKey")
    fun getUserPublicKey(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyData = data.fromJson<GetUserPublicKeyData>()
                LockOperationsClient.getUserPublicKeyRequest(getUserPublicKeyData.userEmail, getUserPublicKeyData.visitor)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByEmail")
    fun getUserPublicKeyByEmail(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyData = data.fromJson<GetUserPublicKeyByEmailData>()
                LockOperationsClient.getUserPublicKeyByEmailRequest(getUserPublicKeyData.email)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByTelephone")
    fun getUserPublicKeyByTelephone(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByTelephoneData = data.fromJson<GetUserPublicKeyByTelephoneData>()
                LockOperationsClient.getUserPublicKeyByTelephoneRequest(getUserPublicKeyByTelephoneData.telephone)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByLocalKey")
    fun getUserPublicKeyByLocalKey(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByLocalKeyData = data.fromJson<GetUserPublicKeyByLocalKeyData>()
                LockOperationsClient.getUserPublicKeyByLocalKeyRequest(getUserPublicKeyByLocalKeyData.localKey)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByForeignKey")
    fun getUserPublicKeyByForeignKey(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByForeignKeyData = data.fromJson<GetUserPublicKeyByForeignKeyData>()
                LockOperationsClient.getUserPublicKeyByForeignKeyRequest(getUserPublicKeyByForeignKeyData.foreignKey)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    @CName("getUserPublicKeyByIdentity")
    fun getUserPublicKeyByIdentity(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByIdentityData = data.fromJson<GetUserPublicKeyByIdentityData>()
                LockOperationsClient.getUserPublicKeyByIdentityRequest(getUserPublicKeyByIdentityData.identity)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByEmails")
    fun getUserPublicKeyByEmails(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByEmailsData = data.fromJson<GetUserPublicKeyByEmailsData>()
                LockOperationsClient.getUserPublicKeyByEmailsRequest(getUserPublicKeyByEmailsData.emails)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByTelephones")
    fun getUserPublicKeyByTelephones(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByTelephonesData = data.fromJson<GetUserPublicKeyByTelephonesData>()
                LockOperationsClient.getUserPublicKeyByTelephonesRequest(getUserPublicKeyByTelephonesData.telephones)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByLocalKeys")
    fun getUserPublicKeyByLocalKeys(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByLocalKeysData = data.fromJson<GetUserPublicKeyByLocalKeysData>()
                LockOperationsClient.getUserPublicKeyByLocalKeysRequest(getUserPublicKeyByLocalKeysData.localKeys)
            },
            callback = callback
        )
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    @CName("getUserPublicKeyByForeignKeys")
    fun getUserPublicKeyByForeignKeys(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val getUserPublicKeyByForeignKeysData = data.fromJson<GetUserPublicKeyByForeignKeysData>()
                LockOperationsClient.getUserPublicKeyByForeignKeysRequest(getUserPublicKeyByForeignKeysData.foreignKeys)
            },
            callback = callback
        )
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    @CName("unlock")
    fun unlock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val unlockOperationData = data.fromJson<UnlockOperationData>()
                LockOperationsClient.unlockRequest(unlockOperationData.toUnlockOperation())
            },
            callback = callback
        )
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    @CName("shareLock")
    fun shareLock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val shareLockOperationData = data.fromJson<ShareLockOperationData>()
                LockOperationsClient.shareLockRequest(shareLockOperationData.toShareLockOperation())
            },
            callback = callback
        )
    }

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    @CName("batchShareLock")
    fun batchShareLock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val batchShareLockOperationData = data.fromJson<BatchShareLockOperationData>()
                LockOperationsClient.batchShareLockRequest(batchShareLockOperationData.toBatchShareLockOperation())
            },
            callback = callback
        )
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    @CName("revokeAccessToLock")
    fun revokeAccessToLock(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val revokeAccessToLockOperationData = data.fromJson<RevokeAccessToLockOperationData>()
                LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperationData.toRevokeAccessToLockOperation())
            },
            callback = callback
        )
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    @CName("updateSecureSettingUnlockDuration")
    fun updateSecureSettingUnlockDuration(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateSecureSettingUnlockDurationData = data.fromJson<UpdateSecureSettingUnlockDurationData>()
                LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDurationData.toUpdateSecureSettingUnlockDuration())
            },
            callback = callback
        )
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    @CName("updateSecureSettingUnlockBetween")
    fun updateSecureSettingUnlockBetween(data: String, callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                val updateSecureSettingUnlockBetweenData = data.fromJson<UpdateSecureSettingUnlockBetweenData>()
                LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetweenData.toUpdateSecureSettingUnlockBetween())
            },
            callback = callback
        )
    }

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    @CName("getPinnedLocks")
    fun getPinnedLocks(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                LockOperationsClient.getPinnedLocksRequest()
            },
            callback = callback
        )
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    @CName("getShareableLocks")
    fun getShareableLocks(callback: CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>) {
        launchCallback(
            block = {
                LockOperationsClient.getShareableLocksRequest()
            },
            callback = callback
        )
    }
}

actual fun lockOperations(): LockOperationsApi = LockOperationsApi