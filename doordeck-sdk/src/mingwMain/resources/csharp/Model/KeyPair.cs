namespace Doordeck.Headless.Sdk.Model;

public class KeyPair
{
    public required byte[] PrivateKey { get; set; }
    public required byte[] PublicKey { get; set; }
}