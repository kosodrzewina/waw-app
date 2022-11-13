package com.example.wawapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wawapp.events.EventHttpClient
import com.example.wawapp.events.EventType
import com.example.wawapp.navigation.BottomNavBar
import com.example.wawapp.navigation.NavGraph
import com.example.wawapp.ui.theme.WawAppTheme
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private lateinit var systemUiController: SystemUiController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            Auth.checkIfUserIsLoggedIn(this@MainActivity)
            EventHttpClient.fetchEvents(*EventType.values())
        }

        setContent {
            navHostController = rememberNavController()

            WawAppTheme {
                val backgroundColor = MaterialTheme.colors.background

                systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(color = backgroundColor)
                }

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
}
