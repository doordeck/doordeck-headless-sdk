import { createStackNavigator } from '@react-navigation/stack';
import { NavigationContainer } from '@react-navigation/native';
import HomeScreen from '../screens/HomeScreen';
import LoginScreen from '../screens/LoginScreen';
import {Text} from "react-native";
import styles from "../styles/styles";
import SetAuthTokenScreen from "../screens/SetAuthTokenScreen";
import VerificationScreen from "../screens/VerificationScreen";
import InspectTileScreen from "../screens/InspectTileScreen";
import UnlockDeviceScreen from "../screens/UnlockDeviceScreen";
import UserDetailsScreen from "../screens/UserDetailsScreen";

export type RootStackParamList = {
  Home: undefined;
  Login: undefined;
  SetAuthToken: undefined;
  SeeUserDetails: undefined;
  Verification: undefined;
  InspectTile: undefined;
  UnlockDevice: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const AppNavigator = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator
        screenOptions={{
          headerTitleAlign: 'center',
        }}
      >
        <Stack.Screen name="Home" component={HomeScreen} options={{ title: 'Home' }} />
        <Stack.Screen
          name="Login"
          component={LoginScreen}
          options={({ navigation }) => ({
            title: 'Login',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
        <Stack.Screen
          name="SetAuthToken"
          component={SetAuthTokenScreen}
          options={({ navigation }) => ({
            title: 'Set Auth Token',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
        <Stack.Screen
          name="SeeUserDetails"
          component={UserDetailsScreen}
          options={({ navigation }) => ({
            title: 'User Details',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
        <Stack.Screen
          name="Verification"
          component={VerificationScreen}
          options={({ navigation }) => ({
            title: 'Verification',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
        <Stack.Screen
          name="InspectTile"
          component={InspectTileScreen}
          options={({ navigation }) => ({
            title: 'Inspect Tile',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
        <Stack.Screen
          name="UnlockDevice"
          component={UnlockDeviceScreen}
          options={({ navigation }) => ({
            title: 'Unlock Device',
            headerBackTitle: '',
            headerLeft: (_) => <Text style={styles.appBarStyle} onPress={() => navigation.goBack()} >Back</Text>,
          })}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default AppNavigator;
