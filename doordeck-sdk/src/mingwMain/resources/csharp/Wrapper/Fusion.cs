using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Fusion : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi _fusion;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionApi_e__Struct _fusionApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _fusion = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion_(sdk);
        _fusionApi = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_fusion.pinned);
    }

    public void Login(FusionLoginData data, Action<FusionLoginResponse> action)
    {
        Process(_fusionApi.login, null, action, data);
    }

    public void GetIntegrationType(Action<IntegrationTypeResponse> action)
    {
        Process(null, _fusionApi.getIntegrationType_, action, null);
    }

    public void GetIntegrationConfiguration(GetIntegrationConfigurationData data,
        Action<List<IntegrationConfigurationResponse>> action)
    {
        Process(_fusionApi.getIntegrationConfiguration_, null, action, data);
    }

    public void EnableDoor(EnableDoorData data, Action<object> action)
    {
        Process(_fusionApi.enableDoor_, null, action, data);
    }

    public void DeleteDoor(DeviceIdData data, Action<object> action)
    {
        Process(_fusionApi.deleteDoor_, null, action, data);
    }

    public void GetDoorStatus(DeviceIdData data, Action<DoorStateResponse> action)
    {
        Process(_fusionApi.getDoorStatus_, null, action, data);
    }

    public void StartDoor(DeviceIdData data, Action<object> action)
    {
        Process(_fusionApi.startDoor_, null, action, data);
    }

    public void StopDoor(DeviceIdData data, Action<object> action)
    {
        Process(_fusionApi.stopDoor_, null, action, data);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            void*, void> processWithoutData,
        Action<TResponse> userCallback,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(userCallback);
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
    }
}