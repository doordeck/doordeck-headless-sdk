using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Wrapper;

public interface ISecureStorage
{
    public void SetApiEnvironment(ApiEnvironment apiEnvironment);

    public ApiEnvironment? GetApiEnvironment();

    public void AddCloudAuthToken(string token);

    public string? GetCloudAuthToken();

    public void AddCloudRefreshToken(string token);

    public string? GetCloudRefreshToken();

    public void SetFusionHost(string host);

    public string? GetFusionHost();

    public void AddFusionAuthToken(string token);

    public string? GetFusionAuthToken();

    public void AddPublicKey(byte[] publicKey);

    public byte[]? GetPublicKey();

    public void AddPrivateKey(byte[] privateKey);

    public byte[]? GetPrivateKey();

    public void SetKeyPairVerified(byte[]? publicKey);

    public bool? GetKeyPairVerified();

    public void AddUserId(string userId);

    public string? GetUserId();

    public void AddUserEmail(string email);

    public string? GetUserEmail();

    public void AddCertificateChain(List<string> certificateChain);

    public List<string>? GetCertificateChain();

    public void Clear();
}