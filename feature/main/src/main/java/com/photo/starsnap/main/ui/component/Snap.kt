package com.photo.starsnap.main.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.text.CustomTextStyle.title3
import com.photo.starsnap.designsystem.text.CustomTextStyle.title5
import com.photo.starsnap.designsystem.text.CustomTextStyle.title7
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.main.ui.component.skeleton.SnapSkeleton
import com.photo.starsnap.main.utils.constant.Constant.getImageUrl
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.network.snap.dto.CommentDto
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration

@Composable
fun Snap(snapImageUrl: String, onClick: () -> Unit) {
    Box(
        Modifier
            .clickableSingle { onClick() }
            .background(shape = RoundedCornerShape(15.dp), color = Color.White)) {
        GlideImage(
            imageModel = { getImageUrl(snapImageUrl) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            ),
            loading = { SnapSkeleton() }
        )
    }
}

@Composable
fun SnapIcon() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        LikeIconButton()
        Spacer(modifier = Modifier.width(8.dp))
        CommentIconButton()
        Spacer(modifier = Modifier.weight(1F))
        SaveIconButton()
    }
}

@Composable
fun SnapUserName(username: String) {
    Text(text = username, style = title3)
}

@Composable
fun SnapTitle(title: String) {
    Text(text = title, style = title5)
}

@Composable
fun SnapTag(tags: List<String>) {
    Row {
        tags.forEach { tagName ->
            Text(text = "#$tagName", style = title7)
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}

@Composable
fun SnapDateTaken(dateTaken: String) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.calendar_icon),
            contentDescription = null,
            tint = CustomColor.sub_title
        )
        Text(text = dateTaken, style = title7)
    }
}

@Composable
fun SnapSource(source: String) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = null,
            tint = CustomColor.sub_title
        )
        Text(text = source, style = title7)
    }
}

@Composable
fun SnapCreateAt(createAt: String) {
    val formattedTime = getTimeAgo(createAt)
    Text(text = formattedTime, style = TextStyle(color = CustomColor.gray))
}

@Composable
fun SnapDivider() {
    HorizontalDivider(
        thickness = 3.dp,
        color = Color(0xFFDBDEE2)
    )
}

fun getTimeAgo(createAt: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val createdDateTime = LocalDateTime.parse(createAt, formatter)
    val now = LocalDateTime.now()

    val duration = Duration.between(createdDateTime, now)

    return when {
        duration.seconds < 60 -> "방금 전"
        duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
        duration.toHours() < 24 -> "${duration.toHours()}시간 전"
        duration.toDays() < 7 -> "${duration.toDays()}일 전"
        duration.toDays() < 30 -> "${duration.toDays() / 7}주 전"
        duration.toDays() < 365 -> "${duration.toDays() / 30}달 전"
        else -> "${duration.toDays() / 365}년 전"
    }
}

@Composable
fun SnapImage(imageUrl: String?) {
    GlideImage(
        modifier = Modifier.fillMaxWidth(),
        imageModel = { getImageUrl(imageUrl) },
        imageOptions = ImageOptions(
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center
        ),
        loading = { SnapSkeleton() }
    )
}

@Composable
fun SnapUser(profileImageUrl: String?, username: String, createAt: String) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundProfileImage(imageKey = profileImageUrl, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(5.dp))
        SnapUserName(username)
        Spacer(modifier = Modifier.width(5.dp))
        SnapCreateAt(createAt)
    }
}

@Composable
fun SnapInformation(
    title: String,
    tags: List<String>,
    dateTaken: String,
    source: String
) {
    Row(
        modifier = Modifier
            .height(30.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SnapTitle(title)
        Spacer(modifier = Modifier.width(5.dp))
        SnapTag(tags)
        Spacer(modifier = Modifier.width(5.dp))
        SnapDateTaken(dateTaken)
        Spacer(modifier = Modifier.width(5.dp))
        SnapSource(source)
        SnapDivider()
    }
}

@Composable
fun SnapMessages(
    messages: List<CommentDto>?
) {
    Column {
        SnapTitle("메시지")
        Spacer(modifier = Modifier.height(5.dp))
        messages?.forEach { message ->
            SnapMessage(message)
        }
    }
}

@Composable
fun SnapMessage(
    message: CommentDto
) {
    Row(
        modifier = Modifier.height(25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundProfileImage(imageKey = message.profileKey, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = message.content, style = title7)
        Spacer(modifier = Modifier.width(4.dp))
        SnapCreateAt(message.createdAt.toString())
        SnapDivider()
    }
}

@Composable
fun SelectImage() {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black.copy(alpha = 0.3f))
    )
}

@Composable
fun SnapStar() {

}

@Composable
fun RelatedSnap() {

}