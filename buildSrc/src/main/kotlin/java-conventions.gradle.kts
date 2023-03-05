import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow")
    id("io.freefair.lombok")
}

repositories {
    mavenLocal()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
}

configure<JavaPluginExtension> {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    compileJava {
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
        manifest {
            attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
            )
        }
    }

    build {
        dependsOn(shadowJar)
    }
}