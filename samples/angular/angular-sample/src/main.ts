import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {com} from '@doordeck/doordeck-headless-sdk';
import SdkConfig = com.doordeck.multiplatform.sdk.config.SdkConfig;
import ApiEnvironment = com.doordeck.multiplatform.sdk.model.data.ApiEnvironment;
import KDoordeckFactory = com.doordeck.multiplatform.sdk.KDoordeckFactory;

export const doordeckSDK = KDoordeckFactory.initialize(
  new SdkConfig.Builder().setApiEnvironment(ApiEnvironment.DEV).build()
);

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
