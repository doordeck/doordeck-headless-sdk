using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class ContextManager : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager _context;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._context_e__Struct._ContextManager_e__Struct _contextManager;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _context = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager_(sdk);
        _contextManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.context.ContextManager;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_context.pinned);
    }

    public unsafe void SetApiEnvironment(ApiEnvironment apiEnvironment)
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

    public unsafe ApiEnvironment GetApiEnvironment()
    {
        var apiEnvironment = _contextManager.getApiEnvironment_(_context);
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

    public unsafe void SetCloudAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setCloudAuthToken_(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public unsafe string GetCloudAuthToken()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getCloudAuthToken_(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public unsafe bool IsCloudAuthTokenAboutToExpire()
    {
        return _contextManager.isCloudAuthTokenAboutToExpire_(_context).ToBoolean();
    }

    public unsafe void SetCloudRefreshToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _contextManager.setCloudRefreshToken_(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public unsafe string GetCloudRefreshToken()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getCloudRefreshToken_(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public unsafe void SetFusionHost(string host)
    {
        var data = host.ToSByte();
        try
        {
            _contextManager.setFusionHost_(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public unsafe string GetFusionHost()
    {
        sbyte* result = null;
        try
        {
            result = _contextManager.getFusionHost_(_context);
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public unsafe void SetFusionAuthToken(string token)
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

    public unsafe string GetFusionAuthToken()
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

    public unsafe void SetUserId(string userId)
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

    public unsafe string GetUserId()
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

    public unsafe void SetUserEmail(string email)
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

    public unsafe string GetUserEmail()
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

    public unsafe bool IsCertificateChainAboutToExpire()
    {
        return _contextManager.isCertificateChainAboutToExpire_(_context).ToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    public unsafe bool IsKeyPairValid()
    {
        return _contextManager.isKeyPairValid_(_context).ToBoolean();
    }

    public unsafe void SetOperationContext(OperationContextData data)
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

    private unsafe void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) _symbols->DisposeString(result);
    }
}