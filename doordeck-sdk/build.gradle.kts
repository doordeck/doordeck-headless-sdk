import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJsCompile
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.targets.native.tasks.KotlinNativeSimulatorTest
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.cocoapods)
    alias(libs.plugins.swift.klib)
    alias(libs.plugins.buildkonfig)
    `maven-publish`
    signing
}

private sealed class PublishData(
    val title: String = "Doordeck Headless SDK",
    val description: String = "The official Doordeck SDK for Kotlin Multiplatform",
    val repository: String = "https://github.com/doordeck/doordeck-headless-sdk",
    val issues: String = "$repository/issues",
    val gitRepository: String = "$repository.git",
    val author: String = "Doordeck Limited",
    val authorEmail: String = "development@doordeck.com",
    val authorRepository: String = "https://github.com/doordeck",
    val authorHomepage: String = "https://www.doordeck.com",
    val licenseType: String = "Apache-2.0",
    val licenseUrl: String = "$repository/blob/main/LICENSE",
    val readmeUrl: String = "$repository/blob/main/README.md"
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
    val appleTargets = listOf(
        iosX64(), iosArm64(), iosSimulatorArm64(),                                      // iOS
        macosX64(), macosArm64(),                                                       // macOS
        watchosX64(), watchosArm64(), watchosDeviceArm64(), watchosSimulatorArm64()     // watchOS
    )

    appleTargets.forEach {
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
            // Only generate the release artifact.
            sharedLib(listOf(NativeBuildType.RELEASE)) {
                baseName = nugetPublish.packageName
            }
        }
    }

    js(IR) {
        outputModuleName = "doordeck-sdk"
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
        ios.deploymentTarget = libs.versions.ios.min.sdk.get()
        osx.deploymentTarget = libs.versions.macos.min.sdk.get()
        watchos.deploymentTarget = libs.versions.watchos.min.sdk.get()
        name = cocoapodsPublish.packageName
        framework {
            baseName = cocoapodsPublish.packageName
        }

        extraSpecAttributes["readme"] = "'${cocoapodsPublish.readmeUrl}'"
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
                optIn("kotlinx.cinterop.UnsafeNumber")
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
                implementation(libs.indispensable.asn1)
                implementation(libs.kermit.logger)
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
                implementation(libs.security.crypto)
                implementation(libs.bouncy.castle)
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
                implementation(libs.nimbus.jose.jwt)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(libs.libsodium.bindings.js)
                implementation(npm("asn1js", libs.versions.asn1js.get()))
                implementation(npm("pkijs", libs.versions.pkijs.get()))
            }
        }

        val mingwMain by getting {
            dependencies {
                implementation(libs.ktor.client.winhttp)
                implementation(libs.libsodium.bindings.mingwx64)
            }
        }
    }

    // Override konan properties: https://github.com/JetBrains/kotlin/blob/master/kotlin-native/konan/konan.properties
    targets.withType<KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                val iosVersion = libs.versions.ios.min.sdk.get().toInt()
                val macosVersion = libs.versions.macos.min.sdk.get().toInt()
                val watchosVersion = libs.versions.watchos.min.sdk.get().toInt()
                val arguments = "-Xoverride-konan-properties=" + listOf(
                    "osVersionMin.ios_x64=$iosVersion.0",
                    "osVersionMin.ios_arm64=$iosVersion.0",
                    "osVersionMin.ios_simulator_arm64=$iosVersion.0",
                    "osVersionMin.macos_x64=$macosVersion.0",
                    "osVersionMin.macos_arm64=$macosVersion.0",
                    "osVersionMin.watchos_x64=$watchosVersion.0",
                    "osVersionMin.watchos_arm64=$watchosVersion.0",
                    "osVersionMin.watchos_device_arm64=$watchosVersion.0",
                    "osVersionMin.watchos_simulator_arm64=$watchosVersion.0"
                ).joinToString(";")
                freeCompilerArgs.addAll(arguments)
            }
        }
    }
}

// Generates a version file containing the project version as a constant
buildkonfig {
    packageName = "com.doordeck.multiplatform.sdk"
    objectName = "ProjectVersion"
    defaultConfigs {
        buildConfigField(STRING, "VERSION", "${project.version}")
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
    compileSdk = libs.versions.android.compile.sdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.min.sdk.get().toInt()
    }
}

swiftklib {
    create("KCryptoKit") {
        path = file("native/KCryptoKit")
        packageName("com.doordeck.multiplatform.sdk.kcryptokit")
        minMacos = libs.versions.macos.min.sdk.get().toInt()
        minIos = libs.versions.ios.min.sdk.get().toInt()
        minWatchos = libs.versions.watchos.min.sdk.get().toInt()
    }
}

