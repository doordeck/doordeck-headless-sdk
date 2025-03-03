package com.doordeck.headlessreactnativesdk

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.KDoordeckFactory
import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider
import java.util.HashMap

class HeadlessReactNativeSdkPackage : BaseReactPackage() {
  override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? {
    return if (name == HeadlessReactNativeSdkModule.NAME) {
      HeadlessReactNativeSdkModule(
        reactContext = reactContext,
        doordeckSdk = KDoordeckFactory.initialize(
          applicationContext = ApplicationContext.apply { set(reactContext.applicationContext)},
        ).also { it.contextManager().loadContext() }
      )
    } else {
      null
    }
  }

  override fun getReactModuleInfoProvider(): ReactModuleInfoProvider {
    return ReactModuleInfoProvider {
      val moduleInfos: MutableMap<String, ReactModuleInfo> = HashMap()
      moduleInfos[HeadlessReactNativeSdkModule.NAME] = ReactModuleInfo(
        HeadlessReactNativeSdkModule.NAME,
        HeadlessReactNativeSdkModule.NAME,
        false,  // canOverrideExistingModule
        false,  // needsEagerInit
        false,  // isCxxModule
        true // isTurboModule
      )
      moduleInfos
    }
  }
}
