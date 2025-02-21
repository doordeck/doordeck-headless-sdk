import React, { useState } from 'react';
import { Text, TextInput, View, Button } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { login } from '@doordeck/headless-react-native-sdk';
import type { AssistedRegisterEphemeralKeyResponse } from '../../../src/NativeHeadlessReactNativeSdk';

type LoginScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Login'>;

interface LoginScreenProps {
  navigation: LoginScreenNavigationProp;
}

const LoginScreen: React.FC<LoginScreenProps> = (_) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [needsVerification, setNeedsVerification] = useState<AssistedRegisterEphemeralKeyResponse>();
  const [error, setError] = useState();

  const handleLogin = async () => {
    setError(undefined);
    setNeedsVerification(undefined);

    login(email, password)
      .then(setNeedsVerification)
      .catch(setError);
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        placeholderTextColor={'grey'}
        autoCapitalize="none"
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        value={password}
        placeholderTextColor={'grey'}
        onChangeText={setPassword}
        secureTextEntry
      />
      <Button title="Submit" onPress={handleLogin} />
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

export default LoginScreen;
