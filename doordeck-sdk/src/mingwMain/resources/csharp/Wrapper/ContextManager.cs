using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

using ContextManagerApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager;

public unsafe class ContextManager(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager context,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._context_e__Struct._ContextManager_e__Struct contextManager,
    Doordeck_Headless_Sdk_ExportedSymbols* symbols) : AbstractWrapper
{
    public ApiEnvironment GetApiEnvironment()
    {
        sbyte* result = null;
        try
        {
            result = contextManager.getApiEnvironment_(context);
            return Enum.Parse<ApiEnvironment>(Utils.SByteToString(result));
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetCloudAuthToken(string token)
    {
        var data = token.StringToSByte();
        try
        {
            contextManager.setCloudAuthToken_(context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetCloudAuthToken()
    {
        sbyte* result = null;
        try
        {
            result = contextManager.getCloudAuthToken_(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public Task<bool> IsCloudAuthTokenInvalidOrExpired() =>
            Process<ContextManagerApi, bool>(context, contextManager.isCloudAuthTokenInvalidOrExpired_);

    public void SetCloudRefreshToken(string token)
    {
        var data = token.StringToSByte();
        try
        {
            contextManager.setCloudRefreshToken_(context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetCloudRefreshToken()
    {
        sbyte* result = null;
        try
        {
            result = contextManager.getCloudRefreshToken_(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetFusionHost(string host)
    {
        var data = host.StringToSByte();
        try
        {
            contextManager.setFusionHost_(context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetFusionHost()
    {
        sbyte* result = null;
        try
        {
            result = contextManager.getFusionHost_(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetFusionAuthToken(string token)
    {
        var data = token.StringToSByte();
        try
        {
            contextManager.setFusionAuthToken_(context, data);
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
            result = contextManager.getFusionAuthToken_(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserId(Guid userId)
    {
        var data = userId.ToString().StringToSByte();
        try
        {
            contextManager.setUserId_(context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public Guid GetUserId()
    {
        sbyte* result = null;
        try
        {
            result = contextManager.getUserId_(context);
            return Guid.Parse(Utils.SByteToString(result));
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserEmail(string email)
    {
        var data = email.StringToSByte();
        try
        {
            contextManager.setUserEmail_(context, data);
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
            result = contextManager.getUserEmail_(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    // SetCertificateChain

    // GetCertificateChain

    public bool IsCertificateChainInvalidOrExpired()
    {
        return contextManager.isCertificateChainInvalidOrExpired_(context).ByteToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    // SetKeyPairVerified

    public bool IsKeyPairVerified()
    {
        return contextManager.isKeyPairVerified_(context).ByteToBoolean();
    }

    public bool IsKeyPairValid()
    {
        return contextManager.isKeyPairValid_(context).ByteToBoolean();
    }

    public void SetOperationContext(string userId, string certificateChain, string publicKey, string privateKey, bool isKeyPairVerified)
    {
        var sData = new { userId, certificateChain, publicKey, privateKey, isKeyPairVerified }.ToJsonSByte();
        try
        {
            contextManager.setOperationContext_(context, sData);
        }
        finally
        {
            ReleaseMemory(sData, null);
        }
    }

    public Task<ContextState> GetContextState() => 
        Process<ContextManagerApi, ContextState>(context, contextManager.getContextState_);

    public void ClearContext()
    {
        contextManager.clearContext_(context);
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) symbols->DisposeString(result);
    }
}