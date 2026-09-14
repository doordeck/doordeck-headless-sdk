using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Fusion
{
    public Task<FusionLoginResponse> Login(string email, string password) =>
        Dispatcher.Call<FusionLoginResponse>("fusion.login", new { email, password });

    public Task<IntegrationTypeResponse> GetIntegrationType() =>
        Dispatcher.Call<IntegrationTypeResponse>("fusion.getIntegrationType");

    public Task<List<IntegrationConfigurationResponse>> GetIntegrationConfiguration(string type, LockController? controller = null) =>
        Dispatcher.Call<List<IntegrationConfigurationResponse>>("fusion.getIntegrationConfiguration", new { type, controller });

    public Task<object> EnableDoor(string name, Guid siteId, LockController controller) =>
        Dispatcher.Call<object>("fusion.enableDoor", new { name, siteId, controller });

    public Task<object> DeleteDoor(Guid deviceId) =>
        Dispatcher.Call<object>("fusion.deleteDoor", new { deviceId });

    public Task<DoorStateResponse> GetDoorStatus(Guid deviceId) =>
        Dispatcher.Call<DoorStateResponse>("fusion.getDoorStatus", new { deviceId });

    public Task<object> StartDoor(Guid deviceId) =>
        Dispatcher.Call<object>("fusion.startDoor", new { deviceId });

    public Task<object> StopDoor(Guid deviceId) =>
        Dispatcher.Call<object>("fusion.stopDoor", new { deviceId });
}