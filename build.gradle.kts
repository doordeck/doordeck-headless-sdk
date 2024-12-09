plugins {
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinxSerialization).apply(false)
    alias(libs.plugins.kotlinCocoapods).apply(false)
    alias(libs.plugins.swift.klib).apply(false)
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("com.netflix.nebula.release") version "19.0.8"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.16.3"
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
        resolution("ws", "8.17.1")
        resolution("webpack", "5.94.0")
    }
}