package com.fw.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun Home(navController: NavHostController) {
    Column {
        UpperPanel()
        LowerPanel(
            navController,
            listOf(
                MenuItemNetwork(
                    1,
                    "Sample Dish",
                    "This is a sample dish description.",
                    "9.99",
                    "sample_dish.png"
                )
            )
        )
    }
}