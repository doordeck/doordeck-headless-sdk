//
//  HeadlessReactNativeSdkImpl.swift
//  HeadlessReactNativeSdkImpl
//
//  Created by Rafael Ruiz Muñoz on 10/02/2025.
//

import Foundation
import React
import DoordeckSDK

@objc(HeadlessReactNativeSdkImpl)
public class HeadlessReactNativeSdkImpl: NSObject {
  
  private let doordeckSdk = KDoordeckFactory().initialize(apiEnvironment: ApiEnvironment.prod)

  /**
   * ✅ Authentication Methods
   */

  @objc public func login(
    _ email: String,
    password: String,
    resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    setKeyPairIfNeeded()
    
    doordeckSdk.accountless().login(email: email, password: password) { tokenResponse, error in
    
      if let error = error {
        rejecter("LOGIN_ERROR", error.localizedDescription, error)
      } else if tokenResponse != nil {
        self.respondNeedsVerification(resolver, rejecter)
      } else {
        rejecter("LOGIN_ERROR", "Unknown error occurred", nil)
      }
    }
  }

  @objc public func setAuthToken(
    _ authToken: String,
    resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    setKeyPairIfNeeded()
    
    doordeckSdk.contextManager().setAuthToken(token: authToken)
    self.respondNeedsVerification(resolver, rejecter)
  }

  @objc public func verify(
    _ code: String,
    resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    doordeckSdk.account().verifyEphemeralKeyRegistration(code: code, privateKey: nil) { _, error in
      if let error = error {
        rejecter("VERIFY_ERROR", error.localizedDescription, error)
      } else {
        self.doordeckSdk.contextManager().storeContext()
        resolver(nil)
      }
    }
  }

  @objc public func logout(
    _ resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    doordeckSdk.account().logout() { error in
      if let error = error {
        rejecter("LOGOUT_ERROR", error.localizedDescription, error)
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
    resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    doordeckSdk.tiles().getLocksBelongingToTile(tileId: tileId) { response, error in
      if let error = error {
        rejecter("LOCKS_ERROR", error.localizedDescription, error)
      } else if let response = response {
        resolver(response.toNativeMap())
      } else {
        rejecter("LOCKS_ERROR", "Unknown error occurred", nil)
      }
    }
  }

  @objc public func unlockDevice(
    _ lockId: String,
    resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    let a = doordeckSdk.contextManager().getUserId()
    let a2 = doordeckSdk.contextManager().getCertificateChain()
    let b = doordeckSdk.contextManager().getKeyPair()
    let c1 = b?.public_
    let c2 = b?.private_
    let baseOperation = LockOperations.BaseOperation(
      userId: nil,
      userCertificateChain: nil,
      userPrivateKey: nil,
      lockId: lockId,
      notBefore: Int32(Date().timeIntervalSince1970), // val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
      issuedAt: Int32(Date().timeIntervalSince1970), // val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
      expiresAt: Int32(Date().addingTimeInterval(60).timeIntervalSince1970), // val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
      jti: UUID().uuidString // val jti: String = Uuid.random().toString()
    )
    
    let unlockOperation = LockOperations.UnlockOperation(baseOperation: baseOperation, directAccessEndpoints: nil)
    
    doordeckSdk.lockOperations().unlock(unlockOperation: unlockOperation) { error in
      if let error = error {
        rejecter("UNLOCK_ERROR", error.localizedDescription, error)
      } else {
        resolver(nil)
      }
    }
  }

  /**
   * ✅ Helper Methods
   */
  
  @objc public func loadContext() {
    doordeckSdk.contextManager().loadContext()
  }

  private func setKeyPairIfNeeded() {
    let a = doordeckSdk.contextManager().getUserId()
    guard doordeckSdk.contextManager().getKeyPair() == nil else { return }
    
    let newKeyPair = doordeckSdk.crypto().generateKeyPair()
    doordeckSdk.contextManager().setKeyPair(publicKey: newKeyPair.public_, privateKey: newKeyPair.private_)
    doordeckSdk.contextManager().storeContext()
  }

  private func respondNeedsVerification(
    _ resolver: @escaping RCTPromiseResolveBlock,
    _ rejecter: @escaping RCTPromiseRejectBlock
  ) {
    doordeckSdk.helper().assistedRegisterEphemeralKey(publicKey: nil) { response, error in
      if let error = error {
        rejecter("NEEDS_VERIFICATION_ERROR", error.localizedDescription, error)
      } else if let response = response {
        resolver(response.toNativeMap())
      } else {
        rejecter("NEEDS_VERIFICATION_ERROR", "Unknown error occurred", nil)
      }
    }
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
