"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class Platform:

    async def create_application(self, data: CreateApplication):
        return await call_async("platform.createApplication", json.dumps(dataclasses.asdict(data)))

    async def list_applications(self):
        return await call_async("platform.listApplications")

    async def get_application(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await call_async("platform.getApplication", data)

    async def update_application_name(self, applicationId: str, name: str):
        data = {
            "applicationId": applicationId,
            "name": name
        }
        return await call_async("platform.updateApplicationName", data)

    async def update_application_company_name(self, applicationId: str, companyName: str):
        data = {
            "applicationId": applicationId,
            "companyName": companyName
        }
        return await call_async("platform.updateApplicationCompanyName", data)

    async def update_application_mailing_address(self, applicationId: str, mailingAddress: str):
        data = {
            "applicationId": applicationId,
            "mailingAddress": mailingAddress
        }
        return await call_async("platform.updateApplicationMailingAddress", data)

    async def update_application_privacy_policy(self, applicationId: str, privacyPolicy: str):
        data = {
            "applicationId": applicationId,
            "privacyPolicy": privacyPolicy
        }
        return await call_async("platform.updateApplicationPrivacyPolicy", data)

    async def update_application_support_contact(self, applicationId: str, supportContact: str):
        data = {
            "applicationId": applicationId,
            "supportContact": supportContact
        }
        return await call_async("platform.updateApplicationSupportContact", data)

    async def update_application_app_link(self, applicationId: str, appLink: str):
        data = {
            "applicationId": applicationId,
            "appLink": appLink
        }
        return await call_async("platform.updateApplicationAppLink", data)

    async def update_application_email_preferences(self, applicationId: str, emailPreferences: EmailPreferences):
        data = {
            "applicationId": applicationId,
            "emailPreferences": dataclasses.asdict(emailPreferences)
        }
        return await call_async("platform.updateApplicationEmailPreferences", data)

    async def update_application_logo_url(self, applicationId: str, logoUrl: str):
        data = {
            "applicationId": applicationId,
            "logoUrl": logoUrl
        }
        return await call_async("platform.updateApplicationLogoUrl", data)

    async def delete_application(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await call_async("platform.deleteApplication", data)

    async def get_logo_upload_url(self, applicationId: str, contentType: str):
        data = {
            "applicationId": applicationId,
            "contentType": contentType
        }
        return await call_async("platform.getLogoUploadUrl", data)

    async def add_auth_key(self, applicationId: str, key: AuthKey):
        data = {
            "applicationId": applicationId,
            "key": dataclasses.asdict(key)
        }
        return await call_async("platform.addAuthKey", data)

    async def add_auth_issuer(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await call_async("platform.addAuthIssuer", data)

    async def delete_auth_issuer(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await call_async("platform.deleteAuthIssuer", data)

    async def add_cors_domain(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await call_async("platform.addCorsDomain", data)

    async def remove_cors_domain(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await call_async("platform.removeCorsDomain", data)

    async def add_application_owner(self, applicationId: str, userId: str):
        data = {
            "applicationId": applicationId,
            "userId": userId
        }
        return await call_async("platform.addApplicationOwner", data)

    async def remove_application_owner(self, applicationId: str, userId: str):
        data = {
            "applicationId": applicationId,
            "userId": userId
        }
        return await call_async("platform.removeApplicationOwner", data)

    async def get_application_owners_details(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await call_async("platform.getApplicationOwnersDetails", data)

    async def get_application_users(self, applicationId: str, pageSize: int = 100, lastUserRetrieved: typing.Optional[str] = None):
        data = {
            "applicationId": applicationId,
            "pageSize": pageSize,
            "lastUserRetrieved": lastUserRetrieved,
        }
        return await call_async("platform.getApplicationUsers", data)
