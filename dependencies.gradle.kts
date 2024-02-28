/**
 * Gradle KTS file to set dependencies.
 *
 * **Note** Set Common Dependencies used in all modules here
 *
 *@since 1.0.0
 */

apply(plugin = "kotlin-kapt")
apply(plugin = "androidx.navigation.safeargs.kotlin")
apply(plugin = "com.google.devtools.ksp")

val kapt by configurations
val ksp by configurations
val implementation by configurations
val testImplementation by configurations
val androidTestImplementation by configurations


extra.apply {
    val retrofitVersion = "2.9.0"
    val okhttpVersion = "4.8.1"
    val coroutinesVersion = "1.3.9"
    val glideVersion = "4.15.1"
    val navigationComponentVersion = "2.7.0-rc01"
    val lifeCycleVersion = "2.7.0-alpha01"
    val androidXCoreVersion = "1.10.1"
    val hiltVersion = "2.50"
    dependencies {

        //android core
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0-alpha01")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")

        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        implementation("androidx.core:core-ktx:$androidXCoreVersion")

        //DI dagger and hilt
        implementation("com.google.dagger:hilt-android:$hiltVersion")
        kapt("com.google.dagger:hilt-compiler:$hiltVersion")

        //networking
        implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
        implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
        implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
        implementation("com.squareup.okhttp3:logging-interceptor:$okhttpVersion")
        implementation("com.google.code.gson:gson:2.10.1")

        //coroutine
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

        //logging
        implementation("com.jakewharton.timber:timber:5.0.1")

        //glide
        ksp("com.github.bumptech.glide:ksp:$glideVersion")
        implementation("com.github.bumptech.glide:glide:$glideVersion")

        //navigation
        implementation("androidx.navigation:navigation-fragment-ktx:$navigationComponentVersion")
        implementation("androidx.navigation:navigation-ui-ktx:$navigationComponentVersion")
        implementation("androidx.navigation:navigation-common:2.7.0-rc01")

        //lifecycle
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifeCycleVersion")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion")


    }
}


