Pod::Spec.new do |spec|
    spec.name                     = 'DoordeckSDK'
    spec.version                  = '0.77.0-dev.1.uncommitted+bb7f412'
    spec.homepage                 = 'https://www.doordeck.com'
    spec.source                   = { :http=> ''}
    spec.authors                  = 'Doordeck Limited'
    spec.license                  = { :type => 'Apache-2.0' }
    spec.summary                  = 'Doordeck Headless SDK'
                
    spec.libraries                = 'c++'
    spec.ios.deployment_target    = '14'
                
                
                
    spec.xcconfig = {
        'ENABLE_USER_SCRIPT_SANDBOXING' => 'NO',
    }
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':doordeck-sdk',
        'PRODUCT_MODULE_NAME' => 'DoordeckSDK',
    }
                
    spec.script_phases = [
        {
            :name => 'Build DoordeckSDK',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.readme = 'https://github.com/doordeck/doordeck-headless-sdk/blob/main/README.md'
    spec.vendored_frameworks = 'DoordeckSDK.xcframework'
end