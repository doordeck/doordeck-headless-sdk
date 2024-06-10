# doordeck-sdk-sample
doordeck-sdk-sample


# Samples
### JVM (Kotlin)
````kotlin
import com.doordeck.sdk.KDoordeckFactory
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.util.Crypto

fun main() {
    // Initialize the SDK
    val token = "YOUR_AUTH_TOKEN"
    val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, token)

    // Retrieve the sites
    val sites = sdk.sites().listSites()
    println(sites)

    // Retrieve the locks
    val locks = sdk.lockOperations().getAllLocks()
    println(locks)

    // Generate a key pair
    val keyPair = Crypto.generateKeyPair()

    // Register a key pair
    val registerKeyPair = sdk.account().registerEphemeralKey(keyPair.public)

    // Unlock a lock
    val baseOperation = LockOperations.BaseOperation(
        userId = registerKeyPair.userId,
        userCertificateChain = registerKeyPair.certificateChain,
        userPrivateKey = keyPair.private,
        lockId = locks.first().id
    )
    val unlockOperation = LockOperations.UnlockOperation(baseOperation)
    sdk.lockOperations().unlock(unlockOperation)
}
````

### JVM (JAVA)
````java
import com.doordeck.sdk.Doordeck;
import com.doordeck.sdk.KDoordeckFactory;
import com.doordeck.sdk.api.model.ApiEnvironment;
import com.doordeck.sdk.api.model.LockOperations;
import com.doordeck.sdk.util.Crypto;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        // Initialize the SDK
        final String token = "YOUR_AUTH_TOKEN";
        final Doordeck sdk = new KDoordeckFactory().initialize(ApiEnvironment.DEV, token);

        // Retrieve the sites
        var sites = sdk.sites().listSites();
        System.out.println(sites);

        // Retrieve the locks
        var locks = sdk.lockOperations().getAllLocks();
        System.out.println(locks);

        // Generate a key pair
        var keyPair = Crypto.INSTANCE.generateKeyPair();

        // Register a key pair
        var registerKeyPair = sdk.account().registerEphemeralKey(keyPair.getPublic());

        // Unlock a lock
        var now = Instant.now();
        var baseOperation = new LockOperations.BaseOperation(registerKeyPair.getUserId(),
                registerKeyPair.getCertificateChain(), keyPair.getPrivate(), locks[0].getId(), 
                (int)now.getEpochSecond(), (int)now.getEpochSecond(), 
                (int)now.plusSeconds(60).getEpochSecond(), null);
        var unlockOperation = new LockOperations.UnlockOperation(baseOperation);
        sdk.lockOperations().unlock(unlockOperation);
    }
}
````


## Michael General Comments! 
I really like the SDK, it looks easy to use and clean, I think you've done a fantastic job so far with it!

- Don't merge this PR, its not meant for merging as you can no doubt tell!
- Should all the APIs be `suspend`? coroutines are multiplaform and mature enough
- We do have a GPG key and account setup for publishing to Maven Central, so when you get to the JDK/Android publishing let me know
- Testing this looks like a pain, we can test some stuff like register, etc without creds but we'll need to store some 
    secrets in GitHub actions if we want to test 'real' accounts which I think we should
- I really dislike the gradle convention-plugins that the templates use, but let's just leave it 