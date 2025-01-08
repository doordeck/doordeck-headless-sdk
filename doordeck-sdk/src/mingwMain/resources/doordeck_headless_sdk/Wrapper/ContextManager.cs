using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class ContextManager : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_ContextManager _context;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._ContextManager_e__Struct _contextManager;
    
    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _context = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
        _contextManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_context.pinned);
    }

    public void SetAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setAuthToken(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetAuthToken()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getAuthToken(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public bool IsAuthTokenAboutToExpire()
    {
        return _contextManager.isAuthTokenAboutToExpire(_context).ToBoolean();
    }

    public void SetRefreshToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setRefreshToken(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetRefreshToken()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getRefreshToken(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetFusionAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setFusionAuthToken(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetFusionAuthToken()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getFusionAuthToken(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserId(string userId)
    {
        var data = userId.ToSByte();
        try
        {
            _contextManager.setUserId(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetUserId()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getUserId(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserEmail(string email)
    {
        var data = email.ToSByte();
        try
        {
            _contextManager.setUserEmail(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetUserEmail()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getUserEmail(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    // SetCertificateChain

    // GetCertificateChain

    public bool IsCertificateChainAboutToExpire()
    {
        return _contextManager.isCertificateChainAboutToExpire(_context).ToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    public bool IsKeyPairValid()
    {
        return _contextManager.isKeyPairValid(_context).ToBoolean();
    }

    public void SetOperationContextJson(OperationContextData operationContextData)
    {
        var data = operationContextData.ToData();
        try
        {
            _contextManager.setOperationContextJson(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public void LoadContext()
    {
        _contextManager.loadContext(_context);
    }

    public void StoreContext()
    {
        _contextManager.storeContext(_context);
    }

    public void ClearContext()
    {
        _contextManager.clearContext(_context);
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) _symbols->DisposeString(result);
    }
}