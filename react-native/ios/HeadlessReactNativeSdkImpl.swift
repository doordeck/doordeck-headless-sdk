//
//  HeadlessReactNativeSdkImpl.swift
//  HeadlessReactNativeSdkImpl
//
//  Created by Rafael Ruiz Muñoz on 10/02/2025.
//

import Foundation
import DoordeckSDK

@objc(HeadlessReactNativeSdkImpl)
public class HeadlessReactNativeSdkImpl: NSObject {
  
  private let doordeckSdk = KDoordeckFactory().initialize(apiEnvironment: ApiEnvironment.prod)

  /**
   * ✅ Authentication Methods (Objective-C Compatible)
   */

  @objc public func login(
    _ email: String,
    password: String,
    resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    setKeyPairIfNeeded()
    
    doordeckSdk.accountless().login(email: email, password: password) { _, error in
      if let error = error {
        rejecter("LOGIN_ERROR", "Failed to log in", error as NSError)
      } else {
        resolver(nil)
      }
    }
  }

  @objc public func setAuthToken(
    _ authToken: String,
    resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    doordeckSdk.contextManager().setAuthToken(token: authToken)
    resolver(nil)
  }

  @objc public func verify(
    _ code: String,
    resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    doordeckSdk.account().verifyEphemeralKeyRegistration(code: code, privateKey: nil) { _, error in
      if let error = error {
        rejecter("VERIFY_ERROR", "Verification failed", error as NSError)
      } else {
        resolver(nil)
      }
    }
  }

  @objc public func logout(
    _ resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    doordeckSdk.account().logout() { error in
      if let error = error {
        rejecter("LOGOUT_ERROR", "Failed to log out", error as NSError)
      } else {
        self.doordeckSdk.contextManager().clearContext()
        resolver(nil)
      }
    }
  }

  /**
   * ✅ Device and Tile Operations
   */

  @objc public func getLocksBelongingToTile(
    _ tileId: String,
    resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    doordeckSdk.tiles().getLocksBelongingToTile(tileId: tileId) { response, error in
      if let error = error {
        rejecter("LOCKS_ERROR", "Failed to get locks", error as NSError)
      } else if let response = response {
        resolver(response.toNativeMap())
      } else {
        rejecter("LOCKS_ERROR", "Unknown error occurred", nil)
      }
    }
  }

  @objc public func unlockDevice(
    _ lockId: String,
    resolver: @escaping @convention(block) (Any?) -> Void,
    rejecter: @escaping @convention(block) (String, String, NSError?) -> Void
  ) {
    let baseOperation = LockOperations.BaseOperation(
      userId: nil,
      userCertificateChain: nil,
      userPrivateKey: nil,
      lockId: lockId,
      notBefore: Int32(Date().timeIntervalSince1970),
      issuedAt: Int32(Date().timeIntervalSince1970),
      expiresAt: Int32(Date().addingTimeInterval(60).timeIntervalSince1970),
      jti: UUID().uuidString
    )
    
    let unlockOperation = LockOperations.UnlockOperation(baseOperation: baseOperation, directAccessEndpoints: nil)
    
    doordeckSdk.lockOperations().unlock(unlockOperation: unlockOperation) { error in
      if let error = error {
        rejecter("UNLOCK_ERROR", "Failed to unlock device", error as NSError)
      } else {
        resolver(nil)
      }
    }
  }

  /**
   * ✅ Helper Methods
   */

  private func setKeyPairIfNeeded() {
    guard doordeckSdk.contextManager().getKeyPair() == nil else { return }
    
    let newKeyPair = doordeckSdk.crypto().generateKeyPair()
    doordeckSdk.contextManager().setKeyPair(publicKey: newKeyPair.public_, privateKey: newKeyPair.private_)
    doordeckSdk.contextManager().storeContext()
  }
}

/**
 * ✅ Extensions for converting Doordeck responses into NSDictionary (React Native format)
 */

extension AssistedRegisterEphemeralKeyResponse {
  func toNativeMap() -> NSDictionary {
    return ["requiresVerification": requiresVerification]
  }
}

extension TileLocksResponse {
  func toNativeMap() -> NSDictionary {
    return [
      "siteId": siteId,
      "tileId": tileId,
      "deviceIds": deviceIds
    ]
  }
}
