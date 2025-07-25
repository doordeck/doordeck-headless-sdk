package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.BasicLockOperations
import com.doordeck.multiplatform.sdk.model.data.BasicPlatform
import com.doordeck.multiplatform.sdk.model.data.BasicPlatform.BasicEmailCallToAction
import com.doordeck.multiplatform.sdk.model.responses.NetworkApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkAuditIssuerResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkAuditSubjectResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkAuthKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ControllerResponse
import com.doordeck.multiplatform.sdk.model.responses.DiscoveredDeviceResponse
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkEcKeyResponseNetwork
import com.doordeck.multiplatform.sdk.model.responses.NetworkEd25519KeyResponseNetwork
import com.doordeck.multiplatform.sdk.model.responses.NetworkEmailCallToActionResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkEmailPreferencesResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLocationRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockSettingsResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockStateResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkOauthResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkRsaKeyResponseNetwork
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateResponse
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.model.responses.NetworkShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkSiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkTileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkTimeRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUnlockBetweenSettingResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUsageRequirementsResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlinx.datetime.DayOfWeek
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Account responses
 */
internal fun randomTokenResponse(): NetworkTokenResponse = NetworkTokenResponse(
    authToken = randomString(),
    refreshToken = randomString(),
)

internal fun randomUserDetailsResponse(): NetworkUserDetailsResponse = NetworkUserDetailsResponse(
    email = randomString(),
    displayName = randomNullable { randomString() },
    emailVerified = randomBoolean(),
    publicKey = randomPublicKey()
)

internal fun randomRegisterEphemeralKeyResponse(): NetworkRegisterEphemeralKeyResponse = NetworkRegisterEphemeralKeyResponse(
    certificateChain = emptyList(),
    userId = randomId()
)

internal fun randomRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse = NetworkRegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    method = TwoFactorMethod.entries.random()
)

/**
 * Fusion responses
 */
internal fun randomFusionLoginResponse(): FusionLoginResponse = FusionLoginResponse(
    authToken = randomString()
)

internal fun randomIntegrationTypeResponse(): IntegrationTypeResponse = IntegrationTypeResponse(
    status = randomNullable { randomString() }
)

internal fun randomDoorStateResponse(): DoorStateResponse = DoorStateResponse(
    state = ServiceStateType.entries.random()
)

internal fun randomIntegrationConfigurationResponse(): IntegrationConfigurationResponse = IntegrationConfigurationResponse(
    doordeck = randomNullable { randomControllerResponse() },
    service = randomNullable { randomServiceStateResponse() },
    integration = randomNullable { randomDiscoveredDeviceResponse() }
)

internal fun randomControllerResponse(): ControllerResponse = ControllerResponse(
    id = randomId(),
    name = randomNullable { randomString() },
    role = UserRole.entries.random()
)

internal fun randomServiceStateResponse(): ServiceStateResponse = ServiceStateResponse(
    state = ServiceStateType.entries.random()
)

internal fun randomDiscoveredDeviceResponse(): DiscoveredDeviceResponse = DiscoveredDeviceResponse(
    key = randomFusionController(),
    metadata = (1..3).associate { randomString() to randomString() }
)

internal fun randomFusionController(): Fusion.LockController = Fusion.DemoController()

/**
 * Lock operation responses
 */
internal fun randomLockResponse(): NetworkLockResponse = NetworkLockResponse(
    id = randomId(),
    name = randomString(),
    colour = randomNullable { randomString() },
    start = randomNullable { randomInstant() },
    end = randomNullable { randomInstant() },
    role = UserRole.entries.random(),
    settings = randomLockSettingsResponse(),
    state = randomLockStateResponse(),
    favourite = randomBoolean(),
    unlockTime = randomNullable { randomDouble() }
)

internal fun randomLockSettingsResponse(): NetworkLockSettingsResponse = NetworkLockSettingsResponse(
    unlockTime = randomDouble(),
    permittedAddresses = (1..3).map { randomString() },
    defaultName = randomString(),
    usageRequirements = randomNullable { randomUsageRequirementsResponse() },
    unlockBetweenWindow = randomNullable { randomUnlockBetweenSettingResponse() },
    tiles = (1..3).map { randomString() },
    hidden = randomBoolean(),
    directAccessEndpoints = (1..3).map { randomString() },
    capabilities = (1..3).associate { CapabilityType.entries.random() to CapabilityStatus.entries.random() }
)

internal fun randomUnlockBetweenSettingResponse(): NetworkUnlockBetweenSettingResponse = NetworkUnlockBetweenSettingResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.toTypedArray().take(3),
    exceptions = randomNullable { (1..3).map { randomString() } }
)

