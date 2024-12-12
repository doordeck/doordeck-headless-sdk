namespace DoordeckHeadlessSDK.model
{
    public class LoginData
    {
        public string email { get; set; }
        public string password { get; set; }

        public LoginData(string email, string password)
        {
            this.email = email;
            this.password = password;
        }
    }

    public class RegistrationData
    {
        public string email { get; set; }
        public string password { get; set; }
        public string? displayName { get; set; }
        public bool force { get; set; }

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
        public string code { get; set; }

        public VerifyEmailData(string code)
        {
            this.code = code;
        }
    }
}
