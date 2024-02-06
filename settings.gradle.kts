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
    }
}

rootProject.name = "Kinopoisk-Android"
include(":app")
include(":core")
include(":features:homeScreen")
include(":data:moviesData")
include(":features:movieDetailsScreen")
include(":features:bookmarksScreen")
include(":features:moreScreen")
