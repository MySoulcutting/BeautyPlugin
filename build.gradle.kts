plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "com.whitesoul"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")

}

dependencies {
    implementation("io.ktor:ktor-client-core:2.3.5")
    implementation("io.ktor:ktor-client-cio:2.3.5") // CIO 引擎
    implementation("io.ktor:ktor-client-content-negotiation:2.3.5")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    compileOnly("net.mamoe:mirai-core:2.16.0") // mirai-core 的 API
    compileOnly("net.mamoe:mirai-console:2.16.0") // 后端
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}