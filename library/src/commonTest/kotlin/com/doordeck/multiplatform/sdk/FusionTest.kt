package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl

open class FusionTest {

    val TEST_ENVIRONMENT = ApiEnvironment.DEV

    val TEST_MAIN_USER_EMAIL = getEnvironmentVariable("TEST_MAIN_USER_EMAIL")
        ?: ""
    val TEST_MAIN_USER_PASSWORD = getEnvironmentVariable("TEST_MAIN_USER_PASSWORD")
        ?: ""
    val TEST_MAIN_FUSION_SITE_ID = getEnvironmentVariable("TEST_MAIN_FUSION_SITE_ID")
        ?: ""

    val CONTEXT_MANAGER = ContextManagerImpl()
    val FUSION_RESOURCE = FusionResourceImpl(createFusionHttpClient(TEST_ENVIRONMENT, CONTEXT_MANAGER))
}