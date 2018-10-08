plugins {
    java
    id("com.gradle.build-scan") version "1.16"
    id("org.shipkit.java") version "2.0.31"
    id("net.ltgt.errorprone") version "0.6" apply false
}

buildScan {
    publishAlwaysIf(System.getenv("CI") != null)
    setTermsOfServiceUrl("https://gradle.com/terms-of-service")
    setTermsOfServiceAgree("yes")
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
    from("$rootDir/gradle/codeQuality.gradle.kts")
    from("$rootDir/gradle/errorprone.gradle.kts")
}

tasks.named<Test>("test") {
    useJUnitPlatform {
        excludeTags("sample")
    }
}

tasks.getByName<Wrapper>("wrapper") {
    gradleVersion = "5.0-milestone-1"
    distributionSha256Sum = "0c4e5366b479934844da39c156c20d509f6b2c40b978c10598221fd591c0cf57"
}
