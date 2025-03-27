using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class ContextManager(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager context,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._context_e__Struct._ContextManager_e__Struct contextManager,
    Doordeck_Headless_Sdk_ExportedSymbols* symbols)
{
    public void SetApiEnvironment(ApiEnvironment apiEnvironment)
    {
        var newApiEnvironment = apiEnvironment switch
        {
            ApiEnvironment.DEV => symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.DEV
                .get(),
            ApiEnvironment.STAGING => symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
                .STAGING.get(),
            ApiEnvironment.PROD => symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.PROD
                .get(),
            _ => throw new ArgumentOutOfRangeException(nameof(apiEnvironment), apiEnvironment, null)
        };
        contextManager.setApiEnvironment(context, newApiEnvironment);
    }

    public ApiEnvironment GetApiEnvironment()
    {
        var apiEnvironment = contextManager.getApiEnvironment_(context);
        sbyte* result = null;
        try
        {
            result = symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.getApiEnvironmentName_(apiEnvironment);
            return (ApiEnvironment)Enum.Parse(typeof(ApiEnvironment), Utils.Utils.ConvertSByteToString(result));
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetCloudAuthToken(string token)
    {
        var data = token.ToSByte();
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
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public bool IsCloudAuthTokenAboutToExpire()
    {
        return contextManager.isCloudAuthTokenAboutToExpire_(context).ToBoolean();
    }

    public void SetCloudRefreshToken(string token)
    {
        var data = token.ToSByte();
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
            return Utils.Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetFusionHost(string host)
    {
        var data = host.ToSByte();
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
            contextManager.setUserId_(context, data);
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
            result = contextManager.getUserId_(context);
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
        return contextManager.isCertificateChainAboutToExpire_(context).ToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    public bool IsKeyPairValid()
    {
        return contextManager.isKeyPairValid_(context).ToBoolean();
    }

    public void SetOperationContext(OperationContextData data)
    {
        var sData = data.ToData();
        try
        {
            contextManager.setOperationContextJson_(context, sData);
        }
        finally
        {
            ReleaseMemory(sData, null);
        }
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) symbols->DisposeString(result);
    }
}