package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("trailers") val trailers: List<TrailersItem>?
)