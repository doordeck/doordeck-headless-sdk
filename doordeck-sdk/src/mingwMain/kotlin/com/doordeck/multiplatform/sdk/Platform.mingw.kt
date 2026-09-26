package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.AccountApi
import com.doordeck.multiplatform.sdk.api.AccountlessApi
import com.doordeck.multiplatform.sdk.api.FusionApi
import com.doordeck.multiplatform.sdk.api.HelperApi
import com.doordeck.multiplatform.sdk.api.LockOperationsApi
import com.doordeck.multiplatform.sdk.api.PlatformApi
import com.doordeck.multiplatform.sdk.api.SitesApi
import com.doordeck.multiplatform.sdk.api.TilesApi
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.storage.hostSecureStorage
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlinx.serialization.Serializable
import com.doordeck.multiplatform.sdk.util.guard
import com.doordeck.multiplatform.sdk.util.reply
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer

actual val platformType: PlatformType = PlatformType.WINDOWS

internal actual object ApplicationContext

/**
 * Receives the outcome of a [call], tagged with the request id it was invoked with. The string is
 * only borrowed: it is released as soon as the callback returns, so it must be copied, not retained.
 */
typealias ResultCallback = CPointer<CFunction<(Long, CPointer<ByteVar>) -> Unit>>

private fun requireArgs(args: String?, method: String): String =
    args ?: throw SdkException("Missing arguments for $method")

/**
 * Configuration for [initialize]. Secure storage is not part of it: an implementation supplied by the
 * host is registered separately through set_secure_storage, since function pointers cannot be
 * carried in JSON.
 */
@Serializable
internal data class SdkConfigData(
    val apiEnvironment: String = "PROD",
    val cloudAuthToken: String? = null,
    val cloudRefreshToken: String? = null,
    val fusionHost: String? = null,
    val debugLogging: Boolean = false
)

private var sdk: Doordeck? = null

/**
 * Initializes the SDK. There is a single instance behind the boundary, so nothing is handed back to
 * the caller; the outcome is reported through [callback] like any other call.
 */
@CName("initialize")
fun initialize(configJson: String?, requestId: Long, callback: ResultCallback) = callback.guard(requestId) {
    callback.reply(requestId) {
        val config = configJson?.fromJson<SdkConfigData>() ?: SdkConfigData()
        sdk = KDoordeckFactory.initialize(
            sdkConfig = SdkConfig.Builder()
                .setApiEnvironment(config.apiEnvironment)
                .setCloudAuthToken(config.cloudAuthToken)
                .setCloudRefreshToken(config.cloudRefreshToken)
                .setFusionHost(config.fusionHost)
                .setSecureStorageOverride(hostSecureStorage)
                .setDebugLogging(config.debugLogging.toString())
                .build()
        )
    }
}

@CName("release")
fun release(requestId: Long, callback: ResultCallback) = callback.guard(requestId) {
    callback.reply(requestId) {
        sdk?.release()
        sdk = null
    }
}

private fun unknownMethod(method: String, requestId: Long, callback: ResultCallback) =
    callback.reply<Unit>(requestId) { throw SdkException("Unknown method: $method") }

/**
 * The single entry point of the SDK. Every operation is addressed by name, takes its arguments as a
 * JSON string and reports back through [callback]; the exported surface therefore stays fixed as the
 * API grows. Synchronous operations invoke the callback before returning, asynchronous ones from a
 * worker thread.
 */
