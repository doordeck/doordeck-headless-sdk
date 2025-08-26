import com.doordeck.multiplatform.sdk.Doordeck
import com.doordeck.multiplatform.sdk.DoordeckFactory
import com.doordeck.multiplatform.sdk.config.SdkConfig
import com.doordeck.multiplatform.sdk.config.toBasicSdkConfig
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

object KDoordeckFactory {

    suspend fun initialize(sdkConfig: SdkConfig): Doordeck =
        DoordeckFactory.initialize(sdkConfig.toBasicSdkConfig())

    fun initializeAsync(sdkConfig: SdkConfig): CompletableFuture<Doordeck> = completableFuture {
        initialize(sdkConfig)
    }
}