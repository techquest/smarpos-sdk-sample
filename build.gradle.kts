// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}
repositories {
    google()
    maven { setUrl("https://jitpack.io") }
    maven { setUrl("https://mirrors.huaweicloud.com/repository/maven/") }
    mavenCentral()
}
