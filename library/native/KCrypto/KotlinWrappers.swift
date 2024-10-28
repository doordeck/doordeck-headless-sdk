import Foundation
import CryptoKit

@objc public class KCrypto : NSObject {

    @objc public class func generateKeyPair() -> [String: Data] {
        let privateKey = Curve25519.Signing.PrivateKey()
        let publicKey = privateKey.publicKey

        let privateKeyBase64 = privateKey.rawRepresentation
        let publicKeyBase64 = publicKey.rawRepresentation

        return [
            "privateKey": privateKeyBase64,
            "publicKey": publicKeyBase64
        ]
    }

    /*@objc public class func generateKeyPair() -> [String: String] {
        let privateKey = Curve25519.Signing.PrivateKey()
        let publicKey = privateKey.publicKey

        let privateKeyBase64 = privateKey.rawRepresentation.base64EncodedString()
        let publicKeyBase64 = publicKey.rawRepresentation.base64EncodedString()

        return [
            "privateKey": privateKeyBase64,
            "publicKey": publicKeyBase64
        ]
    }*/
}