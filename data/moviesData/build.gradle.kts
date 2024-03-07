plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "by.bashlikovvv.moviesdata"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Dependencies.AndroidX.Core.coreKTX)
    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    testImplementation(Dependencies.JUnit.jUnit)
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.jUnit)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.espressoCore)

    implementation(Dependencies.Com.Google.Code.Gson.gson)
    implementation(Dependencies.Com.SquareUp.Retrofit2.retrofit)
    implementation(Dependencies.Com.SquareUp.Retrofit2.converterGson)

    implementation(Dependencies.AndroidX.Paging.pagingRuntime)

    implementation(Dependencies.AndroidX.Room.roomKTX)
    implementation(Dependencies.AndroidX.Room.roomRuntime)
    implementation(Dependencies.AndroidX.Room.roomPaging)
    ksp(Dependencies.AndroidX.Room.roomCompiler)
}