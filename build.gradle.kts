plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinxSerialization).apply(false)
    id("com.netflix.nebula.release") version "19.0.8"
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.16.3"
}
