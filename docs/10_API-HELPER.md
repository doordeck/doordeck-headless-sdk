# Helper resource

This function facilitates the upload of a logo into your application in a single call. Below are examples for different platforms.

## Upload platform logo

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES)
```

💡 **Note:** In Java, use the `uploadPlatformLogoAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.helper().uploadPlatformLogo(applicationId: "APPLICATION_ID", contentType: "CONTENT_TYPE", image: IMAGE_BYTES)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UploadPlatformLogoData("APPLICATION_ID", "CONTENT_TYPE", "BASE64_IMAGE");
sdk.GetHelper().UploadPlatformLogo(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UploadPlatformLogoData("APPLICATION_ID", "CONTENT_TYPE", "BASE64_IMAGE")
sdk.helper.upload_platform_logo(data)
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
