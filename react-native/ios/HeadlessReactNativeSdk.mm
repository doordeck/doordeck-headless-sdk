#import "HeadlessReactNativeSdk.h"
#import "HeadlessReactNativeSdk-Swift.h"

@implementation HeadlessReactNativeSdk {
  HeadlessReactNativeSdkImpl *moduleSdk;
}

- (instancetype) init {
  self = [super init];
  if (self) {
    moduleSdk = [HeadlessReactNativeSdkImpl new];
    [moduleSdk loadContext];
  }
  
  return self;
}

RCT_EXPORT_MODULE()

- (std::shared_ptr<facebook::react::TurboModule>)getTurboModule:
    (const facebook::react::ObjCTurboModule::InitParams &)params
{
    return std::make_shared<facebook::react::NativeHeadlessReactNativeSdkSpecJSI>(params);
}

- (void)getLocksBelongingToTile:(nonnull NSString *)tileId resolve:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk getLocksBelongingToTile:tileId resolver:resolve rejecter:reject];
}

- (void)login:(nonnull NSString *)email password:(nonnull NSString *)password resolve:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk login:email password:password resolver:resolve rejecter:reject];
}

- (void)logout:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk logout:resolve rejecter:reject];
}

- (void)setAuthToken:(nonnull NSString *)authToken resolve:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk setAuthToken:authToken resolver:resolve rejecter:reject];
}

- (void)unlockDevice:(nonnull NSString *)lockId resolve:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk unlockDevice:lockId resolver:resolve rejecter:reject];
}

- (void)verify:(nonnull NSString *)code resolve:(nonnull RCTPromiseResolveBlock)resolve reject:(nonnull RCTPromiseRejectBlock)reject {
  [moduleSdk verify:code resolver:resolve rejecter:reject];
}

@end

