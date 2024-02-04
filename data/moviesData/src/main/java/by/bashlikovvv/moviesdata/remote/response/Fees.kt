package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Fees(
    @SerializedName("usa") val usa: Usa,
    @SerializedName("world") val world: World
)