# doordeck-sdk-sample
doordeck-sdk-sample


# Samples
### JVM (Kotlin)
````kotlin
import com.doordeck.multiplatform.sdk.KDoordeckFactory
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.util.Crypto
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64

fun main() {
    // Initialize the SDK
    val token = "YOUR_AUTH_TOKEN"
    val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, token)

    // Retrieve the sites
    val sites = sdk.sites().listSites()
    println(sites)

    // Retrieve the locks
    val locks = sdk.sites().getLocksForSite(sites.first().id)
    println(locks)

    // Generate a key pair
    val keyPair = Crypto.generateKeyPair()
    println("Private key: ${keyPair.private.encodeByteArrayToBase64()}")
    println("Public key: ${keyPair.public.encodeByteArrayToBase64()}")

    // Register a key pair
    val registerKeyPair = sdk.account().registerEphemeralKey(keyPair.public)
    println("Certificate chain: ${registerKeyPair.certificateChain.certificateChainToString()}")

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
import com.doordeck.multiplatform.sdk.Doordeck;
import com.doordeck.multiplatform.sdk.KDoordeckFactory;
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment;
import com.doordeck.multiplatform.sdk.api.model.LockOperations;
import com.doordeck.multiplatform.sdk.util.Crypto;

public class Main {
    public static void main(String[] args) {
        // Initialize the SDK
        final String token = "YOUR_AUTH_TOKEN";
        final Doordeck sdk = new KDoordeckFactory().initialize(ApiEnvironment.DEV, token);

        // Retrieve the sites
        var sites = sdk.sites().listSites();
        System.out.println(sites);

        // Retrieve the locks
        var locks = sdk.sites().getLocksForSite(sites[0].getId());
        System.out.println(locks);

        // Generate a key pair
        var keyPair = Crypto.INSTANCE.generateKeyPair();
        System.out.println("Private key: " + Crypto.INSTANCE.encodeByteArrayToBase64(keyPair.getPrivate()));
        System.out.println("Public key: " + Crypto.INSTANCE.encodeByteArrayToBase64(keyPair.getPublic()));

        // Register a key pair
        var registerKeyPair = sdk.account().registerEphemeralKey(keyPair.getPublic());
        System.out.println("Certificate chain: " + Crypto.INSTANCE.certificateChainToString(registerKeyPair.getCertificateChain()));

        // Unlock a lock
        var baseOperation = new LockOperations.BaseOperation(registerKeyPair.getUserId(),
                registerKeyPair.getCertificateChain(), keyPair.getPrivate(), locks[0].getId());
        var unlockOperation = new LockOperations.UnlockOperation(baseOperation);
        sdk.lockOperations().unlock(unlockOperation);
    }
}
````
### JS (Angular)
````javascript
import {Component, OnInit} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import doordeck from '../assets/doordeck-sdk'
import {HttpClient, HttpClientModule} from "@angular/common/http";

const apiEnvironment = doordeck.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const crypto = doordeck.com.doordeck.multiplatform.sdk.util.Crypto;

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'TestLibJs';

  constructor(
    private http: HttpClient
  ) { }
  
  ngOnInit() {
    this.test()
  }

  async test() {
    // Initialize the SDK
    const token = "YOUR_AUTH_TOKEN";
    const factory = new doordeck.com.doordeck.sdk.KDoordeckFactory();
    const sdk = await Promise.resolve(factory.initialize(apiEnvironment.DEV, token));

    // Retrieve the sites
    const sites = await Promise.resolve(sdk.sites().listSites());
    console.log(sites);

    // Retrieve the locks
    const locks = await Promise.resolve(sdk.sites().getLocksForSite(sites[0].id));
    console.log(locks);

    // Generate a key pair
    const keyPair = crypto.generateKeyPair();
    console.log(`Private key: ${crypto.encodeByteArrayToBase64(keyPair.private)}`);
    console.log(`Public key: ${crypto.encodeByteArrayToBase64(keyPair.public)}`);

    // Register a key pair
    const registerKeyPair = await Promise.resolve(sdk.account().registerEphemeralKey(keyPair.public));
    console.log(`Public key: ${crypto.certificateChainToString(registerKeyPair.certificateChain)}`);

    // Unlock a lock
    const baseOperation = new lockOperations.BaseOperation(registerKeyPair.userId, registerKeyPair.certificateChain,
        registerKeyPair.private, locks[0].id, (new Date).getTime() / 1000, (new Date).getTime() / 1000,
        ((new Date).getTime() / 1000) + 60, "uuid"
    );
    const unlockOperation = new lockOperations.UnlockOperation(baseOperation);
    await Promise.resolve(sdk.lockOperations().unlock(unlockOperation));
  }
}
````

### Android (Kotlin)
````kotlin
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.doordeck.multiplatform.sdk.KDoordeckFactory
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.util.Crypto
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the SDK
        val token = "YOUR_AUTH_TOKEN"
        val sdk = KDoordeckFactory().initialize(ApiEnvironment.DEV, token)

        // Retrieve the sites
        val sites = sdk.sites().listSites()
        println(sites)

        // Retrieve the locks
        val locks = sdk.sites().getLocksForSite(sites.first().id)
        println(locks)

        // Generate a key pair
        val keyPair = Crypto.generateKeyPair()
        println("Private key: ${keyPair.private.encodeByteArrayToBase64()}")
        println("Public key: ${keyPair.public.encodeByteArrayToBase64()}")

        // Register a key pair
        val registerKeyPair = sdk.account().registerEphemeralKey(keyPair.public)
        println("Certificate chain: ${registerKeyPair.certificateChain.certificateChainToString()}")

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
}
````