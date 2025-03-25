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

    def __init__(self, api_environment, cloud_auth_token = None, cloud_refresh_token = None, fusion_host = None):
        self.sdkApiEnvironment = _doordeck_headless_sdk.getApiEnvironmentByName(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_model_data_ApiEnvironment(), api_environment.name)
        self.sdkConfig = _doordeck_headless_sdk.buildSdkConfig(self.sdkApiEnvironment, cloud_auth_token, cloud_refresh_token, fusion_host)
        self.sdk = initialize(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory(), self.sdkConfig)
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
#include <Python.h>

// Global variable to hold the Python callback for the function.
static PyObject *py_callback = NULL;

// Bridge function: this is the C function pointer that will be passed to the function.
// When the DLL calls this function, it converts the C string argument to a Python string
// and then calls the stored Python callback.
void bridge_callback(const char *result) {
    PyGILState_STATE gstate = PyGILState_Ensure();
    if (py_callback) {
        PyObject *arglist = Py_BuildValue("(s)", result);
        PyObject *res = PyObject_CallObject(py_callback, arglist);
        Py_DECREF(arglist);
        if (res) {
            Py_DECREF(res);
        } else {
            PyErr_Print();
        }
    }
    PyGILState_Release(gstate);
}

// Define a typedef that represents the callback signature.
typedef void (*callback_t)(const char *);
%}

// Include standard typemaps.
%include "typemaps.i"

// Apply our custom typemap to our typedef.
// This typemap converts a Python callable to our C function pointer.
%typemap(in) callback_t {
    if (PyCallable_Check($input)) {
        // Increase reference count to ensure the callback is not garbage collected.
        Py_XINCREF($input);
        py_callback = $input;
        $1 = (callback_t)bridge_callback;
    } else {
        PyErr_SetString(PyExc_TypeError, "Parameter must be callable");
        SWIG_fail;
    }
}

// Inform SWIG that the void* parameter 'callback' should be treated as callback_t.
%apply callback_t { void* callback };

%include "../releaseShared/Doordeck.Headless.Sdk_api.h"