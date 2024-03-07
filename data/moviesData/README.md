# [moviesData module](../moviesData) overview
## moviesData module - manages all movies and related data.
## Responsible for:
- Data storage, modification and modification;
- Getting data over the network;
- Creation and modification database, management of database migration.
## Description:
- ### [schemas package](../moviesData/schemas) - stores json descriptions all version of the
application database with the designation of versions.
- ### [local package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local) - describe local data sources:
    - [Contract](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/contract/MoviesRoomContract.kt) -
      contains description of movies database structure;
    - [converters package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/converters) -
      contains type converters used to convert complex types into a json string and back;
    - [dao package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/dao) - contains data access objects (DAO):
        - [BookmarksDao](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/dao/BookmarksDao.kt),
          [MoviesDao](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/dao/MoviesDao.kt),
          [MoviesDetailsDao](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/dao/MoviesDetailsDao.kt) -
          data access objects (DAOs) for creating, deleting and modifying data about bookmarks, movies and movies details;
    - [model package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/model) - contains entities for movies database:
        - [BookmarkEntity.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/model/BookmarkEntity.kt),
          [MovieDetailsEntity.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/model/MovieDetailsEntity.kt),
          [MovieEntity.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/model/MovieEntity.kt) - entities to create movies database;
    - [tuple package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/tuple) - contains tuples for selecting data from several tables at a time:
        - [BookmarkAndMovieTuple.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/tuple/BookmarkAndMovieTuple.kt),
          [MovieDetailsAndMovieTuple.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/local/tuple/MovieDetailsAndMovieTuple.kt) - tuples for accessing bookmark and related movie and movie details and related movie;
    - ### [remote package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/remote) - describe remote data sources:
        - [response package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/remote/response) - contains data transfer objects (DTO);
        - [MoviesApi](../moviesData/src/main/java/by/bashlikovvv/moviesdata/remote/MoviesApi.kt) - describe request to the movies api;
        - [GenreMoviesRemoteMediator.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/remote/GenreMoviesRemoteMediator.kt),
          [MoviesRemoteMediator.kt](../moviesData/src/main/java/by/bashlikovvv/moviesdata/remote/MoviesRemoteMediator.kt) - remote mediators for pagers;
- ### [mapper package](../moviesData/src/main/java/by/bashlikovvv/moviesdata/mapper) - contains mappers from data layer data to domain layer data representation;
- ### [repository](../moviesData/src/main/java/by/bashlikovvv/moviesdata/repository) - contains implementations of repositories;