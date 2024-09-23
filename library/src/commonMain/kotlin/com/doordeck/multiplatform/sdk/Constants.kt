package com.doordeck.multiplatform.sdk

object Constants {

    const val PLATFORM_HEADER_NAME = "X-POWERED-BY"
    val PLATFORM_HEADER_VALUE = "KMP SDK ${getPlatform()}"
    const val CDN_URL = "https://cdn.doordeck.com"
    const val CERTIFICATE_PINNER_DOMAIN_PATTERN = "**.doordeck.com"
    val TRUSTED_CERTIFICATES = listOf(
         "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=", // CN=Amazon Root CA 1,O=Amazon,C=US
         "sha256/f0KW/FtqTjs108NpYj42SrGvOB2PpxIVM8nWxjPqJGE=", // CN=Amazon Root CA 2,O=Amazon,C=US
         "sha256/NqvDJlas/GRcYbcWE8S/IceH9cq77kg0jVhZeAPXq8k=", // CN=Amazon Root CA 3,O=Amazon,C=US
         "sha256/9+ze1cZgR9KO1kZrVDxA4HQ6voHRCSVNz4RdTCx4U8U=", // CN=Amazon Root CA 4,O=Amazon,C=US
         "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=" // CN=Starfield Services Root Certificate Authority - G2,O=Starfield Technologies, Inc.,L=Scottsdale,ST=Arizona,C=US
    )
}