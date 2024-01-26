plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "by.bashlikovvv.moviedetailsscreen"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(Dependencies.Com.Github.BumpTech.Glide.glide)
}

kapt {
    correctErrorTypes = true
}