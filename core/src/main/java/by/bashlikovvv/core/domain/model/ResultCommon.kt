package by.bashlikovvv.core.domain.model

sealed class ResultCommon {

    data class Success<T>(val data: T) : ResultCommon()

    data class Error(val cause: Throwable?): ResultCommon()

}