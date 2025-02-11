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

private sealed class PublishData(
    val title: String = "Doordeck Headless SDK",
    val description: String = "The official Doordeck SDK for Kotlin Multiplatform",
    val issues: String = "https://github.com/doordeck/doordeck-headless-sdk/issues",
    val repository: String = "https://github.com/doordeck/doordeck-headless-sdk",
    val gitRepository: String = "https://github.com/doordeck/doordeck-headless-sdk.git",
    val author: String = "Doordeck Limited",
    val authorEmail: String = "development@doordeck.com",
    val authorRepository: String = "https://github.com/doordeck",
    val authorHomepage: String = "https://www.doordeck.com",
    val licenseType: String = "Apache-2.0",
    val licenseUrl: String = "https://github.com/doordeck/doordeck-headless-sdk/blob/main/LICENSE"
)

private data class NpmPublishData(
    val packageName: String = "@doordeck/doordeck-headless-sdk",
    val keywords: List<String> = listOf("doordeck", "sdk", "javascript", "access control")
): PublishData()

private data class CocoapodsPublishData(
    val packageName: String = "DoordeckSDK",
    val vendoredFrameworks: String = "$packageName.xcframework",
    val bundleId: String = "com.doordeck.$packageName"
): PublishData()

private data class MavenPublishData(
    val groupId: String = "com.doordeck.headless.sdk"
) : PublishData()

private data class NugetPublishData(
    val packageName: String = "Doordeck.Headless.Sdk",
    val tags: List<String> = listOf("doordeck", "access control")
) : PublishData()

private data class PyPiPublishData(
    val packageName: String = "doordeck_headless_sdk",
    val keywords: List<String> = listOf("doordeck", "sdk", "access control")
) : PublishData()

private val npmPublish = NpmPublishData()
private val cocoapodsPublish = CocoapodsPublishData()
private val mavenPublish = MavenPublishData()
private val nugetPublish = NugetPublishData()
private val pypiPublish = PyPiPublishData()

