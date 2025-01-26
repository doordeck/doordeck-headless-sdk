namespace Doordeck.Headless.Sdk.Model
{
    public class OperationContextData
    {
        public string UserId { get; set; }
        public string UserCertificateChain { get; set; }
        public string UserPublicKey { get; set; }
        public string UserPrivateKey { get; set; }

        public OperationContextData(string userId, string userCertificateChain, string userPublicKey, string userPrivateKey)
        {
            UserId = userId;
            UserCertificateChain = userCertificateChain;
            UserPublicKey = userPublicKey;
            UserPrivateKey = userPrivateKey;
        }
    }
}
