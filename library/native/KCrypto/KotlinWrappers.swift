import Foundation
import CryptoKit

@objc public class KCrypto : NSObject {

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

    @obj(seedKeypair:) public class func seedKeypair(withPrivateKey privateKeyData: Data) -> Data? {
        do {
            let privateKey = try Curve25519.Signing.PrivateKey(rawRepresentation: privateKeyData)
            return privateKey.rawRepresentation
        } catch {
            return nil
        }
    }
}