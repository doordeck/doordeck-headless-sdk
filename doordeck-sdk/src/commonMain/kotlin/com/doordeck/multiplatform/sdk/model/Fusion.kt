package com.doordeck.multiplatform.sdk.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
object Fusion {

    /**
     * The following classes are serializable because we are using them in the actual requests.
     */
    @Serializable
    sealed interface LockController

    @Serializable
    @SerialName("alpeta")
    data class AlpetaController(
        val username: String,
        val password: String,
        val doorId: Int, // Long, this can give problems
        val baseUrl: String? = null
    ) : LockController

    @Serializable
    @SerialName("amag")
    data class AmagController(
        val username: String,
        val password: String,
        val doorId: Int,
        val baseUrl: String? = null
    ) : LockController

    @Serializable
    @SerialName("assa-abloy")
    data class AssaAbloyController(
        val baseUrl: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("avigilon")
    data class AvigilonController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("axis")
    data class AxisController(
        val baseUrl: String,
        val doorIdentifier: String
    ) : LockController

    @Serializable
    @SerialName("demo")
    data class DemoController(
        val port: Int = 8080
    ) : LockController

    @Serializable
    @SerialName("gallagher")
    data class GallagherController(
        val baseUrl: String? = null,
        val apiKey: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("genetec")
    data class GenetecController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("lenel")
    data class LenelController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val directoryId: String,
        val panelId: String,
        val readerId: String
    ) : LockController

    @Serializable
    @SerialName("mitrefinch")
    data class MitrefinchController(
        val host: String,
        val output: Int
    ) : LockController

    @Serializable
    @SerialName("net2")
    data class PaxtonNet2Controller(
        val host: String,
        val address: String,
        val output: Short
    ) : LockController

    @Serializable
    @SerialName("paxton10")
    data class Paxton10Controller(
        val baseUrl: String,
        val username: String,
        val password: String,
        val applianceId: Int
    ) : LockController

    @Serializable
    @SerialName("integra")
    data class IntegraV1Controller(
        val username: String,
        val password: String,
        val controllerId: Int
    ) : LockController

    @Serializable
    @SerialName("integra-v2")
    data class IntegraV2Controller(
        val baseUrl: String,
        val sessionId: String,
        val controllerId: Int,
        val cardholderId: Int,
        val pinCode: Int? = null
    ) : LockController

    @Serializable
    @SerialName("pac512")
    data class PacController(
        val dataSource: DataSource,
        val outputChannel: Int,
        val controllerSerial: Int
    ) : LockController

    @Serializable
    data class DataSource(
        val driverClass: String,
        val url: String,
        val user: String,
        val password: String
    )

    @Serializable
    @SerialName("tdsi-exgarde")
    data class TdsiExgardeController(
        val dbUrl: String? = null,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController

    @Serializable
    @SerialName("tdsi-gardis")
    data class TdsiGardisController(
        val host: String,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController

    @Serializable
    @SerialName("zkteco-zkbio-cvsecurity")
    data class ZktecoController(
        val clientSecret: String,
        val doorId: String,
        val baseUrl: String? = null,
        val entityType: ZktecoEntityType
    ) : LockController

    @Serializable
    enum class ZktecoEntityType {
        DOOR,
        FLOOR
    }
}