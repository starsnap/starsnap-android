package com.photo.starsnap.main.ui.screen.main.star_hub

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.CustomColor.container
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle.title2
import com.photo.starsnap.main.ui.component.PasswordEyeCheckBox
import com.photo.starsnap.main.ui.component.TextEditHint
import com.photo.starsnap.main.utils.EditTextType
import com.photo.starsnap.main.utils.clickableSingle

@Composable
@Preview
fun SearchHubScreen() {
    LaunchedEffect(Unit) {
        Log.d("화면", "SearchHubScreen")
    }

    Scaffold(
        modifier = Modifier.padding(horizontal = 22.dp),
        topBar = { SearchTopBar(state) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
        }
    }
}


@Composable
fun SearchTopBar(state: Boolean = false) {
    var text by remember { mutableStateOf("") }
    var hintText = if (state) "StarGroup 검색" else "Star 검색"
    Column(
        modifier = Modifier
            .height(55.dp)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        BasicTextField(
            value = text,
            textStyle = title2,
            onValueChange = { input ->
                if (100 > input.length) {
                    text = input
                }
            },
            modifier = Modifier
                .height(55.dp)
                .background(container, shape = RoundedCornerShape(size = 8.dp)),
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(start = 0.dp, end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(11.dp))
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                        contentDescription = "search_icon"
                    )
                    Spacer(Modifier.width(9.dp))
                    Box(
                        Modifier.weight(1F)
                    ) {
                        innerTextField()
                        if (text.isEmpty()) {
                            TextEditHint(hintText)
                        }
                    }
                }
            })
        Column(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(CustomColor.container)
        ) { }
        Spacer(modifier = Modifier.height(9.dp))
    }
}