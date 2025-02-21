import React, { useState } from 'react';
import { Text, TextInput, View, Button } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { setAuthToken } from '@doordeck/headless-react-native-sdk';
import type { AssistedRegisterEphemeralKeyResponse } from '../../../src/NativeHeadlessReactNativeSdk';

type SetAuthTokenScreenNavigationProp = StackNavigationProp<RootStackParamList, 'SetAuthToken'>;

interface SetAuthTokenScreenProps {
  navigation: SetAuthTokenScreenNavigationProp;
}

const SetAuthTokenScreen: React.FC<SetAuthTokenScreenProps> = (_) => {
  const [authToken, setAuthTokenValue] = useState('');
  const [needsVerification, setNeedsVerification] = useState<AssistedRegisterEphemeralKeyResponse>();
  const [error, setError] = useState();

  const handleSetAuthToken = async () => {
    setError(undefined);
    setNeedsVerification(undefined);

    setAuthToken(authToken)
      .then(setNeedsVerification)
      .catch(setError);
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Auth Token"
        value={authToken}
        onChangeText={setAuthTokenValue}
        placeholderTextColor={'grey'}
        autoCapitalize="none"
      />
      <Button title="Submit" onPress={handleSetAuthToken} />
      {needsVerification && (
        <Text style={styles.resultNeedsVerificationOrError}>
          Needs verification/Code sent to email: {String(needsVerification.requiresVerification)}
        </Text>
      )}
      {error && (
        <Text style={styles.resultNeedsVerificationOrError}>Error: {String(error)}</Text>
      )}
    </View>
  );
};

export default SetAuthTokenScreen;
