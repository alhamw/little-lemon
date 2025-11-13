package com.fw.littlelemon

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fw.littlelemon.ui.theme.Black
import com.fw.littlelemon.ui.theme.Green
import com.fw.littlelemon.ui.theme.White
import com.fw.littlelemon.ui.theme.Yellow
import com.fw.littlelemon.ui.theme.YellowEE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavHostController) {
    RegistrationForm(
        navController = navController
    )
}

@Composable
fun RegistrationForm(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val userPreferences = remember { UserPreferences(context) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isRegistrationFailed by remember { mutableStateOf(false) }
    var isRegistrationSuccess by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let's get to know you",
            color = White,
            fontSize = 24.sp,
            modifier = Modifier
                .background(Green)
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Personal information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Spacer(modifier = Modifier.height(32.dp))

            LemonTextField(
                label = "First name",
                value = firstName,
                onValueChange = {
                    isRegistrationFailed = false
                    firstName = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LemonTextField(
                label = "Last name",
                value = lastName,
                onValueChange = {
                    isRegistrationFailed = false
                    lastName = it
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            LemonTextField(
                label = "Email",
                value = email,
                onValueChange = {
                    isRegistrationFailed = false
                    email = it
                },
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (isRegistrationFailed) {
                Text(
                    text = "Registration unsuccessful. Please enter all data.",
                    color = Color.Red
                )
            }
            if (isRegistrationSuccess) {
                Text(
                    text = "Registration successful!",
                    color = Green
                )
            }
        }

        Button(
            onClick = {
                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
                    isRegistrationFailed = true
                } else {
                    isRegistrationSuccess = true

                    scope.launch {
                        userPreferences.saveUserData(
                            firstName = firstName,
                            lastName = lastName,
                            email = email
                        )
                    }
                    navController.navigate(Home.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, YellowEE),
            colors = ButtonDefaults.buttonColors(containerColor = Yellow)
        ) {
            Text(
                text = "Register",
                color = Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}
