package org.example.project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.project.NetworkType
import org.example.project.startNetworkObserver

@Composable
fun NetworkObserverScreen() {
    var networkType by remember { mutableStateOf(NetworkType.NONE) }

    DisposableEffect(Unit) {
        val cleanup = startNetworkObserver { type ->
            networkType = type
        }
        onDispose {
            println("onDispose")
            cleanup()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = when (networkType) {
                NetworkType.WIFI -> " Connected to Wi-Fi"
                NetworkType.CELLULAR -> " Using Mobile Data"
                NetworkType.NONE -> " No Internet Connection"
            },
            style = MaterialTheme.typography.titleLarge
        )
    }
}

