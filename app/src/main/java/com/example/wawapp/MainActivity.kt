package com.example.wawapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wawapp.events.EventManager
import com.example.wawapp.events.EventType
import com.example.wawapp.navigation.BottomNavBar
import com.example.wawapp.navigation.NavGraph
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            EventManager.fetchEvents(*EventType.values())
            Auth.checkIfUserIsLoggedIn(this@MainActivity)
        }

        setContent {
            navHostController = rememberNavController()

            Scaffold(
                bottomBar = {
                    BottomNavBar(navController = navHostController)
                }
            ) {
                NavGraph(
                    navController = navHostController,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}
