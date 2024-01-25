package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class TrailersItem(@SerializedName("site")
                        val site: String = "",
                        @SerializedName("name")
                        val name: String = "",
                        @SerializedName("type")
                        val type: String = "",
                        @SerializedName("url")
                        val url: String = "")