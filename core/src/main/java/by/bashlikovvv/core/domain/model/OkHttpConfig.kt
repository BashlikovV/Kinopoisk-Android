package by.bashlikovvv.core.domain.model

import by.bashlikovvv.core.Constants

data class OkHttpConfig(
    val apiKey: String = Constants.API_KEY,
    val baseUrl: String = Constants.BASE_URL,
    val apiKeyName: String = Constants.API_KEY_NAME
)