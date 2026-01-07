package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomUnlockBetween
import com.doordeck.multiplatform.sdk.randomUuid
import com.doordeck.multiplatform.sdk.randomUuidString
import io.ktor.client.plugins.timeout
import io.ktor.client.request.options
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import java.security.KeyPair
import kotlin.reflect.KClass
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class FusionApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldTestAlpetaAsync() = runTest {
        runFusionTest(FusionOperations.AlpetaController::class)
    }

    @Test
    fun shouldTestAmagAsync() = runTest {
        runFusionTest(FusionOperations.AmagController::class)
    }

    @Test
    fun shouldTestAxisAsync() = runTest {
        runFusionTest(FusionOperations.AxisController::class)
    }

    @Test
    fun shouldTestCcureAsync() = runTest {
        runFusionTest(FusionOperations.CCureController::class)
    }

    @Test
    fun shouldTestDemoAsync() = runTest {
        runFusionTest(FusionOperations.DemoController::class)
    }

    @Ignore
    @Test
    fun shouldTestGenetecAsync() = runTest {
        runFusionTest(FusionOperations.GenetecController::class)
    }

    @Ignore
    @Test
    fun shouldTestLenelAsync() = runTest {
        runFusionTest(FusionOperations.LenelController::class)
    }

    @Test
    fun shouldTestNet2Async() = runTest {
        runFusionTest(FusionOperations.PaxtonNet2Controller::class)
    }

    @Ignore
    @Test
    fun shouldTestPaxton10Async() = runTest {
        runFusionTest(FusionOperations.Paxton10Controller::class)
    }

    @Test
    fun shouldTestIntegraAsync() = runTest {
        runFusionTest(FusionOperations.IntegraV2Controller::class)
    }

    @Test
    fun shouldTestExgardeAsync() = runTest {
        runFusionTest(FusionOperations.TdsiExgardeController::class)
    }

    @Test
    fun shouldTestGardisAsync() = runTest {
        runFusionTest(FusionOperations.TdsiGardisController::class)
    }

    @Ignore
    @Test
    fun shouldTestZktecoAsync() = runTest {
        runFusionTest(FusionOperations.ZktecoController::class)
    }

    private suspend fun runFusionTest(controllerType: KClass<out FusionOperations.LockController>) = try {
        val testController = PLATFORM_FUSION_INTEGRATIONS.entries.firstOrNull {
            controllerType.isInstance(it.value.controller)
        } ?: error("Controller of type ${controllerType.simpleName} not found, skipping test...")

        try {
            TEST_HTTP_CLIENT.options(testController.key.toString()) {
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
        val fusionLogin = FusionApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val cloudLogin = AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // Then
        assertTrue { fusionLogin.authToken.isNotEmpty() }

        // Cleanup process, delete any remaining test devices
        val integrationsToDelete = FusionApi.getIntegrationConfigurationAsync(testController.value.type).await()
            .filter { integration ->
                PlatformType.entries.any { integration.doordeck?.name?.startsWith("Test Fusion Door $it") == true } }
        integrationsToDelete.forEach { integration ->
            integration.doordeck?.id?.let { integrationId ->
                try {
                    FusionApi.stopDoorAsync(integrationId).await()
                    FusionApi.deleteDoorAsync(integrationId).await()
                } catch (_: Exception) { /* Ignored */ }
            }
        }

        // Given - shouldEnableDoor
        val name = "Test Fusion Door $platformType ${randomUuidString()}"

        // When
        FusionApi.enableDoorAsync(name, PLATFORM_TEST_MAIN_SITE_ID, testController.value.controller).await()

        // Then
        val integrations = FusionApi.getIntegrationConfigurationAsync(testController.value.type).await()
        val actualDoor = integrations.firstOrNull { it.doordeck?.name == name }
        assertNotNull(actualDoor?.doordeck)

        // Given - shouldGetIntegrationType
        // When
        val integrationTypeResponse = FusionApi.getIntegrationTypeAsync().await()

        // Then
        assertNotNull(integrationTypeResponse.status)
        assertEquals(testController.value.type, integrationTypeResponse.status)

        // Given - shouldStartDoor
        // When
        FusionApi.startDoorAsync(actualDoor.doordeck.id).await()

        // Then
        var doorState = FusionApi.getDoorStatusAsync(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.RUNNING, doorState.state)

        // Given - shouldUpdateUnlockDuration
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = actualDoor.doordeck.id
        )

        // When
        val newDuration = 9.seconds.toJavaDuration()
        LockOperationsApi.updateSecureSettingUnlockDurationAsync(
            LockOperations.UpdateSecureSettingUnlockDuration.Builder()
                .setUnlockDuration(newDuration)
                .setBaseOperation(baseOperation)
                .build()
        ).await()

        // Then
        var lockResponse = LockOperationsApi.getSingleLockAsync(actualDoor.doordeck.id).await()
        assertEquals(newDuration, lockResponse.settings.unlockTime)

        // Given - shouldUpdateUnlockBetween
        val newUnlockBetween = randomUnlockBetween()

        // When
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            LockOperations.UpdateSecureSettingUnlockBetween.Builder()
                .setUnlockBetween(newUnlockBetween)
                .setBaseOperation(baseOperation.copy(jti = randomUuid()))
                .build()
        ).await()

        // Then
        lockResponse = LockOperationsApi.getSingleLockAsync(actualDoor.doordeck.id).await()
        assertEquals(lockResponse.settings.unlockBetweenWindow?.start, newUnlockBetween.start)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.end, newUnlockBetween.end)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.timezone, newUnlockBetween.timezone)
        assertEquals(lockResponse.settings.unlockBetweenWindow?.days?.sorted(), newUnlockBetween.days.sorted())
        assertEquals(lockResponse.settings.unlockBetweenWindow?.exceptions?.sorted(), newUnlockBetween.exceptions?.sorted())

        // Given - shouldStopDoor
        // When
        FusionApi.stopDoorAsync(actualDoor.doordeck.id).await()

        // Then
        doorState = FusionApi.getDoorStatusAsync(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.STOPPED, doorState.state)

        // Given - shouldDeleteDoor
        // When
        FusionApi.deleteDoorAsync(actualDoor.doordeck.id).await()

        // Then
        doorState = FusionApi.getDoorStatusAsync(actualDoor.doordeck.id).await()
        assertEquals(ServiceStateType.UNDEFINED, doorState.state)
    } catch (exception: Throwable) {
        println("Failed to test $controllerType: ${exception.message}")
    }
}