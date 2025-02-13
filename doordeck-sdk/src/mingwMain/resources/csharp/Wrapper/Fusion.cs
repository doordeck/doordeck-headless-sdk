using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Fusion : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource _fusion;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._FusionResource_e__Struct _fusionResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _fusion = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion_(sdk);
        _fusionResource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_fusion.pinned);
    }

    public FusionLoginResponse Login(FusionLoginData data)
    {
        return Process<FusionLoginResponse>(
            _fusionResource.loginJson,
            null,
            data
        );
    }

    public IntegrationTypeResponse GetIntegrationType()
    {
        return Process<IntegrationTypeResponse>(
            null,
            _fusionResource.getIntegrationTypeJson_,
            null
        );
    }

    public List<IntegrationConfigurationResponse> GetIntegrationConfiguration(GetIntegrationConfigurationData data)
    {
        return Process<List<IntegrationConfigurationResponse>>(
            _fusionResource.getIntegrationConfigurationJson_,
            null,
            data
        );
    }

    public void EnableDoor(EnableDoorData data)
    {
        Process<object>(
            _fusionResource.enableDoorJson_,
            null,
            data
        );
    }

    public void DeleteDoor(DeleteDoorData data)
    {
        Process<object>(
            _fusionResource.deleteDoorJson_,
            null,
            data
        );
    }

    public DoorStateResponse GetDoorStatus(GetDoorStatusData data)
    {
        return Process<DoorStateResponse>(
            _fusionResource.getDoorStatusJson_,
            null,
            data
        );
    }

    public void StartDoor(StartDoorData data)
    {
        Process<object>(
            _fusionResource.startDoorJson_,
            null,
            data
        );
    }

    public void StopDoor(StopDoorData data)
    {
        Process<object>(
            _fusionResource.stopDoorJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource,
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