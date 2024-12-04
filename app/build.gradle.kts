import java.util.Properties

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

        // Read properties from secrets.properties
        val secretsProperties = Properties().apply {
            val secretsFile = rootProject.file("secrets.properties")
            if (secretsFile.exists()) {
                load(secretsFile.inputStream())
            }
        }

        // Add each property as a build config field
        buildConfigField("String", "CLIENT_ID", "\"${secretsProperties["CLIENT_ID"] ?: ""}\"")
        buildConfigField("String", "CLIENT_SECRET", "\"${secretsProperties["CLIENT_SECRET"] ?: ""}\"")
        buildConfigField("String", "ALIAS", "\"${secretsProperties["ALIAS"] ?: ""}\"")
        buildConfigField("String", "MERCHANT_CODE", "\"${secretsProperties["MERCHANT_CODE"] ?: ""}\"")
        buildConfigField("String", "MERCHANT_TELEPHONE", "\"${secretsProperties["MERCHANT_TELEPHONE"] ?: ""}\"")
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
        buildConfig = true
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
    implementation(libs.smartpos.pax)

    // Pax Kernel SDK
    implementation(files("libs/NeptuneLiteApi_V2.03.00_20180208.jar"))

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}