package com.doordeck.multiplatform.sdk

/**
 * Internal global constants used throughout the SDK.
 */
internal object Constants {
    /**
     * Base URL for Doordeck's content delivery network.
     */
    const val CDN_URL = "https://cdn.doordeck.com"

    /**
     * Domain pattern for certificate pinning, matching all Doordeck subdomains.
     */
    const val CERTIFICATE_PINNER_DOMAIN_PATTERN = "**.doordeck.com"

    /**
     * Default host URL for Fusion APIs.
     */
    const val DEFAULT_FUSION_HOST = "http://localhost:27700"

    /**
     * List of trusted root certificate SHA-256 pins for secure connections.
     * Includes Amazon Root CAs 1-4 and Starfield Services Root CA.
     */
    val TRUSTED_CERTIFICATES = listOf(
         "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=", // CN=Amazon Root CA 1,O=Amazon,C=US
         "sha256/f0KW/FtqTjs108NpYj42SrGvOB2PpxIVM8nWxjPqJGE=", // CN=Amazon Root CA 2,O=Amazon,C=US
         "sha256/NqvDJlas/GRcYbcWE8S/IceH9cq77kg0jVhZeAPXq8k=", // CN=Amazon Root CA 3,O=Amazon,C=US
         "sha256/9+ze1cZgR9KO1kZrVDxA4HQ6voHRCSVNz4RdTCx4U8U=", // CN=Amazon Root CA 4,O=Amazon,C=US
         "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=" // CN=Starfield Services Root Certificate Authority - G2,O=Starfield Technologies, Inc.,L=Scottsdale,ST=Arizona,C=US
    )
}