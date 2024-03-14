package by.bashlikovvv.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class FlowDestinations : Parcelable {

    @Parcelize
    data object HomeScreenFlow : FlowDestinations()

    @Parcelize
    data class DetailsScreen(val movieId: Long) : FlowDestinations()

    @Parcelize
    data class MoreScreenFlow(val categoryName: String) : FlowDestinations()

}