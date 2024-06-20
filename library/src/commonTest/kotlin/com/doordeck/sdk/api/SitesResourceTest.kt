package com.doordeck.sdk.api

import com.doordeck.sdk.KDoordeckFactory
import com.doordeck.sdk.api.model.ApiEnvironment
import kotlin.test.Test

class SitesResourceTest {

    private val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, "")

    @Test
    fun shouldListSites() {
        sdk.sites().listSites()
    }

    @Test
    fun shouldGetLocksForSite() {
        sdk.sites().getLocksForSite(sdk.sites().listSites().random().id)
    }

    @Test
    fun shouldGetUsersForSite() {
        sdk.sites().getUsersForSite(sdk.sites().listSites().random().id)
    }
}