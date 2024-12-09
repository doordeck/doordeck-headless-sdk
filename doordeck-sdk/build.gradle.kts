import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.swift.klib)
    `maven-publish`
    signing
}

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    val xcf = XCFramework("DoordeckSDK")
    val iosTargets = listOf(iosX64(), iosArm64(), macosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = "DoordeckSDK"
            binaryOption("bundleId", "com.doordeck.DoordeckSDK")
            xcf.add(this)
        }

        it.compilations {
            val main by getting {
                cinterops {
                    create("KCryptoKit")
                }
            }
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
        summary = "Doordeck Headless SDK"
        homepage = "https://www.doordeck.com/"
        license = "{ :type => 'Apache-2.0' }"
        authors = "Doordeck Limited"
        version = "${project.version}"
        source = "{ :http => 'https://cdn.doordeck.com/xcframework/v${project.version}/DoordeckSDK.xcframework.zip' }"
        ios.deploymentTarget = libs.versions.ios.minSdk.get()
        name = "DoordeckSDK"
        framework {
            baseName = "DoordeckSDK"
        }
        extraSpecAttributes["vendored_frameworks"] = "'DoordeckSDK.xcframework'"
    }

    sourceSets {
        all {
            // Remove the warning about using expect/actual in interfaces
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
                optIn("kotlinx.cinterop.BetaInteropApi")
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
                implementation(libs.libsodium.bindings.js)
                implementation(npm("asn1js", libs.versions.asn1js.get()))
                implementation(npm("pkijs", libs.versions.pkijs.get()))
                implementation(npm("ws", "8.17.1")) // Overrides the original version
            }
        }

        val mingwMain by getting {
            dependencies {
                implementation(libs.ktor.client.winhttp)
                implementation(libs.libsodium.bindings.mingwx64)
            }
        }
    }
}

// Display the test log events at all the platforms
tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        events = setOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_OUT, TestLogEvent.STANDARD_ERROR)
    }
}

// Generates empty Javadoc JARs, which are required for publishing to Maven Central
val javadocJar by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles java doc to jar"
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar)
        groupId = "com.doordeck.headless.sdk"
        version = "${project.version}"
        pom {
            name.set("Doordeck Headless SDK")
            inceptionYear.set("2024")
            description.set("The official Doordeck SDK for Kotlin Multiplatform")
            url.set("https://github.com/doordeck/doordeck-headless-sdk")
            licenses {
                license {
                    name.set("Apache-2.0")
                    url.set("https://github.com/doordeck/doordeck-headless-sdk/blob/main/LICENSE")
                }
            }
            issueManagement {
                system.set("Github")
                url.set("https://github.com/doordeck/doordeck-headless-sdk/issues")
            }
            developers {
                developer {
                    id.set("doordeck")
                    name.set("Doordeck Limited")
                    url.set("https://github.com/doordeck")
                }
                organization {
                    name.set("Doordeck Limited")
                    url.set("https://github.com/doordeck")
                }
            }
            scm {
                url.set("https://github.com/doordeck/doordeck-headless-sdk")
                connection.set("scm:git:git://github.com/doordeck/doordeck-headless-sdk.git")
                developerConnection.set("scm:git:ssh://git@github.com/doordeck/doordeck-headless-sdk.git")
            }
        }
    }

    val signingTasks = tasks.withType<Sign>()
    tasks.withType<AbstractPublishToMaven>().configureEach {
        mustRunAfter(signingTasks)
    }
}

signing {
    val signingKey = System.getenv("MAVEN_SIGN_KEY")
    val signingPassword = System.getenv("MAVEN_SIGN_PASSWORD")

    useInMemoryPgpKeys(null, signingKey, signingPassword)
    sign(publishing.publications)
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

swiftklib {
    create("KCryptoKit") {
        path = file("native/KCryptoKit")
        packageName("com.doordeck.multiplatform.sdk.kcryptokit")
        minMacos = libs.versions.ios.minSdk.get().toInt()
        minIos = libs.versions.ios.minSdk.get().toInt()
    }
}

tasks.register<Zip>("zipXCFramework") {
    from("build/XCFrameworks/release")
    archiveFileName.set("DoordeckSDK.xcframework.zip")
    destinationDirectory.set(file("."))
    include("**/*")
}

tasks.named("assembleDoordeckSDKReleaseXCFramework").configure {
    finalizedBy("zipXCFramework")
}

// Disable source map generation
tasks.withType<KotlinJsCompile>().configureEach {
    compilerOptions {
        sourceMap = false
    }
}

