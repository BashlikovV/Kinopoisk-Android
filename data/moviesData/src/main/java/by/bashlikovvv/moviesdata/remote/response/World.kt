package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class World(@SerializedName("currency")
                 val currency: String = "",
                 @SerializedName("value")
                 val value: Int = 0)