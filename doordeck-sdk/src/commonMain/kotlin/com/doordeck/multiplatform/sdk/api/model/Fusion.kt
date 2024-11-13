package com.doordeck.multiplatform.sdk.api.model

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
    class AlpetaController(
        val username: String,
        val password: String,
        val doorId: Int, // Long, this can give problems
        val baseUrl: String? = null
    ) : LockController

    @Serializable
    @SerialName("amag")
    class AmagController(
        val username: String,
        val password: String,
        val doorId: Int,
        val baseUrl: String? = null
    ) : LockController

    @Serializable
    @SerialName("assa-abloy")
    class AssaAbloyController(
        val baseUrl: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("avigilon")
    class AvigilonController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("axis")
    class AxisController(
        val baseUrl: String,
        val doorIdentifier: String
    ) : LockController

    @Serializable
    @SerialName("demo")
    class DemoController(
        val port: Int = 8080
    ) : LockController

    @Serializable
    @SerialName("gallagher")
    class GallagherController(
        val baseUrl: String? = null,
        val apiKey: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("genetec")
    class GenetecController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    @Serializable
    @SerialName("lenel")
    class LenelController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val directoryId: String,
        val panelId: String,
        val readerId: String
    ) : LockController

    @Serializable
    @SerialName("mitrefinch")
    class MitrefinchController(
        val host: String,
        val output: Int
    ) : LockController

    @Serializable
    @SerialName("net2")
    class PaxtonNet2Controller(
        val host: String,
        val address: String,
        val output: Short
    ) : LockController

    @Serializable
    @SerialName("paxton10")
    class Paxton10Controller(
        val baseUrl: String,
        val username: String,
        val password: String,
        val applianceId: Int
    ) : LockController

    @Serializable
    @SerialName("integra")
    class IntegraV1Controller(
        val username: String,
        val password: String,
        val controllerId: Int
    ) : LockController

    @Serializable
    @SerialName("integra-v2")
    class IntegraV2Controller(
        val baseUrl: String,
        val sessionId: String,
        val controllerId: Int,
        val cardholderId: Int,
        val pinCode: Int? = null
    ) : LockController

    @Serializable
    @SerialName("pac512")
    class PacController(
        val dataSource: DataSource,
        val outputChannel: Int,
        val controllerSerial: Int
    ) : LockController

    @Serializable
    class DataSource(
        val driverClass: String,
        val url: String,
        val user: String,
        val password: String
    )

    @Serializable
    @SerialName("tdsi-exgarde")
    class TdsiExgardeController(
        val dbUrl: String? = null,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController

    @Serializable
    @SerialName("tdsi-gardis")
    class TdsiGardisController(
        val host: String,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController

    @Serializable
    @SerialName("zkteco-zkbio-cvsecurity")
    class ZktecoController(
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