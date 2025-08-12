package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.ZktecoEntityType
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.AmagController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.AssaAbloyController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.AvigilonController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.AxisController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.CCureController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.DemoController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.GallagherController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.GenetecController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.IntegraV1Controller
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.IntegraV2Controller
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.LenelController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.MitrefinchController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.PacController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.Paxton10Controller
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.PaxtonNet2Controller
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.TdsiGardisController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations.ZktecoController

object FusionOperations {

    sealed interface LockController
    
    data class AlpetaController(
        val username: String,
        val password: String,
        val doorId: Long,
        val baseUrl: String? = null
    ) : LockController
    
    data class AmagController(
        val username: String,
        val password: String,
        val doorId: Int,
        val baseUrl: String? = null
    ) : LockController
    
    data class AssaAbloyController(
        val baseUrl: String,
        val doorId: String
    ) : LockController

    data class AvigilonController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    data class AxisController(
        val baseUrl: String,
        val doorIdentifier: String
    ) : LockController
    
    data class CCureController(
        val baseUrl: String? = null,
        val username: String,
        val password: String,
        val doorType: String,
        val doorId: Int
    ) : LockController

    data class DemoController(
        val port: UShort = 8080u
    ) : LockController
    
    data class GallagherController(
        val baseUrl: String? = null,
        val apiKey: String,
        val doorId: String
    ) : LockController
    
    data class GenetecController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val doorId: String
    ) : LockController

    data class LenelController(
        val baseUrl: String,
        val username: String,
        val password: String,
        val directoryId: String,
        val panelId: String,
        val readerId: String
    ) : LockController

    data class MitrefinchController(
        val host: String,
        val output: Int
    ) : LockController

    data class PaxtonNet2Controller(
        val host: String,
        val username: String? = null,
        val password: String? = null,
        val address: String,
        val output: Short
    ) : LockController

    data class Paxton10Controller(
        val baseUrl: String,
        val username: String,
        val password: String,
        val applianceId: Int
    ) : LockController

    data class IntegraV1Controller(
        val username: String,
        val password: String,
        val controllerId: Int
    ) : LockController
    
    data class IntegraV2Controller(
        val baseUrl: String,
        val sessionId: String,
        val controllerId: Int,
        val cardholderId: Int,
        val pinCode: Int? = null
    ) : LockController

    data class PacController(
        val dataSource: DataSource,
        val outputChannel: Int,
        val controllerSerial: Int
    ) : LockController
    
    data class DataSource(
        val driverClass: String,
        val url: String,
        val user: String,
        val password: String
    )
    
    data class TdsiExgardeController(
        val dbUrl: String? = null,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController
    
    data class TdsiGardisController(
        val host: String,
        val username: String,
        val password: String,
        val doorId: Int
    ) : LockController
    
    data class ZktecoController(
        val clientSecret: String,
        val doorId: String,
        val baseUrl: String? = null,
        val entityType: ZktecoEntityType
    ) : LockController
}

internal fun FusionOperations.LockController.toBasicLockController(): BasicLockController = when(this) {
    is FusionOperations.AlpetaController -> toBasicAlpetaController()
    is AmagController -> toBasicAmagController()
    is AssaAbloyController -> toBasicAssaAbloyController()
    is AvigilonController -> toBasicAvigilonController()
    is AxisController -> toBasicAxisController()
    is CCureController -> toBasicCCureController()
    is DemoController -> toBasicDemoController()
    is GallagherController -> toBasicGallagherController()
    is GenetecController -> toBasicGenetecController()
    is LenelController -> toBasicLenelController()
    is MitrefinchController -> toBasicMitrefinchController()
    is PaxtonNet2Controller -> toBasicPaxtonNet2Controller()
    is Paxton10Controller -> toBasicPaxton10Controller()
    is IntegraV1Controller -> toBasicIntegraV1Controller()
    is IntegraV2Controller -> toBasicIntegraV2Controller()
    is PacController -> toBasicPacController()
    is FusionOperations.TdsiExgardeController -> toBasicTdsiExgardeController()
    is TdsiGardisController -> toBasicTdsiGardisController()
    is ZktecoController -> toBasicZktecoController()
}

