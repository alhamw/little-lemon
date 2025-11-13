package com.fw.littlelemon

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fw.littlelemon.ui.theme.Green
import com.fw.littlelemon.ui.theme.GreyAF
import com.fw.littlelemon.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonTextField(
    label: String,
    value: String,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column {
        Text(
            text = label,
            color = Green,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            value = value,
            enabled = enabled,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, color = GreyAF, shape = RoundedCornerShape(8.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
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