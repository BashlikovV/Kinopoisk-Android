package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("framesCount") val framesCount: Int = 0,
    @SerializedName("postersCount") val postersCount: Int = 0,
    @SerializedName("backdropsCount") val backdropsCount: Int = 0
)