@CName("call")
fun call(method: String, args: String?, requestId: Long, callback: ResultCallback) = callback.guard(requestId) {
    when (method.substringBefore('.', "")) {
        "account" -> accountCall(method, args, requestId, callback)
        "accountless" -> accountlessCall(method, args, requestId, callback)
        "fusion" -> fusionCall(method, args, requestId, callback)
        "helper" -> helperCall(method, args, requestId, callback)
        "lockOperations" -> lockOperationsCall(method, args, requestId, callback)
        "platform" -> platformCall(method, args, requestId, callback)
        "sites" -> sitesCall(method, args, requestId, callback)
        "tiles" -> tilesCall(method, args, requestId, callback)
        "context" -> contextCall(method, args, requestId, callback)
        "crypto" -> cryptoCall(method, args, requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun accountCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "account.refreshToken" -> AccountApi.refreshToken(args, requestId, callback)
        "account.logout" -> AccountApi.logout(requestId, callback)
        "account.registerEphemeralKey" -> AccountApi.registerEphemeralKey(args, requestId, callback)
        "account.registerEphemeralKeyWithSecondaryAuthentication" -> AccountApi.registerEphemeralKeyWithSecondaryAuthentication(args, requestId, callback)
        "account.verifyEphemeralKeyRegistration" -> AccountApi.verifyEphemeralKeyRegistration(requireArgs(args, method), requestId, callback)
        "account.reverifyEmail" -> AccountApi.reverifyEmail(requestId, callback)
        "account.changePassword" -> AccountApi.changePassword(requireArgs(args, method), requestId, callback)
        "account.getUserDetails" -> AccountApi.getUserDetails(requestId, callback)
        "account.updateUserDetails" -> AccountApi.updateUserDetails(requireArgs(args, method), requestId, callback)
        "account.deleteAccount" -> AccountApi.deleteAccount(requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun accountlessCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "accountless.login" -> AccountlessApi.login(requireArgs(args, method), requestId, callback)
        "accountless.registration" -> AccountlessApi.registration(requireArgs(args, method), requestId, callback)
        "accountless.verifyEmail" -> AccountlessApi.verifyEmail(requireArgs(args, method), requestId, callback)
        "accountless.passwordReset" -> AccountlessApi.passwordReset(requireArgs(args, method), requestId, callback)
        "accountless.passwordResetVerify" -> AccountlessApi.passwordResetVerify(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun fusionCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "fusion.login" -> FusionApi.login(requireArgs(args, method), requestId, callback)
        "fusion.getIntegrationType" -> FusionApi.getIntegrationType(requestId, callback)
        "fusion.getIntegrationConfiguration" -> FusionApi.getIntegrationConfiguration(requireArgs(args, method), requestId, callback)
        "fusion.enableDoor" -> FusionApi.enableDoor(requireArgs(args, method), requestId, callback)
        "fusion.deleteDoor" -> FusionApi.deleteDoor(requireArgs(args, method), requestId, callback)
        "fusion.getDoorStatus" -> FusionApi.getDoorStatus(requireArgs(args, method), requestId, callback)
        "fusion.startDoor" -> FusionApi.startDoor(requireArgs(args, method), requestId, callback)
        "fusion.stopDoor" -> FusionApi.stopDoor(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun helperCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "helper.uploadPlatformLogo" -> HelperApi.uploadPlatformLogo(requireArgs(args, method), requestId, callback)
        "helper.assistedLogin" -> HelperApi.assistedLogin(requireArgs(args, method), requestId, callback)
        "helper.assistedRegisterEphemeralKey" -> HelperApi.assistedRegisterEphemeralKey(args, requestId, callback)
        "helper.assistedRegister" -> HelperApi.assistedRegister(requireArgs(args, method), requestId, callback)
        "helper.serverTime" -> HelperApi.serverTime(requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun lockOperationsCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "lockOperations.getSingleLock" -> LockOperationsApi.getSingleLock(requireArgs(args, method), requestId, callback)
        "lockOperations.getLockAuditTrail" -> LockOperationsApi.getLockAuditTrail(requireArgs(args, method), requestId, callback)
        "lockOperations.getAuditForUser" -> LockOperationsApi.getAuditForUser(requireArgs(args, method), requestId, callback)
        "lockOperations.getUsersForLock" -> LockOperationsApi.getUsersForLock(requireArgs(args, method), requestId, callback)
        "lockOperations.getLocksForUser" -> LockOperationsApi.getLocksForUser(requireArgs(args, method), requestId, callback)
        "lockOperations.updateLockName" -> LockOperationsApi.updateLockName(requireArgs(args, method), requestId, callback)
        "lockOperations.updateLockFavourite" -> LockOperationsApi.updateLockFavourite(requireArgs(args, method), requestId, callback)
        "lockOperations.updateLockSettingDefaultName" -> LockOperationsApi.updateLockSettingDefaultName(requireArgs(args, method), requestId, callback)
        "lockOperations.setLockSettingPermittedAddresses" -> LockOperationsApi.setLockSettingPermittedAddresses(requireArgs(args, method), requestId, callback)
        "lockOperations.updateLockSettingHidden" -> LockOperationsApi.updateLockSettingHidden(requireArgs(args, method), requestId, callback)
        "lockOperations.setLockSettingTimeRestrictions" -> LockOperationsApi.setLockSettingTimeRestrictions(requireArgs(args, method), requestId, callback)
        "lockOperations.updateLockSettingLocationRestrictions" -> LockOperationsApi.updateLockSettingLocationRestrictions(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKey" -> LockOperationsApi.getUserPublicKey(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByEmail" -> LockOperationsApi.getUserPublicKeyByEmail(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByTelephone" -> LockOperationsApi.getUserPublicKeyByTelephone(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByLocalKey" -> LockOperationsApi.getUserPublicKeyByLocalKey(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByForeignKey" -> LockOperationsApi.getUserPublicKeyByForeignKey(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByIdentity" -> LockOperationsApi.getUserPublicKeyByIdentity(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByEmails" -> LockOperationsApi.getUserPublicKeyByEmails(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByTelephones" -> LockOperationsApi.getUserPublicKeyByTelephones(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByLocalKeys" -> LockOperationsApi.getUserPublicKeyByLocalKeys(requireArgs(args, method), requestId, callback)
        "lockOperations.getUserPublicKeyByForeignKeys" -> LockOperationsApi.getUserPublicKeyByForeignKeys(requireArgs(args, method), requestId, callback)
        "lockOperations.unlock" -> LockOperationsApi.unlock(requireArgs(args, method), requestId, callback)
        "lockOperations.shareLock" -> LockOperationsApi.shareLock(requireArgs(args, method), requestId, callback)
        "lockOperations.batchShareLock" -> LockOperationsApi.batchShareLock(requireArgs(args, method), requestId, callback)
        "lockOperations.revokeAccessToLock" -> LockOperationsApi.revokeAccessToLock(requireArgs(args, method), requestId, callback)
        "lockOperations.updateSecureSettingUnlockDuration" -> LockOperationsApi.updateSecureSettingUnlockDuration(requireArgs(args, method), requestId, callback)
        "lockOperations.updateSecureSettingUnlockBetween" -> LockOperationsApi.updateSecureSettingUnlockBetween(requireArgs(args, method), requestId, callback)
        "lockOperations.getPinnedLocks" -> LockOperationsApi.getPinnedLocks(requestId, callback)
        "lockOperations.getShareableLocks" -> LockOperationsApi.getShareableLocks(requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun platformCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "platform.createApplication" -> PlatformApi.createApplication(requireArgs(args, method), requestId, callback)
        "platform.listApplications" -> PlatformApi.listApplications(requestId, callback)
        "platform.getApplication" -> PlatformApi.getApplication(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationName" -> PlatformApi.updateApplicationName(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationCompanyName" -> PlatformApi.updateApplicationCompanyName(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationMailingAddress" -> PlatformApi.updateApplicationMailingAddress(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationPrivacyPolicy" -> PlatformApi.updateApplicationPrivacyPolicy(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationSupportContact" -> PlatformApi.updateApplicationSupportContact(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationAppLink" -> PlatformApi.updateApplicationAppLink(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationEmailPreferences" -> PlatformApi.updateApplicationEmailPreferences(requireArgs(args, method), requestId, callback)
        "platform.updateApplicationLogoUrl" -> PlatformApi.updateApplicationLogoUrl(requireArgs(args, method), requestId, callback)
        "platform.deleteApplication" -> PlatformApi.deleteApplication(requireArgs(args, method), requestId, callback)
        "platform.getLogoUploadUrl" -> PlatformApi.getLogoUploadUrl(requireArgs(args, method), requestId, callback)
        "platform.addAuthKey" -> PlatformApi.addAuthKey(requireArgs(args, method), requestId, callback)
        "platform.addAuthIssuer" -> PlatformApi.addAuthIssuer(requireArgs(args, method), requestId, callback)
        "platform.deleteAuthIssuer" -> PlatformApi.deleteAuthIssuer(requireArgs(args, method), requestId, callback)
        "platform.addCorsDomain" -> PlatformApi.addCorsDomain(requireArgs(args, method), requestId, callback)
        "platform.removeCorsDomain" -> PlatformApi.removeCorsDomain(requireArgs(args, method), requestId, callback)
        "platform.addApplicationOwner" -> PlatformApi.addApplicationOwner(requireArgs(args, method), requestId, callback)
        "platform.removeApplicationOwner" -> PlatformApi.removeApplicationOwner(requireArgs(args, method), requestId, callback)
        "platform.getApplicationOwnersDetails" -> PlatformApi.getApplicationOwnersDetails(requireArgs(args, method), requestId, callback)
        "platform.getApplicationUsers" -> PlatformApi.getApplicationUsers(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun sitesCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "sites.listSites" -> SitesApi.listSites(requestId, callback)
        "sites.getLocksForSite" -> SitesApi.getLocksForSite(requireArgs(args, method), requestId, callback)
        "sites.getUsersForSite" -> SitesApi.getUsersForSite(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun tilesCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "tiles.getLocksBelongingToTile" -> TilesApi.getLocksBelongingToTile(requireArgs(args, method), requestId, callback)
        "tiles.associateMultipleLocks" -> TilesApi.associateMultipleLocks(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun contextCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "context.setApiEnvironment" -> ContextManager.setApiEnvironment(requireArgs(args, method), requestId, callback)
        "context.getApiEnvironment" -> ContextManager.getApiEnvironment(requestId, callback)
        "context.setCloudAuthToken" -> ContextManager.setCloudAuthToken(requireArgs(args, method), requestId, callback)
        "context.getCloudAuthToken" -> ContextManager.getCloudAuthToken(requestId, callback)
        "context.isCloudAuthTokenInvalidOrExpired" -> ContextManager.isCloudAuthTokenInvalidOrExpired(requireArgs(args, method), requestId, callback)
        "context.setCloudRefreshToken" -> ContextManager.setCloudRefreshToken(requireArgs(args, method), requestId, callback)
        "context.getCloudRefreshToken" -> ContextManager.getCloudRefreshToken(requestId, callback)
        "context.setFusionHost" -> ContextManager.setFusionHost(requireArgs(args, method), requestId, callback)
        "context.getFusionHost" -> ContextManager.getFusionHost(requestId, callback)
        "context.setFusionAuthToken" -> ContextManager.setFusionAuthToken(requireArgs(args, method), requestId, callback)
        "context.getFusionAuthToken" -> ContextManager.getFusionAuthToken(requestId, callback)
        "context.setUserId" -> ContextManager.setUserId(requireArgs(args, method), requestId, callback)
        "context.getUserId" -> ContextManager.getUserId(requestId, callback)
        "context.setUserEmail" -> ContextManager.setUserEmail(requireArgs(args, method), requestId, callback)
        "context.getUserEmail" -> ContextManager.getUserEmail(requestId, callback)
        "context.setCertificateChain" -> ContextManager.setCertificateChain(requireArgs(args, method), requestId, callback)
        "context.getCertificateChain" -> ContextManager.getCertificateChain(requestId, callback)
        "context.isCertificateChainInvalidOrExpired" -> ContextManager.isCertificateChainInvalidOrExpired(requestId, callback)
        "context.setKeyPair" -> ContextManager.setKeyPair(requireArgs(args, method), requestId, callback)
        "context.getKeyPair" -> ContextManager.getKeyPair(requestId, callback)
        "context.setKeyPairVerified" -> ContextManager.setKeyPairVerified(args, requestId, callback)
        "context.isKeyPairVerified" -> ContextManager.isKeyPairVerified(requestId, callback)
        "context.isKeyPairValid" -> ContextManager.isKeyPairValid(requestId, callback)
        "context.setOperationContext" -> ContextManager.setOperationContext(requireArgs(args, method), requestId, callback)
        "context.getContextState" -> ContextManager.getContextState(requireArgs(args, method), requestId, callback)
        "context.clearContext" -> ContextManager.clearContext(requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}

private fun cryptoCall(method: String, args: String?, requestId: Long, callback: ResultCallback) {
    when (method) {
        "crypto.generateEncodedKeyPair" -> CryptoManager.generateEncodedKeyPair(requestId, callback)
        "crypto.generateEncodedKeyPairFromEncodedBytes" -> CryptoManager.generateEncodedKeyPairFromEncodedBytes(requireArgs(args, method), requestId, callback)
        else -> unknownMethod(method, requestId, callback)
    }
}
