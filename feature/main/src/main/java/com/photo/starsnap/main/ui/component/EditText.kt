package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.photo.starsnap.designsystem.gray
import com.photo.starsnap.designsystem.container

enum class EditTextType {
    PASSWORD,
    ID
}

@Composable
fun EditText(hint: String, type: EditTextType) {
    var text by remember { mutableStateOf("") }
    val visualTransformation =
        if (type == EditTextType.ID) VisualTransformation.None else PasswordVisualTransformation()
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BasicTextField(value = text,
            onValueChange = { input ->
                text = input
            },
            modifier = Modifier
                .height(50.dp)
                .background(container, shape = RoundedCornerShape(size = 8.dp)),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 0.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(19.dp))
                    Box {
                        innerTextField()
                        if (text.isEmpty()) {
                            Text(hint, color = gray)
                        }


                    }
                    if (type == EditTextType.PASSWORD){
                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
            })
    }
}

@Preview
@Composable
fun PreviewEditText() {
    EditText("아이디", EditTextType.ID)
}

@Preview
@Composable
fun PreviewPasswordEditText() {
    EditText("비밀번호", EditTextType.PASSWORD)
}