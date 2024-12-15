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

include(
    ":app",
    ":core",
    ":core:network",
    ":core:model",
    ":core:di",
    ":core:designsystem",
    ":core:datastore",
    ":feature",
    ":feature:auth",
    ":feature:main"
)