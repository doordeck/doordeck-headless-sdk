%module doordeck_headless_sdk

// Include sdk interfaces
%include "ignore.i"
%include "imports.i"
%include "exceptions.i"
%include "utils/utils.i"
// Model
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
%include "model/data/result.i"
%include "model/data/sites.i"
%include "model/data/tiles.i"
// Responses
%include "model/responses/account.i"
%include "model/responses/helper.i"
%include "model/responses/lock_operation.i"
%include "model/responses/site.i"
%include "model/responses/tile.i"
// Wrapper
%include "wrapper/account.i"
%include "wrapper/accountless.i"
%include "wrapper/context_manager.i"
%include "wrapper/crypto_manager.i"
%include "wrapper/lock_operations.i"
%include "wrapper/sites.i"
%include "wrapper/tiles.i"
%{
#include <stdbool.h>
%}

%{
#include "Doordeck.Headless.Sdk_api.h"
%}

%include "Doordeck.Headless.Sdk_api.h"