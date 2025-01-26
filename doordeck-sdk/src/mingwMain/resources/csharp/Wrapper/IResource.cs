namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe interface IResource
{
    void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk);

    void Release();
}