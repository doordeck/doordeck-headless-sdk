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

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless_(sdk);
        _accountlessApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_accountless.pinned);
    }

    public void Login(LoginData data, Action<TokenResponse> action)
    {
        Process(_accountlessApi.login_, null, action, data);
    }

    public void Registration(RegistrationData data, Action<TokenResponse> action)
    {
        Process(_accountlessApi.registration_, null, action, data);
    }

    public void VerifyEmail(VerifyEmailData data, Action<object> action)
    {
        Process(_accountlessApi.verifyEmail_, null, action, data);
    }

    public void PasswordReset(PasswordResetData data, Action<object> action)
    {
        Process(_accountlessApi.passwordReset_, null, action, data);
    }

    public void PasswordResetVerify(PasswordResetVerifyData data, Action<object> action)
    {
        Process(_accountlessApi.passwordResetVerify_, null, action, data);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi,
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
    }
}