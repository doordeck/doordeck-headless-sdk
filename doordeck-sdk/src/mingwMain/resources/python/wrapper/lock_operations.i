%pythoncode %{
class LockOperations(object):

    def __init__(self, resource):
        self.resource = resource

    async def get_single_lock(self, lockId: str):
        data = { "lockId": lockId }
        return await execute_async(
            _doordeck_headless_sdk.getSingleLock,
            [self.resource, json.dumps(data)]
        )

    async def get_lock_audit_trail(self, lockId: str, start: int, end: int):
        data = {
            "lockId": lockId,
            "start": start,
            "end": end
        }
        return await execute_async(
            _doordeck_headless_sdk.getLockAuditTrail,
            [self.resource, json.dumps(data)]
        )

    async def get_audit_for_user(self, lockId: str, start: int, end: int):
        data = {
            "lockId": lockId,
            "start": start,
            "end": end
        }
        return await execute_async(
            _doordeck_headless_sdk.getAuditForUser,
            [self.resource, json.dumps(data)]
        )

    async def get_users_for_lock(self, userId: str):
        data = { "userId": userId }
        return await execute_async(
            _doordeck_headless_sdk.getUsersForLock,
            [self.resource, json.dumps(data)]
        )

    async def get_locks_for_user(self, userId: str):
        data = { "userId": userId }
        return await execute_async(
            _doordeck_headless_sdk.getLocksForUser,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_name(self, lockId: str, name: typing.Optional[str] = None):
        data = {
            "lockId": lockId,
            "name": name
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockName,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_favourite(self, lockId: str, favourite: typing.Optional[bool] = None):
        data = {
            "lockId": lockId,
            "favourite": favourite
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockFavourite,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_colour(self, lockId: str, colour: typing.Optional[str] = None):
        data = {
            "lockId": lockId,
            "colour": colour
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockColour,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_setting_default_name(self, lockId: str, name: typing.Optional[str] = None):
        data = {
            "lockId": lockId,
            "name": name
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingDefaultName,
            [self.resource, json.dumps(data)]
        )

    async def set_lock_setting_permitted_addresses(self, lockId: str, permittedAddresses: List[str]):
        data = {
            "lockId": lockId,
            "permittedAddresses": permittedAddresses
        }
        return await execute_async(
            _doordeck_headless_sdk.setLockSettingPermittedAddresses,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_setting_hidden(self, lockId: str, hidden: bool):
        data = {
            "lockId": lockId,
            "hidden": hidden
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingHidden,
            [self.resource, json.dumps(data)]
        )

    async def set_lock_setting_time_restrictions(self, lockId: str, times: List[TimeRequirement]):
        data = {
            "lockId": lockId,
            "times": dataclasses.asdict(times)
        }
        return await execute_async(
            _doordeck_headless_sdk.setLockSettingTimeRestrictions,
            [self.resource, json.dumps(data)]
        )

    async def update_lock_setting_location_restrictions(self, lockId: str, location: typing.Optional[LocationRequirement] = None):
        data = {
            "lockId": lockId,
            "location": dataclasses.asdict(location)
        }
        return await execute_async(
            _doordeck_headless_sdk.updateLockSettingLocationRestrictions,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key(self, userEmail: str, visitor: bool = False):
        data = {
            "userEmail": userEmail,
            "visitor": visitor
        }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKey,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_email(self, email: str):
        data = { "email": email }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByEmail,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_telephone(self, telephone: str):
        data = { "telephone": telephone }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByTelephone,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_local_key(self, localKey: str):
        data = { "localKey": localKey }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByLocalKey,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_foreign_key(self, foreignKey: str):
        data = { "foreignKey": foreignKey }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByForeignKey,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_identity(self, identity: str):
        data = { "identity": identity }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByIdentity,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_emails(self, emails: List[str]):
        data = { "emails": emails }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByEmails,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_telephones(self, telephones: List[str]):
        data = { "telephones": telephones }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByTelephones,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_local_keys(self, localKeys: List[str]):
        data = { "localKeys": localKeys }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByLocalKeys,
            [self.resource, json.dumps(data)]
        )

    async def get_user_public_key_by_foreign_keys(self, foreignKeys: List[str]):
        data = { "foreignKeys": foreignKeys }
        return await execute_async(
            _doordeck_headless_sdk.getUserPublicKeyByForeignKeys,
            [self.resource, json.dumps(data)]
        )

    async def unlock(self, data: UnlockOperation):
        return await execute_async(
            _doordeck_headless_sdk.unlock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def share_lock(self, data: ShareLockOperation):
        return await execute_async(
            _doordeck_headless_sdk.shareLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def batch_share_lock(self, data: BatchShareLockOperation):
        return await execute_async(
            _doordeck_headless_sdk.batchShareLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def revoke_access_to_lock(self, data: RevokeAccessToLockOperation):
        return await execute_async(
            _doordeck_headless_sdk.revokeAccessToLock,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_secure_setting_unlock_duration(self, data: UpdateSecureSettingUnlockDuration):
        return await execute_async(
            _doordeck_headless_sdk.updateSecureSettingUnlockDuration,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_secure_setting_unlock_between(self, data: UpdateSecureSettingUnlockBetween):
        return await execute_async(
            _doordeck_headless_sdk.updateSecureSettingUnlockBetween,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_pinned_locks(self):
        return await execute_async(
            _doordeck_headless_sdk.getPinnedLocks,
            [self.resource]
        )

    async def get_shareable_locks(self):
        return await execute_async(
            _doordeck_headless_sdk.getShareableLocks,
            [self.resource]
        )
%}