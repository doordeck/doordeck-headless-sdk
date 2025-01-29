package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_UPLOAD_URL
import com.doordeck.multiplatform.sdk.api.model.AuditEvent
import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType
import com.doordeck.multiplatform.sdk.api.model.Fusion
import com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditIssuerResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.AuditSubjectResponse
import com.doordeck.multiplatform.sdk.api.responses.AuthKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.ControllerResponse
import com.doordeck.multiplatform.sdk.api.responses.DiscoveredDeviceResponse
import com.doordeck.multiplatform.sdk.api.responses.DoorStateResponse
import com.doordeck.multiplatform.sdk.api.responses.EcKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.Ed25519KeyResponse
import com.doordeck.multiplatform.sdk.api.responses.EmailCallToActionResponse
import com.doordeck.multiplatform.sdk.api.responses.EmailPreferencesResponse
import com.doordeck.multiplatform.sdk.api.responses.FusionLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationTypeResponse
import com.doordeck.multiplatform.sdk.api.responses.LocationRequirementResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockSettingsResponse
import com.doordeck.multiplatform.sdk.api.responses.LockStateResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.OauthResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.api.responses.RsaKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateResponse
import com.doordeck.multiplatform.sdk.api.responses.ServiceStateType
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLockSettingsResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteStateResponse
import com.doordeck.multiplatform.sdk.api.responses.TileLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.TimeRequirementResponse
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.api.responses.UnlockBetweenSettingResponse
import com.doordeck.multiplatform.sdk.api.responses.UsageRequirementsResponse
import com.doordeck.multiplatform.sdk.api.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Account
 */
internal fun randomTokenResponse(): TokenResponse = TokenResponse(
    authToken = randomString(),
    refreshToken = randomString(),
)

internal fun randomUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    email = randomString(),
    displayName = randomNullable { randomString() },
    emailVerified = randomBoolean(),
    publicKey = randomString()
)

internal fun randomRegisterEphemeralKeyResponse(): RegisterEphemeralKeyResponse = RegisterEphemeralKeyResponse(
    certificateChain = (1..3).map { randomString() },
    userId = randomString()
)

internal fun randomRegisterEphemeralKeyWithSecondaryAuthenticationResponse(): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    method = TwoFactorMethod.entries.random()
)

/**
 * Fusion
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
 * Lock operations
 */
internal fun randomLockResponse(): LockResponse = LockResponse(
    id = randomString(),
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
    permittedAddresses = (1..3).map { randomString() },
    defaultName = randomString(),
    usageRequirements = randomNullable { randomUsageRequirementsResponse() },
    unlockBetweenWindow = randomNullable { randomUnlockBetweenSettingResponse() },
    tiles = (1..3).map { randomString() },
    hidden = randomBoolean(),
    directAccessEndpoints = (1..3).map { randomString() },
    capabilities = (1..3).associate { CapabilityType.entries.random() to CapabilityStatus.entries.random() }
)

internal fun randomUnlockBetweenSettingResponse(): UnlockBetweenSettingResponse = UnlockBetweenSettingResponse(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = (1..3).map { randomString() },
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
    days = (1..3).map { randomString() }
)

internal fun randomLocationRequirementResponse(): LocationRequirementResponse = LocationRequirementResponse(
    latitude = randomDouble(),
    longitude = randomDouble(),
    enabled = randomNullable { randomBoolean() },
    radius = randomNullable { randomInt() },
    accuracy = randomNullable { randomInt() }
)

internal fun randomShareableLockResponse(): ShareableLockResponse = ShareableLockResponse(
    id = randomString(),
    name = randomString()
)


internal fun randomUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = randomString(),
    publicKey = randomString()
)

internal fun randomBatchUserPublicKeyResponse(): BatchUserPublicKeyResponse = BatchUserPublicKeyResponse(
    id = randomString(),
    email = randomNullable { randomString() },
    foreignKey = randomNullable { randomString() },
    phone = randomNullable { randomString() },
    publicKey = randomString()
)

internal fun randomLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = randomString(),
    email = randomString(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() },
    devices = (1..3).map { randomLockUserDetailsResponse() }
)

internal fun randomUserLockResponse(): UserLockResponse = UserLockResponse(
    userId = randomString(),
    email = randomString(),
    publicKey = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = randomString(),
    role = UserRole.entries.random(),
    start = randomNullable { randomDouble() },
    end = randomNullable { randomDouble() }
)

