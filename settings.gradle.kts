pluginManagement {
    repositories {
        google()
        jcenter()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        jcenter()
        mavenCentral()
    }
}

rootProject.name = "SmartPos SDK sample"
include(":app")
 