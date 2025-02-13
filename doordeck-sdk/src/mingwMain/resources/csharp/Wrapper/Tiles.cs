﻿using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Tiles : IResource
{
    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource _tiles;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesResource_e__Struct _tilesResource;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _tiles = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles_(sdk);
        _tilesResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_tiles.pinned);
    }

    public TileLocksResponse GetLocksBelongingToTile(GetLocksBelongingToTileData data)
    {
        return Process<TileLocksResponse>(
            _tilesResource.getLocksBelongingToTileJson_,
            null,
            data
        );
    }

    public void AssociateMultipleLocks(AssociateMultipleLocksData data)
    {
        Process<object>(
            _tilesResource.associateMultipleLocksJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_tiles, sData) :
                processWithoutDataWithResponse(_tiles);

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