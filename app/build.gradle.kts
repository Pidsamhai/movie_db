plugins {
    id(Plugins.AndroidApplication)
    id(Plugins.Android)
    id(Plugins.KotlinXSerialization)
    id(Plugins.Parcelize)
    kotlin(Plugins.Kapt)
    id(Plugins.Hilt)
    id(Plugins.GitVersion) version Versions.GitVersion
    id(Plugins.ObjectBox)
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildToolsVersion

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = when (val code = androidGitVersion.code()) {
            0 -> 1
            else -> code
        }
        versionName = androidGitVersion.name()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
            storePassword = System.getenv("STORE_PASSWORD")
            storeFile = file("keystore.jks")
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xallow-jvm-ir-dependencies"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose
    }

    externalNativeBuild {
        cmake {
            path("CMakeLists.txt")
        }
    }

    kapt {
        javacOptions {
            option("--source", "8")
            option("--target", "8")
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
    implementation(Dependencies.LiveDataKtx)
    implementation(Dependencies.ComposeLiveData)
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

    implementation(Dependencies.ObjectBoxKotlin)

    implementation(Dependencies.Timber)

    /**
     * Fix JDK 11 Compile Error
     */
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    compileOnly("com.github.pengrad:jdk9-deps:22e725c32e")
}