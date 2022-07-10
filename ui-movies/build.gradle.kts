import tmdb.buildSrc.Modules

plugins {
    id("ui-library-plugin")
}

dependencies{
    implementation(project(Modules.CORE))
    implementation(project(Modules.DOMAIN))
    implementation(project(Modules.CORE_UI))
}