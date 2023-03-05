import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-conventions`
    alias(libs.plugins.runvelocity)
    alias(libs.plugins.runpaper)
    alias(libs.plugins.bukkityml)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {

    implementation(project(":bukkit"))
    implementation(project(":velocity"))

    compileOnly(libs.paper)
    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

bukkit {
    name = "events"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "eu.miopowered.events.plugin.paper.EventsPlugin"
    apiVersion = "1.19"
    authors = listOf("Emmanuel Lampe <mail@emmanuel-lampe.de>")
}

tasks {
    runServer {
        minecraftVersion(libs.versions.minecraft.get())
    }
    runVelocity {
        velocityVersion(libs.versions.velocity.get())
    }
}