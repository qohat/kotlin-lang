import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "io.github.qohat"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val kotest: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    implementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    testImplementation("io.kotest:kotest-assertions-core:$kotest")
    testImplementation("io.kotest:kotest-framework-engine:$kotest")
    testImplementation("io.kotest:kotest-property:$kotest")
    testImplementation("io.kotest:kotest-runner-junit5:$kotest")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
    testImplementation(kotlin("test"))
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "${JavaVersion.VERSION_11}"
            freeCompilerArgs = freeCompilerArgs + "-Xcontext-receivers"
        }
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
}