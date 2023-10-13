import java.util.Date
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id(Plugins.AndroidApplication)
    id(Plugins.Android)
    kotlin(Plugins.Kapt)
    id(Plugins.KotlinXSerialization)
    id(Plugins.Parcelize)
    id(Plugins.Hilt)
    id(Plugins.ObjectBox)
    id(Plugins.KotlinKapt)
}

val properties = Properties()
val propertiesFile = File("key.properties")
propertiesFile.inputStream().use {
    properties.load(it)
}

fun getVersionCode(startAt: Int = 0): Int {
    return try {
        val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
        exec {
            commandLine = arrayOf("git", "rev-list", "--count", "HEAD").toMutableList()
            standardOutput = stdout
        }
        Integer.parseInt(stdout.toString().trim()) + startAt
    }
    catch (e: Exception) {
        1 + startAt
    }
}

fun getVersionName(default: String = "0.0.0.0" ): String {
    return try {
        val stdout = org.apache.commons.io.output.ByteArrayOutputStream()
        exec {
            commandLine = arrayOf("git", "describe", "--tags").toMutableList()
            standardOutput = stdout
        }
        val out = stdout.toString().trim()
        if(out.isEmpty()) {
           return default
        }
        return out
    }
    catch (e: Exception) {
        default
    }
}

fun getBuildMachine(): String {
    return "%s %s".format(System.getProperty("os.name"), System.getProperty("os.version"))
}

fun getBuildDate(): String {
    return Date().toString()
}

android {

    compileSdk = Android.compileSdk

    defaultConfig {
        applicationId = DefaultConfig.applicationId
        minSdk = DefaultConfig.minSdk
        targetSdk = DefaultConfig.targetSdk
        versionCode = getVersionCode(2030000000)
        versionName = getVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "REPOSITORY", "\"https://github.com/Pidsamhai/movie_db\"")
        buildConfigField("String", "BUILD_ON", "\"%s\"".format(getBuildMachine()))
        buildConfigField("String", "BUILD_DATE", "\"%s\"".format(getBuildDate()))
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
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
    namespace = "com.github.psm.moviedb"
    buildFeatures {
        buildConfig = true
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(Libs.CoreKtx)
    implementation(Libs.AppCompat)
    implementation(Libs.Material)
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.5")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
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

    implementation("androidx.hilt:hilt-work:1.0.0")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

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
    implementation("androidx.work:work-runtime-ktx:2.8.1")
}

kapt {
    correctErrorTypes = true
}