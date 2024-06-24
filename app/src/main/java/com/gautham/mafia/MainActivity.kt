package com.gautham.mafia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gautham.mafia.Audio.MafiaAudioViewModel
import com.gautham.mafia.Models.MainViewModel
import com.gautham.mafia.ui.MafiaApp
import com.gautham.mafia.ui.theme.MafiaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MafiaTheme {
                val navController = rememberNavController()
                val viewmodel = hiltViewModel<MainViewModel>()
                val audioViewModel: MafiaAudioViewModel = viewModel()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MafiaApp(
                        navController=navController,
                        viewmodel=viewmodel,
                        innerPadding=innerPadding,
                        audioViewModel=audioViewModel
                    )

                }
            }
        }
    }


}



