using System.Security.Cryptography.X509Certificates;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

/// <summary>
/// Context operations complete against local state, so the SDK invokes their callback before the
/// native call returns and <see cref="Dispatcher.CallSync{T}"/> never actually waits. The two that
/// reach the server stay asynchronous.
/// </summary>
public class ContextManager
{
    public ApiEnvironment GetApiEnvironment() =>
        Enum.Parse<ApiEnvironment>(Dispatcher.CallSync<string>("context.getApiEnvironment"));

    public void SetApiEnvironment(ApiEnvironment apiEnvironment) =>
        Dispatcher.CallSync<object>("context.setApiEnvironment", apiEnvironment.ToString());

    public void SetCloudAuthToken(string token) =>
        Dispatcher.CallSync<object>("context.setCloudAuthToken", token);

    public string GetCloudAuthToken() =>
        Dispatcher.CallSync<string>("context.getCloudAuthToken");

    public Task<bool> IsCloudAuthTokenInvalidOrExpired(bool checkServerInvalidation) =>
        Dispatcher.Call<bool>("context.isCloudAuthTokenInvalidOrExpired", checkServerInvalidation.ToString());

    public void SetCloudRefreshToken(string token) =>
        Dispatcher.CallSync<object>("context.setCloudRefreshToken", token);

    public string GetCloudRefreshToken() =>
        Dispatcher.CallSync<string>("context.getCloudRefreshToken");

    public void SetFusionHost(string host) =>
        Dispatcher.CallSync<object>("context.setFusionHost", host);

    public string GetFusionHost() =>
        Dispatcher.CallSync<string>("context.getFusionHost");

    public void SetFusionAuthToken(string token) =>
        Dispatcher.CallSync<object>("context.setFusionAuthToken", token);

    public string GetFusionAuthToken() =>
        Dispatcher.CallSync<string>("context.getFusionAuthToken");

    public void SetUserId(Guid userId) =>
        Dispatcher.CallSync<object>("context.setUserId", userId.ToString());

    public Guid GetUserId() =>
        Guid.Parse(Dispatcher.CallSync<string>("context.getUserId"));

    public void SetUserEmail(string email) =>
        Dispatcher.CallSync<object>("context.setUserEmail", email);

    public string GetUserEmail() =>
        Dispatcher.CallSync<string>("context.getUserEmail");

    public void SetCertificateChain(List<X509Certificate> certificateChain) =>
        Dispatcher.CallSync<object>("context.setCertificateChain", certificateChain.CertificateChainToString());

    public List<X509Certificate> GetCertificateChain() =>
        Dispatcher.CallSync<string>("context.getCertificateChain").StringToCertificateChain();

    public bool IsCertificateChainInvalidOrExpired() =>
        Dispatcher.CallSync<bool>("context.isCertificateChainInvalidOrExpired");

    public void SetKeyPair(byte[] publicKey, byte[] privateKey) =>
        Dispatcher.CallSync<object>("context.setKeyPair", new
        {
            publicKey = publicKey.EncodeByteArrayToBase64(),
            privateKey = privateKey.EncodeByteArrayToBase64()
        });

    public KeyPair GetKeyPair() =>
        Utils.FromJson<KeyPair>(Dispatcher.CallSync<string>("context.getKeyPair"));

    public void SetKeyPairVerified(byte[]? publicKey) =>
        Dispatcher.CallSync<object>("context.setKeyPairVerified", publicKey?.EncodeByteArrayToBase64());

    public bool IsKeyPairVerified() =>
        Dispatcher.CallSync<bool>("context.isKeyPairVerified");

    public bool IsKeyPairValid() =>
        Dispatcher.CallSync<bool>("context.isKeyPairValid");

    public void SetOperationContext(Guid userId, List<X509Certificate> certificateChain, byte[] publicKey,
        byte[] privateKey, bool isKeyPairVerified) =>
        Dispatcher.CallSync<object>("context.setOperationContext", new
        {
            userId,
            certificateChain = certificateChain.CertificateChainToString(),
            publicKey,
            privateKey,
            isKeyPairVerified
        });

    public Task<ContextState> GetContextState(bool checkServerInvalidation) =>
        Dispatcher.Call<ContextState>("context.getContextState", checkServerInvalidation.ToString());

    public void ClearContext() =>
        Dispatcher.CallSync<object>("context.clearContext");
}
