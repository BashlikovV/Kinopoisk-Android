package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class SimilarMoviesItemDto(
    @SerializedName("year") val year: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("enName") val enName: String? = null,
    @SerializedName("rating") val ratingDto: RatingDto,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("alternativeName") val alternativeName: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("poster") val poster: Poster
)