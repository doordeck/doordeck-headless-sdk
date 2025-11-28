import java.time.Duration

plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.swift.klib).apply(false)
    alias(libs.plugins.buildkonfig).apply(false)
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.netflix.nebula.release") version "21.0.0"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.18.1"
}

group = "com.doordeck"

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl = uri("https://ossrh-staging-api.central.sonatype.com/service/local/")
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_TOKEN")
        }
    }

    // Increase timeouts
    connectTimeout.set(Duration.ofMinutes(15))
    clientTimeout.set(Duration.ofMinutes(15))
}

// Force some JS dependencies to use specific versions (yarn.lock)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("brace-expansion", "2.0.2")
        resolution("node-forge", "1.3.2")
    }
}