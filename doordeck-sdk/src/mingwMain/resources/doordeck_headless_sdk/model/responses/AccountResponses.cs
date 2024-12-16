namespace DoordeckHeadlessSDK.model.responses
{
    public class TokenResponse
    {
        public string authToken { get; set; }
        public string refreshToken { get; set; }
    }

    public class UserDetailsResponse
    {
        public string email { get; set; }
        public string? displayName { get; set; } = null;
        public bool emailVerified { get; set; }
        public string publicKey { get; set; }
    }

    public class RegisterEphemeralKeyResponse
    {
        public List<string> certificateChain { get; set; }
        public string userId { get; set; }
    }

    public class RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    {
        public TwoFactorMethod method { get; set; }
    }
}
