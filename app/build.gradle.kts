plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {

    compileSdk = 33
    val majorVersionName = "0"
    val minorVersionName = "0"
    val hotfixVersionName = "0"
    val fullVersionName = "$majorVersionName.$minorVersionName.$hotfixVersionName"


    defaultConfig {
        applicationId = "com.example.myapplication.myapplication.usamas"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = fullVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled =  false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }

    dataBinding {
        addKtx = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    namespace = "com.example.myapplication.myapplication.usamas"

}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
    testImplementation("junit:junit:4.13.2")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    //Core
    implementation("androidx.core:core-ktx:1.9.0")

    //Work Manager
    implementation("androidx.work:work-runtime-ktx:2.7.1")
    implementation("androidx.hilt:hilt-work:1.0.0")

    //Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINE}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINE}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN}")
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")


    //ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("com.google.android.material:material:1.7.0")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.LIFECYCLE}")


    //OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //Moshi
    implementation("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}")
    implementation("com.squareup.moshi:moshi-adapters:${Versions.MOSHI}")
    implementation("com.squareup.moshi:moshi:${Versions.MOSHI}")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}")


    //Mapper
    api("com.github.pozo:mapstruct-kotlin:1.4.0.0")
    kapt("com.github.pozo:mapstruct-kotlin-processor:1.4.0.0")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}")

     //Hilt
    implementation("com.google.dagger:hilt-android:${Versions.HILT_ANDROID}")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.HILT_ANDROID}")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    //EncryptedSharedPreferences
    implementation("androidx.security:security-crypto:1.0.0")

    //Compose
    val composeBom = platform("androidx.compose:compose-bom:${Versions.COMPOSE}")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    implementation("androidx.fragment:fragment-ktx:1.5.4")

    implementation("io.coil-kt:coil-compose:2.2.2")

}
kapt {
    correctErrorTypes = true
}

