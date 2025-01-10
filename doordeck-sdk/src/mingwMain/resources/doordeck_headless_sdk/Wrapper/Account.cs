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
            null,
            data
        );
    }

    public void Logout()
    {
        _accountResource.logout(_account);
    }

    public RegisterEphemeralKeyWithSecondaryAuthenticationResponse RegisterEphemeralKey(RegisterEphemeralKeyData? data)
    {
        return Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(
            _accountResource.registerEphemeralKeyJson,
            null,
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
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data)
    {
        return Process<RegisterEphemeralKeyResponse>(
            _accountResource.verifyEphemeralKeyRegistrationJson,
            null,
            null,
            data
        );
    }

    public void ReverifyEmail()
    {
        _accountResource.reverifyEmail(_account);
    }

    public void ChangePassword(ChangePasswordData data)
    {
        Process<object>(
            null,
            _accountResource.changePasswordJson,
            null,
            data
        );
    }

    public UserDetailsResponse GetUserDetails()
    {
        return Process<UserDetailsResponse>(
            null,
            null,
            _accountResource.getUserDetailsJson,
            null
        );
    }

    public void UpdateUserDetails(UpdateUserDetailsData data)
    {
        Process<object>(
            null,
            _accountResource.updateUserDetailsJson,
            null,
            data
        );
    }

    public void DeleteAccount()
    {
        _accountResource.deleteAccount(_account);
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
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
                result = withDataAndWithResponse(_account, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_account, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_account);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}