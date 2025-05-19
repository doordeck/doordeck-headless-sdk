plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlinx.serialization).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
    alias(libs.plugins.swift.klib).apply(false)
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.netflix.nebula.release") version "20.2.0"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0"
}

group = "com.doordeck"

nexusPublishing {
    repositories {
        sonatype {
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_TOKEN")
        }
    }
}

// Force some JS dependencies to use specific versions (yarn.lock)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("cross-spawn", "7.0.6")
    }
}