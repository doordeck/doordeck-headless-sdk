%pythoncode %{
class Fusion(object):

    def __init__(self, resource):
        self.resource = resource

    async def login(self, data):
        return await execute_async(
            _doordeck_headless_sdk.loginFusion,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: FusionLoginResponse(**get_success_result(r))
        )

    async def get_integration_type(self):
        return await execute_async(
            _doordeck_headless_sdk.getIntegrationType,
            [self.resource],
            lambda r: IntegrationTypeResponse(**get_success_result(r))
        )

    async def get_integration_configuration(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getIntegrationConfiguration,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [IntegrationConfigurationResponse(**item) for item in get_success_result(r)]
        )

    async def enable_door(self, data):
        return await execute_async(
            _doordeck_headless_sdk.enableDoor,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def delete_door(self, data):
        return await execute_async(
            _doordeck_headless_sdk.deleteDoor,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_door_status(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getDoorStatus,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: DoorStateResponse(**get_success_result(r))
        )

    async def start_door(self, data):
        return await execute_async(
            _doordeck_headless_sdk.startDoor,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def stop_door(self, data):
        return await execute_async(
            _doordeck_headless_sdk.stopDoor,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )
%}