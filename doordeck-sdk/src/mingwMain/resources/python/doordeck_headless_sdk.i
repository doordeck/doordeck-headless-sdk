%module doordeck_headless_sdk

// Include sdk interfaces
%include "imports.i"
%include "exceptions.i"
%include "utils/utils.i"

%include "model/responses/account_responses.i"
%include "model/account_data.i"
%include "model/accountless_data.i"
%include "model/result_data.i"
%include "model/lock_operations_data.i"
%include "wrapper/account.i"

%include "ignore.i"

%{
	#include <stdbool.h>
%}

%{
#   include "../../../../build/bin/mingwX64/releaseShared/Doordeck.Headless.Sdk_api.h"
%}

%include "../../../../build/bin/mingwX64/releaseShared/Doordeck.Headless.Sdk_api.h"