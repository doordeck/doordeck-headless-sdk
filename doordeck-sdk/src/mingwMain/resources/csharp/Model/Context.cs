namespace Doordeck.Headless.Sdk.Model;

public class OperationContextData(
    string userId,
    string userCertificateChain,
    string userPublicKey,
    string userPrivateKey)
{
    public string UserId { get; set; } = userId;
    public string UserCertificateChain { get; set; } = userCertificateChain;
    public string UserPublicKey { get; set; } = userPublicKey;
    public string UserPrivateKey { get; set; } = userPrivateKey;
}