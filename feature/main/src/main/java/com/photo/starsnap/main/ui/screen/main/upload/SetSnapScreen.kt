package com.photo.starsnap.main.ui.screen.main.upload

import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.photo.starsnap.designsystem.CustomColor
import com.photo.starsnap.designsystem.R
import com.photo.starsnap.designsystem.text.CustomTextStyle
import com.photo.starsnap.designsystem.text.CustomTextStyle.TitleSmall
import com.photo.starsnap.designsystem.text.CustomTextStyle.title2
import com.photo.starsnap.designsystem.text.CustomTextStyle.title8
import com.photo.starsnap.main.ui.component.TextEditHint
import com.photo.starsnap.main.ui.component.TopAppBar
import com.photo.starsnap.main.utils.NavigationRoute
import com.photo.starsnap.main.utils.clickableSingle
import com.photo.starsnap.main.viewmodel.main.UploadViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun SetSnapScreen(navController: NavController, uploadViewModel: UploadViewModel) {
    LaunchedEffect(Unit) {
        Log.d("화면", "SetSnapScreen")
    }
    val selectedPhotos by uploadViewModel.selectedPhotos.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = { selectedPhotos.size })

    var showDots by remember { mutableStateOf(false) }
    var showModalInput by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    // snap 정보
    var title by remember { mutableStateOf("") } // 제목
    var tags by remember { mutableStateOf(listOf<String>()) } // 태그
    val stars by uploadViewModel.selectedStars.collectAsStateWithLifecycle()
    var starGroups = uploadViewModel.selectedStarGroups.collectAsStateWithLifecycle()
    var dateTaken by remember { mutableStateOf("YYYY-MM-DD") }
    var aiState by remember { mutableStateOf(false) } // AI 여부
    var commentsEnabled by remember { mutableStateOf(true) } // 댓글 허용 여부

    LaunchedEffect(pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress) {
            showDots = true
        } else {
            // Hide after a short delay when scrolling stops
            delay(600)
            showDots = false
        }
    }

    Scaffold(
        topBar = { TopAppBar("새로운 스냅", navController) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 22.dp)
                .verticalScroll(rememberScrollState(), enabled = true, flingBehavior = null),
        ) {
            val starGridState = rememberLazyGridState()
            val starGroupGridState = rememberLazyGridState()

            // 사진 위치 상태
            val dotsAlpha by animateFloatAsState(
                targetValue = if (showDots) 1f else 0f,
                animationSpec = tween(durationMillis = 250),
                label = "dotsAlpha"
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            ) { page ->
                val photo = selectedPhotos[page]
                GlideImage(
                    modifier = Modifier
                        .animateContentSize()
                        .fillMaxSize(),
                    imageModel = { photo.imageUri },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillHeight,
                        alignment = Alignment.Center,
                        alpha = 1f
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .alpha(dotsAlpha), // 공간 유지 + 페이드 애니메이션
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(selectedPhotos.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (isSelected) 10.dp else 8.dp)
                            .background(
                                color = if (isSelected) CustomColor.light_black
                                else CustomColor.light_gray.copy(alpha = 0.3f), shape = CircleShape
                            )
                    )
                }
            }
            Spacer(Modifier.height(15.dp))
            Text("제목")
            Spacer(Modifier.height(5.dp))
            TextField(
                // 제목 입력
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("제목 추가...") },
            )
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("태그")
            ChipTextField(
                tags = tags,
                onTagsChange = { tags = it }
            )
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("Star")
            Spacer(Modifier.height(5.dp))
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                state = starGridState,
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // +1 to place the "add" tile at index 0, followed by the selected stars
                items(stars.size + 1) { index ->
                    if (index == 0) {
                        // Add tile
                        Column(
                            modifier = Modifier
                                .width(70.dp)
                                .clickableSingle { navController.navigate(NavigationRoute.PICK_STAR) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        CustomColor.light_gray.copy(alpha = 0.3f),
                                        shape = CircleShape
                                    )
                                    .width(70.dp)
                                    .height(70.dp),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.plus_circle_icon),
                                    contentDescription = "plus_circle_icon",
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "추가하기",
                                maxLines = 1,
                                style = title2
                            )
                        }
                    } else {
                        // Star item from the collected list
                        val star = stars[index - 1]
                        Column(
                            modifier = Modifier
                                .width(70.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        CustomColor.light_gray.copy(alpha = 0.15f),
                                        shape = CircleShape
                                    )
                                    .width(70.dp)
                                    .height(70.dp),
                                contentAlignment = Alignment.Center
                            ) {
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = star.name,
                                maxLines = 1,
                                style = title2
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("StarGroup")
            Spacer(Modifier.height(5.dp))
            LazyHorizontalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(87.dp),
                state = starGroupGridState,
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                items(1) {
                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .clickableSingle {
                                navController.navigate(NavigationRoute.PICK_STAR_GROUP)
                            }) {
                        Box(
                            modifier = Modifier
                                .background(
                                    CustomColor.light_gray.copy(alpha = 0.3f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .width(95.dp)
                                .height(60.dp), contentAlignment = Alignment.BottomEnd
                        ) {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.plus_circle_icon),
                                contentDescription = "plus_circle_icon",
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "추가하기",
                            maxLines = 1,
                            style = title2
                        )
                    }
                }
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("사진 찍은 날짜")
            Spacer(Modifier.height(5.dp))
            DateTextField(dateTaken) {
                showModalInput = true
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Row {
                Column {
                    Text("AI 사용 여부")
                    Spacer(Modifier.height(4.dp))
                    Text("AI로 제작된 사진은 체크 해야합니다.")
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = aiState,
                    onCheckedChange = {
                        aiState = it
                    }
                )
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Row {
                Column {
                    Text("댓글 사용 여부")
                    Spacer(Modifier.height(4.dp))
                    Text("게시글 댓글 사용을 중지 하려면 체크 해주세요")
                }
                Spacer(Modifier.weight(1f))
                Switch(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = commentsEnabled,
                    onCheckedChange = {
                        commentsEnabled = it
                    }
                )
            }
            Spacer(Modifier.height(15.dp))
            Divider() // 구분선
            Spacer(Modifier.height(15.dp))
            Text("출처")
            Spacer(Modifier.height(5.dp))
            TextField(
                // 출처 입력
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("출처") },
            )
            Spacer(Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .background(CustomColor.yellow_100)
                    .clickableSingle {
                        uploadViewModel.uploadSnap()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("만들기")
            }
        }
        if (showModalInput) {
            Dialog(onDismissRequest = { showModalInput = false }) {
                Surface(shape = RoundedCornerShape(12.dp), color = Color.White) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        // 달력 모달 본문
                        test(
                            currentSelectedDate = selectedDate,
                            onDateChange = { picked -> selectedDate = picked }
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = { showModalInput = false }) { Text("취소") }
                            Spacer(Modifier.width(8.dp))
                            TextButton(onClick = {
                                showModalInput = false
                                dateTaken =
                                    if (selectedDate != null) selectedDate.toString() else "YYYY-MM-DD"
                            }) { Text("확인") }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipTextField(
    tags: List<String>,
    onTagsChange: (List<String>) -> Unit
) {
    val context = LocalContext.current
    var input by remember { mutableStateOf("") }
    fun addTagIfPossible(raw: String) {
        val t = raw.trim()
        if (t.isEmpty()) return
        if (t.length > 10) {
            Toast.makeText(context, "태그는 최대 10자까지 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (tags.size >= 5) {
            Toast.makeText(context, "최대 ${5}개까지 가능합니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if (tags.any { it.equals(t, ignoreCase = true) }) {
            Toast.makeText(context, "이미 추가된 태그예요.", Toast.LENGTH_SHORT).show()
            return
        }
        onTagsChange(tags + t)
        input = ""
    }

    BasicTextField(
        value = input,
        onValueChange = { text ->
            // 쉼표로도 태그 확정 가능 (e.g. "kotlin,")
            if (text.endsWith(",")) {
                addTagIfPossible(text.removeSuffix(","))
            } else if (tags.size < 5) {
                input = text
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { addTagIfPossible(input) }),
        modifier = Modifier
            .fillMaxWidth()
            .onPreviewKeyEvent { event ->
                if (event.key == Key.Backspace && input.isEmpty() && tags.isNotEmpty()) {
                    // remove the last tag when backspace is pressed and there is no input text
                    onTagsChange(tags.dropLast(1))
                    true
                } else {
                    false
                }
            }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        decorationBox = { innerTextField ->
            // 필드 안에 Chips + 입력 커서를 함께 배치
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF3A3D43), RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(tag, color = Color.White)
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "✕",
                                color = Color(0xFF9AA0A6),
                                modifier = Modifier.clickableSingle {
                                    onTagsChange(tags - tag)
                                }
                            )
                        }
                    }
                }

                // 입력 커서가 들어갈 공간
                Box(
                    modifier = Modifier.weight(1f, fill = false),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (input.isEmpty()) {
                        Text(
                            text = if (tags.isEmpty()) "태그 입력 후 Enter (쉼표도 가능)" else "",
                            color = Color(0xFF9AA0A6)
                        )
                    }
                    // 실제 텍스트 입력
                    innerTextField()
                }
            }
        }
    )
}


@Composable
fun DateTextField(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(40.dp)
            .border(
                border = BorderStroke(0.5.dp, CustomColor.light_gray),
                shape = RoundedCornerShape(8.dp)
            )
            .clickableSingle {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(11.dp))
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = ImageVector.vectorResource(R.drawable.calendar_icon),
            contentDescription = "calendar_icon",
            tint = CustomColor.sub_title
        )
        Spacer(modifier = Modifier.width(9.dp))
        Box(
            Modifier.weight(1F)
        ) {
            TextEditHint(text)
        }
    }
}

