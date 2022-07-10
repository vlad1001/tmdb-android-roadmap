package tmdb.buildSrc

import org.gradle.api.JavaVersion

object AndroidConfig {
    const val compileSdk = 32
    const val minSdk = 23
    const val targetSdk = 32

    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "com.example.moviestmdb"

    const val flavorDimensions = "mode"

    val sourceCompatibility = JavaVersion.VERSION_11
    val targetCompatibility = JavaVersion.VERSION_11
}