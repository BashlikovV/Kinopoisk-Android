# [core module](../core) overview
## core module - classes common to the entire application.
## Responsible for:
 - providing base fragments and activities;
 - common extensions, di classes, data models, utils and constants.
## Description:
 - ### [base package](../core/src/main/java/by/bashlikovvv/core/base) (contains base classes to provide other application components same behaviour):
   - [IMapper](../core/src/main/java/by/bashlikovvv/core/base/IMapper.kt) - 
   base mapper class for mapping data from data layer to domain layer;
   - [ItemViewHolder](../core/src/main/java/by/bashlikovvv/core/base/ItemViewHolder.kt) - base view holder;
   - [ItemErrorViewHolder](../core/src/main/java/by/bashlikovvv/core/base/ItemErrorViewHolder.kt), 
   [ItemProgressViewHolder](core/src/main/java/by/bashlikovvv/core/base/ItemProgressViewHolder.kt) -
   base view holders with different progress states;
   - [SingleLiveEvent](../core/src/main/java/by/bashlikovvv/core/base/SingleLiveEvent.kt) -
   a live event that can only be called once until the value changes;
 - ### [di package](../core/src/main/java/by/bashlikovvv/core/di) (contains common classes for di):
   - [ApplicationQualifier](../core/src/main/java/by/bashlikovvv/core/di/ApplicationQualifier.kt) - 
   qualifier for application context;
   - [AppScope](../core/src/main/java/by/bashlikovvv/core/di/AppScope.kt) - scope for all "singletons" in application level;
   - [Feature](../core/src/main/java/by/bashlikovvv/core/di/Feature.kt) - scope for all "singletons" in feature level;
   - [PagerOffline](../core/src/main/java/by/bashlikovvv/core/di/PagerOffline.kt), 
   [PagerOnline](../core/src/main/java/by/bashlikovvv/core/di/PagerOnline.kt) - qualifiers for all movies pagers.
 - ### [domain package](../core/src/main/java/by/bashlikovvv/core/domain) (contains data models, repositories and use cases):
   - [model package](../core/src/main/java/by/bashlikovvv/core/domain/model) - 
   contains all data models used at the entire application;
   - [repository package](../core/src/main/java/by/bashlikovvv/core/domain/repository) - 
   contains description of all repositories;
   - [usecase package](../core/src/main/java/by/bashlikovvv/core/domain/usecase) - 
   contains use cases available throughout the application;
 - ### [ext package](../core/src/main/java/by/bashlikovvv/core/ext) (contains extensions available throughout the application);
 - [Constants](../core/src/main/java/by/bashlikovvv/core/Constants.kt) - constants for application.