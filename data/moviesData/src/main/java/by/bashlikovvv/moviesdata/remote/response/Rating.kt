package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("imdb") val imdb: Double = 0.0,
    @SerializedName("kp") val kp: Double = 0.0,
    @SerializedName("await") val await: String? = null,
    @SerializedName("russianFilmCritics") val russianFilmCritics: Double = 0.0,
    @SerializedName("filmCritics") val filmCritics: Double = 0.0
)