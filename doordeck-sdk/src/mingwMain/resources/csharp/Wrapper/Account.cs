using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Account(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi account,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountApi_e__Struct accountApi) : IResource
{
    public unsafe Task<TokenResponse> RefreshToken(RefreshTokenData? data)
    {
        return Process<TokenResponse>(accountApi.refreshToken_, null, data);
    }

    public unsafe Task<object> Logout()
    {
        return Process<object>(null, accountApi.logout_, null);
    }

    public unsafe Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(RegisterEphemeralKeyData? data)
    {
        return Process<RegisterEphemeralKeyResponse>(accountApi.registerEphemeralKey_, null, data);
    }

    public unsafe Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(RegisterEphemeralKeyWithSecondaryAuthenticationData? data)
    {
        return Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(accountApi.registerEphemeralKeyWithSecondaryAuthentication_, null, data);
    }

    public unsafe Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data)
    {
        return Process<RegisterEphemeralKeyResponse>(accountApi.verifyEphemeralKeyRegistration_, null, data);
    }

    public unsafe Task<object> ReverifyEmail()
    {
        return Process<object>(null, accountApi.reverifyEmail_, null);
    }

    public unsafe Task<object> ChangePassword(ChangePasswordData data)
    {
        return Process<object>(accountApi.changePassword_, null, data);
    }

    public unsafe Task<UserDetailsResponse> GetUserDetails()
    {
        return Process<UserDetailsResponse>(null, accountApi.getUserDetails_, null);
    }

    public unsafe Task<object> UpdateUserDetails(UpdateUserDetailsData data)
    {
        return Process<object>(accountApi.updateUserDetails_, null, data);
    }

    public unsafe Task<object> DeleteAccount()
    {
        return Process<object>(null, accountApi.deleteAccount_, null);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(account, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(account, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}