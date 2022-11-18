rootProject.name = "events"

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}
include("api")
include("bukkit")
include("bukkit-plugin")
include("velocity-plugin")
include("velocity")
