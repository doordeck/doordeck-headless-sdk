"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

from typing import List, Optional
import dataclasses
import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook
from .models import BatchShareLockOperation, LocationRequirement, RevokeAccessToLockOperation, ShareLockOperation, TimeRequirement, UnlockOperation, UpdateSecureSettingUnlockBetween, UpdateSecureSettingUnlockDuration


class LockOperations:

    async def get_single_lock(self, lockId: str):
        data = { "lockId": lockId }
        return await call_async("lockOperations.getSingleLock", data)

    async def get_lock_audit_trail(self, lockId: str, start: typing.Optional[int] = None, end: typing.Optional[int] = None):
        data = {
            "lockId": lockId,
            "start": start,
            "end": end
        }
        return await call_async("lockOperations.getLockAuditTrail", data)

    async def get_audit_for_user(self, lockId: str, start: typing.Optional[int] = None, end: typing.Optional[int] = None):
        data = {
            "lockId": lockId,
            "start": start,
            "end": end
        }
        return await call_async("lockOperations.getAuditForUser", data)

    async def get_users_for_lock(self, userId: str):
        data = { "userId": userId }
        return await call_async("lockOperations.getUsersForLock", data)

    async def get_locks_for_user(self, userId: str):
        data = { "userId": userId }
        return await call_async("lockOperations.getLocksForUser", data)

    async def update_lock_name(self, lockId: str, name: typing.Optional[str] = None):
        data = {
            "lockId": lockId,
            "name": name
        }
        return await call_async("lockOperations.updateLockName", data)

    async def update_lock_favourite(self, lockId: str, favourite: bool):
        data = {
            "lockId": lockId,
            "favourite": favourite
        }
        return await call_async("lockOperations.updateLockFavourite", data)

    async def update_lock_setting_default_name(self, lockId: str, name: str):
        data = {
            "lockId": lockId,
            "name": name
        }
        return await call_async("lockOperations.updateLockSettingDefaultName", data)

    async def set_lock_setting_permitted_addresses(self, lockId: str, permittedAddresses: List[str]):
        data = {
            "lockId": lockId,
            "permittedAddresses": permittedAddresses
        }
        return await call_async("lockOperations.setLockSettingPermittedAddresses", data)

    async def update_lock_setting_hidden(self, lockId: str, hidden: bool):
        data = {
            "lockId": lockId,
            "hidden": hidden
        }
        return await call_async("lockOperations.updateLockSettingHidden", data)

    async def set_lock_setting_time_restrictions(self, lockId: str, times: List[TimeRequirement]):
        data = {
            "lockId": lockId,
            "times": [dataclasses.asdict(t) for t in times]
        }
        return await call_async("lockOperations.setLockSettingTimeRestrictions", data)

    async def update_lock_setting_location_restrictions(self, lockId: str, location: typing.Optional[LocationRequirement] = None):
        data = {
            "lockId": lockId,
            **({"location": dataclasses.asdict(location)} if location is not None else {})
        }
        return await call_async("lockOperations.updateLockSettingLocationRestrictions", data)

    async def get_user_public_key(self, userEmail: str, visitor: bool = False):
        data = {
            "userEmail": userEmail,
            "visitor": visitor
        }
        return await call_async("lockOperations.getUserPublicKey", data)

    async def get_user_public_key_by_email(self, email: str):
        data = { "email": email }
        return await call_async("lockOperations.getUserPublicKeyByEmail", data)

    async def get_user_public_key_by_telephone(self, telephone: str):
        data = { "telephone": telephone }
        return await call_async("lockOperations.getUserPublicKeyByTelephone", data)

    async def get_user_public_key_by_local_key(self, localKey: str):
        data = { "localKey": localKey }
        return await call_async("lockOperations.getUserPublicKeyByLocalKey", data)

    async def get_user_public_key_by_foreign_key(self, foreignKey: str):
        data = { "foreignKey": foreignKey }
        return await call_async("lockOperations.getUserPublicKeyByForeignKey", data)

    async def get_user_public_key_by_identity(self, identity: str):
        data = { "identity": identity }
        return await call_async("lockOperations.getUserPublicKeyByIdentity", data)

    async def get_user_public_key_by_emails(self, emails: List[str]):
        data = { "emails": emails }
        return await call_async("lockOperations.getUserPublicKeyByEmails", data)

    async def get_user_public_key_by_telephones(self, telephones: List[str]):
        data = { "telephones": telephones }
        return await call_async("lockOperations.getUserPublicKeyByTelephones", data)

    async def get_user_public_key_by_local_keys(self, localKeys: List[str]):
        data = { "localKeys": localKeys }
        return await call_async("lockOperations.getUserPublicKeyByLocalKeys", data)

    async def get_user_public_key_by_foreign_keys(self, foreignKeys: List[str]):
        data = { "foreignKeys": foreignKeys }
        return await call_async("lockOperations.getUserPublicKeyByForeignKeys", data)

    async def unlock(self, data: UnlockOperation):
        return await call_async("lockOperations.unlock", json.dumps(dataclasses.asdict(data)))

    async def share_lock(self, data: ShareLockOperation):
        return await call_async("lockOperations.shareLock", json.dumps(dataclasses.asdict(data)))

    async def batch_share_lock(self, data: BatchShareLockOperation):
        return await call_async("lockOperations.batchShareLock", json.dumps(dataclasses.asdict(data)))

    async def revoke_access_to_lock(self, data: RevokeAccessToLockOperation):
        return await call_async("lockOperations.revokeAccessToLock", json.dumps(dataclasses.asdict(data)))

    async def update_secure_setting_unlock_duration(self, data: UpdateSecureSettingUnlockDuration):
        return await call_async("lockOperations.updateSecureSettingUnlockDuration", json.dumps(dataclasses.asdict(data)))

    async def update_secure_setting_unlock_between(self, data: UpdateSecureSettingUnlockBetween):
        return await call_async("lockOperations.updateSecureSettingUnlockBetween", json.dumps(dataclasses.asdict(data)))

    async def get_pinned_locks(self):
        return await call_async("lockOperations.getPinnedLocks")

    async def get_shareable_locks(self):
        return await call_async("lockOperations.getShareableLocks")
