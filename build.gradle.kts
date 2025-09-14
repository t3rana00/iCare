// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {}
}

plugins {
    id("com.android.application") version "8.6.0" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    // Compose Compiler Gradle plugin is tied to Kotlin 2.x
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
    // KSP version must match Kotlin major/minor
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
}
