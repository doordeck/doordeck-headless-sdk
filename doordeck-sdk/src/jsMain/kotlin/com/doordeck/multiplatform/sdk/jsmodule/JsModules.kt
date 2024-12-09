package com.doordeck.multiplatform.sdk.jsmodule

import org.khronos.webgl.ArrayBuffer

@JsModule("pkijs")
@JsNonModule
external object PKI {
    class Certificate {
        fun fromSchema(schema: dynamic)
        val notAfter: dynamic
    }
}

@JsModule("asn1js")
@JsNonModule
external object ASN1 {
    fun fromBER(buffer: ArrayBuffer): dynamic
}