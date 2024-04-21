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

rootProject.name = "AndroidNavigationPlayground"
include(":app")
include(":uicomponent")
include(":navigator")
include(":feature:home")
include(":feature:search")
include(":feature:player")
include(":feature:trend")
include(":feature:purchase")
include(":navigator-test")
