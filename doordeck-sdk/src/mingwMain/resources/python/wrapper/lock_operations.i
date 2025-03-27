%pythoncode %{
class LockOperations(object):

    def __init__(self, resource):
        self.resource = resource

    async def get_single_lock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getSingleLock,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: LockResponse(**get_success_result(r))
        )

    async def get_lock_audit_trail(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getLockAuditTrail,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [AuditResponse(**item) for item in get_success_result(r)]
        )

    async def get_audit_for_user(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getAuditForUser,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [AuditResponse(**item) for item in get_success_result(r)]
        )

    async def get_users_for_lock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUsersForLock,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [UserLockResponse(**item) for item in get_success_result(r)]
        )

    async def get_locks_for_user(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getLocksForUser,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserLockResponse(**get_success_result(r))
        )

    async def update_lock_name(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockName,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_lock_favourite(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockFavourite,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_lock_colour(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockColour,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_lock_setting_default_name(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingDefaultName,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def set_lock_setting_permitted_addresses(self, data):
        return await execute_async(
            _doordeck_headless_sdk.setLockSettingPermittedAddresses,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_lock_setting_hidden(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingHidden,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def set_lock_setting_time_restrictions(self, data):
        return await execute_async(
            _doordeck_headless_sdk.setLockSettingTimeRestrictions,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_lock_setting_location_restrictions(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingLocationRestrictions,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_user_public_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKey,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_email(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByEmail,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_telephone(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByTelephone,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_local_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByLocalKey,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_foreign_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByForeignKey,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_identity(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByIdentity,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_emails(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByEmails,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: UserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_telephones(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByTelephones,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: BatchUserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_local_keys(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByLocalKeys,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: BatchUserPublicKeyResponse(**get_success_result(r))
        )

    async def get_user_public_key_by_foreign_keys(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByForeignKeys,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: BatchUserPublicKeyResponse(**get_success_result(r))
        )

    async def unlock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.unlock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def share_lock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.shareLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def batch_share_lock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.batchShareLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def revoke_access_to_lock(self, data):
        return await execute_async(
            _doordeck_headless_sdk.revokeAccessToLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_secure_setting_unlock_duration(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateSecureSettingUnlockDuration,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_secure_setting_unlock_between(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateSecureSettingUnlockBetween,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_pinned_locks(self):
        return await execute_async(
            _doordeck_headless_sdk.getPinnedLocks,
            [self.resource],
            lambda r: [LockResponse(**item) for item in get_success_result(r)]
        )

    async def get_shareable_locks(self):
        return await execute_async(
            _doordeck_headless_sdk.getShareableLocks,
            [self.resource],
            lambda r: [ShareableLockResponse(**item) for item in get_success_result(r)]
        )
%}