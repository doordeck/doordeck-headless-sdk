using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using FusionApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi;

public class Fusion(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi fusion) : AbstractWrapper
{
    public unsafe Task<FusionLoginResponse> Login(string email, string password) =>
        Process<FusionApi, FusionLoginResponse>(fusion, &Methods.loginFusion, new { email, password });

    public unsafe Task<IntegrationTypeResponse> GetIntegrationType() =>
        Process<FusionApi, IntegrationTypeResponse>(fusion, &Methods.getIntegrationType);

    public unsafe Task<List<IntegrationConfigurationResponse>> GetIntegrationConfiguration(string type, LockController? controller = null) =>
        Process<FusionApi, List<IntegrationConfigurationResponse>>(fusion, &Methods.getIntegrationConfiguration, new { type, controller });

    public unsafe Task<object> EnableDoor(string name, Guid siteId, LockController controller) =>
        Process<FusionApi, object>(fusion, &Methods.enableDoor, new { name, siteId, controller });

    public unsafe Task<object> DeleteDoor(Guid deviceId) =>
        Process<FusionApi, object>(fusion, &Methods.deleteDoor, new { deviceId });

    public unsafe Task<DoorStateResponse> GetDoorStatus(Guid deviceId) =>
        Process<FusionApi, DoorStateResponse>(fusion, &Methods.getDoorStatus, new { deviceId });

    public unsafe Task<object> StartDoor(Guid deviceId) =>
        Process<FusionApi, object>(fusion, &Methods.startDoor, new { deviceId });

    public unsafe Task<object> StopDoor(Guid deviceId) =>
        Process<FusionApi, object>(fusion, &Methods.stopDoor, new { deviceId });
}