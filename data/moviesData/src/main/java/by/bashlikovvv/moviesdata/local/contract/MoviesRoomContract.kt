package by.bashlikovvv.moviesdata.local.contract

object MoviesRoomContract {

    const val DATABASE_NAME = "movies.room"

    object MoviesTable {
        const val TABLE_NAME = "movies"
        const val COLUMN_ID = "movies_id"
        const val COLUMN_NAME = "movies_name"
        const val COLUMN_SHORT_DESCRIPTION = "movies_short_description"
        const val COLUMN_POSTER = "movies_poster"
        const val COLUMN_AGE = "movies_age"
        const val COLUMN_GENRES = "movies_genres"
        const val COLUMN_PAGE = "movies_page"
        const val COLUMN_COLLECTIONS = "movies_collections"
    }

    object MoviesDetailsTable {
        const val TABLE_NAME = "movies_details"
        const val KEY_MOVIE_ID = "details_key_movie_id"
        const val COLUMN_ID = "details_id"
        const val COLUMN_PERSONS = "details_persons"
        const val COLUMN_DESCRIPTION = "details_description"
        const val COLUMN_SIMILAR_MOVIES = "details_similar_movies"
        const val COLUMN_WATCH_ABILITY = "details_watch_ability"
        const val COLUMN_TRAILERS = "details_trailers"
    }

}