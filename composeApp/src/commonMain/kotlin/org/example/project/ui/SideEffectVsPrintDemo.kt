package org.example.project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.utils.currentTime

@Composable
fun SideEffectVsPrintDemo() {
    var clicks by remember { mutableStateOf(0) }
    SideEffect {
        println("One SideEffect: After recomposition, : ${currentTime()} | clicks=$clicks")
    }
    println(" One println: Recomposition started, : ${currentTime()} | clicks=$clicks\"")

    LaunchedEffect(clicks) {
        println(" One LaunchedEffect: After recomposition, : ${currentTime()} | clicks=$clicks")
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Clicks: $clicks", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { clicks++ }) {
            Text("Click Me")
        }
    }
}
