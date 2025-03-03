# @doordeck/headless-react-native-sdk

## Overview
The `@doordeck/headless-react-native-sdk` is a lightweight React Native SDK built on top of the Doordeck SDK. This SDK provides a minimal interface for authentication and lock management, focusing on user-facing operations. It enables React Native applications to authenticate users, manage authentication tokens, retrieve lock details, and unlock devices via Doordeck's platform.

## Installation
To install the SDK, ensure you have React Native set up in your project, then add the necessary dependencies:

```sh
npm install @doordeck/headless-react-native-sdk
```

or using yarn:

```sh
yarn add @doordeck/headless-react-native-sdk
```

## Usage

### Importing the SDK

Import every single method according to your usage

```javascript
import { login } from '@doordeck/headless-react-native-sdk';
// ...
import { logout } from '@doordeck/headless-react-native-sdk';
```

### Authentication Methods

#### `login(email: string, password: string): Promise<AssistedRegisterEphemeralKeyResponse>`
Logs the user in with their email and password.

> **Note:** If login is performed without verification within the same session, authentication will only persist in runtime memory and will be lost when the app restarts.

```javascript
import { login } from '@doordeck/headless-react-native-sdk';

login("user@example.com", "password123")
  .then(response => console.log("Login successful", response))
  .catch(error => console.error("Login failed", error));
```

#### `setAuthToken(authToken: string): Promise<AssistedRegisterEphemeralKeyResponse>`
Sets an authentication token manually.

> **Note:** Like login, authentication without verification will only persist in runtime memory and will be lost after the app restarts.

```javascript
import { setAuthToken } from '@doordeck/headless-react-native-sdk';

setAuthToken("your-auth-token")
  .then(response => console.log("Token set successfully", response))
  .catch(error => console.error("Error setting token", error));
```

#### `verify(code: string): Promise<void>`
Verifies an ephemeral key registration with a verification code. Upon successful verification, the user ID and authentication context are saved in persistent storage.

```javascript
import { verify } from '@doordeck/headless-react-native-sdk';

verify("123456")
  .then(() => console.log("Verification successful"))
  .catch(error => console.error("Verification failed", error));
```

#### `logout(): Promise<void>`
Logs out the current user and clears the stored authentication context, including removing the persistent user ID.

```javascript
import { logout } from '@doordeck/headless-react-native-sdk';

logout()
  .then(() => console.log("Logged out"))
  .catch(error => console.error("Logout failed", error));
```

### Device and Tile Operations

#### `getLocksBelongingToTile(tileId: string): Promise<TileLocksResponse>`
Retrieves a list of locks associated with a specific tile.

```javascript
import { getLocksBelongingToTile } from '@doordeck/headless-react-native-sdk';

getLocksBelongingToTile("tile-uuid")
  .then(response => console.log("Locks: ", response))
  .catch(error => console.error("Failed to get locks", error));
```

#### `unlockDevice(lockId: string): Promise<void>`
Unlocks a device given its lock ID.

```javascript
import { unlockDevice } from '@doordeck/headless-react-native-sdk';

unlockDevice("lock-uuid")
  .then(() => console.log("Device unlocked"))
  .catch(error => console.error("Unlock failed", error));
```

## Error Handling
Each method returns a `Promise`. If an error occurs, the `catch` block will handle the failure, returning an error object containing an error code and message.

## License
This SDK follows the Doordeck licensing agreement. Ensure compliance with Doordeck's API usage policies before integrating this SDK into your project.

## Support
For additional support, refer to the [Doordeck Developer Documentation](https://developer.doordeck.com/docs/#introduction).

