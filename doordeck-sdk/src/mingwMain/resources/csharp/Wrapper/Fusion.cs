using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Fusion(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi fusion,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionApi_e__Struct fusionApi) : AbstractWrapper
{
    public unsafe Task<FusionLoginResponse> Login(FusionLoginData data) =>
        Process<FusionLoginResponse>(fusionApi.login, null, data);

    public unsafe Task<IntegrationTypeResponse> GetIntegrationType() =>
        Process<IntegrationTypeResponse>(null, fusionApi.getIntegrationType_, null);

    public unsafe Task<IntegrationConfigurationResponse> GetIntegrationConfiguration(GetIntegrationConfigurationData data) =>
        Process<IntegrationConfigurationResponse>(fusionApi.getIntegrationConfiguration_, null, data);

    public unsafe Task<object> EnableDoor(EnableDoorData data) =>
        Process<object>(fusionApi.enableDoor_, null, data);

    public unsafe Task<object> DeleteDoor(DeviceIdData data) =>
        Process<object>(fusionApi.deleteDoor_, null, data);

    public unsafe Task<DoorStateResponse> GetDoorStatus(DeviceIdData data) =>
        Process<DoorStateResponse>(fusionApi.getDoorStatus_, null, data);

    public unsafe Task<object> StartDoor(DeviceIdData data) =>
        Process<object>(fusionApi.startDoor_, null, data);

    public unsafe Task<object> StopDoor(DeviceIdData data) =>
        Process<object>(fusionApi.stopDoor_, null, data);

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