
plugins {
    kotlin("jvm") version embeddedKotlinVersion
    kotlin("kapt") version embeddedKotlinVersion
}

group = "com.mycompany"
version = "0.1.0-SNAPSHOT"

description = "Gauss Filtering"

repositories {
    mavenCentral()
    maven("https://maven.imagej.net/content/groups/public")
    maven("https://repo.maven.apache.org/maven2")
}

dependencies {
    kapt("net.imagej:imagej:2.0.0-rc-61")
    implementation("net.imagej:imagej:2.0.0-rc-61")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
    options.encoding = "UTF-8"
}