internal fun FusionOperations.AlpetaController.toBasicAlpetaController(): BasicAlpetaController = BasicAlpetaController(
    username = username,
    password = password,
    doorId = doorId,
    baseUrl = baseUrl
)

internal fun AmagController.toBasicAmagController(): BasicAmagController = BasicAmagController(
    username = username,
    password = password,
    doorId = doorId,
    baseUrl = baseUrl
)

internal fun AssaAbloyController.toBasicAssaAbloyController(): BasicAssaAbloyController = BasicAssaAbloyController(
    baseUrl = baseUrl,
    doorId = doorId
)

internal fun AvigilonController.toBasicAvigilonController(): BasicAvigilonController = BasicAvigilonController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorId = doorId
)

internal fun AxisController.toBasicAxisController(): BasicAxisController = BasicAxisController(
    baseUrl = baseUrl,
    doorIdentifier = doorIdentifier
)

internal fun CCureController.toBasicCCureController(): BasicCCureController = BasicCCureController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorType = doorType,
    doorId = doorId
)

internal fun DemoController.toBasicDemoController(): BasicDemoController = BasicDemoController(
    port = port
)

internal fun GallagherController.toBasicGallagherController(): BasicGallagherController = BasicGallagherController(
    baseUrl = baseUrl,
    apiKey = apiKey,
    doorId = doorId
)

internal fun GenetecController.toBasicGenetecController(): BasicGenetecController = BasicGenetecController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorId = doorId
)

internal fun LenelController.toBasicLenelController(): BasicLenelController = BasicLenelController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    directoryId = directoryId,
    panelId = panelId,
    readerId = readerId
)

internal fun MitrefinchController.toBasicMitrefinchController(): BasicMitrefinchController = BasicMitrefinchController(
    host = host,
    output = output
)

internal fun PaxtonNet2Controller.toBasicPaxtonNet2Controller(): BasicPaxtonNet2Controller = BasicPaxtonNet2Controller(
    host = host,
    username = username,
    password = password,
    address = address,
    output = output
)

internal fun Paxton10Controller.toBasicPaxton10Controller(): BasicPaxton10Controller = BasicPaxton10Controller(
    baseUrl = baseUrl,
    username = username,
    password = password,
    applianceId = applianceId
)

internal fun IntegraV1Controller.toBasicIntegraV1Controller(): BasicIntegraV1Controller = BasicIntegraV1Controller(
    username = username,
    password = password,
    controllerId = controllerId
)

internal fun IntegraV2Controller.toBasicIntegraV2Controller(): BasicIntegraV2Controller = BasicIntegraV2Controller(
    baseUrl = baseUrl,
    sessionId = sessionId,
    controllerId = controllerId,
    cardholderId = cardholderId,
    pinCode = pinCode
)

internal fun PacController.toBasicPacController(): BasicPacController = BasicPacController(
    dataSource = BasicDataSource(
        driverClass = dataSource.driverClass,
        url = dataSource.url,
        user = dataSource.user,
        password = dataSource.password
    ),
    outputChannel = outputChannel,
    controllerSerial = controllerSerial
)

internal fun FusionOperations.TdsiExgardeController.toBasicTdsiExgardeController(): BasicTdsiExgardeController = BasicTdsiExgardeController(
    dbUrl = dbUrl,
    username = username,
    password = password,
    doorId = doorId
)

internal fun TdsiGardisController.toBasicTdsiGardisController(): BasicTdsiGardisController = BasicTdsiGardisController(
    host = host,
    username = username,
    password = password,
    doorId = doorId
)

internal fun ZktecoController.toBasicZktecoController(): BasicZktecoController = BasicZktecoController(
    clientSecret = clientSecret,
    doorId = doorId,
    baseUrl = baseUrl,
    entityType = entityType
)