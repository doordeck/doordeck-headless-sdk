using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

public class CryptoManager
{
    public KeyPair GenerateKeyPair() =>
        Utils.FromJson<KeyPair>(Dispatcher.CallSync<string>("crypto.generateEncodedKeyPair"));

    public KeyPair GenerateKeyPairFromEncodedBytes(byte[] publicKey, byte[] privateKey) =>
        Utils.FromJson<KeyPair>(Dispatcher.CallSync<string>("crypto.generateEncodedKeyPairFromEncodedBytes", new
        {
            publicKey = publicKey.EncodeByteArrayToBase64(),
            privateKey = privateKey.EncodeByteArrayToBase64()
        }));
}
