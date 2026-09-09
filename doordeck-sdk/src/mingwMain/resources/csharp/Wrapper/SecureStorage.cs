using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

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
    

    // Held in static fields for the lifetime of the process: the native side keeps these pointers,
    // so the delegates they came from must stay reachable.
    private static readonly Delegates.SetApiEnvironmentDelegate setApiEnvironmentDelegate = SetApiEnvironment;
    private static readonly Delegates.GetApiEnvironmentDelegate getApiEnvironmentDelegate = GetApiEnvironment;
    private static readonly Delegates.AddCloudAuthTokenDelegate addCloudAuthTokenDelegate = AddCloudAuthToken;
    private static readonly Delegates.GetCloudAuthTokenDelegate getCloudAuthTokenDelegate = GetCloudAuthToken;
    private static readonly Delegates.AddCloudRefreshTokenDelegate addCloudRefreshTokenDelegate = AddCloudRefreshToken;
    private static readonly Delegates.GetCloudRefreshTokenDelegate getCloudRefreshTokenDelegate = GetCloudRefreshToken;
    private static readonly Delegates.SetFusionHostDelegate setFusionHostDelegate = SetFusionHost;
    private static readonly Delegates.GetFusionHostDelegate getFusionHostDelegate = GetFusionHost;
    private static readonly Delegates.AddFusionAuthTokenDelegate addFusionAuthTokenDelegate = AddFusionAuthToken;
    private static readonly Delegates.GetFusionAuthTokenDelegate getFusionAuthTokenDelegate = GetFusionAuthToken;
    private static readonly Delegates.AddPublicKeyDelegate addPublicKeyDelegate = AddPublicKey;
    private static readonly Delegates.GetPublicKeyDelegate getPublicKeyDelegate = GetPublicKey;
    private static readonly Delegates.AddPrivateKeyDelegate addPrivateKeyDelegate = AddPrivateKey;
    private static readonly Delegates.GetPrivateKeyDelegate getPrivateKeyDelegate = GetPrivateKey;
    private static readonly Delegates.SetKeyPairVerifiedDelegate setKeyPairVerifiedDelegate = SetKeyPairVerified;
    private static readonly Delegates.GetKeyPairVerifiedDelegate getKeyPairVerifiedDelegate = GetKeyPairVerified;
    private static readonly Delegates.AddUserIdDelegate addUserIdDelegate = AddUserId;
    private static readonly Delegates.GetUserIdDelegate getUserIdDelegate = GetUserId;
    private static readonly Delegates.AddUserEmailDelegate addUserEmailDelegate = AddUserEmail;
    private static readonly Delegates.GetUserEmailDelegate getUserEmailDelegate = GetUserEmail;
    private static readonly Delegates.AddCertificateChainDelegate addCertificateChainDelegate = AddCertificateChain;
    private static readonly Delegates.GetCertificateChainDelegate getCertificateChainDelegate = GetCertificateChain;
    private static readonly Delegates.ClearDelegate clearDelegate = Clear;

    internal static void Register() =>
        Native.dd_set_secure_storage(
            Marshal.GetFunctionPointerForDelegate(setApiEnvironmentDelegate),
            Marshal.GetFunctionPointerForDelegate(getApiEnvironmentDelegate),
            Marshal.GetFunctionPointerForDelegate(addCloudAuthTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(getCloudAuthTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(addCloudRefreshTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(getCloudRefreshTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(setFusionHostDelegate),
            Marshal.GetFunctionPointerForDelegate(getFusionHostDelegate),
            Marshal.GetFunctionPointerForDelegate(addFusionAuthTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(getFusionAuthTokenDelegate),
            Marshal.GetFunctionPointerForDelegate(addPublicKeyDelegate),
            Marshal.GetFunctionPointerForDelegate(getPublicKeyDelegate),
            Marshal.GetFunctionPointerForDelegate(addPrivateKeyDelegate),
            Marshal.GetFunctionPointerForDelegate(getPrivateKeyDelegate),
            Marshal.GetFunctionPointerForDelegate(setKeyPairVerifiedDelegate),
            Marshal.GetFunctionPointerForDelegate(getKeyPairVerifiedDelegate),
            Marshal.GetFunctionPointerForDelegate(addUserIdDelegate),
            Marshal.GetFunctionPointerForDelegate(getUserIdDelegate),
            Marshal.GetFunctionPointerForDelegate(addUserEmailDelegate),
            Marshal.GetFunctionPointerForDelegate(getUserEmailDelegate),
            Marshal.GetFunctionPointerForDelegate(addCertificateChainDelegate),
            Marshal.GetFunctionPointerForDelegate(getCertificateChainDelegate),
            Marshal.GetFunctionPointerForDelegate(clearDelegate));

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
            Implementation?.AddUserId(new Guid(result));
        }
    }

    public static IntPtr GetUserId() =>
        GetPtrFromString(Implementation?.GetUserId().ToString());
    
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
    
