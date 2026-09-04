using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class CryptoManager(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager crypto,
    Doordeck_Headless_Sdk_ExportedSymbols* symbols)
{
    public KeyPair GenerateKeyPair()
    {
        sbyte* result = null;
        try
        {
            result = Methods.generateEncodedKeyPair(crypto);
            return Utils.FromJsonSByte<KeyPair>(result);
        }
        finally
        {
            if (result != null) symbols->DisposeString(result);
        }
    }
}