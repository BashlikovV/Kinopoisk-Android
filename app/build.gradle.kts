plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "by.bashlikovvv.kinopoisk_android"
    compileSdk = 34

    defaultConfig {
        applicationId = "by.bashlikovvv.kinopoisk_android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":features:homeScreen"))
    implementation(project(":features:movieDetailsScreen"))
    implementation(project(":data:moviesData"))

    implementation(Dependencies.AndroidX.Core.coreKTX)
    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.Com.Google.Android.Material.material)
    implementation(Dependencies.AndroidX.ConstraintLayout.constraintlayout)
    testImplementation(Dependencies.JUnit.jUnit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation(Dependencies.AndroidX.Test.Ext.jUnit)
    androidTestImplementation(Dependencies.AndroidX.Test.Espresso.espressoCore)

    // Navigation
    implementation(Dependencies.AndroidX.Navigation.navigationFragmentKTX)
    implementation(Dependencies.AndroidX.Navigation.navigationUiKTX)

    //Dagger
    implementation(Dependencies.Com.Google.Dagger.dagger)
    implementation(Dependencies.Com.Google.Dagger.daggerAndroid)
    implementation(Dependencies.Com.Google.Dagger.daggerAndroidSupport)
    kapt(Dependencies.Com.Google.Dagger.daggerCompiler)

    implementation(Dependencies.AndroidX.LifeCycle.lifecycleExtencions)
    implementation(Dependencies.AndroidX.LifeCycle.lifeCycleViewModel)
    implementation(Dependencies.AndroidX.LifeCycle.lifecycleRuntime)

    implementation(Dependencies.Com.Google.Code.Gson.gson)
    implementation(Dependencies.Com.SquareUp.Retrofit2.retrofit)
    implementation(Dependencies.Com.SquareUp.Retrofit2.converterGson)

    implementation(Dependencies.AndroidX.Core.coreSplashScreen)

    implementation(Dependencies.AndroidX.Paging.pagingRuntime)

    implementation(Dependencies.AndroidX.Room.roomKTX)
    implementation(Dependencies.AndroidX.Room.roomRuntime)

}

kapt {
    correctErrorTypes = true
    arguments {
        arg("dagger.fastInit", "enabled")
//
        arg("dagger.fullBindingGraphValidation", "WARNING")
        arg("dagger.fullBindingGraphValidation", "ERROR")
    }
}