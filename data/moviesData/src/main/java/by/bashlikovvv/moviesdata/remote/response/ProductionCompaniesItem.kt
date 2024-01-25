package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(@SerializedName("previewUrl")
                                   val previewUrl: String = "",
                                   @SerializedName("name")
                                   val name: String = "",
                                   @SerializedName("url")
                                   val url: String = "")