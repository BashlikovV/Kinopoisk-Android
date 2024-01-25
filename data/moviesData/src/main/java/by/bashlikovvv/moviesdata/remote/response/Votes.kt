package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Votes(@SerializedName("imdb")
                 val imdb: Int = 0,
                 @SerializedName("kp")
                 val kp: Int = 0,
                 @SerializedName("await")
                 val await: Int = 0,
                 @SerializedName("russianFilmCritics")
                 val russianFilmCritics: Int = 0,
                 @SerializedName("filmCritics")
                 val filmCritics: Int = 0)