using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Account : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi _account;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountApi_e__Struct _accountApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _account = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account_(sdk);
        _accountApi = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_account.pinned);
    }

    public TokenResponse RefreshToken(RefreshTokenData? data)
    {
        return Process<TokenResponse>(
            _accountApi.refreshTokenJson_,
            null,
            data
        );
    }

    public void Logout()
    {
        Process<object>(
            null,
            _accountApi.logoutJson_,
            null
        );
    }

    public RegisterEphemeralKeyWithSecondaryAuthenticationResponse RegisterEphemeralKey(RegisterEphemeralKeyData? data)
    {
        return Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(
            _accountApi.registerEphemeralKeyJson_,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse RegisterEphemeralKeyWithSecondaryAuthentication(
        RegisterEphemeralKeyWithSecondaryAuthenticationData? data)
    {
        return Process<RegisterEphemeralKeyResponse>(
            _accountApi.registerEphemeralKeyWithSecondaryAuthenticationJson_,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data)
    {
        return Process<RegisterEphemeralKeyResponse>(
            _accountApi.verifyEphemeralKeyRegistrationJson_,
            null,
            data
        );
    }

    public void ReverifyEmail()
    {
        Process<object>(
            null,
            _accountApi.reverifyEmailJson_,
            null
        );
    }

    public void ChangePassword(ChangePasswordData data)
    {
        Process<object>(
            _accountApi.changePasswordJson_,
            null,
            data
        );
    }

    public UserDetailsResponse GetUserDetails()
    {
        return Process<UserDetailsResponse>(
            null,
            _accountApi.getUserDetailsJson_,
            null
        );
    }

    public void UpdateUserDetails(UpdateUserDetailsData data)
    {
        Process<object>(
            _accountApi.updateUserDetailsJson_,
            null,
            data
        );
    }

    public void DeleteAccount()
    {
        Process<object>(
            null,
            _accountApi.deleteAccountJson_,
            null
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_account, sData) :
                processWithoutDataWithResponse(_account);

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