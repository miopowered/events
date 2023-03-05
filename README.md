# events

Functional event handling for spigot and velocity

## Usage

Please see the example folder for paper and velocity examples.

## Setup

```kotlin
repositories {
    maven {
        name = "Miopowered"
        url = uri("https://repo.miopowered.eu/releases")
    }
}

dependencies {
    // for bukkit
    implementation("eu.miopowered.events:bukkit:1.0.0")
    // for velocity
    implementation("eu.miopowered.events:velocity:1.0.0")
}
```