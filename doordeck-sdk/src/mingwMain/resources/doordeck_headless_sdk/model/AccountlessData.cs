namespace DoordeckHeadlessSDK.model
{
    public class LoginData
    {
        public string email { get; }
        public string password { get; }

        public LoginData(string email, string password)
        {
            this.email = email;
            this.password = password;
        }
    }

    public class RegistrationData
    {
        public string email { get; }
        public string password { get; }
        public string? displayName { get; }
        public bool force { get; }

        public RegistrationData(string email, string password, string? displayName = null, bool force = false)
        {
            this.email = email;
            this.password = password;
            this.displayName = displayName;
            this.force = force;
        }
    }

    public class VerifyEmailData
    {
        public string code { get; }


        public VerifyEmailData(string code)
        {
            this.code = code;
        }
    }

}
