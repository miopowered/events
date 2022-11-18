plugins {
    id("events.java-conventions")
    id("xyz.jpenilla.run-velocity") version ("2.0.0")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":velocity"))

    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
}

tasks {
    runVelocity {
        velocityVersion("3.1.2-SNAPSHOT")
    }
}