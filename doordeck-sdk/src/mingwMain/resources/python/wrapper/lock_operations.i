%pythoncode %{

def get_single_lock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getSingleLockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return LockResponse(**get_success_result(response))

def get_lock_audit_trail(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLockAuditTrailJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [AuditResponse(**item) for item in get_success_result(response)]

def get_audit_for_user(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getAuditForUserJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [AuditResponse(**item) for item in get_success_result(response)]

def get_users_for_lock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUsersForLockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [UserLockResponse(**item) for item in get_success_result(response)]

def get_locks_for_user(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLocksForUserJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserLockResponse(**get_success_result(response))

def update_lock_name(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockNameJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_lock_favourite(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockFavouriteJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_lock_colour(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockColourJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_lock_setting_default_name(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockSettingDefaultNameJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def set_lock_setting_permitted_addresses(thiz, data):
    response = json.loads(_doordeck_headless_sdk.setLockSettingPermittedAddressesJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_lock_setting_hidden(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockSettingHiddenJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def set_lock_setting_time_restrictions(thiz, data):
    response = json.loads(_doordeck_headless_sdk.setLockSettingTimeRestrictionsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_lock_setting_location_restrictions(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateLockSettingLocationRestrictionsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def get_user_public_key(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_email(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByEmailJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_telephone(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByTelephoneJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_local_key(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByLocalKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_foreign_key(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByForeignKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_identity(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByIdentityJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_emails(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByEmailsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return BatchUserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_telephones(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByTelephonesJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return BatchUserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_local_keys(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByLocalKeysJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return BatchUserPublicKeyResponse(**get_success_result(response))

def get_user_public_key_by_foreign_keys(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserPublicKeyByForeignKeysJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return BatchUserPublicKeyResponse(**get_success_result(response))

def unlock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.unlockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def share_lock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.shareLockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def batch_share_lock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.batchShareLockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def revoke_access_to_lock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.revokeAccessToLockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_secure_setting_unlock_duration(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateSecureSettingUnlockDurationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_secure_setting_unlock_between(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateSecureSettingUnlockBetweenJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def get_pinned_locks(thiz):
    response = json.loads(_doordeck_headless_sdk.getPinnedLocksJson(thiz))
    handle_exception(response)
    return [LockResponse(**item) for item in get_success_result(response)]

def get_shareable_locks(thiz):
    response = json.loads(_doordeck_headless_sdk.getShareableLocksJson(thiz))
    handle_exception(response)
    return [ShareableLockResponse(**item) for item in get_success_result(response)]

%}