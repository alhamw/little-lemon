package com.fw.littlelemon


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fw.littlelemon.ui.theme.Cloud
import com.fw.littlelemon.ui.theme.White
import com.fw.littlelemon.ui.theme.Yellow

@Composable
fun LowerPanel(navController: NavHostController, dishes: List<MenuItemNetwork> = listOf()) {
    Column(
        modifier = Modifier.background(White)
    ) {
        WeeklySpecialCard()
        LazyColumn {
            itemsIndexed(dishes) { _, dish ->
                MenuDish(navController, dish)
            }
        }
    }
}

@Composable
fun WeeklySpecialCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.weekly_special),
//            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

//@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MenuDish(navController: NavHostController? = null, dish: MenuItemNetwork) {
//    Box(onClick = {
//        Log.d("AAA", "Click ${dish.id}")
//        navController?.navigate(DishDetails.route + "/${dish.id}")
//    }) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            Column {
                Text(dish.title, style = MaterialTheme.typography.titleMedium)
                Text(dish.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth(0.75f).padding(vertical = 5.dp))
                Text("$ ${dish.price}", style = MaterialTheme.typography.bodyMedium)
            }
            Image(
                painter = painterResource(id = R.drawable.pasta),
                modifier = Modifier.size(100.dp),
                contentDescription = dish.title,
                contentScale = ContentScale.Crop
            )
        }
//    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
        thickness = 1.dp,
        color = Cloud
    )
}

@Preview
@Composable
fun LowerPanelPreview() {
    LowerPanel(navController = NavHostController(LocalContext.current), dishes = listOf(
        MenuItemNetwork(1, "Dish Name", "This is a description of the dish.", "9.99", ""),
        MenuItemNetwork(2, "Another Dish", "This is another description.", "12.99", "")
    ))
}