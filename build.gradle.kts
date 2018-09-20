plugins {
    java
    id("com.gradle.build-scan") version "1.16"
    id("org.shipkit.java") version "2.0.31"
    id("net.ltgt.errorprone") version "0.6" apply false
}

group = "io.github.epeee"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

val junitJupiter:String by project

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:$junitJupiter")
    implementation("org.junit.platform:junit-platform-launcher:1.3.1")
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiter")
    implementation("com.google.errorprone:error_prone_annotations:2.3.1")
    implementation("org.assertj:assertj-core:3.11.1")
}

apply {
    from("$rootDir/gradle/codeQuality.gradle")
    from("$rootDir/gradle/buildScans.gradle")
    from("$rootDir/gradle/errorprone.gradle")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform {
        excludeTags("sample")
    }
}

tasks.getByName<Wrapper>("wrapper") {
    gradleVersion = "4.10.2"
    distributionSha256Sum = "b49c6da1b2cb67a0caf6c7480630b51c70a11ca2016ff2f555eaeda863143a29"
}
