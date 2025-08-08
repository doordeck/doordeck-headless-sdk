package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.ServiceStateType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.BasicAlpetaController
import com.doordeck.multiplatform.sdk.model.data.BasicAmagController
import com.doordeck.multiplatform.sdk.model.data.BasicAssaAbloyController
import com.doordeck.multiplatform.sdk.model.data.BasicAvigilonController
import com.doordeck.multiplatform.sdk.model.data.BasicAxisController
import com.doordeck.multiplatform.sdk.model.data.BasicCCureController
import com.doordeck.multiplatform.sdk.model.data.BasicDemoController
import com.doordeck.multiplatform.sdk.model.data.BasicGallagherController
import com.doordeck.multiplatform.sdk.model.data.BasicGenetecController
import com.doordeck.multiplatform.sdk.model.data.BasicIntegraV1Controller
import com.doordeck.multiplatform.sdk.model.data.BasicIntegraV2Controller
import com.doordeck.multiplatform.sdk.model.data.BasicLenelController
import com.doordeck.multiplatform.sdk.model.data.BasicLockController
import com.doordeck.multiplatform.sdk.model.data.BasicMitrefinchController
import com.doordeck.multiplatform.sdk.model.data.BasicPacController
import com.doordeck.multiplatform.sdk.model.data.BasicPaxton10Controller
import com.doordeck.multiplatform.sdk.model.data.BasicPaxtonNet2Controller
import com.doordeck.multiplatform.sdk.model.data.BasicTdsiExgardeController
import com.doordeck.multiplatform.sdk.model.data.BasicTdsiGardisController
import com.doordeck.multiplatform.sdk.model.data.BasicZktecoController
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
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

typealias LockControllerResponse = FusionOperations.LockController

data class FusionLoginResponse(
    val authToken: String
)

data class IntegrationTypeResponse(
    val status: String? = null
)

data class DoorStateResponse(
    val state: ServiceStateType
)

data class IntegrationConfigurationResponse(
    val doordeck: ControllerResponse? = null,
    val service: ServiceStateResponse? = null,
    val integration: DiscoveredDeviceResponse? = null
)

data class ControllerResponse(
    val id: String,
    val name: String? = null,
    val role: UserRole? = null
)

data class ServiceStateResponse(
    val state: ServiceStateType
)

data class DiscoveredDeviceResponse(
    val key: LockControllerResponse,
    val metadata: Map<String, String>
)

internal fun BasicFusionLoginResponse.toFusionLoginResponse(): FusionLoginResponse = FusionLoginResponse(
    authToken = authToken,
)

internal fun BasicIntegrationTypeResponse.toIntegrationTypeResponse(): IntegrationTypeResponse = IntegrationTypeResponse(
    status = status,
)

internal fun BasicDoorStateResponse.toDoorStateResponse(): DoorStateResponse = DoorStateResponse(
    state = state
)

internal fun List<BasicIntegrationConfigurationResponse>.toIntegrationConfigurationResponse(): List<IntegrationConfigurationResponse> = map { configuration ->
    IntegrationConfigurationResponse(
        doordeck = configuration.doordeck?.toControllerResponse(),
        service = configuration.service?.toServiceStateResponse(),
        integration = configuration.integration?.toDiscoveredDeviceResponse()
    )
}

internal fun BasicControllerResponse.toControllerResponse(): ControllerResponse = ControllerResponse(
    id = id,
    name = name,
    role = role
)

internal fun BasicServiceStateResponse.toServiceStateResponse(): ServiceStateResponse = ServiceStateResponse(
    state = state
)

internal fun BasicDiscoveredDeviceResponse.toDiscoveredDeviceResponse(): DiscoveredDeviceResponse = DiscoveredDeviceResponse(
    key = key.toLockControllerResponse(),
    metadata = metadata
)

