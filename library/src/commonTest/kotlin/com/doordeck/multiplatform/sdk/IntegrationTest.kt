package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsClient
import com.doordeck.multiplatform.sdk.internal.api.PlatformClient
import com.doordeck.multiplatform.sdk.internal.api.SitesClient
import com.doordeck.multiplatform.sdk.internal.api.TilesClient

internal open class IntegrationTest {

    val ACCOUNTLESS_CLIENT = AccountlessClient(createCloudHttpClient(TEST_ENVIRONMENT, ContextManagerImpl()))
    val CONTEXT_MANAGER = ContextManagerImpl()
    val HTTP_CLIENT = createHttpClient()
    val CLOUD_HTTP_CLIENT = createCloudHttpClient(TEST_ENVIRONMENT, CONTEXT_MANAGER)
    val TILES_CLIENT = TilesClient(CLOUD_HTTP_CLIENT)
    val SITES_CLIENT = SitesClient(CLOUD_HTTP_CLIENT)
    val PLATFORM_CLIENT = PlatformClient(CLOUD_HTTP_CLIENT)
    val ACCOUNT_CLIENT = AccountClient(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER)
    val LOCAL_UNLOCK_CLIENT = LocalUnlockClient(HTTP_CLIENT)
    val LOCK_OPERATIONS_CLIENT = LockOperationsClient(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER, LOCAL_UNLOCK_CLIENT)
}