plugins {
    alias(libs.plugins.kotlin.multiplatform.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.buildkonfig).apply(false)
    id("com.netflix.nebula.release") version "21.1.4"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.2"
}

group = "com.doordeck"

// Force some JS dependencies to use specific versions (yarn.lock)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("ws", "8.21.0")
        resolution("serialize-javascript", "7.0.5")
        resolution("diff", "8.0.3")
    }
}