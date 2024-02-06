package by.bashlikovvv.morescreen.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bashlikovvv.core.base.SingleLiveEvent
import by.bashlikovvv.core.domain.model.Destination
import javax.inject.Inject

class MoreFragmentViewModel : ViewModel() {

    private var _navigationDestinationLiveEvent = SingleLiveEvent<Destination>()
    val navigationDestinationLiveEvent: LiveData<Destination> = _navigationDestinationLiveEvent

    fun navigateToDestination(destination: Destination) {
        _navigationDestinationLiveEvent.postValue(destination)
    }

    class Factory @Inject constructor() : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == MoreFragmentViewModel::class.java)
            return MoreFragmentViewModel() as T
        }

    }

}