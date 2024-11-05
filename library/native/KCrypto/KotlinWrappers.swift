import Foundation
import CryptoKit

@objc public class KCryptoKit : NSObject {

    @objc public class func generateKeyPair() -> [String: Data] {
        let privateKey = Curve25519.Signing.PrivateKey()
        let publicKey = privateKey.publicKey
        return [
            "privateKey": privateKey.rawRepresentation,
            "publicKey": publicKey.rawRepresentation
        ]
    }

    @objc(signWithPrivateKey::) public class func signWithPrivateKey(message: String, withPrivateKey privateKeyData: Data) -> Data? {
        do {
            // Convert the data into a private key object
            let privateKey = try Curve25519.Signing.PrivateKey(rawRepresentation: privateKeyData)
            // Convert the message to Data
            let messageData = Data(message.utf8)
            // Sign the message
            let signature = try privateKey.signature(for: messageData)
            return signature
        } catch {
           return nil
        }
    }
}