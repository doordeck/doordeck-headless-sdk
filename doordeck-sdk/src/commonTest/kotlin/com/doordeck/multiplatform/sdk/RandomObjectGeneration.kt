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
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.AuditIssuerResponse
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.AuditSubjectResponse
import com.doordeck.multiplatform.sdk.model.responses.AuthKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ControllerResponse
import com.doordeck.multiplatform.sdk.model.responses.DiscoveredDeviceResponse
import com.doordeck.multiplatform.sdk.model.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.model.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.EmailCallToActionResponse
import com.doordeck.multiplatform.sdk.model.responses.EmailPreferencesResponse
import com.doordeck.multiplatform.sdk.model.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.model.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.model.responses.LocationRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockSettingsResponse
import com.doordeck.multiplatform.sdk.model.responses.LockStateResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.OauthResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateResponse
import com.doordeck.multiplatform.sdk.model.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.SiteResponse
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.model.responses.TimeRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UnlockBetweenSettingResponse
import com.doordeck.multiplatform.sdk.model.responses.UsageRequirementsResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Account responses
 */
internal fun randomTokenResponse(): TokenResponse = TokenResponse(
    authToken = randomString(),
    refreshToken = randomString(),
)

internal fun randomUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    emailVerified = randomBoolean(),
    publicKey = randomPublicKey()
)

internal fun randomRegisterEphemeralKeyResponse(): RegisterEphemeralKeyResponse = RegisterEphemeralKeyResponse(
    certificateChain = listOf(randomPublicKey()),
    userId = randomUuid()
)

internal fun randomRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
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
internal fun randomLockResponse(): LockResponse = LockResponse(
    id = randomUuid(),
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

internal fun randomLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = randomDouble(),
    permittedAddresses = (1..3).map { randomIp() },
    defaultName = randomString(),
    usageRequirements = randomNullable { randomUsageRequirementsResponse() },
    unlockBetweenWindow = randomNullable { randomUnlockBetweenSettingResponse() },
    tiles = (1..3).map { randomUuid() },
    hidden = randomBoolean(),
    directAccessEndpoints = (1..3).map { randomString() },
    capabilities = (1..3).associate { CapabilityType.entries.random() to CapabilityStatus.entries.random() }
)

internal fun randomUnlockBetweenSettingResponse(): UnlockBetweenSettingResponse = UnlockBetweenSettingResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3),
    exceptions = randomNullable { (1..3).map { randomString() } }
)

internal fun randomUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = randomNullable { (1..3).map { randomTimeRequirementResponse() } },
    location = randomLocationRequirementResponse()
)

internal fun randomTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3)
)

internal fun randomLocationRequirementResponse(): LocationRequirementResponse = LocationRequirementResponse(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomBoolean(),
    radius = randomInt(),
    accuracy = randomInt()
)

internal fun randomShareableLockResponse(): ShareableLockResponse = ShareableLockResponse(
    id = randomUuid(),
    name = randomString()
)


internal fun randomUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = randomUuid(),
    publicKey = randomPublicKey()
)

internal fun randomBatchUserPublicKeyResponse(): BatchUserPublicKeyResponse = BatchUserPublicKeyResponse(
    id = randomUuid(),
    email = randomNullable { randomEmail() },
    foreignKey = randomNullable { randomString() },
    phone = randomNullable { randomString() },
    publicKey = randomString()
)

internal fun randomLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = randomUuid(),
    email = randomEmail(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() },
    devices = (1..3).map { randomLockUserDetailsResponse() }
)

