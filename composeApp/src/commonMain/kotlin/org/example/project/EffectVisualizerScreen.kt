package org.example.project

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun KmpEffectDemo() {
    var clicks by remember { mutableStateOf(0) }

    println("Hello 🔵 println: ${currentTime()} | clicks=$clicks")

    SideEffect {
        println("Hello 🟢 SideEffect: ${currentTime()} | clicks=$clicks")
    }

    LaunchedEffect(clicks) {
        println("Hello 🟣 LaunchedEffect START: ${currentTime()} | clicks=$clicks")
        delay(1000)
        println("Hello 🟣 LaunchedEffect END: ${currentTime()} | clicks=$clicks")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Clicks: $clicks")
        Button(onClick = { clicks++ }) {
            Text("Click Me")
        }
    }
}

