package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment

internal object DoordeckFactory {
    fun initialize(applicationContext: ApplicationContext?, apiEnvironment: ApiEnvironment, token: String?, refreshToken: String?): Doordeck {
        // Add the provided values into the context
        ContextManagerImpl.also { context ->
            context.setApiEnvironment(apiEnvironment)
            token?.let { context.setAuthToken(it) }
            refreshToken?.let { context.setRefreshToken(it) }
            applicationContext?.let { context.setApplicationContext(it) }
        }
        return DoordeckImpl
    }
}