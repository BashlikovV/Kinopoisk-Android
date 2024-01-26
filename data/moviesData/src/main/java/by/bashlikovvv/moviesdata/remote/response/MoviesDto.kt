package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("total") val total: Int = 0,
    @SerializedName("pages") val pages: Int = 0,
    @SerializedName("docs") val docs: List<DocsItem>?,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("page") val page: Int = 0
)