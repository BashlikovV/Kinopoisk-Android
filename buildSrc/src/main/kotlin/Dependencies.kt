object Dependencies {
    object AndroidX {
        object Core {
            private const val nameSpace = "androidx.core"
            const val coreKTX = "$nameSpace:core-ktx:${Versions.AndroidX.Core.coreKTX}"
            const val coreSplashScreen = "$nameSpace:core-splashscreen:${Versions.AndroidX.Core.coreSplashScreen}"
        }
        object AppCompat {
            private const val nameSpace = "androidx.appcompat"
            const val appCompat = "$nameSpace:appcompat:${Versions.AndroidX.AppCompat.appCompat}"
        }
        object ConstraintLayout {
            private const val nameSpace = "androidx.constraintlayout"
            const val constraintlayout = "$nameSpace:constraintlayout:${Versions.AndroidX.ConstraintLayout.constraintLayout}"
        }
        object Test {
            object Ext {
                private const val nameSpace = "androidx.test.ext"
                const val jUnit = "$nameSpace:junit:${Versions.AndroidX.Test.Ext.jUnit}"
            }
            object Espresso {
                private const val nameSpace = "androidx.test.espresso"
                const val espressoCore = "$nameSpace:espresso-core:${Versions.AndroidX.Test.Espresso.espressoCore}"
            }
        }
        object LifeCycle {
            private const val nameSpace = "androidx.lifecycle"
            const val lifeCycleViewModel = "$nameSpace:lifecycle-viewmodel-ktx:${Versions.AndroidX.LifeCycle.lifeCycleViewModel}"
            const val lifecycleExtencions = "$nameSpace:lifecycle-extensions:${Versions.AndroidX.LifeCycle.lifeCycleExtensions}"
            const val lifecycleRuntime = "$nameSpace:lifecycle-runtime-ktx:${Versions.AndroidX.LifeCycle.lifeCycleViewModel}"
        }
        object Navigation {
            private const val nameSpace = "androidx.navigation"
            const val navigationFragmentKTX = "$nameSpace:navigation-fragment-ktx:${Versions.AndroidX.Navigation.navigationFragmentKTX}"
            const val navigationUiKTX = "$nameSpace:navigation-ui-ktx:${Versions.AndroidX.Navigation.navigationUiKTX}"
            const val navigationDynamicFeatures = "$nameSpace:navigation-dynamic-features-fragment:${Versions.AndroidX.Navigation.navigationUiKTX}"
        }
        object Room {
            private const val nameSpace = "androidx.room"
            const val roomKTX = "$nameSpace:room-ktx:${Versions.AndroidX.Room.roomKTX}"
            const val roomRuntime = "$nameSpace:room-runtime:${Versions.AndroidX.Room.roomKTX}"
            const val roomCompiler = "$nameSpace:room-compiler:${Versions.AndroidX.Room.roomKTX}"
            const val roomPaging = "$nameSpace:room-paging:${Versions.AndroidX.Room.roomKTX}"
        }
        object Camera {
            private const val nameSpace = "androidx.camera"
            const val core = "$nameSpace:camera-core:${Versions.AndroidX.Camera.camera}"
            const val camera2 = "$nameSpace:camera-camera2:${Versions.AndroidX.Camera.camera}"
            const val lifecycle = "$nameSpace:camera-lifecycle:${Versions.AndroidX.Camera.camera}"
            const val view = "$nameSpace:camera-view:${Versions.AndroidX.Camera.camera}"
        }
        object Paging {
            private const val nameSpace = "androidx.paging"
            const val pagingRuntime = "$nameSpace:paging-runtime-ktx:3.2.0"
        }
        object SwipeRefreshLayout {
            private const val nameSpace = "androidx.swiperefreshlayout"
            const val swipeRefreshLayout = "$nameSpace:swiperefreshlayout:1.1.0"
        }
    }
    object Com {
        object Google {
            object Android {
                object Material {
                    private const val nameSpace = "com.google.android.material"
                    const val material = "$nameSpace:material:${Versions.Com.Google.Android.Material.material}"
                }
                object Gms {
                    private const val nameSpace = "com.google.android.gms"
                    const val playServices = "$nameSpace:play-services-vision:${Versions.Com.Google.Android.Gms.playServices}"
                }
            }
            object Dagger {
                private const val nameSpace = "com.google.dagger"
                const val dagger = "$nameSpace:dagger:${Versions.Com.Google.Dagger.dagger}"
                const val daggerAndroid = "$nameSpace:dagger-android:${Versions.Com.Google.Dagger.dagger}"
                const val daggerAndroidSupport = "$nameSpace:dagger-android-support:${Versions.Com.Google.Dagger.dagger}"
                const val daggerCompiler = "$nameSpace:dagger-compiler:${Versions.Com.Google.Dagger.dagger}:"
            }
            object Code {
                object Gson {
                    private const val nameSpace = "com.google.code.gson"
                    const val gson = "$nameSpace:gson:${Versions.Com.Google.Code.gson}"
                }
            }
        }
        object Github {
            object BumpTech {
                object Glide {
                    private const val nameSpace = "com.github.bumptech.glide"
                    const val glide = "$nameSpace:glide:${Versions.Com.BumpTech.Glide.glide}"
                }
            }
            object Kirich1409 {
                private const val nameSpace = "com.github.kirich1409"
                const val viewBindingPropertyDelegate = "$nameSpace:viewbindingpropertydelegate-full:${Versions.Com.Kirich1409.viewBindingPropertyDelegate}"
            }
        }
        object SquareUp {
            object Retrofit2 {
                private const val nameSpace = "com.squareup.retrofit2"
                const val retrofit = "$nameSpace:retrofit:${Versions.Com.SquareUp.retrofit}"
                const val converterGson = "$nameSpace:converter-gson:${Versions.Com.SquareUp.retrofit}"
                const val adapterRxJava2 = "$nameSpace:adapter-rxjava2:${Versions.Com.SquareUp.retrofit}"
            }
        }
    }
    object JUnit {
        private const val nameSpace = "junit"
        const val jUnit = "$nameSpace:junit:${Versions.JUnit.jUnit}"
    }
    object Io {
        object ReactiveX {
            object RxJava2 {
                private const val nameSpace = "io.reactivex.rxjava2"
                const val rxJava = "$nameSpace:rxjava:2.2.20"
                const val rxAndroid = "$nameSpace:rxandroid:2.1.1"
                const val rxKotlin = "$nameSpace:rxkotlin:2.3.0"
            }
        }
    }
}