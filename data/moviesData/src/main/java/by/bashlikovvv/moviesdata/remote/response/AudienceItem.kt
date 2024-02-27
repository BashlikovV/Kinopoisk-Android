package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class AudienceItem(
    @SerializedName("country") val country: String = "",
    @SerializedName("count") val count: Int = 0
)