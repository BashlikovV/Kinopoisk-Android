package by.bashlikovvv.moviesdata.repository

import android.net.ConnectivityManager
import by.bashlikovvv.core.domain.repository.INetworkRepository
import by.bashlikovvv.core.ext.isConnected

class NetworkRepository(
    private val connectivityManager: ConnectivityManager
) : INetworkRepository {
    override fun hasInternetConnection(): Boolean {
        return connectivityManager.isConnected()
    }
}