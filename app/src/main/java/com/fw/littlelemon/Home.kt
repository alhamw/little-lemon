package com.fw.littlelemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fw.littlelemon.ui.theme.*

// Main entry point for the Home Screen
@Composable
fun Home(navController: NavHostController, menuItems: List<MenuItemNetwork>) {
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }

    val categories = menuItems.map { it.category }.distinct()

    val filteredItems = remember(searchPhrase, selectedCategory) {
        menuItems.filter { item ->
            val matchesCategory = selectedCategory.isEmpty() || item.category.equals(selectedCategory, ignoreCase = true)
            val matchesSearch = searchPhrase.isEmpty() || item.title.contains(searchPhrase, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }


    Column(modifier = Modifier.fillMaxSize()) {
        HeroSection(searchPhrase = searchPhrase, onSearchPhraseChange = { searchPhrase = it })
        MenuBreakdown(categories = categories, selectedCategory = selectedCategory, onCategorySelect = { selectedCategory = it })
        MenuItemsList(items = filteredItems)
    }
}

// 1. The top green hero section
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroSection(searchPhrase: String, onSearchPhraseChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .background(Green)
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Little Lemon", fontSize = 40.sp, color = Yellow, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Chicago", fontSize = 24.sp, color = White)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image), // Replace with your hero image
                contentDescription = "Hero Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = searchPhrase,
            onValueChange = onSearchPhraseChange,
            placeholder = { Text("Enter search phrase") },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                containerColor = Grey,
//                focusedIndicatorColor = Color.Transparent,
//                unfocusedIndicatorColor = Color.Transparent
//            )
        )
    }
}

// 2. The "ORDER FOR DELIVERY!" and category buttons section
@Composable
fun MenuBreakdown(categories: List<String>, selectedCategory: String, onCategorySelect: (String) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
        Text(text = "ORDER FOR DELIVERY!", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            categories.forEach { category ->
                Button(
                    onClick = {
                        // Toggle selection: if clicking the same category, deselect it.
                        if (selectedCategory == category) {
                            onCategorySelect("")
                        } else {
                            onCategorySelect(category)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedCategory == category) Yellow else Cloud,
                        contentColor = if (selectedCategory == category) Color.White else Green
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = category)
                }
            }
        }
        Divider(modifier = Modifier.padding(top = 16.dp), thickness = 1.dp, color = Cloud)
    }
}

// 3. The lazy list of menu items
@Composable
fun MenuItemsList(items: List<MenuItemNetwork>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { menuItem ->
            MenuItem(item = menuItem)
        }
    }
}

// 4. A single menu item row
@Composable
fun MenuItem(item: MenuItemNetwork) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = item.description, color = Green, modifier = Modifier.padding(vertical = 8.dp))
            Text(text = "$${item.price}", color = Green, fontWeight = FontWeight.SemiBold)
        }
        // NOTE: For network images, you should use an image loading library like Coil
        // For now, we use a placeholder.
        Image(
            painter = painterResource(id = R.drawable.bruschetta), // Replace with a placeholder or use Coil
            contentDescription = item.title,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
    Divider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 1.dp, color = Cloud)
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val previewItems = listOf(
        MenuItemNetwork(1, "Greek Salad", "The famous greek salad of crispy lettuce, peppers, olives and our Chicago...", "12.99", "","starters"),
        MenuItemNetwork(2, "Brushetta", "Our Bruschetta is made from grilled bread that has been smeared with garlic...", "7.99", "","starters"),
        MenuItemNetwork(3, "Lemon Dessert", "This comes straight from grandma’s recipe book, every last ingredient is as authentic...", "6.99", "","desserts")
    )
    Home(navController = NavHostController(LocalContext.current), menuItems = previewItems)
}

