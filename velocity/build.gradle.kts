plugins {
    id("events.publish-conventions")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
    id("io.freefair.lombok") version ("6.6-rc1")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(project(":api"))

    compileOnly("com.velocitypowered:velocity-api:3.1.1")
}