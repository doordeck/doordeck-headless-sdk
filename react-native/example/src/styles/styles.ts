import { StyleSheet } from 'react-native';

const styles = StyleSheet.create({
  title: {
    fontSize: 20, // Increased size for better visibility
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 10,
    textAlign: 'center',
  },

  tileItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 10,
    paddingHorizontal: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },

  deviceIdText: {
    fontSize: 14,
    flex: 1,
    marginRight: 10,
  },

  copyButton: {
    backgroundColor: 'blue',
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 5,
  },

  copyButtonText: {
    color: 'white',
    fontSize: 14,
  },

  container: {
    flex: 1,
    padding: 20,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },

  scrollContainer: {
    alignItems: 'center',
    flexGrow: 1,
    gap: 20,
  },

  text: {
    color: 'black',
    fontSize: 16,
    marginBottom: 10,
    textAlign: 'center',
  },

  label: {
    fontWeight: 'bold',
    color: '#555',
  },

  resultNeedsVerificationOrError: {
    color: 'red',
    fontSize: 14,
    marginBottom: 10,
    marginTop: 10,
    textAlign: 'center',
  },

  input: {
    width: '100%',
    height: 50,
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
    color: 'black',
    paddingHorizontal: 10,
    marginBottom: 15,
  },

  appBarStyle: {
    paddingHorizontal: 16,
  },

  userDetailsScroll: {
    flex: 1,
    width: '100%',
  },

  userDetailsContainer: {
    width: '100%',
    maxWidth: 350,
    backgroundColor: '#f5f5f5',
    padding: 15,
    borderRadius: 10,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.2,
    shadowRadius: 4,
    elevation: 4,
    marginBottom: 20,
  },

  verifiedText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'green',
    textAlign: 'center',
    marginTop: 10,
  },

  notVerifiedText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'red',
    textAlign: 'center',
    marginTop: 10,
  },
  selectableText: {
    fontSize: 16,
    color: '#333',
    marginBottom: 5,
    textAlign: 'left',
  },
  publicKey: {
    fontSize: 14,
    fontFamily: 'monospace',
  },

  missingUserId: {
    fontSize: 16,
    color: 'red',
    fontWeight: 'bold',
    marginBottom: 5,
    textAlign: 'center',
  },

  warningText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: 'orange',
    textAlign: 'center',
    marginTop: 10,
  },

});

export default styles;
