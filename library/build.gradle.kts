import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.testing.KotlinJsTest

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    `maven-publish`
}

kotlin {
    withSourcesJar(publish = false)

    applyDefaultHierarchyTemplate()
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    mingwX64 {
        binaries {
            sharedLib {
                baseName = "doordeck-sdk"
            }
        }
    }
    js(IR) {
        moduleName = "doordeck-sdk"
        useCommonJs()
        nodejs {
            testTask {
                useMocha()
            }
        }
        browser {
            testTask {
                useMocha()
            }
            webpackTask {
                mainOutputFileName = "doordeck-sdk.js"
            }
        }
        binaries.library()
        binaries.executable()
        generateTypeScriptDefinitions()
    }

    sourceSets {
        all {
            // Remove the warnings from the experimental kotlin features
            languageSettings.apply {
                optIn("kotlin.io.encoding.ExperimentalEncodingApi")
                optIn("kotlin.js.ExperimentalJsExport")
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlin.experimental.ExperimentalNativeApi")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.auth)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.encoding)
                implementation(libs.libsodium.bindings)
                implementation(libs.uuid.generator)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.ktor.client.cio)
            }
        }

        val appleMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.apache)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }

        val mingwMain by getting {
            dependencies {
                implementation(libs.ktor.client.winhttp)
            }
        }
    }
}

// Set up the environment variables for the JS - Browser platform
// So far, that is the only way I have found to make those tests pass
tasks.withType<KotlinJsTest>().configureEach {
    environment("TEST_AUTH_TOKEN", System.getenv("TEST_AUTH_TOKEN") ?: "")
    environment("TEST_MAIN_USER_ID", System.getenv("TEST_MAIN_USER_ID") ?: "")
    environment("TEST_MAIN_USER_EMAIL", System.getenv("TEST_MAIN_USER_EMAIL") ?: "")
    environment("TEST_MAIN_USER_CERTIFICATE_CHAIN", System.getenv("TEST_MAIN_USER_CERTIFICATE_CHAIN") ?: "")
    environment("TEST_MAIN_USER_PRIVATE_KEY", System.getenv("TEST_MAIN_USER_PRIVATE_KEY") ?: "")
    environment("TEST_SUPPLEMENTARY_USER_ID", System.getenv("TEST_SUPPLEMENTARY_USER_ID") ?: "")
    environment("TEST_SUPPLEMENTARY_USER_PUBLIC_KEY", System.getenv("TEST_SUPPLEMENTARY_USER_PUBLIC_KEY") ?: "")
    environment("TEST_MAIN_TILE_ID", System.getenv("TEST_MAIN_TILE_ID") ?: "")
    environment("TEST_MAIN_LOCK_ID", System.getenv("TEST_MAIN_LOCK_ID") ?: "")
    environment("TEST_ENV_VAR", System.getenv("TEST_ENV_VAR") ?: "")
}

// Display the test log events at all the platforms
tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        events = setOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_ERROR)
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/doordeck/doordeck-sdk-sample")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

android {
    namespace = "com.doordeck.multiplatform.sdk"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

