// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository
        jcenter()
        flatDir {
            dirs("libs")
        }



    }
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.2")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.46.1")
        classpath ("com.google.gms:google-services:4.3.15")



    }

}

plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "7.2.2" apply false


}