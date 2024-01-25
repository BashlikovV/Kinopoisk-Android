package by.bashlikovvv.moviesdata.remote.response

import com.google.gson.annotations.SerializedName

data class ImagesInfo(@SerializedName("framesCount")
                      val framesCount: Int = 0)