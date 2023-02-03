plugins {
    kotlin("jvm") version "1.8.10"
    id("org.springframework.boot") version "2.7.8"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:2.7.8"))

    // web
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.hsqldb:hsqldb")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
}
