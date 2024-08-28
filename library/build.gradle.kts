import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

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

    val xcf = XCFramework("DoordeckSDK")
    val iosTargets = listOf(iosX64(), iosArm64(), macosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = "DoordeckSDK"
            xcf.add(this)
        }
    }

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
                useMocha {
                    timeout = "60s"
                }
            }
        }
        browser {
            testTask {
                useMocha {
                    timeout = "60s"
                }
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
            // Remove the warning about using expect/actual in interfaces
            @OptIn(ExperimentalKotlinGradlePluginApi::class)
            compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
            // Remove the warnings from the experimental kotlin features
            languageSettings.apply {
                optIn("kotlin.io.encoding.ExperimentalEncodingApi")
                optIn("kotlin.js.ExperimentalJsExport")
                optIn("kotlin.ExperimentalUnsignedTypes")
                optIn("kotlinx.coroutines.DelicateCoroutinesApi")
                optIn("kotlinx.serialization.ExperimentalSerializationApi")
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
                implementation(libs.koin)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.ktor.client.mock)
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
            url = uri("https://maven.pkg.github.com/doordeck/doordeck-headless-sdk")
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

