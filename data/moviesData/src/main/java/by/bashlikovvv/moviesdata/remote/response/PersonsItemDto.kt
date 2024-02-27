package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class PersonsItemDto(
    @SerializedName("profession") val profession: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("enName") val enName: String = "",
    @SerializedName("photo") val photo: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("enProfession") val enProfession: String = ""
)