kotlin {
    applyDefaultHierarchyTemplate()
    jvm()
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    val xcf = XCFramework(cocoapodsPublish.packageName)
    val iosTargets = listOf(iosX64(), iosArm64(), macosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = cocoapodsPublish.packageName
            binaryOption("bundleId", cocoapodsPublish.bundleId)
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
                baseName = nugetPublish.packageName
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
            name = npmPublish.packageName
            customField("homepage", npmPublish.authorHomepage)
            customField("license", npmPublish.licenseType)
            customField("author", mapOf("name" to npmPublish.author))
            customField("keywords", npmPublish.keywords)
            customField("bugs", mapOf("url" to npmPublish.issues))
            customField("repository", mapOf("type" to "git", "url" to "git+${npmPublish.gitRepository}"))
        }
    }

    cocoapods {
        summary = cocoapodsPublish.title
        homepage = cocoapodsPublish.authorHomepage
        license = "{ :type => '${cocoapodsPublish.licenseType}' }"
        authors = cocoapodsPublish.author
        version = "${project.version}"
        source = "{ :http => 'https://cdn.doordeck.com/xcframework/v${project.version}/${cocoapodsPublish.vendoredFrameworks}.zip' }"
        ios.deploymentTarget = libs.versions.ios.minSdk.get()
        name = cocoapodsPublish.packageName
        framework {
            baseName = cocoapodsPublish.packageName
        }
        extraSpecAttributes["vendored_frameworks"] = "'${cocoapodsPublish.vendoredFrameworks}'"
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

// Generates empty Javadoc JARs, which are required for publishing to Maven Central
val javadocJar by tasks.registering(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles java doc to jar"
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar)
        groupId = mavenPublish.groupId
        version = "${project.version}"
        pom {
            name.set(mavenPublish.title)
            inceptionYear.set("2024")
            description.set(mavenPublish.description)
            url.set(mavenPublish.repository)
            licenses {
                license {
                    name.set(mavenPublish.licenseType)
                    url.set(mavenPublish.licenseUrl)
                }
            }
            issueManagement {
                system.set("Github")
                url.set(mavenPublish.issues)
            }
            developers {
                developer {
                    id.set("doordeck")
                    name.set(mavenPublish.author)
                    url.set(mavenPublish.authorRepository)
                }
                organization {
                    name.set(mavenPublish.author)
                    url.set(mavenPublish.authorRepository)
                }
            }
            scm {
                url.set(mavenPublish.repository)
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

// Display the test log events at all the platforms
tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        events = setOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_OUT, TestLogEvent.STANDARD_ERROR)
    }
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

tasks.register<Zip>("zipXCFramework") {
    from("build/XCFrameworks/release")
    archiveFileName.set("${cocoapodsPublish.vendoredFrameworks}.zip")
    destinationDirectory.set(file("."))
    include("**/*")
}

tasks.named("assemble${cocoapodsPublish.packageName}ReleaseXCFramework").configure {
    finalizedBy("zipXCFramework")
}

// Disable source map generation
tasks.withType<KotlinJsCompile>().configureEach {
    compilerOptions {
        sourceMap = false
    }
}

tasks.register("csharpPack").configure {
    doLast {
        // Create output directory
        val outputDir = file("$projectDir/build/bin/mingwX64/csharp")
        mkdir(outputDir)
        // Create nuspec file
        val nuspecFile = file("$outputDir/${nugetPublish.packageName}.nuspec")
        nuspecFile.writeText(nuspecTemplate.trim())
        // Copy readme
        copy {
            from(rootProject.layout.projectDirectory.file("README.md"))
            into(outputDir)
        }
        // Copy csharp resources
        copy {
            from(file("$projectDir/src/mingwMain/resources/csharp"))
            into(file("$outputDir/${nugetPublish.packageName}"))
            include("**/*.cs")
        }
    }
}

tasks.register("pythonPack").configure {
    doLast {
        // Create output directory
        val outputDir = file("$projectDir/build/bin/mingwX64/python")
        mkdir(outputDir)
        // Create pyproject file
        val setupFile = file("$outputDir/pyproject.toml")
        setupFile.writeText(pyprojectTemplate.trim())
        // Copy README & LICENSE
        copy {
            from(rootProject.layout.projectDirectory.file("LICENSE"))
            from(rootProject.layout.projectDirectory.file("README.md"))
            into(outputDir)
        }
        // Copy python resources
        copy {
            from(file("$projectDir/src/mingwMain/resources/python"))
            into(outputDir)
            include("**/*.i")
        }
        // Copy mingwX64 dll
        copy {
            from(file("$projectDir/build/bin/mingwX64/releaseShared/Doordeck.Headless.Sdk.dll"))
            into(file("$outputDir/src/${pypiPublish.packageName}"))
        }
        // Create empty __init__.py file
        file("$outputDir/src/${pypiPublish.packageName}/__init__.py").createNewFile()
    }
}

private val nuspecTemplate = """
<?xml version="1.0"?>
<package xmlns="http://schemas.microsoft.com/packaging/2013/01/nuspec.xsd">
  <metadata>
    <id>${nugetPublish.packageName}</id>
    <version>${project.version}</version>
    <authors>${nugetPublish.author}</authors>
    <owners>${nugetPublish.author}</owners>
    <description>${nugetPublish.description}</description>
    <projectUrl>${nugetPublish.authorHomepage}</projectUrl>
    <repository type="git" url="${nugetPublish.gitRepository}" />
    <tags>${nugetPublish.tags.joinToString(" ")}</tags>
    <license type="expression">${nugetPublish.licenseType}</license>
    <readme>README.md</readme>
    <dependencies>
      <group targetFramework=".NETStandard2.0" />
    </dependencies>
  </metadata>
  <files>
    <file src="README.md" target="\" />
    <file src="..\releaseShared\${nugetPublish.packageName}.dll" target="lib\netstandard2.0\" />
    <file src="${nugetPublish.packageName}\**\*" target="contentFiles\cs\any\${nugetPublish.packageName}\" />
  </files>
</package>
"""

private val pyprojectTemplate = """
[build-system]
requires = ["setuptools"]
build-backend = "setuptools.build_meta"
[project]
name = "${pypiPublish.packageName}"
version = "${project.version}"
description = "${pypiPublish.description}"
readme = "README.md"
requires-python = ">=3.6"
license = { file = "LICENSE.txt" }
keywords = [${pypiPublish.keywords.joinToString(separator = ", ") { "\"$it\"" }}]
authors = [{ name = "${pypiPublish.author}", email = "${pypiPublish.authorEmail}" }]
[project.urls]
"Homepage" = "${pypiPublish.authorHomepage}"
"Bug Reports" = "${pypiPublish.issues}"
"Source" = "${pypiPublish.gitRepository}"
[tool.setuptools]
package-data = { "doordeck_headless_sdk" = ["_doordeck_headless_sdk.pyd", "Doordeck.Headless.Sdk.dll"] }
""".trimIndent()