// Display the test log events at all the platforms
tasks.withType<AbstractTestTask>().configureEach {
    testLogging {
        events = setOf(TestLogEvent.STARTED, TestLogEvent.PASSED, TestLogEvent.SKIPPED, TestLogEvent.FAILED, TestLogEvent.STANDARD_OUT, TestLogEvent.STANDARD_ERROR)
    }
}

// Propagate env variables to the apple simulators
tasks.withType<KotlinNativeSimulatorTest>().configureEach {
    System.getenv("TEST_ENV_VAR")?.let {
        environment("SIMCTL_CHILD_TEST_ENV_VAR", it)
    }
    System.getenv("TEST_MAIN_USER_PASSWORD")?.let {
        environment("SIMCTL_CHILD_TEST_MAIN_USER_PASSWORD", it)
    }
    System.getenv("TEST_MAIN_USER_PRIVATE_KEY")?.let {
        environment("SIMCTL_CHILD_TEST_MAIN_USER_PRIVATE_KEY", it)
    }
    System.getenv("FUSION_INTEGRATIONS")?.let {
        environment("SIMCTL_CHILD_FUSION_INTEGRATIONS", it)
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
        // Copy readme
        copy {
            from(rootProject.layout.projectDirectory.file("README.md"))
            into(outputDir)
        }
        // Copy csproj
        copy {
            from(file("$projectDir/src/mingwMain/resources/csharp/Doordeck.Headless.Sdk.csproj"))
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

/**
 * Generates the .nuspec file, which is required to create the .nupkg file
 * needed for publishing a package to the NuGet repository.
 */
tasks.register("generateNuspecFile").configure {
    doLast {
        // Define the output folder
        val outputDir = file("$projectDir/build/bin/mingwX64/csharp")
        // Create nuspec file
        val nuspecFile = file("$outputDir/${nugetPublish.packageName}.nuspec")
        nuspecFile.writeText(nuspecTemplate.trim())
    }
}

tasks.register("pythonPack").configure {
    doLast {
        // Create output directory
        val outputDir = file("$projectDir/build/bin/mingwX64/python")
        mkdir(outputDir)
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
            from(file("$projectDir/build/bin/mingwX64/releaseShared/${nugetPublish.packageName}.dll"))
            into(file("$outputDir/src/${pypiPublish.packageName}"))
        }
        // Create empty __init__.py file
        file("$outputDir/src/${pypiPublish.packageName}/__init__.py").createNewFile()
    }
}

/**
 * Generates the .toml file, which is required to run the python build command
 */
tasks.register("generateTomlFile").configure {
    doLast {
        // Define the output folder
        val outputDir = file("$projectDir/build/bin/mingwX64/python")
        // Create pyproject file
        val setupFile = file("$outputDir/pyproject.toml")
        setupFile.writeText(pypiTemplate.trim())
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
      <group targetFramework="net9.0" />
    </dependencies>
  </metadata>
  <files>
    <file src="README.md" target="\" />
    <file src="..\releaseShared\${nugetPublish.packageName}.dll" target="lib\net9.0\" />
    <file src="${nugetPublish.packageName}\**\*" target="contentFiles\cs\any\${nugetPublish.packageName}\" />
  </files>
</package>
"""

val pypiTemplate = """
[build-system]
requires = ["setuptools"]
build-backend = "setuptools.build_meta"
[project]
name = "${pypiPublish.packageName}"
version = "${project.version}"
description = "${pypiPublish.description}"
readme = "README.md"
requires-python = "==3.13.2"
license = { file = "LICENSE.txt" }
keywords = [${pypiPublish.keywords.joinToString(separator = ", ") { "\"$it\"" }}]
authors = [{ name = "${pypiPublish.author}", email = "${pypiPublish.authorEmail}" }]
classifiers = [
  "Development Status :: 3 - Alpha",
  "Programming Language :: Python :: 3.13",
  "Operating System :: Microsoft :: Windows",
]
[project.urls]
"Homepage" = "${pypiPublish.authorHomepage}"
"Source" = "${pypiPublish.gitRepository}"
"Issue tracker" = "${pypiPublish.issues}"
[tool.setuptools]
package-data = { "${pypiPublish.packageName}" = ["_doordeck_headless_sdk.pyd", "${nugetPublish.packageName}.dll"] }
""".trimIndent()