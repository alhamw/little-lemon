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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.fw.littlelemon.ui.theme.White
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val httpClient =
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }, contentType = ContentType.Text.Plain)
            }
        }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "little-lemon-db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val menuNetwork = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body<MenuNetwork>()
                val menuEntities = menuNetwork.menu.map { it.toEntity() }
                database.menuDao().insertAll(menuEntities)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        enableEdgeToEdge()
        setContent {
            val menuItems by database.menuDao().getAllMenuItems().observeAsState(emptyList())
            MainPanel(menuItems)
        }
    }
}

@Composable
fun MainPanel(menuItems: List<MenuItemEntity>) {
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
                            painter = painterResource(id = R.drawable.logo),
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
                                    .size(50.dp)
                                    .padding(end = 12.dp),
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
                NavigationComposable(navController, isLoggedIn, menuItems)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainPanelPreview() {
    MainPanel(emptyList())
}