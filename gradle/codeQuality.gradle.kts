apply(plugin = "checkstyle")

configure<CheckstyleExtension> {
    toolVersion = "8.13"
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
}
