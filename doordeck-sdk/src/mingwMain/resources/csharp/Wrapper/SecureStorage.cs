using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

internal static class SecureStorage
{
    public static ISecureStorage? Implementation { get; set; }
    
    public static class Delegates
    {
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void SetApiEnvironmentDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetApiEnvironmentDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddCloudAuthTokenDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetCloudAuthTokenDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddCloudRefreshTokenDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetCloudRefreshTokenDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void SetFusionHostDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetFusionHostDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddFusionAuthTokenDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetFusionAuthTokenDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddPublicKeyDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetPublicKeyDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddPrivateKeyDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetPrivateKeyDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void SetKeyPairVerifiedDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetKeyPairVerifiedDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddUserIdDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetUserIdDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddUserEmailDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetUserEmailDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void AddCertificateChainDelegate(IntPtr ptr);
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate IntPtr GetCertificateChainDelegate();
        
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void ClearDelegate();
    }
    
    public static void SetApiEnvironment(IntPtr c)
    { 
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.SetApiEnvironment(Enum.Parse<ApiEnvironment>(result));
        }
    }
   
    public static IntPtr GetApiEnvironment() =>
        GetPtrFromString(Implementation?.GetApiEnvironment()?.ToString());
    
    public static void AddCloudAuthToken(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddCloudAuthToken(result);
        }
    }
    
    public static IntPtr GetCloudAuthToken() =>
        GetPtrFromString(Implementation?.GetCloudAuthToken());
    
    public static void AddCloudRefreshToken(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddCloudRefreshToken(result);
        }
    }
    
    public static IntPtr GetCloudRefreshToken() =>
        GetPtrFromString(Implementation?.GetCloudRefreshToken());
    
    public static void SetFusionHost(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.SetFusionHost(result);
        }
    }
    
    public static IntPtr GetFusionHost() =>
        GetPtrFromString(Implementation?.GetFusionHost());
    
    public static void AddFusionAuthToken(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddFusionAuthToken(result);
        }
    }
    
    public static IntPtr GetFusionAuthToken() =>
        GetPtrFromString(Implementation?.GetFusionAuthToken());
    
    public static void AddPublicKey(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddPublicKey(result.DecodeBase64ToByteArray());
        }
    }
    
    public static IntPtr GetPublicKey() =>
        GetPtrFromString(Implementation?.GetPublicKey()?.EncodeByteArrayToBase64());
    
    public static void AddPrivateKey(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddPrivateKey(result.DecodeBase64ToByteArray());
        }
    }
    
    public static IntPtr GetPrivateKey() =>
        GetPtrFromString(Implementation?.GetPrivateKey()?.EncodeByteArrayToBase64());
    
    public static void SetKeyPairVerified(IntPtr c)
    {
        var result = GetStringFromPtr(c);
        Implementation?.SetKeyPairVerified(result?.DecodeBase64ToByteArray());
    }
    
    public static IntPtr GetKeyPairVerified() =>
        GetPtrFromString(Implementation?.GetKeyPairVerified()?.ToString());
    
    public static void AddUserId(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddUserId(result);
        }
    }
    
    public static IntPtr GetUserId() =>
        GetPtrFromString(Implementation?.GetUserId());
    
    public static void AddUserEmail(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddUserEmail(result);
        }
    }
    
    public static IntPtr GetUserEmail() =>
        GetPtrFromString(Implementation?.GetUserEmail());
    
    public static void AddCertificateChain(IntPtr c)
    {
        if (GetStringFromPtr(c) is {} result)
        {
            Implementation?.AddCertificateChain(result.StringToCertificateChain());
        }
    }
    
    public static IntPtr GetCertificateChain() =>
        GetPtrFromString(Implementation?.GetCertificateChain()?.CertificateChainToString());
    
    public static void Clear() =>
        Implementation?.Clear();

    private static string? GetStringFromPtr(IntPtr c)
    {
        if (c == IntPtr.Zero) return null;
        var value = Marshal.PtrToStringAnsi(c);
        return value ?? null;
    }
    
    private static IntPtr GetPtrFromString(string? input)
    {
        if (input == null) return IntPtr.Zero;
        var c = IntPtr.Zero;
        try
        {
            c = Marshal.StringToHGlobalAnsi(input);
            return c;
        }
        finally
        {
            if (c != IntPtr.Zero) Marshal.FreeHGlobal(c);
        }
    }
}
    
