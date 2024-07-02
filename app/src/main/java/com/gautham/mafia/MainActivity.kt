package com.gautham.mafia

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController


import com.gautham.mafia.Models.MainViewModel
import com.gautham.mafia.ui.MafiaApp
import com.gautham.mafia.ui.theme.MafiaTheme
import dagger.hilt.android.AndroidEntryPoint
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    val TAG ="ACTIVITYT"
    var viewmodel:MainViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MafiaTheme {
                val navController = rememberNavController()
                 viewmodel = hiltViewModel<MainViewModel>()
                val windowInsetsController =
                    WindowCompat.getInsetsController(window, window.decorView)
                windowInsetsController.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                windowInsetsController.hide(WindowInsetsCompat.Type.statusBars())

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    MafiaApp(
                        navController=navController,
                        viewmodel=viewmodel!!,
                        innerPadding=innerPadding,
                        windowInsetsController

                    )

                }
            }
        }
    }
    override fun onResume() {

        Log.d(TAG, "onResume: ")

        super.onResume()
    }
    override fun onPause() {
        Log.d(TAG, "onPause: ")
        super.onPause()
    }
    override fun onStart() {
        Log.d(TAG, "onStart: ")
        super.onStart()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }
    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
    }
    override fun onRestart() {
        Log.d(TAG, "onRestart: ")
        super.onRestart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d(TAG, "onSaveInstanceState: ")
        super.onSaveInstanceState(outState)
    }


}



