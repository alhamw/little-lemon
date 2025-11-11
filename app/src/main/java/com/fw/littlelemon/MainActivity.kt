@file:OptIn(ExperimentalMaterial3Api::class)

package com.fw.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fw.littlelemon.ui.theme.LittleLemonTheme
import com.fw.littlelemon.ui.theme.White
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            MainPanel()
        }
    }
}

@Composable
fun MainPanel() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var isLoggedIn by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        userPreferences.isLoggedIn.collect {
            isLoggedIn = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo), // Replace with your logo
                            contentDescription = "Little Lemon Logo",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                actions = {
                    if (isLoggedIn && navBackStackEntry?.destination?.route != Profile.route) {
                        IconButton(onClick = {
                            navController.navigate(Profile.route)
                        }) {
                            Image (
                                painter = painterResource(id = R.drawable.profile),
                                contentDescription = "Profile",
                                modifier = Modifier
                                    .size(50.dp) // Example size
                                    .padding(end = 12.dp), // Padding on the right
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = White)
            )
        },
        bottomBar = { },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavigationComposable(navController, isLoggedIn)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainPanelPreview() {
    MainPanel()
}