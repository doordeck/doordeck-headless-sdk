package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.api.responses.IntegrationConfigurationResponse
import com.doordeck.multiplatform.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserDetailsResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteLocksResponse
import com.doordeck.multiplatform.sdk.api.responses.SiteResponse
import com.doordeck.multiplatform.sdk.api.responses.TimeRequirementResponse
import com.doordeck.multiplatform.sdk.api.responses.UserAuditResponse
import com.doordeck.multiplatform.sdk.api.responses.UserForSiteResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse

fun Array<SiteResponse>.toSiteResponseList(): List<SiteResponse> = toList()
fun Array<SiteLocksResponse>.toSiteLocksResponseList(): List<SiteLocksResponse> = toList()
fun Array<UserForSiteResponse>.toUserForSiteResponseList(): List<UserForSiteResponse> = toList()
fun Array<ApplicationResponse>.toApplicationResponseList(): List<ApplicationResponse> = toList()
fun Array<ApplicationOwnerDetailsResponse>.toApplicationOwnerDetailsResponseList(): List<ApplicationOwnerDetailsResponse> = toList()
fun Array<IntegrationConfigurationResponse>.toIntegrationConfigurationResponseList(): List<IntegrationConfigurationResponse> = toList()
fun Array<LockResponse>.toLockResponseList(): List<LockResponse> = toList()
fun Array<ShareableLockResponse>.toShareableLockResponseList(): List<ShareableLockResponse> = toList()
fun Array<LockAuditTrailResponse>.toLockAuditTrailResponseList(): List<LockAuditTrailResponse> = toList()
fun Array<UserAuditResponse>.toUserAuditResponseList(): List<UserAuditResponse> = toList()
fun Array<UserLockResponse>.toUserLockResponseList(): List<UserLockResponse> = toList()
fun Array<TimeRequirementResponse>.toTimeRequirementResponseList(): List<TimeRequirementResponse> = toList()
fun Array<LockUserDetailsResponse>.toLockUserDetailsResponseList(): List<LockUserDetailsResponse> = toList()
fun Array<String>.toStringList(): List<String> = toList()
fun List<String>.toArrayList(): Array<String> = toTypedArray()
fun List<LockOperations.TimeRequirement>.toTimeRequirementArray(): Array<LockOperations.TimeRequirement> = toTypedArray()