using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Accountless : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi _accountless;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessApi_e__Struct _accountlessApi;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless_(sdk);
        _accountlessApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_accountless.pinned);
    }

    public unsafe Task<TokenResponse> Login(LoginData data)
    {
        return Process<TokenResponse>(_accountlessApi.login_, null, data);
    }

    public unsafe Task<TokenResponse> Registration(RegistrationData data)
    {
        return Process<TokenResponse>(_accountlessApi.registration_, null, data);
    }

    public unsafe Task<object> VerifyEmail(VerifyEmailData data)
    {
        return Process<object>(_accountlessApi.verifyEmail_, null, data);
    }

    public unsafe Task<object> PasswordReset(PasswordResetData data)
    {
        return Process<object>(_accountlessApi.passwordReset_, null, data);
    }

    public unsafe Task<object> PasswordResetVerify(PasswordResetVerifyData data)
    {
        return Process<object>(_accountlessApi.passwordResetVerify_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
            void*, void> processWithoutData,
        object? data = null
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(null, tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_accountless, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_accountless, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}