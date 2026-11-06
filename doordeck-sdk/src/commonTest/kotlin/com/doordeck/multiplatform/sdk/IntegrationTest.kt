package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clock.ServerTimeSynchronizer
import com.doordeck.multiplatform.sdk.clock.SystemClock
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.lifecycle.Lifecycle
import com.doordeck.multiplatform.sdk.lifecycle.LifecycleState
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import io.ktor.client.HttpClient as KtorHttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.withContext

/**
 * Base class for every test that touches the SDK singletons (HTTP clients, [Context], [SystemClock],
 * [ServerTimeSynchronizer], [CapabilityCache], [Lifecycle]).
 *
 * Test bodies are written with the usual `runTest`, which inside this class resolves to [IntegrationTest.runTest]
 * (a member function takes precedence over the top-level `kotlinx.coroutines.test.runTest`, so do not import the
 * latter in subclasses):
 *
 * ```
 * @Test
 * fun shouldDoSomething() = runTest {
 *     ...
 * }
 * ```
 *
 * Do not put suspending work in `@BeforeTest`/`@AfterTest`: on JS, the test runner does not await their result,
 * so it would overlap with the next test. Set up and clean up inside the test body instead; the global SDK state
 * is handled by [runTest] itself.
 */
abstract class IntegrationTest {

    /**
     * Runs [block] as a test body with every SDK singleton in its baseline state, and puts it back into that state
     * afterward, whether the test passed, failed or timed out.
     *
     * - [block] runs on [Dispatchers.Default], so delays, timeouts and background coroutines use real time. The
     *   virtual time of the standard `runTest` would otherwise make waits such as the first server time
     *   synchronization complete instantly.
     * - The cleanup runs inside the test body rather than in `@AfterTest`, so it is awaited on every platform.
     * - The standard `runTest` is kept underneath as the cross-platform way to return a [TestResult] with a
     *   real-time timeout.
     */
    protected fun runTest(block: suspend CoroutineScope.() -> Unit): TestResult = kotlinx.coroutines.test.runTest {
        withContext(Dispatchers.Default) {
            resetSdkState()
            try {
                block()
            } finally {
                withContext(NonCancellable) {
                    resetSdkState()
                }
            }
        }
    }
}

/**
 * The single place that knows every piece of global SDK state and how to bring it back to the test baseline:
 * SDK released, no synchronization loop, zero clock skew, empty caches, [LifecycleState.STOPPED], a fresh in-memory
 * [Context] pointing at [TEST_ENVIRONMENT], and the shared test HTTP clients installed.
 */
internal suspend fun resetSdkState() {
    DoordeckFactory.release() // No-op unless a test left the SDK initialized
    ServerTimeSynchronizer.stop()
    SystemClock.reset()
    CapabilityCache.reset()
    Lifecycle.set(LifecycleState.STOPPED)
    SdkLogger.enableDebugLogging(false)
    Context.setSecureStorageImpl(DefaultSecureStorage(MemorySettings()))
    Context.setApiEnvironment(TEST_ENVIRONMENT)
    CloudHttpClient.installSharedClient(TEST_CLOUD_CLIENT)
    FusionHttpClient.installSharedClient(TEST_FUSION_CLIENT)
    HttpClient.installSharedClient(TEST_HTTP_CLIENT)
}

/**
 * Installs [shared], closing first whatever other client a test left behind (typically a mock client that was not
 * closed). The shared clients themselves are never closed.
 */
private suspend fun BaseHttpClient.installSharedClient(shared: KtorHttpClient) {
    val current = runCatching { client }.getOrNull()
    if (current != null && current !== shared) {
        close()
    }
    overrideClient(shared)
}
