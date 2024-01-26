package by.bashlikovvv.core.domain.model

data class Movie(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val poster: String = "",
    val age: Int = 0,
    val genres: List<String> = listOf()
)