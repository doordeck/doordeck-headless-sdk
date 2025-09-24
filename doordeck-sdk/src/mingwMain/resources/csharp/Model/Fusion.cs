using System.Net;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

public interface ILockController;

[JsonPolymorphic(TypeDiscriminatorPropertyName = "type")]
[JsonDerivedType(typeof(AlpetaController), "alpeta")]
[JsonDerivedType(typeof(AmagController), "amag")]
[JsonDerivedType(typeof(AssaAbloyController), "assa-abloy")]
[JsonDerivedType(typeof(AvigilonController), "avigilon")]
[JsonDerivedType(typeof(AxisController), "axis")]
[JsonDerivedType(typeof(CCureController), "ccure")]
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
    public string? Username { get; set; }
    public string? Password { get; set; }
    public required int DoorId { get; set; }
    public Uri? BaseUrl { get; set; }
}

public class AmagController : LockController
{
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required int DoorId { get; set; }
    public Uri? BaseUrl { get; set; }
}

public class AssaAbloyController : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string DoorId { get; set; }
}

public class AvigilonController : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required string DoorId { get; set; }
}

public class AxisController : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string DoorIdentifier { get; set; }
}

public class CCureController : LockController
{
    public Uri? BaseUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required string DoorType { get; set; }
    public required int DoorId { get; set; }
}

public class DemoController : LockController
{
    public required ushort Port { get; set; } = 8080;
}

public class GallagherController : LockController
{
    public Uri? BaseUrl { get; set; }
    public required string ApiKey { get; set; }
    public required string DoorId { get; set; }
}

public class GenetecController : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required string DoorId { get; set; }
}

public class LenelController : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required string DirectoryId { get; set; }
    public required string PanelId { get; set; }
    public required string ReaderId { get; set; }
}

public class MitrefinchController : LockController
{
    public required IPAddress Host { get; set; }
    public required int Output { get; set; }
}

public class PaxtonNet2Controller : LockController
{
    public required IPAddress Host { get; set; }
    public string? Username { get; set; }
    public string? Password { get; set; }
    public required string Address { get; set; }
    public required short Output { get; set; }
}

public class Paxton10Controller : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required int ApplianceId { get; set; }
}

public class IntegraV1Controller : LockController
{
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required int ControllerId { get; set; }
}

public class IntegraV2Controller : LockController
{
    public required Uri BaseUrl { get; set; }
    public required string SessionId { get; set; }
    public required int ControllerId { get; set; }
    public required int CardholderId { get; set; }
    public int? PinCode { get; set; }
}

public class PacController : LockController
{
    public required DataSource DataSource { get; set; }
    public required int OutputChannel { get; set; }
    public required int ControllerSerial { get; set; }
}

public class DataSource
{
    public required string DriverClass { get; set; }
    public required string Url { get; set; }
    public required string User { get; set; }
    public required string Password { get; set; }
}

public class TdsiExgardeController : LockController
{
    public string? DbUrl { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required int DoorId { get; set; }
}

public class TdsiGardisController : LockController
{
    public required IPAddress Host { get; set; }
    public required string Username { get; set; }
    public required string Password { get; set; }
    public required int DoorId { get; set; }
}

public class ZktecoController : LockController
{
    public required string ClientSecret { get; set; }
    public required string DoorId { get; set; }
    public string? BaseUrl { get; set; }
    public required ZktecoEntityType EntityType { get; set; }
}
    
[JsonConverter(typeof(JsonStringEnumConverter))]
public enum ZktecoEntityType
{
    DOOR,
    FLOOR
}