internal fun randomUsageRequirementsResponse(): NetworkUsageRequirementsResponse = NetworkUsageRequirementsResponse(
    time = randomNullable { (1..3).map { randomTimeRequirementResponse() } },
    location = randomLocationRequirementResponse()
)

internal fun randomTimeRequirementResponse(): NetworkTimeRequirementResponse = NetworkTimeRequirementResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = (1..3).map { randomString() }
)

internal fun randomLocationRequirementResponse(): NetworkLocationRequirementResponse = NetworkLocationRequirementResponse(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomBoolean(),
    radius = randomInt(),
    accuracy = randomInt()
)

internal fun randomShareableLockResponse(): NetworkShareableLockResponse = NetworkShareableLockResponse(
    id = randomId(),
    name = randomString()
)


internal fun randomUserPublicKeyResponse(): NetworkUserPublicKeyResponse = NetworkUserPublicKeyResponse(
    id = randomId(),
    publicKey = randomPublicKey()
)

internal fun randomBatchUserPublicKeyResponse(): NetworkBatchUserPublicKeyResponse = NetworkBatchUserPublicKeyResponse(
    id = randomId(),
    email = randomNullable { randomString() },
    foreignKey = randomNullable { randomString() },
    phone = randomNullable { randomString() },
    publicKey = randomPublicKey()
)

internal fun randomLockUserResponse(): NetworkLockUserResponse = NetworkLockUserResponse(
    userId = randomId(),
    email = randomString(),
    publicKey = randomPublicKey(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() },
    devices = (1..3).map { randomLockUserDetailsResponse() }
)

