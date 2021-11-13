plugins {
    kotlin("jvm") version "1.5.31"
}

group "com.koisv"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") // PaperMC
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:+") // Paper Latest
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "16" // https://papermc.io/java16 | 모장 개놈들아
    }
    processResources {
        filesMatching("**/*.yml") {
            expand(project.properties)
        }
        filteringCharset = "UTF-8"
    }
}

