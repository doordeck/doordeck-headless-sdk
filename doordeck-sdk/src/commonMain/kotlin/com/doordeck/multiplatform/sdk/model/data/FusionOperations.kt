package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.ZktecoEntityType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface BasicLockController

@Serializable
@SerialName("alpeta")
internal data class BasicAlpetaController(
    val username: String,
    val password: String,
    val doorId: Long,
    val baseUrl: String? = null
) : BasicLockController

@Serializable
@SerialName("amag")
internal data class BasicAmagController(
    val username: String,
    val password: String,
    val doorId: Int,
    val baseUrl: String? = null
) : BasicLockController

@Serializable
@SerialName("assa-abloy")
internal data class BasicAssaAbloyController(
    val baseUrl: String,
    val doorId: String
) : BasicLockController

@Serializable
@SerialName("avigilon")
internal data class BasicAvigilonController(
    val baseUrl: String,
    val username: String,
    val password: String,
    val doorId: String
) : BasicLockController

@Serializable
@SerialName("axis")
internal data class BasicAxisController(
    val baseUrl: String,
    val doorIdentifier: String
) : BasicLockController

@Serializable
@SerialName("ccure")
internal data class BasicCCureController(
    val baseUrl: String? = null,
    val username: String,
    val password: String,
    val doorType: String,
    val doorId: Int
) : BasicLockController

@Serializable
@SerialName("demo")
internal data class BasicDemoController(
    val port: UShort
) : BasicLockController

@Serializable
@SerialName("gallagher")
internal data class BasicGallagherController(
    val baseUrl: String? = null,
    val apiKey: String,
    val doorId: String
) : BasicLockController

@Serializable
@SerialName("genetec")
internal data class BasicGenetecController(
    val baseUrl: String,
    val username: String,
    val password: String,
    val doorId: String
) : BasicLockController

@Serializable
@SerialName("lenel")
internal data class BasicLenelController(
    val baseUrl: String,
    val username: String,
    val password: String,
    val directoryId: String,
    val panelId: String,
    val readerId: String
) : BasicLockController

@Serializable
@SerialName("mitrefinch")
internal data class BasicMitrefinchController(
    val host: String,
    val output: Int
) : BasicLockController

@Serializable
@SerialName("net2")
internal data class BasicPaxtonNet2Controller(
    val host: String,
    val username: String? = null,
    val password: String? = null,
    val address: String,
    val output: Short
) : BasicLockController

@Serializable
@SerialName("paxton10")
internal data class BasicPaxton10Controller(
    val baseUrl: String,
    val username: String,
    val password: String,
    val applianceId: Int
) : BasicLockController

@Serializable
@SerialName("integra")
internal data class BasicIntegraV1Controller(
    val username: String,
    val password: String,
    val controllerId: Int
) : BasicLockController

@Serializable
@SerialName("integra-v2")
internal data class BasicIntegraV2Controller(
    val baseUrl: String,
    val sessionId: String,
    val controllerId: Int,
    val cardholderId: Int,
    val pinCode: Int? = null
) : BasicLockController

@Serializable
@SerialName("pac512")
internal data class BasicPacController(
    val dataSource: BasicDataSource,
    val outputChannel: Int,
    val controllerSerial: Int
) : BasicLockController

@Serializable
internal data class BasicDataSource(
    val driverClass: String,
    val url: String,
    val user: String,
    val password: String
)

@Serializable
@SerialName("tdsi-exgarde")
internal data class BasicTdsiExgardeController(
    val dbUrl: String? = null,
    val username: String,
    val password: String,
    val doorId: Int
) : BasicLockController

@Serializable
@SerialName("tdsi-gardis")
internal data class BasicTdsiGardisController(
    val host: String,
    val username: String,
    val password: String,
    val doorId: Int
) : BasicLockController

@Serializable
@SerialName("zkteco-zkbio-cvsecurity")
internal data class BasicZktecoController(
    val clientSecret: String,
    val doorId: String,
    val baseUrl: String? = null,
    val entityType: ZktecoEntityType
) : BasicLockController