package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.exceptions.BatchShareFailedException
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.BasicBaseOperation
import com.doordeck.multiplatform.sdk.model.data.BasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicShareLock
import com.doordeck.multiplatform.sdk.model.data.BasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.responses.BasicLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockSettingsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockStateResponse
import com.doordeck.multiplatform.sdk.randomPrivateKey
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.respondContent
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.addExceptionInterceptor
import com.doordeck.multiplatform.sdk.util.installAuth
import com.doordeck.multiplatform.sdk.util.installContentNegotiation
import com.doordeck.multiplatform.sdk.util.installResponseValidator
import io.ktor.client.HttpClient
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondError
import io.ktor.client.engine.mock.respondOk
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.Uuid

class LockOperationsClientTest : IntegrationTest() {

    @Test
    fun shouldPerformOperationWithSystemClockBehind() = runTest {
        // Given
        SystemClock.setSkew(3.hours)
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val certificateChain = AccountClient.registerEphemeralKeyRequest(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()

        ).certificateChain
        val behindNow = Clock.System.now().minus(3.hours)
        val baseOperation = BasicUnlockOperation(
            baseOperation = BasicBaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = certificateChain,
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID,
                notBefore = behindNow,
                issuedAt = behindNow,
                expiresAt = behindNow.plus(60.seconds),
                jti = Uuid.random().toString(),
            )
        )

        LockOperationsClient.unlockRequest(baseOperation)
    }


    @Test
    fun shouldPerformOperationWithSystemClockAhead() = runTest {
        // Given
        SystemClock.setSkew(-(4).hours)
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val certificateChain = AccountClient.registerEphemeralKeyRequest(
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()

        ).certificateChain
        val aheadNow = Clock.System.now().plus(4.hours)
        val baseOperation = BasicUnlockOperation(
            baseOperation = BasicBaseOperation(
                userId = TEST_MAIN_USER_ID,
                userCertificateChain = certificateChain,
                userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
                lockId = TEST_MAIN_LOCK_ID,
                notBefore = aheadNow,
                issuedAt = aheadNow,
                expiresAt = aheadNow.plus(60.seconds),
                jti = Uuid.random().toString(),
            )
        )

        LockOperationsClient.unlockRequest(baseOperation)
    }

    @Test
    fun shouldThrowBatchShareFailedExceptionWithTheFailedUserIds() = runTest {
        // Given
        val now = Clock.System.now()
        val batchShareLockOperation = BasicBatchShareLockOperation(
            baseOperation = BasicBaseOperation(
                userId = randomUuidString(),
                userCertificateChain = listOf(randomString()),
                userPrivateKey = randomPrivateKey(),
                lockId = randomUuidString(),
                notBefore = now,
                issuedAt = now,
                expiresAt = now.plus(60.seconds),
                jti = Uuid.random().toString()
            ),
            users = (1..3).map {
                BasicShareLock(
                    targetUserId = randomUuidString(),
                    targetUserRole = UserRole.USER,
                    targetUserPublicKey = randomPublicKey()
                )
            }
        )
        val lockResponse = BasicLockResponse(
            id = batchShareLockOperation.baseOperation.lockId,
            name = randomString(),
            role = UserRole.ADMIN,
            settings = BasicLockSettingsResponse(
                unlockTime = 5.0,
                permittedAddresses = emptyList(),
                defaultName = randomString(),
                tiles = emptyList(),
                hidden = false,
                capabilities = emptyMap()
            ),
            state = BasicLockStateResponse(connected = true),
            favourite = false
        )

        val mockEngine = MockEngine.config {
            addHandler {
                respondContent(lockResponse)
            }
            addHandler {
                respondOk()
            }
            addHandler {
                respondError(HttpStatusCode.Forbidden)
            }
            addHandler {
                respondError(HttpStatusCode.Forbidden)
            }
        }

        val client = HttpClient(mockEngine) {
            installResponseValidator()
            installContentNegotiation()
            installAuth()
        }.also { it.addExceptionInterceptor() }
        CloudHttpClient.overrideClient(client)

        client.use { _ ->
            // When
            val exception = assertFailsWith<BatchShareFailedException> {
                LockOperationsClient.batchShareLockRequest(batchShareLockOperation)
            }

            // Then
            assertEquals("Batch share failed", exception.message)
            assertEquals(batchShareLockOperation.users.drop(1).map { it.targetUserId }, exception.userIds)
        }
    }
}