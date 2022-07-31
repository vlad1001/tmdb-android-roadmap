import tmdb.buildSrc.Modules.MODEL

plugins {
    id("core-library-plugin")
}

dependencies{
    api(project(MODEL))

    implementation(platform(tmdb.buildSrc.Libs.Firebase.bom))
    implementation(tmdb.buildSrc.Libs.Firebase.auth)
    implementation(tmdb.buildSrc.Libs.Firebase.database)
}