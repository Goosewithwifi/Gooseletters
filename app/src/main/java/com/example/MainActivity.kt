package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.MainFeedScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NewsViewModel = viewModel()
            val isDarkModeOverride by viewModel.isDarkMode.collectAsState()
            val darkTheme = isDarkModeOverride ?: isSystemInDarkTheme()

            MyApplicationTheme(darkTheme = darkTheme) {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "feed",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("feed") {
                        MainFeedScreen(
                            viewModel = viewModel,
                            onNavigateToDetail = {
                                navController.navigate("detail")
                            }
                        )
                    }
                    composable("detail") {
                        ArticleDetailScreen(
                            viewModel = viewModel,
                            onNavigateBack = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
