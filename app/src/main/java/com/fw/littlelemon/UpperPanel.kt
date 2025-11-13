package com.fw.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fw.littlelemon.ui.theme.Cloud
import com.fw.littlelemon.ui.theme.Green
import com.fw.littlelemon.ui.theme.White
import com.fw.littlelemon.ui.theme.Yellow

@Composable
fun UpperPanel() {
    Column(
        modifier = Modifier
            .background(Green)
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.title),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Yellow
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.location),
                    fontSize = 24.sp,
                    color = Cloud
                )
                Text(
                    text = stringResource(id = R.string.description),
//                style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 20.dp, bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(0.6f),
                    color = Cloud
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Upper Panel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .size(width = 130.dp, height = 130.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            placeholder = { Text(text = "Search") },
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = White,
                unfocusedIndicatorColor = White,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                disabledIndicatorColor = White,
                disabledContainerColor = White,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UpperPanelPreview() {
    UpperPanel()
}