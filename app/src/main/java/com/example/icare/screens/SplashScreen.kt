package com.example.icare.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.icare.ui.components.FlowerLoader
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(nav: NavHostController) {
    LaunchedEffect(Unit) {
        delay(1200)
        nav.navigate("beauty") {
            popUpTo("splash") { inclusive = true }
            launchSingleTop = true
        }
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
            FlowerLoader(96)
            Spacer(Modifier.height(16.dp))
            Text("I Care", style = MaterialTheme.typography.headlineSmall)
            Text("Glow from the inside out", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
