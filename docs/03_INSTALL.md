# Install the SDK into your project

Easily integrate the Doordeck SDK into your project by following the platform-specific instructions below.

### Kotlin Multiplatform
<details>
<summary>Show Details</summary>

The Kotlin Multiplatform artifacts are available on [Maven Central](https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk).

```kotlin
// Add Maven Central repository
repositories {
    mavenCentral()
}

// Import the package in the common source set
implementation("com.doordeck.headless.sdk:doordeck-sdk:[SDK_VERSION]")
```

💡 **Supported Platforms:** JVM, Android, jsNode, jsBrowser, iOS (x64, ARM x64, simulator ARM x64), macOS (ARM x64), and mingW (x64).
</details>

### JVM
<details>
<summary>Show Details</summary>

The JVM artifacts are available on [Maven Central](https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk-jvm).

```kotlin
// Add Maven Central repository
repositories {
    mavenCentral()
}

// Import the JVM package
implementation("com.doordeck.headless.sdk:doordeck-sdk-jvm:[SDK_VERSION]")
```

💡 **Requirement:** Java SDK 1.8 or higher.
</details>

### Android
<details>
<summary>Show Details</summary>

The Android artifacts are available on [Maven Central](https://central.sonatype.com/artifact/com.doordeck.headless.sdk/doordeck-sdk-android).

```kotlin
// Add Maven Central repository
repositories {
    mavenCentral()
}

// Import the Android package
implementation("com.doordeck.headless.sdk:doordeck-sdk-android:[SDK_VERSION]")
```

💡 **Requirement:** Android SDK 26 or higher.
</details>

### Swift
<details>
<summary>Show Details</summary>

The iOS and macOS packages are available via [CocoaPods](https://cocoapods.org/pods/DoordeckSDK) and [Swift Package Manager (SPM)](https://github.com/doordeck/doordeck-headless-sdk-spm).

#### CocoaPods
```swift
pod 'DoordeckSDK', '~> [SDK_VERSION]'
```

#### Swift Package Manager
1. In Xcode, select **File > Add Package Dependencies...**.
2. Enter the URL: [https://github.com/doordeck/doordeck-headless-sdk-spm](https://github.com/doordeck/doordeck-headless-sdk-spm).
3. Choose **Up to next major version** from the dependency rule dropdown, and click **Add Package**.

💡 **Requirement:** iOS 14 or higher.
</details>

### JavaScript
<details>
<summary>Show Details</summary>

The JavaScript artifacts are available on [NPM](https://www.npmjs.com/package/@doordeck/doordeck-headless-sdk)

```bash
npm install @doordeck/doordeck-headless-sdk --save
```
</details>

### C#
<details>
<summary>Show Details</summary>

The Windows artifacts for C# are available on [NuGet](https://www.nuget.org/packages/Doordeck.Headless.Sdk).

```csharp
dotnet add package Doordeck.Headless.Sdk
```
💡 **Requirement:** In Visual Studio, it's necessary to enable the unsafe block with ```<AllowUnsafeBlocks>true</AllowUnsafeBlocks>```
</details>

### Python
<details>
<summary>Show Details</summary>

The Windows artifacts for Python are available on [PyPi](https://pypi.org/project/doordeck-headless-sdk/).

```python
pip install doordeck-headless-sdk
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
