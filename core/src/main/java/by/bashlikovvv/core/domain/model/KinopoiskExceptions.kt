package by.bashlikovvv.core.domain.model

sealed class KinopoiskExceptions : RuntimeException()

class NotImplementedException() : KinopoiskExceptions()

class ConnectionException : KinopoiskExceptions()

class EmptyBodyException(
    override val cause: Throwable? = null
) : KinopoiskExceptions()