import React, { useState } from 'react';
import { Text, TextInput, View, Button } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { unlockDevice } from '@doordeck/headless-react-native-sdk';

type UnlockDeviceScreenNavigationProp = StackNavigationProp<RootStackParamList, 'UnlockDevice'>;

interface UnlockDeviceScreenProps {
  navigation: UnlockDeviceScreenNavigationProp;
}

const UnlockDeviceScreen: React.FC<UnlockDeviceScreenProps> = () => {
  const [uuid, setUuid] = useState('');
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleUnlockDevice = async () => {
    setError(null);
    setSuccess(false);

    unlockDevice(uuid)
      .then(() => setSuccess(true))
      .catch((err) => setError(String(err)));
  };

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Enter Device UUID"
        value={uuid}
        onChangeText={setUuid}
        placeholderTextColor={'grey'}
        autoCapitalize="none"
      />
      <Button title="Unlock Device" onPress={handleUnlockDevice} />

      {success && <Text style={styles.resultNeedsVerificationOrError}>Unlocked!</Text>}
      {error && <Text style={styles.resultNeedsVerificationOrError}>Error: {error}</Text>}
    </View>
  );
};

export default UnlockDeviceScreen;
