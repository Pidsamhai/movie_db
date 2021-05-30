plugins {
    id(Plugins.AndroidApplication)
    id(Plugins.Android)
    id(Plugins.KotlinXSerialization)
    id(Plugins.Parcelize)
    kotlin(Plugins.Kapt)
    id(Plugins.Hilt)
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose
    }

    externalNativeBuild {
        cmake {
            path ("CMakeLists.txt")
        }
    }
}

dependencies {
    implementation(Dependencies.CoreKtx)
    implementation(Dependencies.AppCompat)
    implementation(Dependencies.Material)
    implementation(Dependencies.ComposeUi)
    implementation(Dependencies.ComposeMaterial)
    implementation(Dependencies.ComposeUiTool)
    implementation(Dependencies.LifecycleKtx)
    implementation(Dependencies.ActivityCompose)

    testImplementation(Dependencies.Test.Junit)
    androidTestImplementation(Dependencies.Test.AndroidxJunit)
    androidTestImplementation(Dependencies.Test.Espresso)
    androidTestImplementation(Dependencies.Test.ComposeJunit)

    implementation(Dependencies.NavigationCompose)
    implementation(Dependencies.AccompanistCoil)
    implementation(Dependencies.KtorClient)
    implementation(Dependencies.KtorSerialization)
    implementation(Dependencies.KotlinxSerialization)
    implementation(Dependencies.OkhttpInterceptor)

    implementation(Dependencies.HiltAndroid)
    kapt(Dependencies.HiltCompiler)
    implementation(Dependencies.HiltNavigationCompose)
}