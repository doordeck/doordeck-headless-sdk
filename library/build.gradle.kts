import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotlinCocoapods)
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

        // Add the necessary fields to the package.json file
        compilations["main"].packageJson {
            name = "@doordeck/doordeck-headless-sdk"
            customField("homepage", "https://www.doordeck.com/")
            customField("license", "Apache-2.0")
            customField("author", mapOf("name" to "Doordeck Limited"))
            customField("keywords", listOf("doordeck", "sdk", "javascript", "access control"))
            customField("bugs", mapOf("url" to "https://github.com/doordeck/doordeck-headless-sdk/issues"))
            customField("repository", mapOf("type" to "git", "url" to "git+https://github.com/doordeck/doordeck-headless-sdk.git"))
        }
    }

    cocoapods {
        summary = "Doordeck KMP SDK"
        homepage = "https://www.doordeck.com/"
        license = "Apache-2.0"
        authors = "Doordeck Limited"
        source = "{ :git => 'https://github.com/doordeck/doordeck-headless-sdk.git', :tag => 'v${project.version}' }"
        ios.deploymentTarget = libs.versions.ios.minSdk.get()
        name = "DoordeckSDK"
        framework {
            baseName = "DoordeckSDK"
        }
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
                optIn("kotlin.uuid.ExperimentalUuidApi")
                optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")
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
                implementation(libs.kotlinx.datetime)
                implementation(libs.koin)
                implementation(libs.multiplatform.settings)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlin.coroutines.test)
                implementation(libs.ktor.client.mock)
                implementation(libs.multiplatform.settings.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.security.crypto)
            }
        }

        val appleMain by getting {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
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

tasks.named("publish").configure {
    finalizedBy("jsBrowserProductionLibraryDistribution", "podPublishReleaseXCFramework")
}

tasks.named("jsBrowserProductionLibraryDistribution").configure {
    doLast {
        // Specify the directory where the package is generated
        val publishDir = rootProject.layout.buildDirectory.dir("js/packages/doordeck-sdk")
        // Copy the README file from the root project into the package folder
        copy {
            from(rootProject.layout.projectDirectory.file("README.md"))
            into(publishDir.get().asFile)
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

