package com.example.icare.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.icare.viewmodel.AppViewModel
import kotlin.math.max
import kotlin.math.min

@Composable
fun WaterScreen(vm: AppViewModel) {
    val goal by vm.waterGoal.collectAsState()
    val today by vm.waterToday.collectAsState()

    var customMlText by remember { mutableStateOf("") }
    val customMl = customMlText.toIntOrNull() ?: 0

    val progress = if (goal <= 0) 0f else min(1f, today.toFloat() / goal.toFloat())

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Water Intake", style = MaterialTheme.typography.titleLarge)

        // Progress ring
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(progress = { progress }, strokeWidth = 10.dp, modifier = Modifier.size(180.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("$today / $goal ml", style = MaterialTheme.typography.headlineSmall)
                Text("${(progress * 100).toInt()}%")
            }
        }

        Text("Quick add", style = MaterialTheme.typography.titleMedium)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            QuickAdd(200) { vm.addWater(200) }
            QuickAdd(300) { vm.addWater(300) }
            QuickAdd(500) { vm.addWater(500) }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = customMlText,
                onValueChange = { customMlText = it.filter { ch -> ch.isDigit() }.take(4) },
                label = { Text("Custom (ml)") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Button(
                onClick = {
                    if (customMl > 0) {
                        vm.addWater(customMl)
                        customMlText = ""
                    }
                },
                enabled = customMl > 0
            ) { Text("Add") }
        }

        Spacer(Modifier.weight(1f))
        // Tip
        AssistChip(
            onClick = {},
            label = { Text("Tip: Small sips often beat big gulps 💧") }
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun QuickAdd(ml: Int, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick) {
        Text("+${ml} ml")
    }
}
