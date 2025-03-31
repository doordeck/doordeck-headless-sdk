using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Fusion(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi fusion,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionApi_e__Struct fusionApi) : AbstractWrapper
{
    public unsafe Task<FusionLoginResponse> Login(string email, string password) =>
        Process<FusionLoginResponse>(fusionApi.login, null, new { email, password });

    public unsafe Task<IntegrationTypeResponse> GetIntegrationType() =>
        Process<IntegrationTypeResponse>(null, fusionApi.getIntegrationType_, null);

    public unsafe Task<IntegrationConfigurationResponse> GetIntegrationConfiguration(string type) =>
        Process<IntegrationConfigurationResponse>(fusionApi.getIntegrationConfiguration_, null, new { type });

    public unsafe Task<object> EnableDoor(string name, string siteId, LockController controller) =>
        Process<object>(fusionApi.enableDoor_, null, new { name, siteId, controller });

    public unsafe Task<object> DeleteDoor(string deviceId) =>
        Process<object>(fusionApi.deleteDoor_, null, new { deviceId });

    public unsafe Task<DoorStateResponse> GetDoorStatus(string deviceId) =>
        Process<DoorStateResponse>(fusionApi.getDoorStatus_, null, new { deviceId });

    public unsafe Task<object> StartDoor(string deviceId) =>
        Process<object>(fusionApi.startDoor_, null, new { deviceId });

    public unsafe Task<object> StopDoor(string deviceId) =>
        Process<object>(fusionApi.stopDoor_, null, new { deviceId });

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            void*, void> processWithoutData,
        object? data) => 
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi, TResponse>(
            fusion,
            data,
            processWithData,
            processWithoutData);
}