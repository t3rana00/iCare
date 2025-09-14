package com.example.icare.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.icare.data.db.BeautyTask
import com.example.icare.viewmodel.AppViewModel

@Composable
fun HomeBeautyScreen(vm: AppViewModel) {
    val skin by vm.skin.collectAsState()
    val hair by vm.hair.collectAsState()
    val body by vm.body.collectAsState()
    val nails by vm.nails.collectAsState()
    val logs by vm.todayLogs.collectAsState()

    var tab by remember { mutableStateOf(0) }
    val tabs = listOf("Skin", "Hair", "Body", "Nails")

    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = tab) {
            tabs.forEachIndexed { i, t ->
                Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) })
            }
        }
        val tasks = when (tab) {
            0 -> skin
            1 -> hair
            2 -> body
            else -> nails
        }
        TaskList(
            tasks = tasks,
            logs = logs,
            onToggle = { id, checked -> vm.setTaskDone(id, checked) }
        )
    }
}

@Composable
private fun TaskList(
    tasks: List<BeautyTask>,
    logs: List<com.example.icare.data.db.BeautyLog>,
    onToggle: (Long, Boolean) -> Unit
) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(tasks, key = { it.id }) { task ->
            val checked = logs.any { it.taskId == task.id && it.done }
            ListItem(
                headlineContent = { Text(task.name) },
                supportingContent = { Text(task.category) },
                trailingContent = {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { onToggle(task.id, it) }
                    )
                }
            )
            Divider()
        }
        item { Spacer(Modifier.height(80.dp)) }
    }
}
