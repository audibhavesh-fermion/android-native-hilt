@file:Suppress("UnstableApiUsage")

import java.net.URI


pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url= URI("https://jitpack.io")
        }
    }
}

rootProject.name = "ProjectTemplateHiltNative"
include(":app")
include(":library:base")
include(":examples:cats-facts")
