package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.project.ui.NetworkObserverScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

//        AllEffectsScreen()
//        SideEffectVsPrintDemo()
//        LaunchedEffectDemo()
        NetworkObserverScreen()
    }
}