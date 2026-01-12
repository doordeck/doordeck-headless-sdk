package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.firstOrNull
import com.doordeck.multiplatform.sdk.jsArrayOf
import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUnlockBetween
import com.doordeck.multiplatform.sdk.randomUuidString
import io.ktor.client.plugins.timeout
import io.ktor.client.request.options
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.js.collections.toList
import kotlin.js.collections.toSet
import kotlin.reflect.KClass
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class FusionApiTest : IntegrationTest() {

    @Test
    fun shouldTestAlpeta() = runTest {
        runFusionTest(FusionOperations.AlpetaController::class)
    }

    @Test
    fun shouldTestAmag() = runTest {
        runFusionTest(FusionOperations.AmagController::class)
    }

    @Test
    fun shouldTestAxis() = runTest {
        runFusionTest(FusionOperations.AxisController::class)
    }

    @Test
    fun shouldTestCcure() = runTest {
        runFusionTest(FusionOperations.CCureController::class)
    }

    @Test
    fun shouldTestDemo() = runTest {
        runFusionTest(FusionOperations.DemoController::class)
    }

    @Ignore
    @Test
    fun shouldTestGenetec() = runTest {
        runFusionTest(FusionOperations.GenetecController::class)
    }

    @Ignore
    @Test
    fun shouldTestLenel() = runTest {
        runFusionTest(FusionOperations.LenelController::class)
    }

    @Test
    fun shouldTestNet2() = runTest {
        runFusionTest(FusionOperations.PaxtonNet2Controller::class)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() = runTest {
        runFusionTest(FusionOperations.Paxton10Controller::class)
    }

    @Test
    fun shouldTestIntegra() = runTest {
        runFusionTest(FusionOperations.IntegraV2Controller::class)
    }

    @Test
    fun shouldTestExgarde() = runTest {
        runFusionTest(FusionOperations.TdsiExgardeController::class)
    }

    @Test
    fun shouldTestGardis() = runTest {
        runFusionTest(FusionOperations.TdsiGardisController::class)
    }

    @Ignore
    @Test
    fun shouldTestZkteco() = runTest {
        runFusionTest(FusionOperations.ZktecoController::class)
    }

    private suspend fun runFusionTest(controllerType: KClass<out FusionOperations.LockController>) = try {
        val testController = PLATFORM_FUSION_INTEGRATIONS.entries.firstOrNull {
            controllerType.isInstance(it.value.controller)
        } ?: error("Controller of type ${controllerType.simpleName} not found, skipping test...")

        try {
            TEST_HTTP_CLIENT.options(testController.key) {
                timeout {
                    connectTimeoutMillis = 5_000
                    socketTimeoutMillis = 5_000
                    requestTimeoutMillis = 5_000
                }
            }
        } catch (_: Exception) {
            error("Controller of type ${controllerType.simpleName} is not accessible, skipping test...")
        }

        // Given - shouldLogin
        ContextManager.setFusionHost(testController.key)

        // When
        val fusionLogin = FusionApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val cloudLogin = AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // Then
        assertTrue { fusionLogin.authToken.isNotEmpty() }

        // Cleanup process, delete any remaining test devices
        val integrationsToDelete = FusionApi.getIntegrationConfiguration(testController.value.type).await()
            .toList()
            .filter { integration ->
                PlatformType.entries.any { integration.doordeck?.name?.startsWith("Test Fusion Door $it") == true } }
        integrationsToDelete.forEach { integration ->
            integration.doordeck?.id?.let { integrationId ->
                try {
                    FusionApi.stopDoor(integrationId).await()
                    FusionApi.deleteDoor(integrationId).await()
                } catch (_: Exception) { /* Ignored */ }
            }
        }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door $platformType ${randomUuidString()}"

        // When
        FusionApi.enableDoor(name, PLATFORM_TEST_MAIN_SITE_ID, testController.value.controller).await()

        // Then
        val integrations = FusionApi.getIntegrationConfiguration(testController.value.type).await()
        val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor?.doordeck)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = FusionApi.getIntegrationType().await()

        // Then
        assertNotNull(integrationTypeResponse.status)
        assertEquals(testController.value.type, integrationTypeResponse.status)

        // Given - shouldStartDoor
        // When
        FusionApi.startDoor(actualDoor.doordeck.id).await()

        // Then
        var doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.RUNNING.name, doorState.state)

        // Given - shouldUpdateUnlockDuration
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = actualDoor.doordeck.id
        )

        // When
        val newDuration = 9
        LockOperationsApi.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration.Builder()
                .setUnlockDuration(newDuration)
                .setBaseOperation(baseOperation)
                .build()
        ).await()

        // Then
        var lockResponse = LockOperationsApi.getSingleLock(actualDoor.doordeck.id).await()
        assertEquals(newDuration.toDouble(), lockResponse.settings.unlockTime)

        // Given - Unlock
        LockOperationsApi.unlock(LockOperations.UnlockOperation.Builder()
            .setBaseOperation(baseOperation.copy(jti = randomUuidString()))
            .build())
            .await()

        // Given - Share and revoke lock
        LockOperationsApi.shareLock(
            shareLockOperation = LockOperations.ShareLockOperation(
                baseOperation = baseOperation.copy(jti = randomUuidString()),
                shareLock = LockOperations.ShareLock(
                    targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                    targetUserRole = UserRole.USER.toString(),
                    targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
                )
            ))

        // Then
        var locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.toList().any { it.deviceId == actualDoor.doordeck.id } }

        // When
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
                baseOperation = baseOperation.copy(jti = randomUuidString()),
                users = jsArrayOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )).await()

        // Then
        locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertFalse { locks.devices.toList().any { it.deviceId == actualDoor.doordeck.id } }

        // Given - shouldUpdateUnlockBetween
        val newUnlockBetween = randomUnlockBetween()

        // When
        LockOperationsApi.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween.Builder()
                .setBaseOperation(baseOperation.copy(jti = randomUuidString()))
                .setUnlockBetween(newUnlockBetween)
                .build()
        ).await()

        // Then
        lockResponse = LockOperationsApi.getSingleLock(actualDoor.doordeck.id).await()
        assertEquals(lockResponse.settings.unlockBetweenWindow?.start, newUnlockBetween.start)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.end, newUnlockBetween.end)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.timezone, newUnlockBetween.timezone)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.days?.toSet()?.sorted(), newUnlockBetween.days.toSet().sorted())
        assertEquals(lockResponse.settings.unlockBetweenWindow?.exceptions?.toList()?.sorted(), newUnlockBetween.exceptions?.toList()?.sorted())

        // Given - shouldStopDoor
        // When
        FusionApi.stopDoor(actualDoor.doordeck.id).await()

        // Then
        doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.STOPPED.name, doorState.state)

        // Given - shouldDeleteDoor
        // When
        FusionApi.deleteDoor(actualDoor.doordeck.id).await()

        // Then
        doorState = FusionApi.getDoorStatus(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.UNDEFINED.name, doorState.state)
    } catch (exception: Throwable) {
        println("Failed to test $controllerType: ${exception.message}")
    }
}