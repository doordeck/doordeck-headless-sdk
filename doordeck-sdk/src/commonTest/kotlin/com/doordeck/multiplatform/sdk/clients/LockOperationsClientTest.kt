package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.model.data.BasicBaseOperation
import com.doordeck.multiplatform.sdk.model.data.BasicUnlockOperation
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
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
}