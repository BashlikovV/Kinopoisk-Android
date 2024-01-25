package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(@SerializedName("name")
                               val name: String = "",
                               @SerializedName("nameEn")
                               val nameEn: String = "")