internal fun randomUserLockResponse(): NetworkUserLockResponse = NetworkUserLockResponse(
    userId = randomId(),
    email = randomString(),
    publicKey = randomPublicKey(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockUserDetailsResponse(): NetworkLockUserDetailsResponse = NetworkLockUserDetailsResponse(
    deviceId = randomId(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockStateResponse(): NetworkLockStateResponse = NetworkLockStateResponse(
    locked = randomBoolean(),
    connected = randomBoolean()
)

internal fun randomAuditResponse(): NetworkAuditResponse = NetworkAuditResponse(
    deviceId = randomId(),
    timestamp = randomDouble(),
    type = AuditEvent.entries.random(),
    issuer = randomAuditIssuerResponse(),
    subject = randomNullable { randomAuditSubjectResponse() },
    rejectionReason = randomNullable { randomString() },
    rejected = randomBoolean()
)

internal fun randomAuditSubjectResponse(): NetworkAuditSubjectResponse = NetworkAuditSubjectResponse(
    userId = randomId(),
    email = randomString(),
    displayName = randomNullable { randomString() }
)

internal fun randomAuditIssuerResponse(): NetworkAuditIssuerResponse = NetworkAuditIssuerResponse(
    userId = randomId(),
    email = randomNullable { randomString() },
    ip = randomNullable { randomString() }
)

/**
 * Platform responses
 */
internal fun randomApplicationResponse(): NetworkApplicationResponse = NetworkApplicationResponse(
    applicationId = randomId(),
    name = randomString(),
    lastUpdated = randomNullable { randomDouble() },
    owners = (1..3).map { randomString() },
    corsDomains = (1..3).map { randomString() },
    authDomains = (1..3).map { randomString() },
    logoUrl = randomNullable { randomString() },
    privacyPolicy = randomNullable { randomString() },
    mailingAddress = randomNullable { randomString() },
    companyName = randomNullable { randomString() },
    supportContact = randomNullable { randomString() },
    appLink = randomNullable { randomString() },
    slug = randomNullable { randomString() },
    emailPreferences = randomEmailPreferencesResponse(),
    authKeys = (1..3).associate { randomString() to randomAuthKeyResponse() },
    oauth = randomNullable { randomOauthResponse() },
    isDoordeckApplication = randomBoolean()
)

internal fun randomAuthKeyResponse(): NetworkAuthKeyResponse = when(listOf(NetworkRsaKeyResponseNetwork::class, NetworkEcKeyResponseNetwork::class, NetworkEd25519KeyResponseNetwork::class).random()) {
    NetworkRsaKeyResponseNetwork::class -> randomRsaKeyResponse()
    NetworkEcKeyResponseNetwork::class -> randomEcKeyResponse()
    NetworkEd25519KeyResponseNetwork::class -> randomEd25519KeyResponse()
    else -> error("Unknown key class")
}

internal fun randomRsaKeyResponse(): NetworkRsaKeyResponseNetwork = NetworkRsaKeyResponseNetwork(
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    ops = randomNullable { (1..3).map { randomString() } },
    x5u = randomNullable { randomString() },
    x5t = randomNullable { randomString() },
    x5t256 = randomNullable { randomString() },
    x5c = randomNullable { (1..3).map { randomString() } },
    exp = randomNullable { randomInt() },
    nbf = randomNullable { randomInt() },
    iat = randomNullable { randomInt() },
    e = randomString(),
    n = randomString()
)

internal fun randomEcKeyResponse(): NetworkEcKeyResponseNetwork = NetworkEcKeyResponseNetwork(
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    ops = randomNullable { (1..3).map { randomString() } },
    x5u = randomNullable { randomString() },
    x5t = randomNullable { randomString() },
    x5t256 = randomNullable { randomString() },
    x5c = randomNullable { (1..3).map { randomString() } },
    exp = randomNullable { randomInt() },
    nbf = randomNullable { randomInt() },
    iat = randomNullable { randomInt() },
    crv = randomString(),
    x = randomString(),
    y = randomString()
)

internal fun randomEd25519KeyResponse(): NetworkEd25519KeyResponseNetwork = NetworkEd25519KeyResponseNetwork(
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    ops = randomNullable { (1..3).map { randomString() } },
    x5u = randomNullable { randomString() },
    x5t = randomNullable { randomString() },
    x5t256 = randomNullable { randomString() },
    x5c = randomNullable { (1..3).map { randomString() } },
    exp = randomNullable { randomInt() },
    nbf = randomNullable { randomInt() },
    iat = randomNullable { randomInt() },
    d = randomNullable { randomString() },
    crv = randomString(),
    x = randomString()
)

internal fun randomEmailPreferencesResponse(): NetworkEmailPreferencesResponse = NetworkEmailPreferencesResponse(
    senderEmail = randomNullable { randomString() },
    senderName = randomNullable { randomString() },
    primaryColour = randomString(),
    secondaryColour = randomString(),
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomEmailCallToActionResponse()
)

internal fun randomEmailCallToActionResponse(): NetworkEmailCallToActionResponse = NetworkEmailCallToActionResponse(
    actionTarget = randomString(),
    headline = randomString(),
    actionText = randomString()
)

internal fun randomOauthResponse(): NetworkOauthResponse = NetworkOauthResponse(
    authorizationEndpoint = randomString(),
    clientId = randomString(),
    grantType = randomString()
)

internal fun randomApplicationOwnerDetailsResponse(): NetworkApplicationOwnerDetailsResponse = NetworkApplicationOwnerDetailsResponse(
    userId = randomId(),
    email = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean()
)

internal fun randomGetLogoUploadUrlResponse(): NetworkGetLogoUploadUrlResponse = NetworkGetLogoUploadUrlResponse(
    uploadUrl = DEFAULT_UPLOAD_URL
)

/**
 * Site responses
 */
internal fun randomSiteResponse(): NetworkSiteResponse = NetworkSiteResponse(
    id = randomId(),
    name = randomString(),
    colour = randomString(),
    longitude = randomDouble(),
    latitude = randomDouble(),
    radius = randomInt(),
    passBackground = randomString(),
    created = randomInstant(),
    updated = randomInstant()
)

internal fun randomSiteLocksResponse(): NetworkSiteLocksResponse = NetworkSiteLocksResponse(
    id = randomId(),
    name = randomString(),
    colour = randomNullable { randomString() },
    role = UserRole.entries.random(),
    settings = randomLockSettingsResponse(),
    state = randomLockStateResponse(),
    favourite = randomBoolean()
)


internal fun randomUserForSiteResponse(): NetworkUserForSiteResponse = NetworkUserForSiteResponse(
    userId = randomId(),
    email = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean()
)

/**
 * Tile responses
 */
internal fun randomTileLocksResponse(): NetworkTileLocksResponse = NetworkTileLocksResponse(
    siteId = randomId(),
    tileId = randomId(),
    deviceIds = (1..3).map { randomId() }
)

/**
 * Lock operation data
 */
internal fun randomTimeRequirement(): BasicLockOperations.BasicTimeRequirement = BasicLockOperations.BasicTimeRequirement(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = (1..3).map { randomString() }
)

internal fun randomLocationRequirement(): BasicLockOperations.BasicLocationRequirement = BasicLockOperations.BasicLocationRequirement(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomBoolean(),
    radius = randomInt(),
    accuracy = randomInt()
)

internal fun randomUnlockBetween(): BasicLockOperations.BasicUnlockBetween = BasicLockOperations.BasicUnlockBetween(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = (1..3).map { randomString() },
    exceptions = randomNullable { (1..3).map { randomString() } }
)

internal fun randomUnlockOperation(): BasicLockOperations.BasicUnlockOperation = BasicLockOperations.BasicUnlockOperation(
    baseOperation = randomBaseOperation(),
    directAccessEndpoints = randomNullable { (1..3).map { randomString() } }
)

internal fun randomShareLockOperation(): BasicLockOperations.BasicShareLockOperation = BasicLockOperations.BasicShareLockOperation(
    baseOperation = randomBaseOperation(),
    shareLock = randomShareLock()
)

internal fun randomBatchShareLockOperation(): BasicLockOperations.BasicBatchShareLockOperation = BasicLockOperations.BasicBatchShareLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomShareLock() }
)

internal fun randomRevokeAccessToLockOperation(): BasicLockOperations.BasicRevokeAccessToLockOperation = BasicLockOperations.BasicRevokeAccessToLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomString() }
)

