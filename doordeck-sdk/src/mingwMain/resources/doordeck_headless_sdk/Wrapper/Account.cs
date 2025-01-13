using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Account : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource _account;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountResource_e__Struct _accountResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _account = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
        _accountResource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_account.pinned);
    }

    public TokenResponse RefreshToken(RefreshTokenData? data)
    {
        return Process<TokenResponse>(
            _accountResource.refreshTokenJson,
            null,
            data
        );
    }

    public void Logout()
    {
        Process<object>(
            null,
            _accountResource.logoutJson,
            null
        );
    }

    public RegisterEphemeralKeyWithSecondaryAuthenticationResponse RegisterEphemeralKey(RegisterEphemeralKeyData? data)
    {
        return Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(
            _accountResource.registerEphemeralKeyJson,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse RegisterEphemeralKeyWithSecondaryAuthentication(
        RegisterEphemeralKeyWithSecondaryAuthenticationData? data)
    {
        return Process<RegisterEphemeralKeyResponse>(
            _accountResource.registerEphemeralKeyWithSecondaryAuthenticationJson,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data)
    {
        return Process<RegisterEphemeralKeyResponse>(
            _accountResource.verifyEphemeralKeyRegistrationJson,
            null,
            data
        );
    }

    public void ReverifyEmail()
    {
        Process<object>(
            null,
            _accountResource.reverifyEmailJson,
            null
        );
    }

    public void ChangePassword(ChangePasswordData data)
    {
        Process<object>(
            _accountResource.changePasswordJson,
            null,
            data
        );
    }

    public UserDetailsResponse GetUserDetails()
    {
        return Process<UserDetailsResponse>(
            null,
            _accountResource.getUserDetailsJson,
            null
        );
    }

    public void UpdateUserDetails(UpdateUserDetailsData data)
    {
        Process<object>(
            _accountResource.updateUserDetailsJson,
            null,
            data
        );
    }

    public void DeleteAccount()
    {
        Process<object>(
            null,
            _accountResource.deleteAccountJson,
            null
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
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