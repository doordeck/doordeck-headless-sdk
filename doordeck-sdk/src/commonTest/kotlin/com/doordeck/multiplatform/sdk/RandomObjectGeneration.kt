package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.data.Fusion
import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.BasicBaseOperation
import com.doordeck.multiplatform.sdk.model.data.BasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicCreateApplication
import com.doordeck.multiplatform.sdk.model.data.BasicEcKey
import com.doordeck.multiplatform.sdk.model.data.BasicEd25519Key
import com.doordeck.multiplatform.sdk.model.data.BasicEmailCallToAction
import com.doordeck.multiplatform.sdk.model.data.BasicEmailPreferences
import com.doordeck.multiplatform.sdk.model.data.BasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.BasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicRsaKey
import com.doordeck.multiplatform.sdk.model.data.BasicShareLock
import com.doordeck.multiplatform.sdk.model.data.BasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.BasicUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.BasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.BasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAuditIssuerResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAuditSubjectResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAuthKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ControllerResponse
import com.doordeck.multiplatform.sdk.model.responses.DiscoveredDeviceResponse
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEd25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEmailCallToActionResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEmailPreferencesResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLocationRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockSettingsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockStateResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicOauthResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRsaKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateResponse
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.model.responses.BasicShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTimeRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUnlockBetweenSettingResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUsageRequirementsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Account responses
 */
internal fun randomTokenResponse(): BasicTokenResponse = BasicTokenResponse(
    authToken = randomString(),
    refreshToken = randomString(),
)

internal fun randomUserDetailsResponse(): BasicUserDetailsResponse = BasicUserDetailsResponse(
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    emailVerified = randomBoolean(),
    publicKey = randomPublicKey()
)

internal fun randomRegisterEphemeralKeyResponse(): BasicRegisterEphemeralKeyResponse = BasicRegisterEphemeralKeyResponse(
    certificateChain = listOf(randomPublicKey()),
    userId = randomUuidString()
)

internal fun randomRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse = BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse(
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
    id = randomString(),
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
internal fun randomLockResponse(): BasicLockResponse = BasicLockResponse(
    id = randomUuidString(),
    name = randomString(),
    colour = randomNullable { randomString() },
    start = randomNullable { randomString() },
    end = randomNullable { randomString() },
    role = UserRole.entries.random(),
    settings = randomLockSettingsResponse(),
    state = randomLockStateResponse(),
    favourite = randomBoolean(),
    unlockTime = randomNullable { randomDouble() }
)

internal fun randomLockSettingsResponse(): BasicLockSettingsResponse = BasicLockSettingsResponse(
    unlockTime = randomDouble(),
    permittedAddresses = (1..3).map { randomIp() },
    defaultName = randomString(),
    usageRequirements = randomNullable { randomUsageRequirementsResponse() },
    unlockBetweenWindow = randomNullable { randomUnlockBetweenSettingResponse() },
    tiles = (1..3).map { randomUuidString() },
    hidden = randomBoolean(),
    directAccessEndpoints = (1..3).map { randomString() },
    capabilities = (1..3).associate { CapabilityType.entries.random() to CapabilityStatus.entries.random() }
)

internal fun randomUnlockBetweenSettingResponse(): BasicUnlockBetweenSettingResponse = BasicUnlockBetweenSettingResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3),
    exceptions = randomNullable { (1..3).map { randomString() } }
)

internal fun randomUsageRequirementsResponse(): BasicUsageRequirementsResponse = BasicUsageRequirementsResponse(
    time = randomNullable { (1..3).map { randomTimeRequirementResponse() } },
    location = randomLocationRequirementResponse()
)

internal fun randomTimeRequirementResponse(): BasicTimeRequirementResponse = BasicTimeRequirementResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3)
)

internal fun randomLocationRequirementResponse(): BasicLocationRequirementResponse = BasicLocationRequirementResponse(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomBoolean(),
    radius = randomInt(),
    accuracy = randomInt()
)

