package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class FactsItem(@SerializedName("spoiler")
                     val spoiler: Boolean = false,
                     @SerializedName("type")
                     val type: String = "",
                     @SerializedName("value")
                     val value: String = "")