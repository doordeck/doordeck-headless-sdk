%pythoncode %{

class LockOperations(object):

    def __init__(self, resource):
        self.resource = resource

    def get_single_lock(self, data):
        response = json.loads(_doordeck_headless_sdk.getSingleLockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return LockResponse(**get_success_result(response))

    def get_lock_audit_trail(self, data):
        response = json.loads(_doordeck_headless_sdk.getLockAuditTrailJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [AuditResponse(**item) for item in get_success_result(response)]

    def get_audit_for_user(self, data):
        response = json.loads(_doordeck_headless_sdk.getAuditForUserJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [AuditResponse(**item) for item in get_success_result(response)]

    def get_users_for_lock(self, data):
        response = json.loads(_doordeck_headless_sdk.getUsersForLockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [UserLockResponse(**item) for item in get_success_result(response)]

    def get_locks_for_user(self, data):
        response = json.loads(_doordeck_headless_sdk.getLocksForUserJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserLockResponse(**get_success_result(response))

    def update_lock_name(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockNameJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_lock_favourite(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockFavouriteJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_lock_colour(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockColourJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_lock_setting_default_name(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockSettingDefaultNameJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def set_lock_setting_permitted_addresses(self, data):
        response = json.loads(_doordeck_headless_sdk.setLockSettingPermittedAddressesJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_lock_setting_hidden(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockSettingHiddenJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def set_lock_setting_time_restrictions(self, data):
        response = json.loads(_doordeck_headless_sdk.setLockSettingTimeRestrictionsJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_lock_setting_location_restrictions(self, data):
        response = json.loads(_doordeck_headless_sdk.updateLockSettingLocationRestrictionsJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def get_user_public_key(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_email(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByEmailJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_telephone(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByTelephoneJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_local_key(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByLocalKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_foreign_key(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByForeignKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_identity(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByIdentityJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return UserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_emails(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByEmailsJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return BatchUserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_telephones(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByTelephonesJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return BatchUserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_local_keys(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByLocalKeysJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return BatchUserPublicKeyResponse(**get_success_result(response))

    def get_user_public_key_by_foreign_keys(self, data):
        response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByForeignKeysJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return BatchUserPublicKeyResponse(**get_success_result(response))

    def unlock(self, data):
        response = json.loads(_doordeck_headless_sdk.unlockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def share_lock(self, data):
        response = json.loads(_doordeck_headless_sdk.shareLockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def batch_share_lock(self, data):
        response = json.loads(_doordeck_headless_sdk.batchShareLockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def revoke_access_to_lock(self, data):
        response = json.loads(_doordeck_headless_sdk.revokeAccessToLockJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_secure_setting_unlock_duration(self, data):
        response = json.loads(_doordeck_headless_sdk.updateSecureSettingUnlockDurationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_secure_setting_unlock_between(self, data):
        response = json.loads(_doordeck_headless_sdk.updateSecureSettingUnlockBetweenJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def get_pinned_locks(self):
        response = json.loads(_doordeck_headless_sdk.getPinnedLocksJson(self.resource))
        handle_exception(response)
        return [LockResponse(**item) for item in get_success_result(response)]

    def get_shareable_locks(self):
        response = json.loads(_doordeck_headless_sdk.getShareableLocksJson(self.resource))
        handle_exception(response)
        return [ShareableLockResponse(**item) for item in get_success_result(response)]

%}