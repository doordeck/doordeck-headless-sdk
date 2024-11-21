// swift-tools-version:5.3
import PackageDescription

let package = Package(
   name: "DoordeckSDK",
   platforms: [
     .iOS(.v14),
     .macOS(.v11),
   ],
   products: [
      .library(name: "DoordeckSDK", targets: ["DoordeckSDK"])
   ],
   targets: [
      .binaryTarget(
         name: "DoordeckSDK",
         url: "https://cdn.doordeck.com/xcframework/v1.0.0/DoordeckSDK.xcframework.zip",
         checksum: "d9192011406d14f00a948b144f9bdec9f29934a82b31aaf7f1261c7f98483c67"
      )
   ]
)