package com.fw.littlelemon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fw.littlelemon.ui.theme.Black
import com.fw.littlelemon.ui.theme.Green
import com.fw.littlelemon.ui.theme.GreyAF
import com.fw.littlelemon.ui.theme.White
import com.fw.littlelemon.ui.theme.Yellow
import com.fw.littlelemon.ui.theme.YellowEE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        firstName = userPreferences.firstName.first() ?: ""
        lastName = userPreferences.lastName.first() ?: ""
        email = userPreferences.email.first() ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = "Personal information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Spacer(modifier = Modifier.height(32.dp))
            LemonTextField(
                label = "First name",
                enabled = false,
                value = firstName,
                onValueChange = { }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LemonTextField(
                label = "Last name",
                enabled = false,
                value = lastName,
                onValueChange = { }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LemonTextField(
                label = "Email",
                enabled = false,
                value = email,
                onValueChange = { }
            )
        }

        Button(
            onClick = {
                scope.launch {
                    userPreferences.clearUserData()
                }
                navController.navigate(Onboarding.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, YellowEE),
            colors = ButtonDefaults.buttonColors(containerColor = Yellow)
        ) {
            Text(
                text = "Log out",
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ProfileTextField(
    label: String,
    value: String,
) {
    Column {
        Text(
            text = label,
            color = Green,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            color = Green,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = GreyAF, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
        )
    }
}

@Preview
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    Profile(navController)
}
