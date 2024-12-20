pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "2024"
include("src:jmh:untitled")
findProject(":src:jmh:untitled")?.name = "untitled"
