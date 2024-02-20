import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath(kotlin("gradle-plugin", version = "2.0.0-Beta4"))

    }
}
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0-Beta4" apply false
    id("com.google.devtools.ksp") version "2.0.0-Beta4-1.0.17" apply false
    id("org.jetbrains.kotlin.jvm") version "2.0.0-Beta4" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}


apply(from = "$rootDir/environment.gradle.kts")


/**
 * Sets configurations for all project modules
 *@since 1.0.0
 */
allprojects {
    configureKapt()
    configureAndroid()
}


/**
 * Sets kapt configurations
 *@since 1.0.0
 */
fun Project.configureKapt() {
    apply(plugin = "kotlin-kapt")
    configure<KaptExtension> {
        correctErrorTypes = true
        generateStubs = false

    }
}


/**
 * Sets common android configurations for all modules
 *@since 1.0.0
 */
fun Project.configureAndroid() {
    configurations.register("compileClasspath") // see https://youtrack.jetbrains.com/issue/KT-27170
    val tree = (group as String).split(".")

    // applies common plugins to modules and sub modules
    // if you added any modules please mention them below
    when {
        tree.contains("library") -> {
            apply(plugin = "com.android.library")
            apply(plugin = "kotlin-android")
            apply(plugin = "com.google.dagger.hilt.android")
            apply(plugin = "com.google.devtools.ksp")

        }

        tree.contains("app") -> {
            apply(plugin = "com.android.application")
            apply(plugin = "kotlin-android")
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

        }

        tree.contains("examples") -> {
            apply(plugin = "com.android.application")
            apply(plugin = "kotlin-android")
            apply(plugin = "com.google.devtools.ksp")
            apply(plugin = "com.google.dagger.hilt.android")

        }

        tree.contains("feature") -> apply(plugin = "com.android.dynamic-feature")
        else -> return
    }


//     Resource packaging breaks otherwise for some reason
//    tasks.matching { it.name.contains("Test") }.configureEach { enabled = false }


    //Environment enum with different environment like DEV, UAT, PRE-PROD, PROD
    val environmentClassName = "com.fermion.android.base.config.Environment"

    //Base Android configurations
    //Add extra configurations which should be used in modules.


    configure<BaseExtension> {
        defaultConfig {
            minSdk = rootProject.extra["baseMinSDK"] as Int
            versionCode = rootProject.extra["versionCode"] as Int
            versionName = rootProject.extra["versionName"].toString()
            vectorDrawables.useSupportLibrary = true

            buildConfigField(
                environmentClassName,
                "ENVIRONMENT",
                "${environmentClassName}.${rootProject.extra["environment"]}"
            )

            buildConfigField(
                "String", "BASE_API_URL", """  "${rootProject.extra["baseUrl"]}" """
            )

            buildConfigField(
                "String", "sslPinnerSha256", """  "${rootProject.extra["sslPinnerSha256"]}" """
            )
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        (this as ExtensionAware).configure<KotlinJvmOptions> {
            jvmTarget = "17"
        }
        buildFeatures.apply {
            buildConfig = true
            viewBinding = true
        }

    }



    when {
        tree.contains("library") -> {
            configure<LibraryExtension> {
                compileSdk = rootProject.extra["baseCompileSDK"] as Int
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                lint {
                    checkReleaseBuilds = true
                }
            }

            apply(from = "$rootDir/dependencies.gradle.kts")

        }


        tree.contains("app") || tree.contains("examples") -> {
            configure<BaseAppModuleExtension> {
                compileSdk = rootProject.extra["baseCompileSDK"] as Int
                defaultConfig {
                    targetSdk = rootProject.extra["baseTargetSDK"] as Int
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                lint {
                    checkReleaseBuilds = true
                }


                //sets output file name
                applicationVariants.all {
                    this.outputs.all {

                        //Change the app name if you want different name on your generated apk
                        val appName = "app"

                        //current env
                        val env = (rootProject.extra["environment"].toString()).lowercase()

                        //apk file name (can be modified)
                        val apkFileName = "${appName}-${env}-v${versionName}-${
                            LocalDateTime.now().format(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy_hh-mm")
                            )
                        }"

                        //checking build type
                        when (buildType.name) {
                            "debug" -> {
                                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                                    "debug-${apkFileName}.apk"
                            }

                            "release" -> {
                                (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                                    "${apkFileName}.apk"
                            }
                        }
                    }
                }
            }

            //add base or core lib here
            dependencies {
                "implementation"(project(mapOf("path" to ":library:base")))
            }

            apply(from = "$rootDir/dependencies.gradle.kts")


        }

        else -> {
            println("Tree contains no modules to apply config")
        }
    }


}
