package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClientImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl

open class IntegrationTest {

    val ACCOUNTLESS_RESOURCE = AccountlessResourceImpl(createCloudHttpClient(TEST_ENVIRONMENT, ContextManagerImpl()))
    val CONTEXT_MANAGER = ContextManagerImpl()
    val HTTP_CLIENT = createHttpClient()
    val CLOUD_HTTP_CLIENT = createCloudHttpClient(TEST_ENVIRONMENT, CONTEXT_MANAGER)
    val TILES_RESOURCE = TilesResourceImpl(CLOUD_HTTP_CLIENT)
    val SITES_RESOURCE = SitesResourceImpl(CLOUD_HTTP_CLIENT)
    val PLATFORM_RESOURCE = PlatformResourceImpl(CLOUD_HTTP_CLIENT)
    val ACCOUNT_RESOURCE = AccountResourceImpl(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER)
    val LOCAL_UNLOCK_RESOURCE = LocalUnlockClientImpl(HTTP_CLIENT)
    val LOCK_OPERATIONS_RESOURCE = LockOperationsResourceImpl(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER, LOCAL_UNLOCK_RESOURCE)
}