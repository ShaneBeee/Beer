import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16" // the latest version can be found on the Gradle Plugin Portal
}

// The Minecraft version we're currently building for
val minecraftVersion = "1.21.5"
// Where this builds on the server
val serverLocation = "Skript/1-21-5"
// Version of BeerPlugin
val projectVersion = "1.0.0"
// Client instance
val clientInstance = "1.21.5-Fabric"

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

paperweight.reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION

repositories {
    mavenCentral()
    mavenLocal()

    // Command Api Snapshots
    maven("https://s01.oss.sonatype.org/content/repositories")

    // JitPack repo
    maven("https://jitpack.io")
}

dependencies {
    // Paper
    paperweight.paperDevBundle("${minecraftVersion}-R0.1-SNAPSHOT")
    // CoreAPI
    implementation("com.github.shanebeestudios:coreapi:1.0.0")
}

tasks {
    register("server", Copy::class) {
        dependsOn("shadowJar")
        from("build/libs") {
            include("BeerPlugin-*.jar")
            destinationDir = file("/Users/ShaneBee/Desktop/Server/${serverLocation}/plugins/")
        }

    }
    register("resourcepack", Zip::class) {
        archiveFileName = "Beer-ResourcePack-${projectVersion}-${minecraftVersion}.zip"
        from("src/main/resources/resource-pack") {
            exclude("**/.DS_Store")
            destinationDirectory = file("build/libs/")
        }
    }
    register("resourcepack-client", Zip::class) {
        archiveFileName = "Beer-ResourcePack-${projectVersion}-${minecraftVersion}.zip"
        from("src/main/resources/resource-pack") {
            exclude("**/.DS_Store")
            destinationDirectory = file("/Users/ShaneBee/Library/Application Support/PrismLauncher/instances/$clientInstance/minecraft/resourcepacks")
        }
    }
    register("datapack", Zip::class) {
        archiveFileName = "Beer-DataPack-${projectVersion}-${minecraftVersion}.zip"
        from("src/main/resources/datapack") {
            exclude("**/.DS_Store")
            destinationDirectory = file("build/libs/")
        }
    }
    register("datapack-server", Zip::class) {
        archiveFileName = "Z-Beer.zip"
        from("src/main/resources/datapack") {
            exclude("**/.DS_Store")
            destinationDirectory = file("/Users/ShaneBee/Desktop/Server/${serverLocation}/worlds/world/datapacks/")
        }
    }
    processResources {
        expand("version" to projectVersion)
        exclude("datapack/*")
        exclude("resource-pack/*")
    }
    compileJava {
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        //exclude("com/shanebeestudios/core/plugin")
        (options as StandardJavadocDocletOptions).links(
            "https://jd.papermc.io/paper/1.21.1/",
            "https://jd.advntr.dev/api/4.17.0/",
            "https://tr7zw.github.io/Item-NBT-API/v2-api/"
        )

    }
    shadowJar {
        archiveFileName = "BeerPlugin-${projectVersion}.jar"
        relocate("com.shanebeestudios.coreapi", "com.shanebeestudios.beer.coreapi")

    }
    jar {
        dependsOn(shadowJar)
        exclude("datapack/")
        exclude("resource-pack/")
        archiveFileName = "BeerPlugin-${projectVersion}.jar"
    }
}
