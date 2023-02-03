plugins {
    val kotlinVersion = "1.8.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    id("org.springframework.boot") version "3.0.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.hsqldb:hsqldb")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
}
