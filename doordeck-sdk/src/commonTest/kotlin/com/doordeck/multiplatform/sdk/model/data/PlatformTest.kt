package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.randomCreateApplication
import com.doordeck.multiplatform.sdk.randomEcKey
import com.doordeck.multiplatform.sdk.randomEd25519Key
import com.doordeck.multiplatform.sdk.randomEmailCallToAction
import com.doordeck.multiplatform.sdk.randomEmailPreferences
import com.doordeck.multiplatform.sdk.randomRsaKey
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PlatformTest {

    @Test
    fun shouldBuildCreateApplication() = runTest {
        // Given
        val createApplication = randomCreateApplication()

        // When
        val result = BasicPlatformOperations.BasicCreateApplication.Builder()
            .setName(createApplication.name)
            .setCompanyName(createApplication.companyName)
            .setMailingAddress(createApplication.mailingAddress)
            .setPrivacyPolicy(createApplication.privacyPolicy)
            .setSupportContact(createApplication.supportContact)
            .setAppLink(createApplication.appLink)
            .setEmailPreferences(createApplication.emailPreferences)
            .setLogoUrl(createApplication.logoUrl)
            .build()

        // Then
        assertEquals(createApplication, result)
    }

    @Test
    fun shouldBuildEmailPreferences() = runTest {
        // Given
        val emailPreferences = randomEmailPreferences()

        // When
        val result = BasicPlatformOperations.BasicEmailPreferences.Builder()
            .setSenderEmail(emailPreferences.senderEmail)
            .setSenderName(emailPreferences.senderName)
            .setPrimaryColour(emailPreferences.primaryColour)
            .setSecondaryColour(emailPreferences.secondaryColour)
            .setOnlySendEssentialEmails(emailPreferences.onlySendEssentialEmails)
            .setCallToAction(emailPreferences.callToAction)
            .build()

        // Then
        assertEquals(emailPreferences, result)
    }

    @Test
    fun shouldBuildEmailCallToAction() = runTest {
        // Given
        val emailCallToAction = randomEmailCallToAction()

        // When
        val result = BasicPlatformOperations.BasicEmailCallToAction.Builder()
            .setActionTarget(emailCallToAction.actionTarget)
            .setHeadline(emailCallToAction.headline)
            .setActionText(emailCallToAction.actionText)
            .build()

        // Then
        assertEquals(emailCallToAction, result)
    }

    @Test
    fun shouldBuildRsaKey() = runTest {
        // Given
        val rsaKey = randomRsaKey()

        // When
        val result = BasicPlatformOperations.BasicRsaKey.Builder()
            .setKty(rsaKey.kty)
            .setUse(rsaKey.use)
            .setKid(rsaKey.kid)
            .setAlg(rsaKey.alg)
            .setP(rsaKey.p)
            .setQ(rsaKey.q)
            .setD(rsaKey.d)
            .setE(rsaKey.e)
            .setQi(rsaKey.qi)
            .setDp(rsaKey.dp)
            .setDq(rsaKey.dq)
            .setN(rsaKey.n)
            .build()

        // Then
        assertEquals(rsaKey, result)
    }

    @Test
    fun shouldBuildEcKey() = runTest {
        // Given
        val ecKey = randomEcKey()

        // When
        val result = BasicPlatformOperations.BasicEcKey.Builder()
            .setKty(ecKey.kty)
            .setUse(ecKey.use)
            .setKid(ecKey.kid)
            .setAlg(ecKey.alg)
            .setD(ecKey.d)
            .setCrv(ecKey.crv)
            .setX(ecKey.x)
            .setY(ecKey.y)
            .build()

        // Then
        assertEquals(ecKey, result)
    }

    @Test
    fun shouldBuildEd25519Key() = runTest {
        // Given
        val ed25519Key = randomEd25519Key()

        // When
        val result = BasicPlatformOperations.BasicEd25519Key.Builder()
            .setKty(ed25519Key.kty)
            .setUse(ed25519Key.use)
            .setKid(ed25519Key.kid)
            .setAlg(ed25519Key.alg)
            .setD(ed25519Key.d)
            .setCrv(ed25519Key.crv)
            .setX(ed25519Key.x)
            .build()

        // Then
        assertEquals(ed25519Key, result)
    }
}