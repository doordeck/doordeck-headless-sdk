package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.PlatformType
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.model.UserRole
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.getPlatform
import com.doordeck.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.sdk.util.Crypto.stringToCertificateChain
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days

class LockOperationsResourceTest : SystemTest() {
    // Initialize the resource
    private val resource = LockOperationsResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

    private val updatedLockName = "Demo ${uuid4()} Lock"
    private val updatedLockColour = "#000000"
    private val updatedLockDefaultName = "Demo ${uuid4()} Lock"
    private val updatedLockPermittedAddresses = arrayOf("73.238.49.118")
    private val updatedFavourite = true
    private val updatedHidden = false
    private val updatedUnlockDuration = 30
    //private val updatedTimeRestriction = LockOperations.TimeRequirement()
    //private val updatedLocationRestriction = LockOperations.LocationRequirement
    private val updatedUnlockBetween by lazy {
        val timezone = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(timezone)
        LockOperations.UnlockBetween(
            start = "${now.hour}:${now.minute - 1}",
            end = "${now.hour}:${now.minute + 5}",
            timezone = timezone.id,
            days = arrayOf(now.dayOfWeek.name),
            exceptions = null
        )
    }


    @Test
    fun shouldTestLockOperations() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }
        LibsodiumInitializer.initialize()

        shouldGetSingleLock()
        shouldUpdateLockName()
        shouldUpdateLockSettingHidden()
        shouldUpdateLockFavourite()
        shouldUpdateLockColour()
        shouldUpdateLockSettingDefaultName()
        //shouldUpdateLockSettingPermittedAddresses() // TODO
        //shouldUpdateLockSettingTimeRestrictions() // TODO
        //shouldUpdateLockSettingLocationRestrictions() // TODO
        shouldGetUserPublicKey()
        shouldGetUserPublicKeyByEmail()
        shouldGetLockAuditTrail()
        //shouldGetAuditForUser() // FAILS - EMPTY
        shouldGetUsersForLock()
        shouldGetLockForUser()
        shouldGetPinnedLocks()
        shouldGetShareableLocks()
        shouldUnlock()
        shouldShareLock()
        shouldRevokeAccessToLock()
        //shouldUpdateSecureSettingUnlockDuration()
        //shouldUploadSecureSettingUnlockBetween()
    }

    private fun shouldGetSingleLock(): LockResponse {
        return resource.getSingleLock(TEST_MAIN_LOCK_ID)
    }

    private fun shouldUpdateLockName() {
        resource.updateLockName(TEST_MAIN_LOCK_ID, updatedLockName)

        val lock = shouldGetSingleLock()
        assertEquals(updatedLockName, lock.name)
    }

    private fun shouldUpdateLockFavourite() {
        resource.updateLockFavourite(TEST_MAIN_LOCK_ID, updatedFavourite)

        val lock = shouldGetSingleLock()
        assertEquals(updatedFavourite, lock.favourite)
    }

    private fun shouldUpdateLockColour() {
        resource.updateLockColour(TEST_MAIN_LOCK_ID, updatedLockColour)

        val lock = shouldGetSingleLock()
        assertEquals(updatedLockColour, lock.colour)
    }

    private fun shouldUpdateLockSettingDefaultName() {
        resource.updateLockSettingDefaultName(TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        val lock = shouldGetSingleLock()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    private fun shouldUpdateLockSettingPermittedAddresses() {
        resource.updateLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, updatedLockPermittedAddresses)

        var lock = shouldGetSingleLock()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertEquals(updatedLockPermittedAddresses, lock.settings.permittedAddresses)

        resource.updateLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, null)
        lock = shouldGetSingleLock()
        assertNull(lock.settings.permittedAddresses)
    }

    private fun shouldUpdateLockSettingHidden() {
        resource.updateLockSettingHidden(TEST_MAIN_LOCK_ID, updatedHidden)

        val lock = shouldGetSingleLock()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    private fun shouldUpdateLockSettingTimeRestrictions() {
        //resource.updateLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, )
    }

    private fun shouldUpdateLockSettingLocationRestrictions() {
        //resource.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, )
    }

    private fun shouldGetUserPublicKey() {
        resource.getUserPublicKey(TEST_MAIN_USER_EMAIL, true)
    }

    private fun shouldGetUserPublicKeyByEmail() {
        resource.getUserPublicKey(TEST_MAIN_USER_EMAIL)
    }

    private fun shouldGetLockAuditTrail() {
        val now = Clock.System.now()
        val lockAuditTrail = resource.getLockAuditTrail(TEST_MAIN_LOCK_ID,
            now.minus(7.days).epochSeconds.toInt(), now.epochSeconds.toInt())
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    private fun shouldGetAuditForUser() {
        val now = Clock.System.now()
        val auditForUser = resource.getAuditForUser(TEST_MAIN_LOCK_ID,
            now.minus(7.days).epochSeconds.toInt(), now.epochSeconds.toInt())
        assertTrue { auditForUser.isNotEmpty() }
    }

    private fun shouldGetUsersForLock() {
        val usersForLock = resource.getUsersForLock(TEST_MAIN_LOCK_ID)
        assertTrue { usersForLock.isNotEmpty() }
    }

    private fun shouldGetLockForUser() {
        val locksForUser = resource.getLocksForUser(TEST_MAIN_USER_ID)
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    private fun shouldGetPinnedLocks() {
        val pinnedLocks = resource.getPinnedLocks()
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    private fun shouldGetShareableLocks() {
        val shareableLocks = resource.getShareableLocks()
        assertTrue { shareableLocks.isNotEmpty() }
    }

    private fun shouldUnlock() {
        resource.unlock(LockOperations.UnlockOperation(
            baseOperation = LockOperations.BaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID
            )
        ))
    }

    private fun shouldShareLock() {
        resource.shareLock(LockOperations.ShareLockOperation(
            baseOperation = LockOperations.BaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID
            ),
            targetUserId = TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        ))
    }

    private fun shouldRevokeAccessToLock() {
        resource.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID
            ),
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        ))
    }

    private fun shouldUpdateSecureSettingUnlockDuration() {
        resource.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID
            ),
            unlockDuration = updatedUnlockDuration
        ))

        val lock = shouldGetSingleLock()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    private fun shouldUploadSecureSettingUnlockBetween() {
        resource.uploadSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID
            ),
            unlockBetween = updatedUnlockBetween
        ))

        val lock = shouldGetSingleLock()
        assertEquals(lock.settings.usageRequirements?.time?.start, updatedUnlockBetween.start)
        assertEquals(lock.settings.usageRequirements?.time?.end, updatedUnlockBetween.end)
        assertEquals(lock.settings.usageRequirements?.time?.timezone, updatedUnlockBetween.timezone)
        assertEquals(lock.settings.usageRequirements?.time?.days, updatedUnlockBetween.days)
    }
}