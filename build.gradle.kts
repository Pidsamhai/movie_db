// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Dependencies.AGT)
        classpath(Dependencies.KotlinGradlePlugin)
        classpath(Dependencies.KotlinSerialization)
        classpath(Dependencies.Hilt)
        classpath(Dependencies.ObjectBox)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}