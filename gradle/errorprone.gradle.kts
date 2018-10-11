
fun errorproneDependency() {
    if (JavaVersion.current() == JavaVersion.VERSION_1_8) {
        val errorproneJavac by configurations
        dependencies {
            errorproneJavac("com.google.errorprone:javac:9+181-r4173-1")
        }
    }

    val errorprone by configurations
    dependencies {
        errorprone("com.google.errorprone:error_prone_core:2.3.2")
    }
}

apply(plugin = "net.ltgt.errorprone")
errorproneDependency()
