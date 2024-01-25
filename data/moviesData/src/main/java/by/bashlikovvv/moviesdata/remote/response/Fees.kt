package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Fees(@SerializedName("usa")
                val usa: by.bashlikovvv.moviesdata.remote.response.Usa,
                @SerializedName("world")
                val world: by.bashlikovvv.moviesdata.remote.response.World
)