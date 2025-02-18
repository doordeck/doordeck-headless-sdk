using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class ContextManager : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager _context;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._context_e__Struct._ContextManager_e__Struct _contextManager;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _context = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager_(sdk);
        _contextManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.context.ContextManager;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_context.pinned);
    }

    public void SetApiEnvironment(ApiEnvironment apiEnvironment)
    {
        var newApiEnvironment = apiEnvironment switch
        {
            ApiEnvironment.DEV => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.DEV
                .get(),
            ApiEnvironment.STAGING => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
                .STAGING.get(),
            ApiEnvironment.PROD => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.PROD
                .get(),
            _ => throw new ArgumentOutOfRangeException(nameof(apiEnvironment), apiEnvironment, null)
        };
        _contextManager.setApiEnvironment(_context, newApiEnvironment);
    }

    public ApiEnvironment GetApiEnvironment()
    {
        var apiEnvironment = _contextManager.getApiEnvironment(_context);
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.getApiEnvironmentName_(apiEnvironment);
            return (ApiEnvironment)Enum.Parse(typeof(ApiEnvironment), Utils.Utils.ConvertSByteToString(result));
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setAuthToken_(_context, data);
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
            result = _contextManager.getAuthToken_(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public bool IsAuthTokenAboutToExpire()
    {
        return _contextManager.isAuthTokenAboutToExpire_(_context).ToBoolean();
    }

    public void SetRefreshToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setRefreshToken_(_context, data);
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
            result = _contextManager.getRefreshToken_(_context);
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
            _contextManager.setFusionAuthToken_(_context, data);
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
            result = _contextManager.getFusionAuthToken_(_context);
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
            _contextManager.setUserId_(_context, data);
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
            result = _contextManager.getUserId_(_context);
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
            _contextManager.setUserEmail_(_context, data);
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
            result = _contextManager.getUserEmail_(_context);
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
        return _contextManager.isCertificateChainAboutToExpire_(_context).ToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    public bool IsKeyPairValid()
    {
        return _contextManager.isKeyPairValid_(_context).ToBoolean();
    }

    public void SetOperationContext(OperationContextData data)
    {
        var sData = data.ToData();
        try
        {
            _contextManager.setOperationContextJson_(_context, sData);
        }
        finally
        {
            ReleaseMemory(sData, null);
        }
    }

    public void LoadContext()
    {
        _contextManager.loadContext_(_context);
    }

    public void StoreContext()
    {
        _contextManager.storeContext_(_context);
    }

    public void ClearContext()
    {
        _contextManager.clearContext_(_context);
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) _symbols->DisposeString(result);
    }
}