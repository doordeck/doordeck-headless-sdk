import { StyleSheet } from 'react-native';

const styles = StyleSheet.create({
  title: {
    fontSize: 18, // ✅ Reduced font size so it fits
    fontWeight: 'bold',
    marginTop: 20,
    marginBottom: 10,
    textAlign: 'center',
  },

  tileItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 10, // ✅ Added padding
    paddingHorizontal: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },

  deviceIdText: {
    fontSize: 14, // ✅ Reduced font size
    flex: 1, // ✅ Makes sure text doesn't overlap button
    marginRight: 10, // ✅ Adds space between text and button
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
  },
  scrollContainer: {
    alignItems: 'center',
    flexGrow: 1,
    gap: 20,
  },
  text: {
    color: 'black',
    fontSize: 18,
    marginBottom: 20,
    textAlign: 'center',
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
});

export default styles;
