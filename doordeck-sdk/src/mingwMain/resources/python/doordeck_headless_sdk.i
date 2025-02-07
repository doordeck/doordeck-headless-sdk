%module doordeck_headless_sdk

// Include sdk interfaces
%include "imports.i"
%include "exceptions.i"
%include "utils/utils.i"

// Model
%include "model/api_environment.i"
%include "model/two_factor_method.i"
%include "model/user_role.i"
%include "model/capability.i"
%include "model/audit_event.i"
%include "model/key_pair.i"

// Data
%include "model/data/account.i"
%include "model/data/accountless.i"
%include "model/data/context.i"
%include "model/data/helper.i"
%include "model/data/lock_operations.i"
%include "model/data/platform.i"
%include "model/data/result.i"
%include "model/data/sites.i"
%include "model/data/tiles.i"

// Responses
%include "model/responses/account.i"
%include "model/responses/helper.i"
%include "model/responses/lock_operation.i"
%include "model/responses/platform.i"
%include "model/responses/site.i"
%include "model/responses/tile.i"

// Wrapper
%include "wrapper/account.i"
%include "wrapper/accountless.i"
%include "wrapper/context_manager.i"
%include "wrapper/crypto_manager.i"
%include "wrapper/helper.i"
%include "wrapper/lock_operations.i"
%include "wrapper/platform.i"
%include "wrapper/sites.i"
%include "wrapper/tiles.i"

%pythoncode %{
class InitializeSdk(object):

    def __init__(self, api_environment):
        self.factory = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory()
        self.apiEnvironment = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_model_ApiEnvironment()
        self.sdkApiEnvironment = _doordeck_headless_sdk.getApiEnvironmentByName(self.apiEnvironment, api_environment.name)
        self.sdk = initialize(self.factory, self.sdkApiEnvironment)
        self.accountless = Accountless(accountless(self.sdk))
        self.account = Account(account(self.sdk))
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
%}

%include "../releaseShared/Doordeck.Headless.Sdk_api.h"