import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id(Plugins.AndroidApplication)
    id(Plugins.Android)
    kotlin(Plugins.Kapt)
    id(Plugins.KotlinXSerialization)
    id(Plugins.Parcelize)
    id(Plugins.Hilt)
    id(Plugins.GitVersion) version Versions.GitVersion
    id(Plugins.ObjectBox)
}

val properties = Properties()
val propertiesFile = File("key.properties")
propertiesFile.inputStream().use {
    properties.load(it)
}

android {

    androidGitVersion {
        codeFormat = "MNNPPPRR"
    }

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

        buildConfigField("String", "REPOSITORY", "\"https://github.com/Pidsamhai/movie_db\"")
    }

    signingConfigs {
        create("release") {
            keyAlias = properties["KEY_ALIAS"] as String?
            keyPassword = properties["KEY_PASSWORD"] as String?
            storePassword = properties["STORE_PASSWORD"] as String?
            storeFile = file("keystore.jks")
            enableV3Signing = true
            enableV4Signing = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        allWarningsAsErrors = false
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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
}

dependencies {
    implementation(Libs.CoreKtx)
    implementation(Libs.AppCompat)
    implementation(Libs.Material)
    implementation(Libs.ComposeUi)
    implementation(Libs.ComposeMaterial)
    implementation(Libs.ComposeUiTool)
    debugImplementation(Libs.ComposeUiToolPreView)
    implementation(Libs.LifecycleKtx)
    implementation(Libs.LiveDataKtx)
    implementation(Libs.ComposeLiveData)
    implementation(Libs.ActivityCompose)

    testImplementation(Libs.Test.Junit)
    androidTestImplementation(Libs.Test.AndroidxJunit)
    androidTestImplementation(Libs.Test.Espresso)
    androidTestImplementation(Libs.Test.ComposeJunit)

    implementation(Libs.NavigationCompose)
    implementation(Libs.Coil)
    implementation(Libs.KtorClient)
    implementation(Libs.KtorSerialization)
    implementation(Libs.KotlinxSerialization)
    implementation(Libs.OkhttpInterceptor)

    implementation(Libs.HiltAndroid)
    kapt(Libs.HiltCompiler)
    implementation(Libs.HiltNavigationCompose)

    implementation(Libs.ObjectBoxKotlin)

    implementation(Libs.Timber)
    implementation(Libs.ComposePaging)
    implementation(Libs.LottieCompose)
    implementation(Libs.ConstraintLayoutCompose)
    implementation(Libs.Accompanist.PlaceHolderMaterial)
    implementation(Libs.Accompanist.Pager)
    implementation(Libs.Accompanist.PagerIndicator)
    implementation(Libs.Accompanist.SwipeRefresh)
    implementation(Libs.KotlinxDatetime)

    /**
     * Fix JDK 11 Compile Error
     */
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    compileOnly("com.github.pengrad:jdk9-deps:22e725c32e")
}