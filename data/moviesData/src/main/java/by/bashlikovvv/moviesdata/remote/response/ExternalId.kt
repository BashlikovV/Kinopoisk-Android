package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class ExternalId(@SerializedName("tmdb")
                      val tmdb: Int = 0,
                      @SerializedName("imdb")
                      val imdb: String = "")