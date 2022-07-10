repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("application-plugin") {
            id = "application-plugin"
            implementationClass = "tmdb.buildSrc.plugins.ApplicationPlugin"
        }

        register("core-library-plugin") {
            id = "core-library-plugin"
            implementationClass = "tmdb.buildSrc.plugins.CoreLibraryPlugin"
        }

        register("core-ui-library-plugin") {
            id = "core-ui-library-plugin"
            implementationClass = "tmdb.buildSrc.plugins.CoreUiLibraryPlugin"
        }

        register("domain-library-plugin") {
            id = "domain-library-plugin"
            implementationClass = "tmdb.buildSrc.plugins.DomainLibraryPlugin"
        }

        register("model-library-plugin") {
            id = "model-library-plugin"
            implementationClass = "tmdb.buildSrc.plugins.ModelLibraryPlugin"
        }

        register("ui-library-plugin") {
            id = "ui-library-plugin"
            implementationClass = "tmdb.buildSrc.plugins.UiLibraryPlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.2.1")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.42")
}