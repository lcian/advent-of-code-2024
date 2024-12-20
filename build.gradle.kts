import kotlinx.benchmark.gradle.*

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.allopen") version "2.1.0"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.13"
}

group = "dev.cian.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.13")
    api("space.kscience:kmath-core:0.4.0")
    api("space.kscience:kmath-tensors:0.3.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

benchmark {
    targets {
        register("main")
    }
}