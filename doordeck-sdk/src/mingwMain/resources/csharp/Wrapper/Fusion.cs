using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using FusionApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi;

public class Fusion(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi fusion,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionApi_e__Struct fusionApi) : AbstractWrapper
{
    public unsafe Task<FusionLoginResponse> Login(string email, string password) =>
        Process<FusionApi, FusionLoginResponse>(fusion, fusionApi.login, new { email, password });

    public unsafe Task<IntegrationTypeResponse> GetIntegrationType() =>
        Process<FusionApi, IntegrationTypeResponse>(fusion, fusionApi.getIntegrationType_);

    public unsafe Task<List<IntegrationConfigurationResponse>> GetIntegrationConfiguration(string type) =>
        Process<FusionApi, List<IntegrationConfigurationResponse>>(fusion, fusionApi.getIntegrationConfiguration_, new { type });

    public unsafe Task<object> EnableDoor(string name, string siteId, LockController controller) =>
        Process<FusionApi, object>(fusion, fusionApi.enableDoor_, new { name, siteId, controller });

    public unsafe Task<object> DeleteDoor(string deviceId) =>
        Process<FusionApi, object>(fusion, fusionApi.deleteDoor_, new { deviceId });

    public unsafe Task<DoorStateResponse> GetDoorStatus(string deviceId) =>
        Process<FusionApi, DoorStateResponse>(fusion, fusionApi.getDoorStatus_, new { deviceId });

    public unsafe Task<object> StartDoor(string deviceId) =>
        Process<FusionApi, object>(fusion, fusionApi.startDoor_, new { deviceId });

    public unsafe Task<object> StopDoor(string deviceId) =>
        Process<FusionApi, object>(fusion, fusionApi.stopDoor_, new { deviceId });
}