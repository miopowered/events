import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion

plugins {
    `java-library`
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
}