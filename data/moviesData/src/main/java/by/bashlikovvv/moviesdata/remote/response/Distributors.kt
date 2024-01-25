package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Distributors(@SerializedName("distributorRelease")
                        val distributorRelease: String = "",
                        @SerializedName("distributor")
                        val distributor: String = "")