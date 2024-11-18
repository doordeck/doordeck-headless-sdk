plugins {
    //trick: for the same plugin versions in all submodules
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

tasks.register("updatePackageSwift") {
    val packageVersion: String? by project
    val packageChecksum: String? by project

    doLast {
        if (!packageVersion.isNullOrEmpty() && !packageChecksum.isNullOrEmpty()) {
            val packageFile = file("Package.swift")
            val content = packageFile.readText()
            val updatedContent = content
                .replace(Regex("""url: ".*""""), """url: "https://github.com/doordeck/doordeck-headless-sdk/releases/download/v$packageVersion/DoordeckSDK.xcframework.zip"""")
                .replace(Regex("""checksum: ".*""""), """checksum: "$packageChecksum"""")
            packageFile.writeText(updatedContent)
        }
    }
}