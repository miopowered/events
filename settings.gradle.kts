rootProject.name = "events"

enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}
include("api")
include("bukkit")
include("velocity")
include("example")
