namespace DoordeckHeadlessSDK.model
{
    public class OperationContextData
    {
        public string userId { get; set; }
        public List<string> userCertificateChain { get; set; }
        public string userPublicKey { get; set; }
        public string userPrivateKey { get; set; }

        public OperationContextData(string userId, List<string> userCertificateChain, string userPublicKey, string userPrivateKey)
        {
            this.userId = userId;
            this.userCertificateChain = userCertificateChain;
            this.userPublicKey = userPublicKey;
            this.userPrivateKey = userPrivateKey;
        }
    }
}
