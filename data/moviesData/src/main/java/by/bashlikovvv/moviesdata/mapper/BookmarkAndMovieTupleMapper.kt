package by.bashlikovvv.moviesdata.mapper

import by.bashlikovvv.core.domain.model.IMapper
import by.bashlikovvv.core.domain.model.Movie
import by.bashlikovvv.moviesdata.local.model.BookmarkEntity
import by.bashlikovvv.moviesdata.local.tuple.BookmarkAndMovieTuple

class BookmarkAndMovieTupleMapper : IMapper<BookmarkAndMovieTuple, Movie> {
    override fun mapFromEntity(entity: BookmarkAndMovieTuple): Movie {
        return MovieEntityToMovieMapper(isBookmark = true).mapFromEntity(entity.movie)
    }

    override fun mapToEntity(domain: Movie): BookmarkAndMovieTuple {
        return BookmarkAndMovieTuple(
            bookmark = BookmarkEntity(id = 0, keyMovieId = domain.id),
            movie = MovieEntityToMovieMapper(isBookmark = true).mapToEntity(domain)
        )
    }

}