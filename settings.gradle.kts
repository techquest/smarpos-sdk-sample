pluginManagement {
    repositories {
        google()
        maven { setUrl("https://jitpack.io") }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { setUrl("https://mirrors.huaweicloud.com/repository/maven/") }
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "SmartPos SDK sample"
include(":app")
 