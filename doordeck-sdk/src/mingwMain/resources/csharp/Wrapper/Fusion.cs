using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Fusion : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi _fusion;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionApi_e__Struct _fusionApi;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _fusion = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion_(sdk);
        _fusionApi = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_fusion.pinned);
    }

    public unsafe Task<FusionLoginResponse> Login(FusionLoginData data)
    {
        return Process<FusionLoginResponse>(_fusionApi.login, null, data);
    }

    public unsafe Task<IntegrationTypeResponse> GetIntegrationType()
    {
        return Process<IntegrationTypeResponse>(null, _fusionApi.getIntegrationType_, null);
    }

    public unsafe Task<IntegrationConfigurationResponse> GetIntegrationConfiguration(GetIntegrationConfigurationData data)
    {
        return Process<IntegrationConfigurationResponse>(_fusionApi.getIntegrationConfiguration_, null, data);
    }

    public unsafe Task<object> EnableDoor(EnableDoorData data)
    {
        return Process<object>(_fusionApi.enableDoor_, null, data);
    }

    public unsafe Task<object> DeleteDoor(DeviceIdData data)
    {
        return Process<object>(_fusionApi.deleteDoor_, null, data);
    }

    public unsafe Task<DoorStateResponse> GetDoorStatus(DeviceIdData data)
    {
        return Process<DoorStateResponse>(_fusionApi.getDoorStatus_, null, data);
    }

    public unsafe Task<object> StartDoor(DeviceIdData data)
    {
        return Process<object>(_fusionApi.startDoor_, null, data);
    }

    public unsafe Task<object> StopDoor(DeviceIdData data)
    {
        return Process<object>(_fusionApi.stopDoor_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_fusion, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_fusion, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}