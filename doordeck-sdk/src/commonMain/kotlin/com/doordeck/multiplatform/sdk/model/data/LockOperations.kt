package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole

internal data class BasicTimeRequirement(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Set<DayOfWeek>
)

internal data class BasicLocationRequirement(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean = false,
    val radius: Int,
    val accuracy: Int
)

internal data class BasicUnlockBetween(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Set<DayOfWeek>,
    val exceptions: List<String>? = null
)

internal data class BasicUnlockOperation(
    val baseOperation: BasicBaseOperation,
    val directAccessEndpoints: List<String>? = null
): BasicOperation

internal data class BasicShareLockOperation(
    val baseOperation: BasicBaseOperation,
    val shareLock: BasicShareLock
): BasicOperation

internal data class BasicShareLock(
    val targetUserId: String,
    val targetUserRole: UserRole,
    val targetUserPublicKey: ByteArray,
    val start: Long? = null,
    val end: Long? = null
)

internal data class BasicBatchShareLockOperation(
    val baseOperation: BasicBaseOperation,
    val users: List<BasicShareLock>
): BasicOperation

internal data class BasicRevokeAccessToLockOperation(
    val baseOperation: BasicBaseOperation,
    val users: List<String>
): BasicOperation

internal data class BasicUpdateSecureSettingUnlockDuration(
    val baseOperation: BasicBaseOperation,
    val unlockDuration: Int
): BasicOperation

internal data class BasicUpdateSecureSettingUnlockBetween(
    val baseOperation: BasicBaseOperation,
    val unlockBetween: BasicUnlockBetween? = null
): BasicOperation

internal data class BasicBaseOperation(
    val userId: String? = null,
    val userCertificateChain: List<String>? = null,
    val userPrivateKey: ByteArray? = null,
    val lockId: String,
    val notBefore: Long,
    val issuedAt: Long,
    val expiresAt: Long,
    val jti: String
)

internal sealed interface BasicOperation