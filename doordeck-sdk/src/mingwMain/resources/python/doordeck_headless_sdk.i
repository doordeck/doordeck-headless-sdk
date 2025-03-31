%module doordeck_headless_sdk

// Include sdk interfaces
%include "imports.i"
%include "exceptions.i"
%include "utils/utils.i"

// Model
%include "model/enums.i"

// Data
%include "model/data/fusion.i"
%include "model/data/lock_operations.i"
%include "model/data/platform.i"

// Wrapper
%include "wrapper/account.i"
%include "wrapper/accountless.i"
%include "wrapper/context_manager.i"
%include "wrapper/crypto_manager.i"
%include "wrapper/fusion.i"
%include "wrapper/helper.i"
%include "wrapper/lock_operations.i"
%include "wrapper/platform.i"
%include "wrapper/sites.i"
%include "wrapper/tiles.i"

%pythoncode %{
class InitializeSdk(object):

    def __init__(self, api_environment: ApiEnvironment, cloud_auth_token: str = None, cloud_refresh_token: str = None, fusion_host: str = None):
        self.sdkApiEnvironment = _doordeck_headless_sdk.getApiEnvironmentByName(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_model_data_ApiEnvironment(), api_environment.name)
        self.sdkConfig = _doordeck_headless_sdk.buildSdkConfig(self.sdkApiEnvironment, cloud_auth_token, cloud_refresh_token, fusion_host)
        self.sdk = initialize(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory(), self.sdkConfig)
        self.accountless = Accountless(accountless(self.sdk))
        self.account = Account(account(self.sdk))
        self.fusion = Fusion(fusion(self.sdk))
        self.helper = Helper(helper(self.sdk))
        self.lockOperations = LockOperations(lockOperations(self.sdk))
        self.platform = Platform(platform(self.sdk))
        self.sites = Sites(sites(self.sdk))
        self.tiles = Tiles(tiles(self.sdk))
        self.contextManager = ContextManager(contextManager(self.sdk))
        self.cryptoManager = CryptoManager(crypto(self.sdk))
%}

%{
#include <stdbool.h>
%}

%{
#include "../releaseShared/Doordeck.Headless.Sdk_api.h"

// Define the callback type.
typedef void (*callback_t)(const char *);
%}

// Include standard typemaps.
%include "typemaps.i"

// Use SWIG’s callback typemap to convert a Python callable (provided as an integer)
// to a C function pointer. Here, the Python side must supply the address of a ctypes-wrapped function.
%typemap(in) callback_t {
    $1 = (callback_t)PyLong_AsVoidPtr($input);
}

%apply callback_t { void* callback };

%include "../releaseShared/Doordeck.Headless.Sdk_api.h"