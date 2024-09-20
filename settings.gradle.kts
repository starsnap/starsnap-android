pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "starsnap-android"
include(":app")
include(":core")
include(":feature")
include(":core:network")
include(":core:model")
include(":core:di")
include(":core:designsystem")
include(":core:datastore")
include(":feature:auth")
include(":feature:main")