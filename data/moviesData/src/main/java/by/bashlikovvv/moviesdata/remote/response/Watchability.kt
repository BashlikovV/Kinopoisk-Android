package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class Watchability(@SerializedName("items")
                        val items: List<by.bashlikovvv.moviesdata.remote.response.ItemsItem>?)