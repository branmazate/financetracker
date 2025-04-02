plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android) apply true
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.financetracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.financetracker"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions{
                arguments += mapOf(
                    "room.schemaLocation" to "${projectDir}/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    ksp {
        arg("room.schemaLocation", "${projectDir}/schemas")
        arg("me.tatarka.incremental", "true")
    }

    sourceSets {
        getByName("main") {
            java.srcDirs("build/generated/ksp")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //Jetpack Compose
    implementation (libs.androidx.activity.compose.v1101)
    implementation (libs.material3)
    implementation (libs.androidx.lifecycle.viewmodel.compose)

    //Room Database
    implementation (libs.androidx.room.runtime)
    implementation(libs.androidx.paging.common.android)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.firebase.functions.ktx)
    implementation(libs.androidx.junit.ktx)
    ksp (libs.androidx.room.compiler)

    //Graphics
    implementation (libs.mpandroidchart)

    implementation (libs.gson)

    implementation (libs.hilt.android)

    ksp (libs.hilt.compiler)

    implementation (libs.javapoet)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.work.runtime.ktx){
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}