internal fun randomShareableLockResponse(): BasicShareableLockResponse = BasicShareableLockResponse(
    id = randomUuidString(),
    name = randomString()
)


internal fun randomUserPublicKeyResponse(): BasicUserPublicKeyResponse = BasicUserPublicKeyResponse(
    id = randomUuidString(),
    publicKey = randomPublicKey()
)

internal fun randomBatchUserPublicKeyResponse(): BasicBatchUserPublicKeyResponse = BasicBatchUserPublicKeyResponse(
    id = randomUuidString(),
    email = randomNullable { randomEmail() },
    foreignKey = randomNullable { randomString() },
    phone = randomNullable { randomString() },
    publicKey = randomString()
)

internal fun randomLockUserResponse(): BasicLockUserResponse = BasicLockUserResponse(
    userId = randomUuidString(),
    email = randomEmail(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() },
    devices = (1..3).map { randomLockUserDetailsResponse() }
)

internal fun randomUserLockResponse(): BasicUserLockResponse = BasicUserLockResponse(
    userId = randomUuidString(),
    email = randomEmail(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockUserDetailsResponse(): BasicLockUserDetailsResponse = BasicLockUserDetailsResponse(
    deviceId = randomUuidString(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockStateResponse(): BasicLockStateResponse = BasicLockStateResponse(
    locked = randomBoolean(),
    connected = randomBoolean()
)

internal fun randomAuditResponse(): BasicAuditResponse = BasicAuditResponse(
    deviceId = randomUuidString(),
    timestamp = randomString(),
    type = AuditEvent.entries.random(),
    issuer = randomAuditIssuerResponse(),
    subject = randomNullable { randomAuditSubjectResponse() },
    rejectionReason = randomNullable { randomString() },
    rejected = randomBoolean()
)

internal fun randomAuditSubjectResponse(): BasicAuditSubjectResponse = BasicAuditSubjectResponse(
    userId = randomUuidString(),
    email = randomEmail(),
    displayName = randomNullable { randomString() }
)

internal fun randomAuditIssuerResponse(): BasicAuditIssuerResponse = BasicAuditIssuerResponse(
    userId = randomUuidString(),
    email = randomNullable { randomEmail() },
    ip = randomNullable { randomIp() }
)

/**
 * Platform responses
 */
internal fun randomApplicationResponse(): BasicApplicationResponse = BasicApplicationResponse(
    applicationId = randomUuidString(),
    name = randomString(),
    lastUpdated = randomNullable { randomDouble() },
    owners = (1..3).map { randomUuidString() },
    corsDomains = (1..3).map { randomUrl() },
    authDomains = (1..3).map { randomUrl() },
    logoUrl = randomNullable { randomUrl() },
    privacyPolicy = randomNullable { randomUrl() },
    mailingAddress = randomNullable { randomEmail() },
    companyName = randomNullable { randomString() },
    supportContact = randomNullable { randomUrl() },
    appLink = randomNullable { randomUrl() },
    slug = randomNullable { randomString() },
    emailPreferences = randomEmailPreferencesResponse(),
    authKeys = (1..3).associate { randomString() to randomAuthKeyResponse() },
    oauth = randomNullable { randomOauthResponse() },
    isDoordeckApplication = randomBoolean()
)

internal fun randomAuthKeyResponse(): BasicAuthKeyResponse = when(listOf(BasicRsaKeyResponse::class, BasicEcKeyResponse::class, BasicEd25519KeyResponse::class).random()) {
    BasicRsaKeyResponse::class -> randomRsaKeyResponse()
    BasicEcKeyResponse::class -> randomEcKeyResponse()
    BasicEd25519KeyResponse::class -> randomEd25519KeyResponse()
    else -> error("Unknown key class")
}

internal fun randomRsaKeyResponse(): BasicRsaKeyResponse = BasicRsaKeyResponse(
    use = randomString(),
    kid = randomUuidString(),
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

internal fun randomEcKeyResponse(): BasicEcKeyResponse = BasicEcKeyResponse(
    use = randomString(),
    kid = randomUuidString(),
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

internal fun randomEd25519KeyResponse(): BasicEd25519KeyResponse = BasicEd25519KeyResponse(
    use = randomString(),
    kid = randomUuidString(),
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

internal fun randomEmailPreferencesResponse(): BasicEmailPreferencesResponse = BasicEmailPreferencesResponse(
    senderEmail = randomNullable { randomEmail() },
    senderName = randomNullable { randomString() },
    primaryColour = randomString(),
    secondaryColour = randomString(),
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomEmailCallToActionResponse()
)

internal fun randomEmailCallToActionResponse(): BasicEmailCallToActionResponse = BasicEmailCallToActionResponse(
    actionTarget = randomString(),
    headline = randomString(),
    actionText = randomString()
)

internal fun randomOauthResponse(): BasicOauthResponse = BasicOauthResponse(
    authorizationEndpoint = randomUrl(),
    clientId = randomString(),
    grantType = GrantType.entries.random()
)

internal fun randomApplicationOwnerDetailsResponse(): BasicApplicationOwnerDetailsResponse = BasicApplicationOwnerDetailsResponse(
    userId = randomUuidString(),
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean()
)

internal fun randomGetLogoUploadUrlResponse(): BasicGetLogoUploadUrlResponse = BasicGetLogoUploadUrlResponse(
    uploadUrl = DEFAULT_UPLOAD_URL
)

/**
 * Site responses
 */
internal fun randomSiteResponse(): BasicSiteResponse = BasicSiteResponse(
    id = randomUuidString(),
    name = randomString(),
    colour = randomString(),
    longitude = randomDouble(),
    latitude = randomDouble(),
    radius = randomInt(),
    passBackground = randomString(),
    created = randomString(),
    updated = randomString()
)

internal fun randomSiteLocksResponse(): BasicSiteLocksResponse = BasicSiteLocksResponse(
    id = randomUuidString(),
    name = randomString(),
    colour = randomNullable { randomString() },
    role = UserRole.entries.random(),
    settings = randomLockSettingsResponse(),
    state = randomLockStateResponse(),
    favourite = randomBoolean()
)


internal fun randomUserForSiteResponse(): BasicUserForSiteResponse = BasicUserForSiteResponse(
    userId = randomUuidString(),
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean()
)

/**
 * Tile responses
 */
internal fun randomTileLocksResponse(): BasicTileLocksResponse = BasicTileLocksResponse(
    siteId = randomUuidString(),
    tileId = randomUuidString(),
    deviceIds = (1..3).map { randomUuidString() }
)

/**
 * Lock operation data
 */
internal fun randomTimeRequirement(): BasicTimeRequirement = BasicTimeRequirement(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3)
)

internal fun randomLocationRequirement(): BasicLocationRequirement = BasicLocationRequirement(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomBoolean(),
    radius = randomInt(),
    accuracy = randomInt()
)

internal fun randomUnlockBetween(): BasicUnlockBetween = BasicUnlockBetween(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3),
    exceptions = randomNullable { (1..3).map { randomString() } }
)

internal fun randomUnlockOperation(): BasicUnlockOperation = BasicUnlockOperation(
    baseOperation = randomBaseOperation(),
    directAccessEndpoints = randomNullable { (1..3).map { randomString() } }
)

internal fun randomShareLockOperation(): BasicShareLockOperation = BasicShareLockOperation(
    baseOperation = randomBaseOperation(),
    shareLock = randomShareLock()
)

internal fun randomBatchShareLockOperation(): BasicBatchShareLockOperation = BasicBatchShareLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomShareLock() }
)

internal fun randomRevokeAccessToLockOperation(): BasicRevokeAccessToLockOperation = BasicRevokeAccessToLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomString() }
)

internal fun randomUpdateSecureSettingUnlockDuration(): BasicUpdateSecureSettingUnlockDuration = BasicUpdateSecureSettingUnlockDuration(
    baseOperation = randomBaseOperation(),
    unlockDuration = randomInt()
)

internal fun randomUpdateSecureSettingUnlockBetween(): BasicUpdateSecureSettingUnlockBetween = BasicUpdateSecureSettingUnlockBetween(
    baseOperation = randomBaseOperation(),
    unlockBetween = randomNullable { randomUnlockBetween() }
)

internal fun randomShareLock(): BasicShareLock = BasicShareLock(
    targetUserId = randomUuidString(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = randomByteArray(),
    start = randomNullable { randomLong() },
    end = randomNullable { randomLong() }
)

internal fun randomBaseOperation(): BasicBaseOperation = BasicBaseOperation(
    userId = randomNullable { randomUuidString() },
    userCertificateChain = randomNullable { (1..3).map { randomPublicKey() } },
    userPrivateKey = randomNullable { randomByteArray() },
    lockId = randomUuidString(),
    notBefore = randomLong(),
    issuedAt = randomLong(),
    expiresAt = randomLong(),
    jti = randomUuidString()
)

/**
 * Platform data
 */
internal fun randomCreateApplication(): BasicCreateApplication = BasicCreateApplication(
    name = randomString(),
    companyName = randomString(),
    mailingAddress = randomString(),
    privacyPolicy = randomNullable { randomUrl() },
    supportContact = randomNullable { randomUrl() },
    appLink = randomNullable { randomUrl() },
    emailPreferences = randomNullable { randomEmailPreferences() },
    logoUrl = randomNullable { randomUrl() }
)

internal fun randomEmailPreferences(): BasicEmailPreferences = BasicEmailPreferences(
    senderEmail = randomNullable { randomString() },
    senderName = randomNullable { randomString() },
    primaryColour = randomNullable { randomString() },
    secondaryColour = randomNullable { randomString() },
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomNullable { randomEmailCallToAction() }
)

internal fun randomEmailCallToAction(): BasicEmailCallToAction = BasicEmailCallToAction(
    actionTarget = randomUrl(),
    headline = randomString(),
    actionText = randomString()
)

internal fun randomRsaKey(): BasicRsaKey = BasicRsaKey(
    kty = randomString(),
    use = randomString(),
    kid = randomUuidString(),
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

internal fun randomEcKey(): BasicEcKey = BasicEcKey(
    kty = randomString(),
    use = randomString(),
    kid = randomUuidString(),
    alg = randomNullable { randomString() },
    d = randomString(),
    crv = randomString(),
    x = randomString(),
    y = randomString()
)

internal fun randomEd25519Key(): BasicEd25519Key = BasicEd25519Key(
    kty = randomString(),
    use = randomString(),
    kid = randomUuidString(),
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

internal fun randomUrl(): String = "https://${randomString()}.com"
internal fun randomUuidString(): String = Uuid.random().toString()
internal fun randomPublicKey(): String = CryptoManager.generateRawKeyPair().public.encodeByteArrayToBase64()
internal fun randomEmail(): String = "${randomUuidString()}@doordeck.com"
internal fun randomIp(): String = (1..4).joinToString(".") { Random.nextInt(0, 256).toString() }

internal fun randomInt(min: Int = 0, max: Int = Int.MAX_VALUE) = Random.nextInt(min, max)
internal fun randomDouble(from: Double = 0.0, to: Double = 100.0): Double = Random.nextDouble(from, to)
internal fun randomBoolean(): Boolean = Random.nextBoolean()
internal fun randomLong(from: Long = 0, to: Long = 100): Long = Random.nextLong(from, to)
internal fun randomString(): String = Uuid.random().toString() // TODO
internal fun randomByteArray(): ByteArray = Random.nextBytes(ByteArray(randomInt(1, 20)))
internal inline fun <T> randomNullable(supplier: () -> T): T? = if (randomBoolean()) supplier() else null