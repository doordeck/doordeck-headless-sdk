﻿namespace Doordeck.Headless.Sdk.Model
{
    public class LoginData
    {
        public string Email { get; set; }
        public string Password { get; set; }

        public LoginData(string email, string password)
        {
            Email = email;
            Password = password;
        }
    }

    public class RegistrationData
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string? DisplayName { get; set; }
        public bool Force { get; set; }
        public string? PublicKey { get; set; }

        public RegistrationData(string email, string password, string? displayName = null, bool force = false, string? publicKey = null)
        {
            Email = email;
            Password = password;
            DisplayName = displayName;
            Force = force;
            PublicKey = publicKey;
        }
    }

    public class VerifyEmailData
    {
        public string Code { get; set; }

        public VerifyEmailData(string code)
        {
            Code = code;
        }
    }

    public class PasswordResetData
    {
        public string Email { get; set; }

        public PasswordResetData(string email)
        {
            Email = email;
        }
    }

    public class PasswordResetVerifyData
    {
        public string UserId { get; set; }
        public string Token { get; set; }
        public string Password { get; set; }

        public PasswordResetVerifyData(string userId, string token, string password)
        {
            UserId = userId;
            Token = token;
            Password = password;
        }
    }
}
