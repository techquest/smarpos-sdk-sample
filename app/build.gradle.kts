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

    signingConfigs {
        create("kozenConfig") {
            keyAlias = "ISWKozenOSSignature"
            keyPassword = "ISWKozen2024"
            storeFile = file("kozen2024.jks")
            storePassword = "ISWKozen2024"
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            applicationIdSuffix = ".kozen.release"
            signingConfig = signingConfigs.getByName("kozenConfig")
        }
        debug {
            applicationIdSuffix = ".kozen.debug"
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
    implementation(libs.smartpos.core)
    api(libs.smartpos.kozen)

    implementation(files("libs/koin-android-1.0.2.aar"))
    implementation(files("libs/koin-android-viewmodel-1.0.2.aar"))
//    implementation(files("libs/kozen_emv_bundle.aar"))
    implementation(files("libs/app-release.aar"))

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}