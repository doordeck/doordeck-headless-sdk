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
        _fusion = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
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
            null,
            data
        );
    }
    
    public IntegrationTypeResponse GetIntegrationType()
    {
        return Process<IntegrationTypeResponse>(
            null,
            null,
            _fusionResource.getIntegrationTypeJson,
            null
        );
    }
    
    public List<IntegrationConfigurationResponse> GetIntegrationConfiguration(GetIntegrationConfigurationData data)
    {
        return Process<List<IntegrationConfigurationResponse>>(
            _fusionResource.getIntegrationConfigurationJson,
            null,
            null,
            data
        );
    }
    
    public void EnableDoor(EnableDoorData data)
    {
        Process<object>(
            null,
            _fusionResource.enableDoorJson,
            null,
            data
        );
    }
    
    public void DeleteDoor(DeleteDoorData data)
    {
        Process<object>(
            null,
            _fusionResource.deleteDoorJson,
            null,
            data
        );
    }
    
    public DoorStateResponse GetDoorStatus(GetDoorStatusData data)
    {
        return Process<DoorStateResponse>(
            _fusionResource.getDoorStatusJson,
            null,
            null,
            data
        );
    }
    
    public void StartDoor(StartDoorData data)
    {
        Process<object>(
            null,
            _fusionResource.startDoor,
            null,
            data
        );
    }
    
    public void StopDoor(StopDoorData data)
    {
        Process<object>(
            null,
            _fusionResource.stopDoor,
            null,
            data
        );
    }
    
    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource,
            sbyte*> withoutDataAndWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var withResponse = typeof(TResponse) != typeof(object);
            var withData = data != null;

            if (withData && withResponse)
                result = withDataAndWithResponse(_fusion, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_fusion, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_fusion);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}