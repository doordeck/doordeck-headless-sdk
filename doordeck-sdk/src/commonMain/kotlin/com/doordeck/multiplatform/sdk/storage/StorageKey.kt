package com.doordeck.multiplatform.sdk.storage

/**
 * Keys used to persist values in the secure storage.
 *
 * The constant [name] is the key stored on the device (e.g. in the Keychain or the
 * EncryptedSharedPreferences), so these names are part of the persisted data format:
 * **do not rename or remove any constant**. Doing so would make existing installations lose the
 * stored value (e.g. logging out the user or losing their key pair). If a key has to change,
 * add a new constant and a [com.doordeck.multiplatform.sdk.storage.migrations.StorageMigration]
 * that moves the value from the old key (referenced by its literal string) to the new one.
 */
internal enum class StorageKey {
    API_ENVIRONMENT_KEY,
    CLOUD_AUTH_TOKEN_KEY,
    CLOUD_REFRESH_TOKEN_KEY,
    FUSION_HOST_KEY,
    FUSION_AUTH_TOKEN_KEY,
    PUBLIC_KEY_KEY,
    PRIVATE_KEY_KEY,
    VERIFIED_KEY_PAIR_KEY,
    USER_ID_KEY,
    USER_EMAIL_KEY,
    CERTIFICATE_CHAIN_KEY,
    STORAGE_VERSION_KEY
}