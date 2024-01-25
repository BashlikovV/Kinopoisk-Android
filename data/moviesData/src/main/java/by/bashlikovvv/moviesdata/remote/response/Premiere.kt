package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Premiere(@SerializedName("world")
                    val world: String = "",
                    @SerializedName("dvd")
                    val dvd: String = "",
                    @SerializedName("russia")
                    val russia: String = "")