import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

plugins {
    id("java-conventions")
    `maven-publish`
}

val snapshotRepository: String by project
val releaseRepository: String by project

tasks {
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).tags("todo")
    }

    val javadocJar by creating(Jar::class) {
        dependsOn("javadoc")
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    val sourcesJar by creating(Jar::class) {
        dependsOn("classes")
        archiveClassifier.set("sources")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        from(sourceSets["main"].allSource)
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    build {
        dependsOn(shadowJar)
        dependsOn(sourcesJar)
        dependsOn(javadocJar)
    }
}

configure<PublishingExtension> {
    repositories {
        maven {
            if (project.version.toString().endsWith("-SNAPSHOT")) {
                name = "miopoweredRepositorySnapshot"
                url = uri(snapshotRepository)
            } else {
                name = "miopoweredRepositoryRelease"
                url = uri(releaseRepository)
            }
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}