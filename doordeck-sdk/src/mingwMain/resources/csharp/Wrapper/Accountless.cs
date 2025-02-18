using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Accountless : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi _accountless;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessApi_e__Struct _accountlessApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless_(sdk);
        _accountlessApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_accountless.pinned);
    }

    public TokenResponse Login(LoginData data)
    {
        return Process<TokenResponse>(
            _accountlessApi.loginJson_,
            null,
            data
        );
    }

    public TokenResponse Registration(RegistrationData data)
    {
        return Process<TokenResponse>(
            _accountlessApi.registrationJson_,
            null,
            data
        );
    }

    public void VerifyEmail(VerifyEmailData data)
    {
        Process<object>(
            _accountlessApi.verifyEmailJson_,
            null,
            data
        );
    }

    public void PasswordReset(PasswordResetData data)
    {
        Process<object>(
            _accountlessApi.passwordResetJson_,
            null,
            data
        );
    }

    public void PasswordResetVerify(PasswordResetVerifyData data)
    {
        Process<object>(
            _accountlessApi.passwordResetVerifyJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_accountless, sData) :
                processWithoutDataWithResponse(_accountless);

            var resultData = result != null
                ? Utils.Utils.FromData<ResultData<TResponse>>(result)
                : default!;

            resultData.HandleException();

            return resultData.Success!.Result ?? default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}