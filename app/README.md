# [app module](../app) overview

## app module - entry point at the application.

## Responsible for:

- Providing dependencies for the feature modules;
- Navigation between feature modules
  using [NavigationUtil](../core/src/main/java/by/bashlikovvv/core/ext/NavigationUtil.kt);
- Setting splash screen.

## Description:

- ### [di package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/di) (contains classes responsible for dependency injection):
    - [AppComponent](../app/src/main/java/by/bashlikovvv/kinopoisk_android/di/AppComponent.kt) -
      main application component that create a graph of the dependencies;
    - [DataModule](../app/src/main/java/by/bashlikovvv/kinopoisk_android/di/DataModule.kt) -
      the part of the dependency graph that provides data sources;
    - [DomainModule](../app/src/main/java/by/bashlikovvv/kinopoisk_android/di/DomainModule.kt) -
      the part of the dependency graph that provides use cases;
- ### [presentation package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation) (contains classes responsible for application ui):
    - [KinopoiskApplication](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/KinopoiskApplication.kt) -
      the base class for maintaining global application state. This class creates dependency graph
      and
      provides features components;
    - ### [flows package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/flows) (contains fragments that perform the activity function):
        - [HomeFlowFragment](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/flows/HomeFlowFragment.kt),
        [MoreFlowFragment](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/flows/MoreFlowFragment.kt) - flow fragments;
    - ### [ui package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/ui) (contains classes responsible for ui configuration and interaction with user):
        - [MainActivity](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/ui/MainActivity.kt) -
          the entry point at the application. This class configures splash screen, navigation
          between features and
          setting up navigation ui;
    - ### [view package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/view) (contains classes with custom views specific to the app module only):
        - [CustomBottomNavigationView](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/view/CustomBottomNavigationView.kt) -
          custom bottom navigation bar with items underline;
    - ### [viewmodel package](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/viewmodel) (contains view models for mvvm pattern)
        - [MainActivityViewModel](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/viewmodel/MainActivityViewModel.kt) -
          [MainActivity](../app/src/main/java/by/bashlikovvv/kinopoisk_android/presentation/ui/MainActivity.kt)
          viewmodel