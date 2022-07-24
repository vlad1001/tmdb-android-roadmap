import tmdb.buildSrc.Modules
import tmdb.buildSrc.Libs

plugins {
    id("application-plugin")
}

dependencies {
    implementation(project(Modules.CORE))
    implementation(project(Modules.CORE_UI))
    implementation(project(Modules.MODEL))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.UI_PEOPLE))
    implementation(project(Modules.UI_TVSHOWS))
    implementation(project(Modules.UI_MOVIES))
    implementation(project(Modules.UI_LOGIN))

    implementation(platform(Libs.Firebase.bom))
    implementation(Libs.Firebase.auth)
}
