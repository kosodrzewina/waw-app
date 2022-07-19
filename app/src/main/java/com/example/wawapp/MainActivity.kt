package com.example.wawapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.wawapp.navigation.BottomNavBar
import com.example.wawapp.navigation.NavGraph

class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            navHostController = rememberNavController()

            Scaffold(bottomBar = { BottomNavBar(navController = navHostController) }) {
                NavGraph(
                    navController = navHostController,
                    context = this,
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}
