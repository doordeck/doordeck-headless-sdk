package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_JWT
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.randomBoolean
import com.doordeck.multiplatform.sdk.randomEmail
import com.doordeck.multiplatform.sdk.randomPrivateKey
import com.doordeck.multiplatform.sdk.randomPublicKey
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.requestHistory
import com.doordeck.multiplatform.sdk.responseHistory
import com.doordeck.multiplatform.sdk.setupMockClient
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ContextManagerTest : CallbackTest() {

    @Test
    fun shouldStoreAndLoadContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random().name
        val fusionHost = randomUrlString()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuidString()
        val email = randomEmail()
        val certificateChain = PLATFORM_TEST_VALID_CERTIFICATE
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        val keyPairVerified = publicKey
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.apply {
            setApiEnvironment(apiEnvironment, callback = TestCallback)
            setCloudAuthToken(cloudAuthToken, callback = TestCallback)
            setCloudRefreshToken(cloudRefreshToken, callback = TestCallback)
            setFusionHost(fusionHost, callback = TestCallback)
            setFusionAuthToken(fusionAuthToken, callback = TestCallback)
            setUserId(userId, callback = TestCallback)
            setCertificateChain(certificateChain, callback = TestCallback)
            setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)
            setKeyPairVerified(keyPairVerified, callback = TestCallback)
            setUserEmail(email, callback = TestCallback)
        }

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        ContextManager.clearContext(callback = TestCallback)
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(apiEnvironment, callbackApiCall<ResultData<String?>> { ContextManager.getApiEnvironment(callback = TestCallback) }.unwrap())
        assertEquals(userId, callbackApiCall<ResultData<String?>> { ContextManager.getUserId(callback = TestCallback) }.unwrap())
        assertEquals(email, callbackApiCall<ResultData<String?>> { ContextManager.getUserEmail(callback = TestCallback) }.unwrap())
        assertEquals(certificateChain, callbackApiCall<ResultData<String?>> { ContextManager.getCertificateChain(callback = TestCallback) }.unwrap())
        val contextKeyPair = callbackApiCall<ResultData<String?>> { ContextManager.getKeyPair(callback = TestCallback) }.unwrap()?.fromJson<EncodedKeyPair>()
        assertEquals(publicKey, contextKeyPair?.publicKey)
        assertEquals(privateKey, contextKeyPair?.privateKey)
        assertTrue { callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairVerified(callback = TestCallback) }.unwrap() == true }
        assertEquals(cloudAuthToken, callbackApiCall<ResultData<String?>> { ContextManager.getCloudAuthToken(callback = TestCallback) }.unwrap())
        assertEquals(cloudRefreshToken, callbackApiCall<ResultData<String?>> { ContextManager.getCloudRefreshToken(callback = TestCallback) }.unwrap())
        assertEquals(fusionAuthToken, callbackApiCall<ResultData<String?>> { ContextManager.getFusionAuthToken(callback = TestCallback) }.unwrap())
        assertEquals(fusionHost, callbackApiCall<ResultData<String?>> { ContextManager.getFusionHost(callback = TestCallback) }.unwrap())
    }

    @Test
    fun shouldClearContext() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.entries.random().name
        val fusionHost = randomUrlString()
        val cloudAuthToken = randomString()
        val cloudRefreshToken = randomString()
        val fusionAuthToken = randomString()
        val userId = randomUuidString()
        val email = randomEmail()
        val certificateChain = PLATFORM_TEST_VALID_CERTIFICATE
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKeyEncoded = keyPair.public.encodeByteArrayToBase64()
        val privateKeyEncoded = keyPair.private.encodeByteArrayToBase64()
        val keyPairVerified = publicKeyEncoded
        ContextManager.apply {
            setApiEnvironment(apiEnvironment, callback = TestCallback)
            setCloudAuthToken(cloudAuthToken, callback = TestCallback)
            setCloudRefreshToken(cloudRefreshToken, callback = TestCallback)
            setFusionHost(fusionHost, callback = TestCallback)
            setFusionAuthToken(fusionAuthToken, callback = TestCallback)
            setUserId(userId, callback = TestCallback)
            setCertificateChain(certificateChain, callback = TestCallback)
            setKeyPair(EncodedKeyPair(publicKey = publicKeyEncoded, privateKey = privateKeyEncoded).toJson(), callback = TestCallback)
            setKeyPairVerified(keyPairVerified, callback = TestCallback)
            setUserEmail(email, callback = TestCallback)
        }

        // When
        ContextManager.clearContext(callback = TestCallback)

        // Then
        assertEquals(ApiEnvironment.PROD.name, callbackApiCall<ResultData<String?>> { ContextManager.getApiEnvironment(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getUserId(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getUserEmail(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCertificateChain(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getKeyPair(callback = TestCallback) }.unwrap())
        assertFalse { callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairVerified(callback = TestCallback) }.unwrap() == true }
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCloudAuthToken(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getCloudRefreshToken(callback = TestCallback) }.unwrap())
        assertNull(callbackApiCall<ResultData<String?>> { ContextManager.getFusionAuthToken(callback = TestCallback) }.unwrap())
        assertEquals(DEFAULT_FUSION_HOST, callbackApiCall<ResultData<String?>> { ContextManager.getFusionHost(callback = TestCallback) }.unwrap())
    }

    @Test
    fun shouldStoreOperationContext() = runTest {
        // Given
        val userId = randomUuidString()
        val certificateChain = (1..3).map { randomPublicKey().encodeByteArrayToBase64() }
        val publicKey = randomPublicKey()
        val privateKey = randomPrivateKey()
        val operationContextData = OperationContextData(
            userId = userId,
            certificateChain = certificateChain.certificateChainToString(),
            publicKey = publicKey.encodeByteArrayToBase64(),
            privateKey = privateKey.encodeByteArrayToBase64()
        )
        val settings = DefaultSecureStorage(MemorySettings())
        Context.setSecureStorageImpl(settings)
        ContextManager.setOperationContext(operationContextData.toJson(), callback = TestCallback)

        // When
        Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings())) // Override the storage so that it is not deleted upon a reset call
        Context.reset()
        Context.setSecureStorageImpl(settings) // Re-add the original storage

        // Then
        assertEquals(userId, Context.getUserId())
        assertContentEquals(certificateChain, Context.getCertificateChain())
        assertContentEquals(publicKey, Context.getPublicKey())
        assertContentEquals(privateKey, Context.getPrivateKey())
        assertContentEquals(publicKey, Context.getKeyPair()?.public)
        assertContentEquals(privateKey, Context.getKeyPair()?.private)
    }

    @Test
    fun shouldCheckAuthTokenValidity() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(
            BasicUserDetailsResponse(
                email = randomEmail(),
                displayName = randomString(),
                emailVerified = randomBoolean(),
                publicKey = randomPublicKey().encodeByteArrayToBase64()
            )
        )
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenValidityWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenInvalidity() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString(), callback = TestCallback)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenInvalidityWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString(), callback = TestCallback)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenNullValidity() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckAuthTokenNullValidityWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)

        client.use {
            // When
            callbackApiCall<ResultData<Boolean>> {
                ContextManager.isCloudAuthTokenInvalidOrExpired(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldCheckCertificateChainNullValidity() = runTest {
        // Given
        ContextManager.clearContext(callback = TestCallback)

        // When
        val result = callbackApiCall<ResultData<Boolean?>> { ContextManager.isCertificateChainInvalidOrExpired(callback = TestCallback) }.unwrap()

        // Then
        assertTrue { result == true }
    }

    @Test
    fun shouldCheckKeyPairNullValidity() = runTest {
        // Given
        ContextManager.clearContext(callback = TestCallback)

        // When
        val result = callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairValid(callback = TestCallback) }.unwrap()

        // Then
        assertFalse { result == true }
    }

    @Test
    fun shouldCheckKeyPairValidityWithInvalidKeys() = runTest {
        // Given
        val publicKey = randomString().encodeToByteArray()
        val privateKey = randomString().encodeToByteArray()
        Context.setKeyPair(publicKey, privateKey)

        // When
        val result = callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairValid(callback = TestCallback) }.unwrap()

        // Then
        assertFalse { result == true }
    }

    @Test
    fun shouldCheckKeyPairValidityWithNonMatchingKeys() = runTest {
        // Given
        val publicKey = CryptoManager.generateRawKeyPair().public.encodeByteArrayToBase64()
        val privateKey = CryptoManager.generateRawKeyPair().private.encodeByteArrayToBase64()
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)

        // When
        val result = callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairValid(callback = TestCallback) }.unwrap()

        // Then
        assertFalse { result == true }
    }

    @Test
    fun shouldCheckKeyPairValidity() = runTest {
        // Given
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)

        // When
        val result = callbackApiCall<ResultData<Boolean?>> { ContextManager.isKeyPairValid(callback = TestCallback) }.unwrap()

        // Then
        assertTrue { result == true }
    }

    @Test
    fun shouldUpdateApiEnvironment() = runTest {
        // Given
        val apiEnvironment = ApiEnvironment.STAGING.name

        // When
        ContextManager.setApiEnvironment(apiEnvironment, callback = TestCallback)

        // Then
        assertEquals(apiEnvironment, callbackApiCall<ResultData<String?>> { ContextManager.getApiEnvironment(callback = TestCallback) }.unwrap())
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalid() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString(), callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCloudTokenIsInvalidWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(randomString(), callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalid() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(
            BasicUserDetailsResponse(
                email = randomEmail(),
                displayName = randomString(),
                emailVerified = randomBoolean(),
                publicKey = randomPublicKey().encodeByteArrayToBase64()
            )
        )
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsInvalidWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_INVALID, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerified() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(
            BasicUserDetailsResponse(
                email = randomEmail(),
                displayName = randomString(),
                emailVerified = randomBoolean(),
                publicKey = randomPublicKey().encodeByteArrayToBase64()
            )
        )
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateKeyPairIsNotVerifiedWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.KEY_PAIR_IS_NOT_VERIFIED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalid() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(
            BasicUserDetailsResponse(
                email = randomEmail(),
                displayName = randomString(),
                emailVerified = randomBoolean(),
                publicKey = randomPublicKey().encodeByteArrayToBase64()
            )
        )
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)
        ContextManager.setKeyPairVerified(publicKey, callback = TestCallback)
        ContextManager.setCertificateChain(PLATFORM_TEST_EXPIRED_CERTIFICATE, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateCertificateChainIsInvalidWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)
        ContextManager.setKeyPairVerified(publicKey, callback = TestCallback)
        ContextManager.setCertificateChain(PLATFORM_TEST_EXPIRED_CERTIFICATE, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateReady() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(
            BasicUserDetailsResponse(
                email = randomEmail(),
                displayName = randomString(),
                emailVerified = randomBoolean(),
                publicKey = randomPublicKey().encodeByteArrayToBase64()
            )
        )
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)
        ContextManager.setKeyPairVerified(publicKey, callback = TestCallback)
        ContextManager.setCertificateChain(PLATFORM_TEST_VALID_CERTIFICATE, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = true.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.READY, result)
            assertEquals(1, CloudHttpClient.client.requestHistory().size)
            assertEquals(1, CloudHttpClient.client.responseHistory().size)
        }
    }

    @Test
    fun shouldGetContextStateReadyWithoutServerCheck() = runTest {
        // Given
        val client = CloudHttpClient.setupMockClient(null)
        val keyPair = CryptoManager.generateRawKeyPair()
        val publicKey = keyPair.public.encodeByteArrayToBase64()
        val privateKey = keyPair.private.encodeByteArrayToBase64()
        ContextManager.setCloudAuthToken(TEST_VALID_JWT, callback = TestCallback)
        ContextManager.setKeyPair(EncodedKeyPair(publicKey = publicKey, privateKey = privateKey).toJson(), callback = TestCallback)
        ContextManager.setKeyPairVerified(publicKey, callback = TestCallback)
        ContextManager.setCertificateChain(PLATFORM_TEST_VALID_CERTIFICATE, callback = TestCallback)

        client.use {
            // When
            val result = callbackApiCall<ResultData<ContextState>> {
                ContextManager.getContextState(
                    checkServerInvalidation = false.toString(),
                    callback = TestCallback
                )
            }.unwrap()

            // Then
            assertEquals(ContextState.READY, result)
            assertEquals(0, CloudHttpClient.client.requestHistory().size)
            assertEquals(0, CloudHttpClient.client.responseHistory().size)
        }
    }
}