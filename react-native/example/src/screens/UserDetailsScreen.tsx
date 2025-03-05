import React, { useState, useEffect } from 'react';
import { View, Text, ActivityIndicator, ScrollView } from 'react-native';
import type { StackNavigationProp } from '@react-navigation/stack';
import type { RootStackParamList } from '../navigation/AppNavigator';
import styles from '../styles/styles';
import { getUserDetails } from '@doordeck/headless-react-native-sdk';
import type { UserDetailsResponse } from "../../../src/NativeHeadlessReactNativeSdk";

type UserDetailsScreenNavigationProp = StackNavigationProp<RootStackParamList, 'SeeUserDetails'>;

interface UserDetailsScreenProps {
  navigation: UserDetailsScreenNavigationProp;
}

const UserDetailsScreen: React.FC<UserDetailsScreenProps> = () => {
  const [userDetails, setUserDetails] = useState<UserDetailsResponse>();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    getUserDetails()
      .then(setUserDetails)
      .catch((err) => setError(String(err)))
      .finally(() => setLoading(false));
  }, []);

  return (
    <View style={styles.container}>
      {loading ? (
        <ActivityIndicator size="large" color="#000" />
      ) : error ? (
        <Text style={styles.resultNeedsVerificationOrError}>Error: {error}</Text>
      ) : userDetails ? (
        <ScrollView contentContainerStyle={styles.scrollContainer} style={styles.userDetailsScroll}>
          <View style={styles.userDetailsContainer}>
            <Text style={styles.title}>User Details</Text>

            {/* User ID */}
            <Text style={styles.label}>User ID:</Text>
            {userDetails.userId ? (
              <Text style={styles.selectableText}>{userDetails.userId}</Text>
            ) : (
              <Text style={styles.missingUserId}>User ID Missing - Requires 2FA Verification</Text>
            )}

            {/* Email */}
            <Text style={styles.label}>Email:</Text>
            <Text style={styles.selectableText}>{userDetails.email}</Text>

            {/* Public Key */}
            <Text style={styles.label}>Public Key:</Text>
            <Text style={[styles.selectableText, styles.publicKey]}>{userDetails.publicKey}</Text>

            {/* Display Name (Optional) */}
            {userDetails.displayName && (
              <>
                <Text style={styles.label}>Display Name:</Text>
                <Text style={styles.selectableText}>{userDetails.displayName}</Text>
              </>
            )}

            {/* Email Verification Status */}
            <Text style={userDetails.emailVerified ? styles.verifiedText : styles.notVerifiedText}>
              {userDetails.emailVerified ? 'Email Verified' : 'Email Not Verified'}
            </Text>

            {/* Warnings for Certificate & Token Expiry */}
            {userDetails.certificateChainAboutToExpire && (
              <Text style={styles.warningText}>⚠️ Certificate Chain is About to Expire</Text>
            )}

            {userDetails.tokenAboutToExpire && (
              <Text style={styles.warningText}>⚠️ Token is About to Expire</Text>
            )}
          </View>
        </ScrollView>
      ) : (
        <Text style={styles.text}>No user details available.</Text>
      )}
    </View>
  );
};

export default UserDetailsScreen;
