using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Accountless : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource _accountless;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessResource_e__Struct _accountlessResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
        _accountlessResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_accountless.pinned);
    }

    public TokenResponse Login(LoginData data)
    {
        return ProcessAccountlessResource<TokenResponse>(
            _accountlessResource.loginJson,
            null,
            null,
            data
        );
    }

    public TokenResponse Registration(RegistrationData data)
    {
        return ProcessAccountlessResource<TokenResponse>(
            _accountlessResource.registrationJson,
            null,
            null,
            data
        );
    }

    public void VerifyEmail(VerifyEmailData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.verifyEmailJson,
            null,
            data
        );
    }

    public void PasswordReset(PasswordResetData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.passwordResetJson,
            null,
            data
        );
    }

    public void PasswordResetVerify(PasswordResetVerifyData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.passwordResetVerifyJson,
            null,
            data
        );
    }

    private TResponse ProcessAccountlessResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
            sbyte*> withoutDataAndWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var withResponse = typeof(TResponse) != typeof(object);
            var withData = data != null;

            if (withData && withResponse)
                result = withDataAndWithResponse(_accountless, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_accountless, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_accountless);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}