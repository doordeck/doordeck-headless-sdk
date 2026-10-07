package com.doordeck.multiplatform.sdk

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual val platformType: PlatformType = PlatformType.WINDOWS

internal actual object ApplicationContext

typealias CStringCallback = CPointer<CFunction<(CPointer<ByteVar>) -> CPointer<ByteVar>>>