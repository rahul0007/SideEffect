package org.example.project
import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

lateinit var appContext: Context
fun provideAppContext(context: Context) {
    appContext = context.applicationContext
}

actual fun startNetworkObserver(onChanged: (NetworkType) -> Unit): () -> Unit {
    val connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            val type = when {
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> NetworkType.WIFI
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> NetworkType.CELLULAR
                else -> NetworkType.NONE
            }
            onChanged(type)
        }

        override fun onLost(network: Network) {
            onChanged(NetworkType.NONE)
        }
    }

    connectivityManager.registerDefaultNetworkCallback(networkCallback)

    // Return cleanup lambda
    return {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
