import HeadlessReactNativeSdk, {
  type AssistedRegisterEphemeralKeyResponse,
  type TileLocksResponse,
} from './NativeHeadlessReactNativeSdk';

export function login(email: string, password: string): Promise<AssistedRegisterEphemeralKeyResponse> {
  return HeadlessReactNativeSdk.login(
    email,
    password,
  );
}

export function setAuthToken(authToken: string): Promise<AssistedRegisterEphemeralKeyResponse> {
  return HeadlessReactNativeSdk.setAuthToken(authToken);
}

export function verify(code: string): Promise<void> {
  return HeadlessReactNativeSdk.verify(code);
}

export function logout(): Promise<void> {
  return HeadlessReactNativeSdk.logout();
}

export function getLocksBelongingToTile(tileId: string): Promise<TileLocksResponse> {
  return HeadlessReactNativeSdk.getLocksBelongingToTile(tileId);
}

export function unlockDevice(lockId: string): Promise<void> {
  return HeadlessReactNativeSdk.unlockDevice(lockId);
}
