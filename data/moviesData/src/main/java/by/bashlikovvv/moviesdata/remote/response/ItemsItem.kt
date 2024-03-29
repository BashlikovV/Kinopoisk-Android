package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class ItemsItem(
    @SerializedName("name") val name: String = "",
    @SerializedName("logo") val logo: Logo,
    @SerializedName("url") val url: String = ""
)