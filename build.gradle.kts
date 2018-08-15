plugins {
    java
    id("com.gradle.build-scan") version "1.15.2"
    id("org.shipkit.java") version "2.0.29"
    id("net.ltgt.errorprone") version "0.0.16" apply false
    id("net.ltgt.errorprone-javacplugin") version "0.3" apply false
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
    implementation("org.junit.platform:junit-platform-launcher:1.2.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiter")
    implementation("com.google.errorprone:error_prone_annotations:2.3.1")
    implementation("org.assertj:assertj-core:3.10.0")
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
    gradleVersion = "4.10-rc-2"
    distributionSha256Sum = "e90d3c32910e259814bcca82b3911172ecca1ff1ab5ed69b4de3c1df8b378b40"
}
