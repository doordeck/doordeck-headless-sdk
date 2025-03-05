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

  private let doordeckSdk: Doordeck

  override public init() {
    self.doordeckSdk = KDoordeckFactory().initialize(sdkConfig: SdkConfig.Builder().build())

    super.init()
  }

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

    doordeckSdk.contextManager().setCloudAuthToken(token: authToken)
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
        resolver(nil)
      }
    }
  }

  /**
   * ✅ User Operations
   */

  @objc public func getUserDetails(
    _ resolver: @escaping RCTPromiseResolveBlock,
    rejecter: @escaping RCTPromiseRejectBlock
  ) {
    doordeckSdk.account().getUserDetails { response, error in
      if let error = error {
        rejecter("USER_DETAILS_ERROR", error.localizedDescription, error)
      } else if let response = response {
        resolver(response.toNativeMap(
          userId: self.doordeckSdk.contextManager().getUserId(),
          certificateChainAboutToExpire: self.doordeckSdk.contextManager().isCertificateChainAboutToExpire(),
          tokenAboutToExpire: self.doordeckSdk.contextManager().isCloudAuthTokenAboutToExpire()
        ))
      } else {
        rejecter("USER_DETAILS_ERROR", "Unknown error occurred", nil)
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

  private func setKeyPairIfNeeded() {
    guard doordeckSdk.contextManager().getKeyPair() == nil else { return }

    let newKeyPair = doordeckSdk.crypto().generateKeyPair()
    doordeckSdk.contextManager().setKeyPair(publicKey: newKeyPair.public_, privateKey: newKeyPair.private_)
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

extension UserDetailsResponse {
  func toNativeMap(userId: String?, certificateChainAboutToExpire: Bool, tokenAboutToExpire: Bool) -> NSDictionary {
    return [
      "userId": userId as Any,
      "certificateChainAboutToExpire": certificateChainAboutToExpire,
      "tokenAboutToExpire": tokenAboutToExpire,
      "publicKey": publicKey,
      "email": email,
      "displayName": displayName as Any,
      "emailVerified": emailVerified
    ]
  }
}
