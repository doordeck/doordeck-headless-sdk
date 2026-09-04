using System.Runtime.InteropServices;
using System.Security.Cryptography.X509Certificates;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

using ContextManagerApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager;

public unsafe class ContextManager(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager context,
    Doordeck_Headless_Sdk_ExportedSymbols* symbols) : AbstractWrapper
{
    public ApiEnvironment GetApiEnvironment()
    {
        sbyte* result = null;
        try
        {
            result = Methods.getApiEnvironment(context);
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
            Methods.setCloudAuthToken(context, data);
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
            result = Methods.getCloudAuthToken(context);
            return Utils.SByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public Task<bool> IsCloudAuthTokenInvalidOrExpired(bool checkServerInvalidation) =>
        Process<ContextManagerApi, bool>(context, &Methods.isCloudAuthTokenInvalidOrExpired, checkServerInvalidation);

    public void SetCloudRefreshToken(string token)
    {
        var data = token.StringToSByte();
        try
        {
            Methods.setCloudRefreshToken(context, data);
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
            result = Methods.getCloudRefreshToken(context);
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
            Methods.setFusionHost(context, data);
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
            result = Methods.getFusionHost(context);
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
            Methods.setFusionAuthToken(context, data);
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
            result = Methods.getFusionAuthToken(context);
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
            Methods.setUserId(context, data);
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
            result = Methods.getUserId(context);
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
            Methods.setUserEmail(context, data);
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
            result = Methods.getUserEmail(context);
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
        return Methods.isCertificateChainInvalidOrExpired(context).ByteToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    // SetKeyPairVerified

    public bool IsKeyPairVerified()
    {
        return Methods.isKeyPairVerified(context).ByteToBoolean();
    }

    public bool IsKeyPairValid()
    {
        return Methods.isKeyPairValid(context).ByteToBoolean();
    }

    public void SetOperationContext(Guid userId, List<X509Certificate> certificateChain, byte[] publicKey, byte[] privateKey, bool isKeyPairVerified)
    {
        var sData = new { userId, certificateChain = certificateChain.CertificateChainToString(), publicKey, privateKey, isKeyPairVerified }.ToJsonSByte();
        try
        {
            Methods.setOperationContext(context, sData);
        }
        finally
        {
            ReleaseMemory(sData, null);
        }
    }

    public Task<ContextState> GetContextState(bool checkServerInvalidation) =>
            Process<ContextManagerApi, ContextState>(context, &Methods.getContextState,  checkServerInvalidation);

    public void ClearContext()
    {
        Methods.clearContext(context);
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) symbols->DisposeString(result);
    }
}