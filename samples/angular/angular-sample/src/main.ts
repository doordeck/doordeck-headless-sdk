import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import doordeck from '@doordeck/doordeck-headless-sdk';

const apiEnvironment =  doordeck.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment;
export const doordeckSDK = doordeck.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize(apiEnvironment.DEV);

// Utils
export const doordeckUtil = doordeck.com.doordeck.multiplatform.sdk.util.Utils;

// Resources
export const sitesResource = doordeck.com.doordeck.multiplatform.sdk.api.sites();
export const accountResource = doordeck.com.doordeck.multiplatform.sdk.api.account();
export const accountlessResource = doordeck.com.doordeck.multiplatform.sdk.api.accountless();
export const lockOperationResource = doordeck.com.doordeck.multiplatform.sdk.api.lockOperations();
export const doordeckCrypto = doordeck.com.doordeck.multiplatform.sdk.crypto.crypto();

bootstrapApplication(AppComponent, appConfig)
  .catch((err) => console.error(err));
