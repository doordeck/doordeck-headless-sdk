package com.doordeck.multiplatform.sdk.crypto

import Foundation
import CryptoKit

@objc public class CryptoHelper: NSObject {

    // Generate Ed25519 Key Pair
    @objc public func generateEd25519KeyPair() -> [String: String] {
        let privateKey = Curve25519.Signing.PrivateKey()
        let publicKey = privateKey.publicKey

        let privateKeyBase64 = privateKey.rawRepresentation.base64EncodedString()
        let publicKeyBase64 = publicKey.rawRepresentation.base64EncodedString()

        return [
            "privateKey": privateKeyBase64,
             "publicKey": publicKeyBase64
        ]
    }
}