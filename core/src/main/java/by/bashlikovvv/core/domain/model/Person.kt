package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val profession: String? = "",
    val name: String? = "",
    val enName: String? = "",
    val photo: String? = "",
    val description: String? = "",
    val id: Int? = 0,
    val enProfession: String? = ""
) : Parcelable