internal fun BasicLockController.toLockControllerResponse(): LockControllerResponse = when(this) {
    is BasicAlpetaController -> toAlpetaController()
    is BasicAmagController -> toAmagController()
    is BasicAssaAbloyController -> toAssaAbloyController()
    is BasicAvigilonController -> toAvigilonController()
    is BasicAxisController -> toAxisController()
    is BasicCCureController -> toCCureController()
    is BasicDemoController -> toDemoController()
    is BasicGallagherController -> toGallagherController()
    is BasicGenetecController -> toGenetecController()
    is BasicLenelController -> toLenelController()
    is BasicMitrefinchController -> toMitrefinchController()
    is BasicPaxtonNet2Controller -> toPaxtonNet2Controller()
    is BasicPaxton10Controller -> toPaxton10Controller()
    is BasicIntegraV1Controller -> toIntegraV1Controller()
    is BasicIntegraV2Controller -> toIntegraV2Controller()
    is BasicPacController -> toPacController()
    is BasicTdsiExgardeController -> toTdsiExgardeController()
    is BasicTdsiGardisController -> toTdsiGardisController()
    is BasicZktecoController -> toZktecoController()
}

internal fun BasicAlpetaController.toAlpetaController(): FusionOperations.AlpetaController = FusionOperations.AlpetaController(
    username = username,
    password = password,
    doorId = doorId,
    baseUrl = baseUrl
)

internal fun BasicAmagController.toAmagController(): AmagController = AmagController(
    username = username,
    password = password,
    doorId = doorId,
    baseUrl = baseUrl
)

internal fun BasicAssaAbloyController.toAssaAbloyController(): AssaAbloyController = AssaAbloyController(
    baseUrl = baseUrl,
    doorId = doorId
)

internal fun BasicAvigilonController.toAvigilonController(): AvigilonController = AvigilonController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorId = doorId
)

internal fun BasicAxisController.toAxisController(): AxisController = AxisController(
    baseUrl = baseUrl,
    doorIdentifier = doorIdentifier
)

internal fun BasicCCureController.toCCureController(): CCureController = CCureController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorType = doorType,
    doorId = doorId
)

internal fun BasicDemoController.toDemoController(): DemoController = DemoController(
    port = port
)

internal fun BasicGallagherController.toGallagherController(): GallagherController = GallagherController(
    baseUrl = baseUrl,
    apiKey = apiKey,
    doorId = doorId
)

internal fun BasicGenetecController.toGenetecController(): GenetecController = GenetecController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    doorId = doorId
)

internal fun BasicLenelController.toLenelController(): LenelController = LenelController(
    baseUrl = baseUrl,
    username = username,
    password = password,
    directoryId = directoryId,
    panelId = panelId,
    readerId = readerId
)

internal fun BasicMitrefinchController.toMitrefinchController(): MitrefinchController = MitrefinchController(
    host = host,
    output = output
)

internal fun BasicPaxtonNet2Controller.toPaxtonNet2Controller(): PaxtonNet2Controller = PaxtonNet2Controller(
    host = host,
    username = username,
    password = password,
    address = address,
    output = output
)

internal fun BasicPaxton10Controller.toPaxton10Controller(): Paxton10Controller = Paxton10Controller(
    baseUrl = baseUrl,
    username = username,
    password = password,
    applianceId = applianceId
)

internal fun BasicIntegraV1Controller.toIntegraV1Controller(): IntegraV1Controller = IntegraV1Controller(
    username = username,
    password = password,
    controllerId = controllerId
)

internal fun BasicIntegraV2Controller.toIntegraV2Controller(): IntegraV2Controller = IntegraV2Controller(
    baseUrl = baseUrl,
    sessionId = sessionId,
    controllerId = controllerId,
    cardholderId = cardholderId,
    pinCode = pinCode
)

internal fun BasicPacController.toPacController(): PacController = PacController(
    dataSource = FusionOperations.DataSource(
        driverClass = dataSource.driverClass,
        url = dataSource.url,
        user = dataSource.user,
        password = dataSource.password
    ),
    outputChannel = outputChannel,
    controllerSerial = controllerSerial
)

internal fun BasicTdsiExgardeController.toTdsiExgardeController(): FusionOperations.TdsiExgardeController =
    FusionOperations.TdsiExgardeController(
        dbUrl = dbUrl,
        username = username,
        password = password,
        doorId = doorId
    )

internal fun BasicTdsiGardisController.toTdsiGardisController(): TdsiGardisController = TdsiGardisController(
    host = host,
    username = username,
    password = password,
    doorId = doorId
)

internal fun BasicZktecoController.toZktecoController(): ZktecoController = ZktecoController(
    clientSecret = clientSecret,
    doorId = doorId,
    baseUrl = baseUrl,
    entityType = entityType
)