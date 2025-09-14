package com.example.icare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.icare.navigation.NavGraph
import com.example.icare.ui.theme.ICareTheme
import com.example.icare.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ICareTheme {
                val vm: AppViewModel = viewModel()
                NavGraph(vm)
            }
        }
    }
}
