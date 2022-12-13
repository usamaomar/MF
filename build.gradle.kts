plugins {
    id("com.google.dagger.hilt.android") version Versions.HILT_ANDROID apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
        maven(url = "https://maven.aliyun.com/repository/jcenter")
        maven(url = "https://storage.googleapis.com/r8-releases/raw")
    }

    dependencies {
        classpath("com.android.tools:r8:3.3.75")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_SAFE_ARGS}")
    }

}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}