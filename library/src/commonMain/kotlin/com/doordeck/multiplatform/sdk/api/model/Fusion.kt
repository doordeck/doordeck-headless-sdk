package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
object Fusion {

    /**
     * The following classes are serializable because we are using them in the actual requests.
     */
    @Serializable
    sealed interface LockController {
        val type: String
    }

    @Serializable
    class AlpetaController(
        val username: String,
        val password: String,
        val doorId: Int, // Long, this can give problems
        val baseUrl: String? = null,
        override val type: String = "alpeta"
    ) : LockController

    @Serializable
    class AmagController(
        val username: String,
        val password: String,
        val doorId: Int,
        val baseUrl: String? = null,
        override val type: String = "amag"
    ) : LockController

    @Serializable
    class AssaAbloyController(
        val baseUrl: String,
        val doorId: String,
        override val type: String = "assa-abloy"
    ) : LockController

    @Serializable
    class AvigilonController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String,
        override val type: String = "avigilon"
    ) : LockController

    @Serializable
    class AxisController(
        val baseUrl: String,
        val doorIdentifier: String,
        override val type: String = "axis"
    ) : LockController

    @Serializable
    class DemoController(
        val port: Int = 8080,
        override val type: String = "demo"
    ) : LockController

    @Serializable
    class GallagherController(
        val baseUrl: String? = null,
        val apiKey: String,
        val doorId: String,
        override val type: String = "gallagher"
    ) : LockController

    @Serializable
    class GenetecController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String,
        override val type: String = "gallagher"
    ) : LockController

    @Serializable
    class LenelController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val directoryId: String,
        val panelId: String,
        val readerId: String,
        override val type: String = "lenel"
    ) : LockController

    @Serializable
    class MitrefinchController(
        val host: String,
        val output: Int,
        override val type: String = "mitrefinch"
    ) : LockController

    @Serializable
    class PaxtonNet2Controller(
        val host: String,
        val address: String,
        val output: Short,
        override val type: String = "net2"
    ) : LockController

    @Serializable
    class Paxton10Controller(
        val baseUrl: String,
        val username: String,
        val password: String,
        val applianceId: Int,
        override val type: String = "paxton10"
    ) : LockController

    @Serializable
    class IntegraController(
        val username: String,
        val password: String,
        val controllerId: Int,
        override val type: String = "integra"
    ) : LockController

    @Serializable
    class IntegraV2Controller(
        val baseUrl: String,
        val sessionId: String,
        val controllerId: Int,
        val cardholderId: Int,
        val pinCode: Int? = null,
        override val type: String = "integra-v2"
    ) : LockController

    @Serializable
    class PacController(
        val dataSource: DataSource,
        val outputChannel: Int,
        val controllerSerial: Int,
        override val type: String = "pac512"
    ) : LockController

    @Serializable
    class DataSource(
        val driverClass: String,
        val url: String,
        val user: String,
        val password: String
    )

    @Serializable
    class TdsiExgardeController(
        val dbUrl: String? = null,
        val username: String,
        val password: String,
        val doorId: Int,
        override val type: String = "tdsi-exgarde"
    ) : LockController

    @Serializable
    class TdsiGardisController(
        val host: String,
        val username: String,
        val password: String,
        val doorID: Int,
        override val type: String = "tdsi-gardis"
    ) : LockController

    @Serializable
    class ZktecoController(
        val clientSecret: String,
        val doorId: String,
        val baseUrl: String? = null,
        val entityType: ZktecoEntityType,
        override val type: String = "zkteco-zkbio-cvsecurity"
    ) : LockController

    @Serializable
    enum class ZktecoEntityType {
        DOOR,
        FLOOR
    }
}