package org.example.project.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import org.example.project.utils.currentTime

@Composable
fun KmpEffectDemo() {
    var clicks by remember { mutableStateOf(0) }

    println("TIME  println: ${currentTime()} | clicks=$clicks")

    SideEffect {
        println("TIME  SideEffect: ${currentTime()} | clicks=$clicks")
    }


    LaunchedEffect(clicks) {
        println("TIME  LaunchedEffect START: ${currentTime()} | clicks=$clicks")
        delay(1000)
        println("TIME  LaunchedEffect END: ${currentTime()} | clicks=$clicks")
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

