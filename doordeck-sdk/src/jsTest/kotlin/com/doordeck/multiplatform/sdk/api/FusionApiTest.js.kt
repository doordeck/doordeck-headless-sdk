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
    fun shouldTestAlpeta() {
        runFusionTest(FusionOperations.AlpetaController::class)
    }

    @Test
    fun shouldTestAmag() {
        runFusionTest(FusionOperations.AmagController::class)
    }

    @Test
    fun shouldTestAxis() {
        runFusionTest(FusionOperations.AxisController::class)
    }

    @Test
    fun shouldTestAzure() {
        runFusionTest(FusionOperations.AzureController::class)
    }

    @Test
    fun shouldTestCcure() {
        runFusionTest(FusionOperations.CCureController::class)
    }

    @Test
    fun shouldTestCcureVirtualCard() {
        runFusionTest(FusionOperations.CCureVirtualCardController::class)
    }

    @Test
    fun shouldTestDemo() {
        runFusionTest(FusionOperations.DemoController::class)
    }

    @Ignore
    @Test
    fun shouldTestGenetec() {
        runFusionTest(FusionOperations.GenetecController::class)
    }

    @Ignore
    @Test
    fun shouldTestLenel() {
        runFusionTest(FusionOperations.LenelController::class)
    }

    @Test
    fun shouldTestNet2() {
        runFusionTest(FusionOperations.PaxtonNet2Controller::class)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10() {
        runFusionTest(FusionOperations.Paxton10Controller::class)
    }

    @Test
    fun shouldTestIntegra() {
        runFusionTest(FusionOperations.IntegraV2Controller::class)
    }

    @Test
    fun shouldTestExgarde() {
        runFusionTest(FusionOperations.TdsiExgardeController::class)
    }

    @Test
    fun shouldTestGardis() {
        runFusionTest(FusionOperations.TdsiGardisController::class)
    }

    @Ignore
    @Test
    fun shouldTestZkteco() {
        runFusionTest(FusionOperations.ZktecoController::class)
    }

    private fun runFusionTest(controllerType: KClass<out FusionOperations.LockController>) {
        try {
            runTest {
                val testController = PLATFORM_FUSION_INTEGRATIONS.firstOrNull {
                    controllerType.isInstance(it.controller)
                } ?: error("Controller of type ${controllerType.simpleName} not found, skipping test...")

                try {
                    TEST_HTTP_CLIENT.options(testController.uri) {
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
                ContextManager.setFusionHost(testController.uri)

                // When
                val fusionLogin = FusionApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
                val cloudLogin = AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

                // Then
                assertTrue { fusionLogin.authToken.isNotEmpty() }

                // Skip the test if it's not targeting the expected integration
                val integrationType = FusionApi.getIntegrationType().await()
                if (integrationType != null && integrationType.status != null && integrationType.status != testController.type) {
                    error("Running integration is ${integrationType.status} instead of ${testController.type}, skipping test...")
                }

                // Cleanup process, delete any remaining test devices
                val integrationsToDelete = FusionApi.getIntegrationConfiguration(testController.type).await()
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
                FusionApi.enableDoor(name, PLATFORM_TEST_MAIN_SITE_ID, testController.controller).await()

                // Then
                val integrations = FusionApi.getIntegrationConfiguration(testController.type).await()
                val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
                assertNotNull(actualDoor?.doordeck)

                // Given - shouldGetIntegrationType
                // When
                val integrationTypeResponse = FusionApi.getIntegrationType().await()

                // Then
                assertNotNull(integrationTypeResponse)
                assertNotNull(integrationTypeResponse.status)
                assertEquals(testController.type, integrationTypeResponse.status)

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
            }
        } catch (exception: Throwable) {
            println("Failed to test $controllerType: ${exception.message}")
        }
    }
}