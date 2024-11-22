# Install the SDK into your project
<details><summary>Kotlin Multiplatform</summary>
The Kotlin Multiplatform artifacts are accessible through <a href="https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk">Maven Central</a>

````kotlin
// Add maven central repository
repositories {
    mavenCentral()
}

// Import the package in the common source set:
implementation("com.doordeck.headless.sdk:doordeck-sdk:[SDK_VERSION]")
````
>:information_source: The supported platforms are: JVM, Android, jsNode, jsBrowser, iOS (x64), iOS (ARM x64), iOS simulator (ARM x64), macOS (ARM x64) and mingW (x64)
</details>

<details><summary>JVM</summary>
The JVM artifacts are accessible through <a href="https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk-jvm">Maven Central</a>

````kotlin
// Add maven central repository
repositories {
    mavenCentral()
}

// Import the JVM package
implementation("com.doordeck.headless.sdk:doordeck-sdk-jvm:[SDK_VERSION]")
````
>:information_source: The JVM package requires at least Java SDK 1.8
</details>

<details><summary>Android</summary>
The Android artifacts are accessible through <a href="https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk-android">Maven Central</a>

````kotlin
// Add maven central repository
repositories {
    mavenCentral()
}

// Import the Android package
implementation("com.doordeck.headless.sdk:doordeck-sdk-android:[SDK_VERSION]")
````
>:information_source: The Android package requires at least Android SDK 21
</details>

<details><summary>Swift</summary>
The iOS and macOS packages are accessible through <a href="https://cocoapods.org/pods/DoordeckSDK">CocoaPods</a> and <a href="https://github.com/doordeck/doordeck-headless-sdk-spm">SPM</a>

````swift
pod 'DoordeckSDK', '~> [SDK_VERSION]'
````
>:information_source: The iOS package requires at least iOS version 14
</details>

<details><summary>JS</summary>
The JS artifacts are accessible through <a href="https://www.npmjs.com/package/@doordeck/doordeck-headless-sdk">NPM</a>

````cmd
npm install @doordeck/doordeck-headless-sdk --save
````
</details>

<details><summary>C#</summary>

````csharp
// TODO
````
</details>

:arrow_left: [Back to index](01_INDEX.md)