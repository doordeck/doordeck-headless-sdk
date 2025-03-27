using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class CryptoManager(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager crypto,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._crypto_e__Struct._CryptoManager_e__Struct cryptoManager,
    Doordeck_Headless_Sdk_ExportedSymbols* symbols)
{
    public EncodedKeyPair GenerateEncodedKeyPair()
    {
        sbyte* result = null;
        try
        {
            result = cryptoManager.generateEncodedKeyPair_(crypto);
            return Utils.Utils.FromData<EncodedKeyPair>(result);
        }
        finally
        {
            if (result != null) symbols->DisposeString(result);
        }
    }
}