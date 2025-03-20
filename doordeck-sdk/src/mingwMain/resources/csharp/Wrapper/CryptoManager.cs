using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Wrapper;

public class CryptoManager : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager _crypto;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._crypto_e__Struct._CryptoManager_e__Struct _cryptoManager;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _crypto = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto_(sdk);
        _cryptoManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.crypto.CryptoManager;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_crypto.pinned);
    }

    public unsafe EncodedKeyPair GenerateEncodedKeyPair()
    {
        sbyte* result = null;
        try
        {
            result = _cryptoManager.generateEncodedKeyPair_(_crypto);
            return Utils.Utils.FromData<EncodedKeyPair>(result);
        }
        finally
        {
            if (result != null) _symbols->DisposeString(result);
        }
    }
}