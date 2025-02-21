import React, { useState } from 'react';
import { Text, TextInput, View, Button } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { verify } from '@doordeck/headless-react-native-sdk';

type VerificationScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Verification'>;

interface VerificationScreenProps {
  navigation: VerificationScreenNavigationProp;
}

const VerificationScreen: React.FC<VerificationScreenProps> = () => {
  const [verificationCode, setVerificationCode] = useState('');
  const [success, setSuccess] = useState<boolean>();
  const [error, setError] = useState();

  const handleVerification = async () => {
    setError(undefined);
    setSuccess(undefined);

    verify(verificationCode)
      .then((_) => { setSuccess(true)})
      .catch(setError);
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Enter Verification Code"
        value={verificationCode}
        onChangeText={setVerificationCode}
        keyboardType="numeric"
        placeholderTextColor={'grey'}
        autoCapitalize="none"
      />
      <Button title="Submit" onPress={handleVerification} />
      {success && (
        <Text style={styles.resultNeedsVerificationOrError}>
          Verification successful!
        </Text>
      )}
      {error && (
        <Text style={styles.resultNeedsVerificationOrError}>Error: {String(error)}</Text>
      )}
    </View>
  );
};

export default VerificationScreen;
