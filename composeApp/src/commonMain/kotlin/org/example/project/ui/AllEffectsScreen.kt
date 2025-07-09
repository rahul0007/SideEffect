package org.example.project.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AllEffectsScreen() {
    var userName by remember { mutableStateOf<String?>(null) }
    val user by produceState(initialValue = null as String?, key1 = true) {
        delay(1000)
        value = "ComposeUser"
    }
    var clicks by remember { mutableStateOf(0) }

    val scope = rememberCoroutineScope()

    val latestClickHandler = rememberUpdatedState {
        println("Handled click #$clicks for user: $user")
    }

    DisposableEffect(Unit) {
        println("Resource registered")
        onDispose { println(" Resource unregistered") }
    }


    SideEffect {
        println(" inside Recomposed, current userName=$userName, clicks=$clicks")

    }

    println(" First Recomposed, current userName=$userName, clicks=$clicks")


    LaunchedEffect(user) {
        println("👋 Welcome, $user!")
        userName = user
    }

    // Infinite animation transition
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.5f, targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("👤 User: ${userName ?: "Loading..."}")
            Button(onClick = {
                clicks++
                scope.launch { latestClickHandler.value() }
            }) {
                Text("Clicks: $clicks")
            }
            Box(
                modifier = Modifier
                    .size((100 * pulse).dp)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text("Pulse")
            }
        }
    }
}
