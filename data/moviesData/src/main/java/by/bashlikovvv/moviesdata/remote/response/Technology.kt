package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Technology(@SerializedName("hasImax")
                      val hasImax: Boolean = false,
                      @SerializedName("has3D")
                      val hasD: Boolean = false)