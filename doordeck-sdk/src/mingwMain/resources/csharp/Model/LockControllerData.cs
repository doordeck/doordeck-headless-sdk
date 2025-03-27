using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

public interface ILockController;

[JsonPolymorphic(TypeDiscriminatorPropertyName = "type")]
[JsonDerivedType(typeof(AlpetaController), "alpeta")]
[JsonDerivedType(typeof(AmagController), "amag")]
[JsonDerivedType(typeof(AssaAbloyController), "assa-abloy")]
[JsonDerivedType(typeof(AvigilonController), "avigilon")]
[JsonDerivedType(typeof(AxisController), "axis")]
[JsonDerivedType(typeof(DemoController), "demo")]
[JsonDerivedType(typeof(GallagherController), "gallagher")]
[JsonDerivedType(typeof(GenetecController), "genetec")]
[JsonDerivedType(typeof(LenelController), "lenel")]
[JsonDerivedType(typeof(MitrefinchController), "mitrefinch")]
[JsonDerivedType(typeof(PaxtonNet2Controller), "net2")]
[JsonDerivedType(typeof(Paxton10Controller), "paxton10")]
[JsonDerivedType(typeof(IntegraV1Controller), "integra")]
[JsonDerivedType(typeof(IntegraV2Controller), "integra-v2")]
[JsonDerivedType(typeof(PacController), "pac512")]
[JsonDerivedType(typeof(TdsiExgardeController), "tdsi-exgarde")]
[JsonDerivedType(typeof(TdsiGardisController), "tdsi-gardis")]
[JsonDerivedType(typeof(ZktecoController), "zkteco-zkbio-cvsecurity")]
public abstract class LockController : ILockController;

public class AlpetaController : LockController
{
    public string? Username { get; set; } = string.Empty;
    public string? Password { get; set; } = string.Empty;
    public int DoorId { get; set; } = 0;
    public string? BaseUrl { get; set; } = null;
}

public class AmagController : LockController
{
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public int DoorId { get; set; } = 0;
    public string? BaseUrl { get; set; } = null;
}

public class AssaAbloyController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string DoorId { get; set; } = string.Empty;
}

public class AvigilonController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string DoorId { get; set; } = string.Empty;
}

public class AxisController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string DoorIdentifier { get; set; } = string.Empty;
}

public class CCureController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string DoorType { get; set; } = string.Empty;
    public int DoorId { get; set; } = 0;
}

public class DemoController : LockController
{
    public int Port { get; set; } = 8080;
}

public class GallagherController : LockController
{
    public string? BaseUrl { get; set; } = null;
    public string ApiKey { get; set; } = string.Empty;
    public string DoorId { get; set; } = string.Empty;
}

public class GenetecController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string DoorId { get; set; } = string.Empty;
}

public class LenelController : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string DirectoryId { get; set; } = string.Empty;
    public string PanelId { get; set; } = string.Empty;
    public string ReaderId { get; set; } = string.Empty;
}

public class MitrefinchController : LockController
{
    public string Host { get; set; } = string.Empty;
    public int Output { get; set; } = 0;
}

public class PaxtonNet2Controller : LockController
{
    public string Host { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public string Address { get; set; } = string.Empty;
    public short Output { get; set; } = 0;
}

public class Paxton10Controller : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public int ApplianceId { get; set; } = 0;
}

public class IntegraV1Controller : LockController
{
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public int ControllerId { get; set; } = 0;
}

public class IntegraV2Controller : LockController
{
    public string BaseUrl { get; set; } = string.Empty;
    public string SessionId { get; set; } = string.Empty;
    public int ControllerId { get; set; } = 0;
    public int CardholderId { get; set; } = 0;
    public int? PinCode { get; set; } = null;
}

public class PacController : LockController
{
    public DataSource DataSource { get; set; } = new();
    public int OutputChannel { get; set; } = 0;
    public int ControllerSerial { get; set; } = 0;
}

public class DataSource
{
    public string DriverClass { get; set; } = string.Empty;
    public string Url { get; set; } = string.Empty;
    public string User { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
}

public class TdsiExgardeController : LockController
{
    public string? DbUrl { get; set; } = null;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public int DoorId { get; set; } = 0;
}

public class TdsiGardisController : LockController
{
    public string Host { get; set; } = string.Empty;
    public string Username { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public int DoorId { get; set; } = 0;
}

public class ZktecoController : LockController
{
    public string ClientSecret { get; set; } = string.Empty;
    public string DoorId { get; set; } = string.Empty;
    public string? BaseUrl { get; set; } = null;
    public ZktecoEntityType EntityType { get; set; }
}
    
[JsonConverter(typeof(JsonStringEnumConverter))]
public enum ZktecoEntityType
{
    DOOR,
    FLOOR
}