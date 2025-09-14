package com.example.icare.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.icare.screens.*
import com.example.icare.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class Dest(val route: String, val label: String) {
    data object Splash : Dest("splash", "Splash")
    data object Beauty : Dest("beauty", "Beauty")
    data object Exercise : Dest("exercise", "Exercise")
    data object Water : Dest("water", "Water")
    data object Summary : Dest("summary", "Summary")
    data object Achievements : Dest("achievements", "Badges")
    data object Settings : Dest("settings", "Settings")
}

@Composable
fun NavGraph(vm: AppViewModel) {
    val nav = rememberNavController()
    val currentRoute = currentRoute(nav)
    val showBottomBar = currentRoute != Dest.Splash.route

    Scaffold(
        bottomBar = { if (showBottomBar) BottomBar(nav) }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Dest.Splash.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Dest.Splash.route) { SplashScreen(nav) }
            composable(Dest.Beauty.route) { HomeBeautyScreen(vm) }
            composable(Dest.Exercise.route) { ExerciseScreen(vm) }   // ✅ pass vm
            composable(Dest.Water.route) { WaterScreen(vm) }
            composable(Dest.Summary.route) { WeeklySummaryScreen() }
            composable(Dest.Achievements.route) { AchievementsScreen() }
            composable(Dest.Settings.route) { SettingsScreen() }
        }
    }
}

@Composable
private fun BottomBar(nav: NavHostController) {
    val items = listOf(
        Dest.Beauty to Icons.Filled.Face,
        Dest.Exercise to Icons.Filled.FitnessCenter,
        Dest.Water to Icons.Filled.Opacity,
        Dest.Summary to Icons.Filled.BarChart,
        Dest.Achievements to Icons.Filled.EmojiEvents,
        Dest.Settings to Icons.Filled.Settings
    )
    val current = currentRoute(nav)
    NavigationBar {
        items.forEach { (dest, icon) ->
            NavigationBarItem(
                selected = current == dest.route,
                onClick = {
                    nav.navigate(dest.route) {
                        popUpTo(Dest.Beauty.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = icon, contentDescription = dest.label) },
                label = { Text(dest.label) }
            )
        }
    }
}

@Composable
private fun currentRoute(nav: NavHostController): String? {
    val entry by nav.currentBackStackEntryAsState()
    return entry?.destination?.route
}
