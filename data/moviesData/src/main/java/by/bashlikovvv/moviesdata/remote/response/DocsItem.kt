package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class DocsItem(
    @SerializedName("year") val year: Int = 0,
    @SerializedName("rating") val rating: Rating,
    @SerializedName("description") val description: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("movieLength") val movieLength: Int = 0,
    @SerializedName("typeNumber") val typeNumber: Int = 0,
    @SerializedName("ticketsOnSale") val ticketsOnSale: Boolean = false,
    @SerializedName("genres") val genres: List<GenresItem>?,
    @SerializedName("enName") val enName: String = "",
    @SerializedName("logo") val logo: Logo,
    @SerializedName("top250") val top250: Int = 0,
    @SerializedName("id") val id: Long = 0,
    @SerializedName("alternativeName") val alternativeName: String = "",
    @SerializedName("ratingMpaa") val ratingMpaa: String = "",
    @SerializedName("top10") val top10: Int? = null,
    @SerializedName("totalSeriesLength") val totalSeriesLength: String? = null,
    @SerializedName("backdrop") val backdrop: Backdrop,
    @SerializedName("countries") val countries: List<CountriesItem>?,
    @SerializedName("shortDescription") val shortDescription: String? = "",
    @SerializedName("ageRating") val ageRating: Int = 0,
    @SerializedName("isSeries") val isSeries: Boolean = false,
    @SerializedName("names") val names: List<NamesItem>?,
    @SerializedName("name") val name: String = "",
    @SerializedName("seriesLength") val seriesLength: String? = null,
    @SerializedName("votes") val votes: Votes,
    @SerializedName("poster") val poster: Poster,
    @SerializedName("status") val status: String? = null
)