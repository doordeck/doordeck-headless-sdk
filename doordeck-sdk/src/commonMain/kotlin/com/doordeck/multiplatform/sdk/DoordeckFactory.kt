package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl

internal object DoordeckFactory {
    fun initialize(applicationContext: ApplicationContext?, apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck {
        // Init the context manager with the provided values
        ContextManagerImpl.also { context ->
            context.setApiEnvironment(apiEnvironment)
            token?.let { context.setAuthToken(it) }
            refreshToken?.let { context.setRefreshToken(it) }
            applicationContext?.let { context.setApplicationContext(it) }
        }
        return DoordeckImpl
    }
}