internal fun randomUpdateSecureSettingUnlockDuration(): BasicLockOperations.BasicUpdateSecureSettingUnlockDuration = BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
    baseOperation = randomBaseOperation(),
    unlockDuration = randomInt()
)

internal fun randomUpdateSecureSettingUnlockBetween(): BasicLockOperations.BasicUpdateSecureSettingUnlockBetween = BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
    baseOperation = randomBaseOperation(),
    unlockBetween = randomNullable { randomUnlockBetween() }
)

internal fun randomShareLock(): BasicLockOperations.BasicShareLock = BasicLockOperations.BasicShareLock(
    targetUserId = randomString(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = randomByteArray(),
    start = randomNullable { randomInt() },
    end = randomNullable { randomInt() }
)

internal fun randomBaseOperation(): BasicLockOperations.BasicBaseOperation = BasicLockOperations.BasicBaseOperation(
    userId = randomNullable { randomString() },
    userCertificateChain = randomNullable { (1..3).map { randomString() } },
    userPrivateKey = randomNullable { randomByteArray() },
    lockId = randomString(),
    notBefore = randomInt(),
    issuedAt = randomInt(),
    expiresAt = randomInt(),
    jti = randomString()
)

/**
 * Platform data
 */
internal fun randomCreateApplication(): BasicPlatform.BasicCreateApplication = BasicPlatform.BasicCreateApplication(
    name = randomString(),
    companyName = randomString(),
    mailingAddress = randomString(),
    privacyPolicy = randomNullable { randomString() },
    supportContact = randomNullable { randomString() },
    appLink = randomNullable { randomString() },
    emailPreferences = randomNullable { randomEmailPreferences() },
    logoUrl = randomNullable { randomString() }
)

internal fun randomEmailPreferences(): BasicPlatform.BasicEmailPreferences = BasicPlatform.BasicEmailPreferences(
    senderEmail = randomNullable { randomString() },
    senderName = randomNullable { randomString() },
    primaryColour = randomNullable { randomString() },
    secondaryColour = randomNullable { randomString() },
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomNullable { randomEmailCallToAction() }
)

internal fun randomEmailCallToAction(): BasicEmailCallToAction = BasicEmailCallToAction(
    actionTarget = randomString(),
    headline = randomString(),
    actionText = randomString()
)

internal fun randomRsaKey(): BasicPlatform.BasicRsaKey = BasicPlatform.BasicRsaKey(
    kty = randomString(),
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    p = randomString(),
    q = randomString(),
    d = randomString(),
    e = randomString(),
    qi = randomString(),
    dp = randomString(),
    dq = randomString(),
    n = randomString()
)

internal fun randomEcKey(): BasicPlatform.BasicEcKey = BasicPlatform.BasicEcKey(
    kty = randomString(),
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    d = randomString(),
    crv = randomString(),
    x = randomString(),
    y = randomString()
)

internal fun randomEd25519Key(): BasicPlatform.BasicEd25519Key = BasicPlatform.BasicEd25519Key(
    kty = randomString(),
    use = randomString(),
    kid = randomString(),
    alg = randomNullable { randomString() },
    d = randomString(),
    crv = randomString(),
    x = randomString()
)

/**
 * Config
 */
fun randomSdkConfig(): SdkConfig = SdkConfig(
    apiEnvironment = randomNullable { ApiEnvironment.entries.random() },
    cloudAuthToken = randomNullable { randomString() },
    cloudRefreshToken = randomNullable { randomString() },
    fusionHost = randomNullable { randomString() },
    secureStorage = DefaultSecureStorage(MemorySettings()),
    debugLogging = randomNullable { randomBoolean() }
)

/**
 * Test utils
 */
internal fun randomInt(min: Int = 0, max: Int = Int.MAX_VALUE) = Random.nextInt(min, max)
internal fun randomDouble(from: Double = 0.0, to: Double = 100.0): Double = Random.nextDouble(from, to)
internal fun randomBoolean(): Boolean = Random.nextBoolean()
internal inline fun <T> randomNullable(supplier: () -> T): T? = if (randomBoolean()) supplier() else null
internal fun randomString(): String = Uuid.random().toString()
internal fun randomByteArray(): ByteArray = Random.nextBytes(ByteArray(randomInt(1, 20)))