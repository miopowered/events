import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("events.java-conventions")
    id("xyz.jpenilla.run-paper") version ("2.0.0")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    id("net.minecrell.plugin-yml.bukkit") version ("0.5.2")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")
    implementation(project(":bukkit"))
}

bukkit {
    name = "events"
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "eu.miopowered.events.plugin.EventsPlugin"
    apiVersion = "1.19"
    authors = listOf("Emmanuel Lampe <mail@emmanuel-lampe.de>")
}

tasks {
    runServer {
        minecraftVersion("1.19.2")
    }
}