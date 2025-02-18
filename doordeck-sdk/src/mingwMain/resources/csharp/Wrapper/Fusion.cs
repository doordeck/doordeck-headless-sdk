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

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _fusion = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion_(sdk);
        _fusionApi = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_fusion.pinned);
    }

    public FusionLoginResponse Login(FusionLoginData data)
    {
        return Process<FusionLoginResponse>(
            _fusionApi.loginJson,
            null,
            data
        );
    }

    public IntegrationTypeResponse GetIntegrationType()
    {
        return Process<IntegrationTypeResponse>(
            null,
            _fusionApi.getIntegrationTypeJson_,
            null
        );
    }

    public List<IntegrationConfigurationResponse> GetIntegrationConfiguration(GetIntegrationConfigurationData data)
    {
        return Process<List<IntegrationConfigurationResponse>>(
            _fusionApi.getIntegrationConfigurationJson_,
            null,
            data
        );
    }

    public void EnableDoor(EnableDoorData data)
    {
        Process<object>(
            _fusionApi.enableDoorJson_,
            null,
            data
        );
    }

    public void DeleteDoor(DeleteDoorData data)
    {
        Process<object>(
            _fusionApi.deleteDoorJson_,
            null,
            data
        );
    }

    public DoorStateResponse GetDoorStatus(GetDoorStatusData data)
    {
        return Process<DoorStateResponse>(
            _fusionApi.getDoorStatusJson_,
            null,
            data
        );
    }

    public void StartDoor(StartDoorData data)
    {
        Process<object>(
            _fusionApi.startDoorJson_,
            null,
            data
        );
    }

    public void StopDoor(StopDoorData data)
    {
        Process<object>(
            _fusionApi.stopDoorJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_fusion, sData) :
                processWithoutDataWithResponse(_fusion);

            var resultData = result != null
                ? Utils.Utils.FromData<ResultData<TResponse>>(result)
                : default!;

            resultData.HandleException();

            return resultData.Success!.Result ?? default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}