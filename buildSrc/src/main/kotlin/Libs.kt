object Libs {
    const val CoreKtx = "androidx.core:core-ktx:${Versions.CoreKtx}"
    const val AppCompat = "androidx.appcompat:appcompat:${Versions.AppCompat}"
    const val Material = "com.google.android.material:material:${Versions.Material}"
    const val ComposeUi = "androidx.compose.ui:ui:${Versions.ComposeLib}"
    const val ComposeMaterial = "androidx.compose.material:material:${Versions.ComposeLib}"
    const val ComposeUiTool = "androidx.compose.ui:ui-tooling:${Versions.ComposeLib}"
    const val ComposeUiToolPreView = "androidx.compose.ui:ui-tooling-preview:${Versions.ComposeLib}"
    const val ActivityCompose = "androidx.activity:activity-compose:${Versions.ActivityCompose}"
    const val LifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.Lifecycle}"
    const val LiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Lifecycle}"

    object Test {
        const val Junit = "junit:junit:${Versions.Junit}"
        const val AndroidxJunit = "androidx.test.ext:junit:${Versions.AndroidxJunit}"
        const val Espresso = "androidx.test.espresso:espresso-core:${Versions.Espresso}"
        const val ComposeJunit = "androidx.compose.ui:ui-test-junit4:${Versions.ComposeLib}"
    }

    const val NavigationCompose = "androidx.navigation:navigation-compose:${Versions.NavigationCompose}"
    const val Coil = "io.coil-kt:coil-compose:${Versions.Coil}"
    const val KtorClient = "io.ktor:ktor-client-okhttp:${Versions.Ktor}"
    const val KtorSerialization = "io.ktor:ktor-client-serialization:${Versions.Ktor}"
    const val KotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KotlinxSerialization}"
    const val OkhttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Okhttp}"

    const val HiltAndroid = "com.google.dagger:hilt-android:${Versions.Hilt}"
    const val HiltCompiler = "com.google.dagger:hilt-compiler:${Versions.Hilt}"
    const val HiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.HiltNavigationCompose}"

    const val ComposeLiveData = "androidx.compose.runtime:runtime-livedata:${Versions.ComposeLib}"

    const val ObjectBoxKotlin = "io.objectbox:objectbox-kotlin:${Versions.ObjectBox}"
    const val Timber = "com.jakewharton.timber:timber:${Versions.Timber}"
    const val ComposePaging = "androidx.paging:paging-compose:${Versions.ComposePaging}"
    const val LottieCompose = "com.airbnb.android:lottie-compose:${Versions.LottieCompose}"
    const val ConstraintLayoutCompose = "androidx.constraintlayout:constraintlayout-compose:${Versions.ConstraintLayoutCompose}"
    const val KotlinxDatetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KotlinxDatetime}"

    object Accompanist {
        const val Pager = "com.google.accompanist:accompanist-pager:${Versions.Accompanist}"
        const val PlaceHolderMaterial = "com.google.accompanist:accompanist-placeholder-material:${Versions.Accompanist}"
        const val PagerIndicator = "com.google.accompanist:accompanist-pager-indicators:${Versions.Accompanist}"
        const val SwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.Accompanist}"
    }
}