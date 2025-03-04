import React, { useState } from 'react';
import {Text, TextInput, View, Button, FlatList, TouchableOpacity, Clipboard} from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { getLocksBelongingToTile } from '@doordeck/headless-react-native-sdk';

export type TileLocksResponse = {
  siteId: string;
  tileId: string;
  deviceIds: string[];
};

type InspectTileScreenNavigationProp = StackNavigationProp<RootStackParamList, 'InspectTile'>;

interface InspectTileScreenProps {
  navigation: InspectTileScreenNavigationProp;
}

const InspectTileScreen: React.FC<InspectTileScreenProps> = () => {
  const [uuid, setUuid] = useState('');
  const [tileData, setTileData] = useState<TileLocksResponse>();
  const [error, setError] = useState();

  const handleInspectTile = async () => {
    setError(undefined);
    setTileData(undefined);

    getLocksBelongingToTile(uuid)
      .then(setTileData)
      .catch(setError);
  };

  const copyToClipboard = Clipboard.setString;

  return (
    <View style={styles.container}>
      <TextInput
        style={styles.input}
        placeholder="Enter Tile UUID"
        value={uuid}
        onChangeText={setUuid}
        placeholderTextColor={'grey'}
        autoCapitalize="none"
      />
      <Button title="Inspect Tile" onPress={handleInspectTile} />

      {tileData && (
        <>
          <Text style={styles.title}>List of devices</Text>
          <FlatList
            data={tileData?.deviceIds ?? []}
            keyExtractor={(_, index) => index.toString()}
            renderItem={({ item }) => (
              <View style={styles.tileItem}>
                <Text style={styles.deviceIdText}>{item}</Text>
                <TouchableOpacity onPress={() => copyToClipboard(item)} style={styles.copyButton}>
                  <Text style={styles.copyButtonText}>Copy</Text>
                </TouchableOpacity>
              </View>
            )}
          />
        </>
      )}

      {error && <Text style={styles.resultNeedsVerificationOrError}>Error: {String(error)}</Text>}
    </View>
  );
};

export default InspectTileScreen;
