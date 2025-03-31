%pythoncode %{
class Fusion(object):

    def __init__(self, resource):
        self.resource = resource

    async def login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await execute_async(
            _doordeck_headless_sdk.loginFusion,
            [self.resource, json.dumps(data)]
        )

    async def get_integration_type(self):
        return await execute_async(
            _doordeck_headless_sdk.getIntegrationType,
            [self.resource]
        )

    async def get_integration_configuration(self, type: str):
        data = { "type": type }
        return await execute_async(
            _doordeck_headless_sdk.getIntegrationConfiguration,
            [self.resource, json.dumps(data)]
        )

    async def enable_door(self, name: str, siteId: str, controller: LockController):
        data = {
            "name": name,
            "siteId": siteId,
            "controller": dataclasses.asdict(controller)
        }
        return await execute_async(
            _doordeck_headless_sdk.enableDoor,
            [self.resource, json.dumps(data)]
        )

    async def delete_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await execute_async(
            _doordeck_headless_sdk.deleteDoor,
            [self.resource, json.dumps(data)]
        )

    async def get_door_status(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await execute_async(
            _doordeck_headless_sdk.getDoorStatus,
            [self.resource, json.dumps(data)]
        )

    async def start_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await execute_async(
            _doordeck_headless_sdk.startDoor,
            [self.resource, json.dumps(data)]
        )

    async def stop_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await execute_async(
            _doordeck_headless_sdk.stopDoor,
            [self.resource, json.dumps(data)]
        )
%}