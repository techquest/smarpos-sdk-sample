plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.interswitchng.smartpossdksample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.interswitchng.smartpossdksample"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            applicationIdSuffix = ".pax.release"
        }

        debug {
            applicationIdSuffix = ".pax.debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Application dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // SmartPos dependencies
    implementation(libs.smartpos.core) {
        exclude(group = "org.koin")
    }

    implementation(libs.smartpos.pax)

    // AAR files for dependencies needed to run
    implementation(files("libs/koin-core-1.0.2.jar"))
    implementation(files("libs/koin-test-1.0.2.jar"))
    implementation(files("libs/koin-android-1.0.2.aar"))
    implementation(files("libs/koin-android-viewmodel-1.0.2.aar"))

    // Pax Kernel SDK
    implementation(files("libs/NeptuneLiteApi_V2.03.00_20180208.jar"))

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}