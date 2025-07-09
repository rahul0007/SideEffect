package org.example.project

enum class NetworkType {
    NONE, WIFI, CELLULAR
}
expect fun startNetworkObserver(onChanged: (NetworkType) -> Unit): () -> Unit