internal fun randomUserLockResponse(): UserLockResponse = UserLockResponse(
    userId = randomUuid(),
    email = randomEmail(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = randomUuid(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockStateResponse(): LockStateResponse = LockStateResponse(
    locked = randomBoolean(),
    connected = randomBoolean()
)

internal fun randomAuditResponse(): AuditResponse = AuditResponse(
    deviceId = randomUuid(),
    timestamp = randomString(),
    type = AuditEvent.entries.random(),
    issuer = randomAuditIssuerResponse(),
    subject = randomNullable { randomAuditSubjectResponse() },
    rejectionReason = randomNullable { randomString() },
    rejected = randomBoolean()
)

internal fun randomAuditSubjectResponse(): AuditSubjectResponse = AuditSubjectResponse(
    userId = randomUuid(),
    email = randomEmail(),
    displayName = randomNullable { randomString() }
)

internal fun randomAuditIssuerResponse(): AuditIssuerResponse = AuditIssuerResponse(
    userId = randomUuid(),
    email = randomNullable { randomEmail() },
    ip = randomNullable { randomIp() }
)

/**
 * Platform responses
 */
internal fun randomApplicationResponse(): ApplicationResponse = ApplicationResponse(
    applicationId = randomUuid(),
    name = randomString(),
    lastUpdated = randomNullable { randomDouble() },
    owners = (1..3).map { randomUuid() },
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

internal fun randomAuthKeyResponse(): AuthKeyResponse = when(listOf(RsaKeyResponse::class, EcKeyResponse::class, Ed25519KeyResponse::class).random()) {
    RsaKeyResponse::class -> randomRsaKeyResponse()
    EcKeyResponse::class -> randomEcKeyResponse()
    Ed25519KeyResponse::class -> randomEd25519KeyResponse()
    else -> error("Unknown key class")
}

internal fun randomRsaKeyResponse(): RsaKeyResponse = RsaKeyResponse(
    use = randomString(),
    kid = randomUuid(),
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

internal fun randomEcKeyResponse(): EcKeyResponse = EcKeyResponse(
    use = randomString(),
    kid = randomUuid(),
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

internal fun randomEd25519KeyResponse(): Ed25519KeyResponse = Ed25519KeyResponse(
    use = randomString(),
    kid = randomUuid(),
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

internal fun randomEmailPreferencesResponse(): EmailPreferencesResponse = EmailPreferencesResponse(
    senderEmail = randomNullable { randomEmail() },
    senderName = randomNullable { randomString() },
    primaryColour = randomString(),
    secondaryColour = randomString(),
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomEmailCallToActionResponse()
)

internal fun randomEmailCallToActionResponse(): EmailCallToActionResponse = EmailCallToActionResponse(
    actionTarget = randomString(),
    headline = randomString(),
    actionText = randomString()
)

internal fun randomOauthResponse(): OauthResponse = OauthResponse(
    authorizationEndpoint = randomUrl(),
    clientId = randomString(),
    grantType = GrantType.entries.random()
)

internal fun randomApplicationOwnerDetailsResponse(): ApplicationOwnerDetailsResponse = ApplicationOwnerDetailsResponse(
    userId = randomUuid(),
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean()
)

internal fun randomGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse = GetLogoUploadUrlResponse(
    uploadUrl = DEFAULT_UPLOAD_URL
)

/**
 * Site responses
 */
internal fun randomSiteResponse(): SiteResponse = SiteResponse(
    id = randomUuid(),
    name = randomString(),
    colour = randomString(),
    longitude = randomDouble(),
    latitude = randomDouble(),
    radius = randomInt(),
    passBackground = randomString(),
    created = randomString(),
    updated = randomString()
)

internal fun randomSiteLocksResponse(): SiteLocksResponse = SiteLocksResponse(
    id = randomUuid(),
    name = randomString(),
    colour = randomNullable { randomString() },
    role = UserRole.entries.random(),
    settings = randomLockSettingsResponse(),
    state = randomLockStateResponse(),
    favourite = randomBoolean()
)


internal fun randomUserForSiteResponse(): UserForSiteResponse = UserForSiteResponse(
    userId = randomUuid(),
    email = randomEmail(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean()
)

/**
 * Tile responses
 */
internal fun randomTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = randomUuid(),
    tileId = randomUuid(),
    deviceIds = (1..3).map { randomUuid() }
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
    targetUserId = randomUuid(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = randomByteArray(),
    start = randomNullable { randomLong() },
    end = randomNullable { randomLong() }
)

internal fun randomBaseOperation(): BasicBaseOperation = BasicBaseOperation(
    userId = randomNullable { randomUuid() },
    userCertificateChain = randomNullable { (1..3).map { randomPublicKey() } },
    userPrivateKey = randomNullable { randomByteArray() },
    lockId = randomUuid(),
    notBefore = randomLong(),
    issuedAt = randomLong(),
    expiresAt = randomLong(),
    jti = randomUuid()
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
    kid = randomUuid(),
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
    kid = randomUuid(),
    alg = randomNullable { randomString() },
    d = randomString(),
    crv = randomString(),
    x = randomString(),
    y = randomString()
)

internal fun randomEd25519Key(): BasicEd25519Key = BasicEd25519Key(
    kty = randomString(),
    use = randomString(),
    kid = randomUuid(),
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
internal fun randomUuid(): String = Uuid.random().toString()
internal fun randomPublicKey(): String = CryptoManager.generateRawKeyPair().public.encodeByteArrayToBase64()
internal fun randomEmail(): String = "${randomUuid()}@doordeck.com"
internal fun randomIp(): String = (1..4).joinToString(".") { Random.nextInt(0, 256).toString() }

internal fun randomInt(min: Int = 0, max: Int = Int.MAX_VALUE) = Random.nextInt(min, max)
internal fun randomDouble(from: Double = 0.0, to: Double = 100.0): Double = Random.nextDouble(from, to)
internal fun randomBoolean(): Boolean = Random.nextBoolean()
internal fun randomLong(from: Long = 0, to: Long = 100): Long = Random.nextLong(from, to)
internal fun randomString(): String = Uuid.random().toString() // TODO
internal fun randomByteArray(): ByteArray = Random.nextBytes(ByteArray(randomInt(1, 20)))
internal inline fun <T> randomNullable(supplier: () -> T): T? = if (randomBoolean()) supplier() else null