package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Logo(
    @SerializedName("url") val url: String = ""
)