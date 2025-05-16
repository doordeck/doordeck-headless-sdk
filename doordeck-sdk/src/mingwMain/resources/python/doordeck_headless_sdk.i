%module doordeck_headless_sdk

// Include sdk interfaces
%include "imports.i"
%include "exceptions.i"
%include "utils/utils.i"

// Data
%include "model/data/fusion.i"
%include "model/data/lock_operations.i"
%include "model/data/platform.i"

// Wrapper
%include "wrapper/secure_storage.i"
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

    def __init__(self, api_environment: typing.Literal["DEV", "STAGING", "PROD"] = "PROD",
        cloud_auth_token: typing.Optional[str] = None, cloud_refresh_token: typing.Optional[str] = None,
        fusion_host: typing.Optional[str] = None, secure_storage_impl: typing.Optional[ISecureStorage] = None,
        debug_logging: typing.Optional[bool] = None):

        self.secureStorage = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_storage_SecureStorage()
        if secure_storage_impl is not None:
            SecureStorage.Implementation = secure_storage_impl

            setApiEnvironmentCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.set_api_environment), ctypes.c_void_p).value
            getApiEnvironmentCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_api_environment), ctypes.c_void_p).value
            addCloudAuthTokenCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_cloud_auth_token), ctypes.c_void_p).value
            getCloudAuthTokenCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_cloud_auth_token), ctypes.c_void_p).value
            addCloudRefreshTokenCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_cloud_refresh_auth_token), ctypes.c_void_p).value
            getCloudRefreshTokenCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_cloud_refresh_token), ctypes.c_void_p).value
            setFusionHostCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.set_fusion_host), ctypes.c_void_p).value
            getFusionHostCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_fusion_host), ctypes.c_void_p).value
            addFusionAuthTokenCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_fusion_auth_token), ctypes.c_void_p).value
            getFusionAuthTokenCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_fusion_auth_token), ctypes.c_void_p).value
            addPublicKeyCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_public_key), ctypes.c_void_p).value
            getPublicKeyCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_public_key), ctypes.c_void_p).value
            addPrivateKeyCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_private_key), ctypes.c_void_p).value
            getPrivateKeyCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_private_key), ctypes.c_void_p).value
            setKeyPairVerifiedCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.set_key_pair_verified), ctypes.c_void_p).value
            getKeyPairVerifiedCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_key_pair_verified), ctypes.c_void_p).value
            addUserIdCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_user_id), ctypes.c_void_p).value
            getUserIdCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_user_id), ctypes.c_void_p).value
            addUserEmailCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.set_user_email), ctypes.c_void_p).value
            getUserEmailCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_user_email), ctypes.c_void_p).value
            addCertificateChainCp = ctypes.cast(SecureStorage.py_set_data_type(SecureStorage.add_certificate_chain), ctypes.c_void_p).value
            getCertificateChainCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.get_certificate_chain), ctypes.c_void_p).value
            clearCp = ctypes.cast(SecureStorage.py_get_data_type(SecureStorage.clear), ctypes.c_void_p).value

            self.secureStorage = _doordeck_headless_sdk.createMingwSecureStorage(
                setApiEnvironmentCp, getApiEnvironmentCp,
                addCloudAuthTokenCp, getCloudAuthTokenCp,
                addCloudRefreshTokenCp, getCloudRefreshTokenCp,
                setFusionHostCp, getFusionHostCp,
                addFusionAuthTokenCp, getFusionAuthTokenCp,
                addPublicKeyCp, getPublicKeyCp,
                addPrivateKeyCp, getPrivateKeyCp,
                setKeyPairVerifiedCp, getKeyPairVerifiedCp,
                addUserIdCp, getUserIdCp,
                addUserEmailCp, getUserEmailCp,
                addCertificateChainCp, getCertificateChainCp,
                clearCp
            )

        self.sdkApiEnvironment = _doordeck_headless_sdk.getApiEnvironmentByName(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_model_data_ApiEnvironment(), api_environment)
        self.debugLogging = 'False' if debug_logging is None else str(debug_logging)
        self.sdkConfig = _doordeck_headless_sdk.buildSdkConfig(self.sdkApiEnvironment, cloud_auth_token, cloud_refresh_token, fusion_host, self.secureStorage, self.debugLogging)
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

// Define the callback types.
typedef void (*callback_t)(const char *);
typedef void (*callback_send)(const char *);
typedef char* (*callback_get)(void);
typedef void (*callback_empty)(void);
%}

// Include standard typemaps.
%include "typemaps.i"

// Use SWIG’s callback typemap to convert a Python callable (provided as an integer)
// to a C function pointer. Here, the Python side must supply the address of a ctypes-wrapped function.
%typemap(in) callback_t {
    $1 = (callback_t)PyLong_AsVoidPtr($input);
}
%typemap(in) callback_send {
    $1 = (callback_send) PyLong_AsVoidPtr($input);
}
%typemap(in) callback_get {
    $1 = (callback_get) PyLong_AsVoidPtr($input);
}
%typemap(in) callback_empty {
    $1 = (callback_empty) PyLong_AsVoidPtr($input);
}

%apply callback_t { void* callback };
%apply callback_send { void* setApiEnvironmentCp, void* addCloudAuthTokenCp, void* addCloudRefreshTokenCp, void* setFusionHostCp, void* addFusionAuthTokenCp, void* addPublicKeyCp, void* addPrivateKeyCp, void* setKeyPairVerifiedCp, void* addUserIdCp, void* addUserEmailCp, void* addCertificateChainCp };
%apply callback_get { void* getApiEnvironmentCp, void* getCloudAuthTokenCp, void* getCloudRefreshTokenCp, void* getFusionHostCp,  void* getFusionAuthTokenCp, void* getPublicKeyCp, void* getPrivateKeyCp, void* getKeyPairVerifiedCp, void* getUserIdCp, void* getUserEmailCp, void* getCertificateChainCp };
%apply callback_empty { void* clearCp };

%include "../releaseShared/Doordeck.Headless.Sdk_api.h"