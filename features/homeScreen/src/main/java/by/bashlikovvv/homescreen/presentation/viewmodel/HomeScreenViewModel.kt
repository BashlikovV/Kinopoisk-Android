package by.bashlikovvv.homescreen.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.bashlikovvv.core.domain.usecase.GetPagedMoviesUseCase
import javax.inject.Inject
import javax.inject.Provider

class HomeScreenViewModel(
    private val getPagedMoviesUseCase: GetPagedMoviesUseCase
) : ViewModel() {

    var moviesPagedData = getPagedMoviesUseCase.execute()
        private set


    class Factory @Inject constructor(
        private val getPagedMoviesUseCase: Provider<GetPagedMoviesUseCase>
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == HomeScreenViewModel::class.java)
            return HomeScreenViewModel(
                getPagedMoviesUseCase.get()
            ) as T
        }

    }

}