@Composable
fun test(currentSelectedDate: LocalDate?, onDateChange: (LocalDate?) -> Unit) {
    val currentMonth = remember { YearMonth.now() } // 현재 날짜 구하기
    val startMonth = remember { currentMonth.minusYears(10) } // 현재 날짜에서 -10년을 마지막으로
    val endMonth = remember { YearMonth.now() } // 최대 일을 현재로
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek,
    )

    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = state.firstVisibleMonth.yearMonth
    val monthTitle = "${visibleMonth.year}년 ${visibleMonth.month.value}월"

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        coroutineScope.launch {
                            state.animateScrollToMonth(
                                visibleMonth.minusMonths(
                                    1
                                )
                            )
                        }
                    },
                imageVector = ImageVector.vectorResource(R.drawable.chevron_left_icon),
                contentDescription = "chevron_left_icon",
                tint = CustomColor.sub_title
            )
            Text(
                text = monthTitle,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1F),
                style = CustomTextStyle.title1
            )
            // Conditionally show right arrow icon or spacer to preserve layout
            if (visibleMonth.isBefore(endMonth)) {
                Icon(
                    modifier = Modifier
                        .size(18.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            coroutineScope.launch {
                                state.animateScrollToMonth(
                                    visibleMonth.plusMonths(1)
                                )
                            }
                        },
                    imageVector = ImageVector.vectorResource(R.drawable.chevron_right_icon),
                    contentDescription = "chevron_right_icon",
                    tint = CustomColor.sub_title
                )
            } else {
                Spacer(modifier = Modifier.size(18.dp))
            }
        }
        HorizontalCalendar(
            state = state,
            dayContent = { day ->
                Day(
                    day = day,
                    isSelected = currentSelectedDate == day.date,
                    onClick = { clickedDay ->
                        val newSelection =
                            if (currentSelectedDate == clickedDay.date) null else clickedDay.date
                        onDateChange(newSelection)
                    }
                )
            },
            monthHeader = {
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            }
        )
    }
}

@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    val isMonthDate = day.position == DayPosition.MonthDate
    val d = day.date

    val textColor = when {
        !isMonthDate -> CustomColor.light_gray
        d.dayOfWeek == java.time.DayOfWeek.SUNDAY -> Color.Red
        d.dayOfWeek == java.time.DayOfWeek.SATURDAY -> Color.Blue
        else -> CustomColor.light_black
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(color = if (isSelected) Color.Green else Color.Transparent)
            .clickable(
                enabled = isMonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = d.dayOfMonth.toString(),
            color = textColor
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
@Preview
fun StarProfile() {
    Column(
        modifier = Modifier
            .width(70.dp)
            .clickableSingle {

            }) {
        Box(
            modifier = Modifier
                .background(CustomColor.light_gray.copy(alpha = 0.3f), shape = CircleShape)
                .width(70.dp)
                .height(70.dp), contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.plus_circle_icon),
                contentDescription = "plus_circle_icon",
            )
        }
        Spacer(Modifier.height(3.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "추가하기",
            maxLines = 1
        )
    }
}

@Composable
fun StarGroup() {

}