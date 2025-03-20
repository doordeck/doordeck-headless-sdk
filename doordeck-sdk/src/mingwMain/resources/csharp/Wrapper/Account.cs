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

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _account = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account_(sdk);
        _accountApi = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_account.pinned);
    }

    public void RefreshToken(RefreshTokenData? data, Action<TokenResponse> action)
    {
        Process(_accountApi.refreshToken_, null, action, data);
    }

    public void Logout(Action<object> action)
    {
        Process(null, _accountApi.logout_, action, null);
    }

    public void RegisterEphemeralKey(RegisterEphemeralKeyData? data,
        Action<RegisterEphemeralKeyResponse> action)
    {
        Process(_accountApi.registerEphemeralKey_, null, action, data);
    }

    public void RegisterEphemeralKeyWithSecondaryAuthentication(RegisterEphemeralKeyWithSecondaryAuthenticationData? data,
        Action<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> action)
    {
        Process(_accountApi.registerEphemeralKeyWithSecondaryAuthentication_, null, action, data);
    }

    public void VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data,
        Action<RegisterEphemeralKeyResponse> action)
    {
        Process(_accountApi.verifyEphemeralKeyRegistration_, null, action, data);
    }

    public void ReverifyEmail(Action<object> action)
    {
        Process(null, _accountApi.reverifyEmail_, action, null);
    }

    public void ChangePassword(ChangePasswordData data, Action<object> action)
    {
        Process(_accountApi.changePassword_, null, action, data);
    }

    public void GetUserDetails(Action<UserDetailsResponse> action)
    {
        Process(null, _accountApi.getUserDetails_, action, null);
    }

    public void UpdateUserDetails(UpdateUserDetailsData data, Action<object> action)
    {
        Process(_accountApi.updateUserDetails_, null, action, data);
    }

    public void DeleteAccount(Action<object> action)
    {
        Process(null, _accountApi.deleteAccount_, action, null);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            void*, void> processWithoutData,
        Action<TResponse> userCallback,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(userCallback);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_account, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_account, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }
    }
}