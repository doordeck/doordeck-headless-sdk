import Foundation
import CryptoKit

@objc(KKeyPair) public class KKeyPair: NSObject {
    @objc public let publicKey: Data
    @objc public let privateKey: Data
    @objc public init(publicKey: Data, privateKey: Data) {
        self.publicKey = publicKey
        self.privateKey = privateKey
        super.init()
    }
}

@objc(KCryptoKit) public class KCryptoKit: NSObject {

    @objc public class func generateKeyPair() -> KKeyPair {
        let priv = Curve25519.Signing.PrivateKey()
        return KKeyPair(
            publicKey: priv.publicKey.rawRepresentation,
            privateKey: priv.rawRepresentation
        )
    }

    @objc(signWithPrivateKey::) public class func signWithPrivateKey(message: String, privateKey: Data) -> Data? {
        guard let key = try? Curve25519.Signing.PrivateKey(rawRepresentation: privateKey),
              let sig = try? key.signature(for: Data(message.utf8))
        else { return nil }
        return sig
    }

    @objc(verifySignature:::) public class func verifySignature(publicKey: Data, message: String, signature: Data) -> Bool {
        guard let key = try? Curve25519.Signing.PublicKey(rawRepresentation: publicKey)
        else { return false }
        return key.isValidSignature(signature, for: Data(message.utf8))
    }
}