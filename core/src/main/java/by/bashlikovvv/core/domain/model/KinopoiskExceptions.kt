package by.bashlikovvv.core.domain.model

sealed class KinopoiskExceptions : RuntimeException()

data object NotImplementedException : KinopoiskExceptions()

data object ConnectionException : KinopoiskExceptions()

data object EmptyBodyException : KinopoiskExceptions()