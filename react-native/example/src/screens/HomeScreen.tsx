import React from 'react';
import { Button, ScrollView, View } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import {logout} from "@doordeck/headless-react-native-sdk";

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Home'>;

interface HomeScreenProps {
  navigation: HomeScreenNavigationProp;
}

const HomeScreen: React.FC<HomeScreenProps> = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <ScrollView contentContainerStyle={styles.scrollContainer}>
        <Button title="Login" onPress={() => navigation.navigate('Login')} />
        <Button title="Set auth token" onPress={() => navigation.navigate('SetAuthToken')} />
        <Button title="See user details" onPress={() => navigation.navigate('SeeUserDetails')} />
        <Button title="Verification" onPress={() => navigation.navigate('Verification')} />
        <Button title="Inspect tile" onPress={() => navigation.navigate('InspectTile')} />
        <Button title="Unlock device" onPress={() => navigation.navigate('UnlockDevice')} />
        <Button title="Logout" onPress={() => {logout()}} />
      </ScrollView>
    </View>
  );
};

export default HomeScreen;
