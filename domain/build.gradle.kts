import tmdb.buildSrc.Modules.CORE

plugins {
    id("domain-library-plugin")
}

dependencies{
    implementation(project(CORE))
}