internal fun randomLockStateResponse(): LockStateResponse = LockStateResponse(
    locked = randomBoolean(),
    connected = randomBoolean()
)

internal fun randomAuditResponse(): AuditResponse = AuditResponse(
    deviceId = randomString(),
    timestamp = randomDouble(),
    type = AuditEvent.entries.random(),
    issuer = randomAuditIssuerResponse(),
    subject = randomNullable { randomAuditSubjectResponse() },
    rejectionReason = randomNullable { randomString() },
    rejected = randomBoolean()
)

internal fun randomAuditSubjectResponse(): AuditSubjectResponse = AuditSubjectResponse(
    userId = randomString(),
    email = randomString(),
    displayName = randomNullable { randomString() }
)

internal fun randomAuditIssuerResponse(): AuditIssuerResponse = AuditIssuerResponse(
    userId = randomString(),
    email = randomNullable { randomString() },
    ip = randomNullable { randomString() }
)

/**
 * Platform
 */
internal fun randomApplicationResponse(): ApplicationResponse = ApplicationResponse(
    applicationId = randomString(),
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

internal fun randomAuthKeyResponse(): AuthKeyResponse = when(listOf(RsaKeyResponse::class, EcKeyResponse::class, Ed25519KeyResponse::class).random()) {
    RsaKeyResponse::class -> randomRsaKeyResponse()
    EcKeyResponse::class -> randomEcKeyResponse()
    Ed25519KeyResponse::class -> randomEd25519KeyResponse()
    else -> error("Unknown key class")
}

internal fun randomRsaKeyResponse(): RsaKeyResponse = RsaKeyResponse(
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

internal fun randomEcKeyResponse(): EcKeyResponse = EcKeyResponse(
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

internal fun randomEd25519KeyResponse(): Ed25519KeyResponse = Ed25519KeyResponse(
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

internal fun randomEmailPreferencesResponse(): EmailPreferencesResponse = EmailPreferencesResponse(
    senderEmail = randomNullable { randomString() },
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
    authorizationEndpoint = randomString(),
    clientId = randomString(),
    grantType = randomString()
)

internal fun randomApplicationOwnerDetailsResponse(): ApplicationOwnerDetailsResponse = ApplicationOwnerDetailsResponse(
    userId = randomString(),
    email = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean(),
    foreign = randomBoolean()
)

internal fun randomGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse = GetLogoUploadUrlResponse(
    uploadUrl = DEFAULT_UPLOAD_URL
)

/**
 * Site
 */
internal fun randomSiteResponse(): SiteResponse = SiteResponse(
    id = randomString(),
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
    id = randomString(),
    name = randomString(),
    colour = randomNullable { randomString() },
    role = UserRole.entries.random(),
    settings = randomSiteLockSettingsResponse()
)

internal fun randomSiteLockSettingsResponse(): SiteLockSettingsResponse = SiteLockSettingsResponse(
    unlockTime = randomDouble(),
    permittedAddresses = (1..3).map { randomString() },
    defaultName = randomString(),
    tiles = (1..3).map { randomString() },
    state = randomSiteStateResponse(),
    favourite = randomBoolean()
)

internal fun randomSiteStateResponse(): SiteStateResponse = SiteStateResponse(
    connected = randomBoolean()
)

internal fun randomUserForSiteResponse(): UserForSiteResponse = UserForSiteResponse(
    userId = randomString(),
    email = randomString(),
    displayName = randomNullable { randomString() },
    orphan = randomBoolean()
)

/**
 * Tile
 */
internal fun randomTileLocksResponse(): TileLocksResponse = TileLocksResponse(
    siteId = randomString(),
    tileId = randomString(),
    deviceIds = (1..3).map { randomString() }
)

private fun randomInt(min: Int = 0, max: Int = Int.MAX_VALUE) = Random.nextInt(min, max)
private fun randomDouble(from: Double = 0.0, to: Double = 100.0): Double = Random.nextDouble(from, to)
private fun randomBoolean(): Boolean = Random.nextBoolean()
private inline fun <T> randomNullable(supplier: () -> T): T? = if (randomBoolean()) supplier() else null
private fun randomString(): String = Uuid.random().toString()