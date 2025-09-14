package com.example.icare.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField


import androidx.compose.ui.unit.dp
import com.example.icare.viewmodel.AppViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ExerciseScreen(vm: AppViewModel) {
    val weight by vm.weightKg.collectAsState()
    val logs by vm.weekExercises.collectAsState()

    var roundsText by remember { mutableStateOf("12") }
    var minutesText by remember { mutableStateOf("15") }

    val rounds = roundsText.toIntOrNull() ?: 0
    val minutes = minutesText.toIntOrNull() ?: 0

    val estCalories = (rounds * weight * 0.2).toInt()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Surya Namaskar", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Your weight: ${weight} kg", color = MaterialTheme.colorScheme.primary)

        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = roundsText,
                onValueChange = { if (it.length <= 4) roundsText = it.filter { ch -> ch.isDigit() } },
                label = { Text("Rounds") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = minutesText,
                onValueChange = { if (it.length <= 4) minutesText = it.filter { ch -> ch.isDigit() } },
                label = { Text("Minutes") },
                singleLine = true,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

        }

        Spacer(Modifier.height(8.dp))
        Text("Estimated calories: $estCalories kcal")

        Spacer(Modifier.height(12.dp))
        Button(onClick = { vm.addSurya(rounds = rounds, minutes = minutes) }) {
            Text("Log Surya")
        }

        Spacer(Modifier.height(24.dp))
        Text("This week", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(logs, key = { it.id }) { log ->
                ExerciseRow(
                    when (log.name) {
                        "Surya Namaskar" -> "Surya: ${log.rounds} rounds, ${log.durationMin} min"
                        else -> "${log.name} — ${log.durationMin} min"
                    },
                    log.calories,
                    log.dateTime
                )
                Divider()
            }
            item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun ExerciseRow(title: String, calories: Int, dateTime: Long) {
    val sdf = remember { SimpleDateFormat("EEE, dd MMM HH:mm", Locale.getDefault()) }
    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(sdf.format(Date(dateTime)), style = MaterialTheme.typography.bodySmall)
        }
        Text("$calories kcal", color = MaterialTheme.colorScheme.primary)
    }
}
