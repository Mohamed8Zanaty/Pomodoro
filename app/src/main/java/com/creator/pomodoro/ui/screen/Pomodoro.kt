package com.example.pomodoro.ui.theme.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.creator.pomodoro.R
import com.example.pomodoro.model.Pomodoro
import com.example.pomodoro.model.PomodoroTimer
import kotlinx.coroutines.delay

const val longBreakSessionNumber = 4
@SuppressLint("DefaultLocale")
@Composable
fun PomodoroScreen() {
    var isPlayPomodoro by remember { mutableStateOf(false) }
    var pomodoro by remember { mutableStateOf(Pomodoro.FOCUS) }
    var timer by remember { mutableStateOf(PomodoroTimer()) }
    var timerLeft by remember { mutableIntStateOf(
        when(pomodoro) {
            Pomodoro.FOCUS -> timer.focus
            Pomodoro.SHORT_BREAK -> timer.shortBreak
            Pomodoro.LONG_BREAK -> timer.longBreak
        }
    ) }
    val timeText = String.format("%02d\n%02d", timerLeft / 60, timerLeft % 60)
    var sessionCount by remember { mutableIntStateOf(1) }

    LaunchedEffect(key1 = isPlayPomodoro) {
        while (isPlayPomodoro && timerLeft > 0) {
            delay(1000L)
            timerLeft--
            if(timerLeft <= 0 && pomodoro == Pomodoro.FOCUS) {
                if(sessionCount == longBreakSessionNumber) {
                    pomodoro = Pomodoro.LONG_BREAK
                    sessionCount = 1
                }
                else {
                    pomodoro = Pomodoro.SHORT_BREAK
                    sessionCount++
                }
                timerLeft = when(pomodoro) {
                    Pomodoro.SHORT_BREAK -> timer.shortBreak
                    Pomodoro.LONG_BREAK -> timer.longBreak
                    else -> timer.focus
                }
                isPlayPomodoro = false
            }
            else if(timerLeft <= 0) {
                pomodoro = Pomodoro.FOCUS
                timerLeft =  timer.focus
                isPlayPomodoro = false
            }

        }
    }
    var expanded by remember { mutableStateOf(false) }
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(pomodoro.backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .width(264.dp)
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = pomodoro.buttonColorSecondary,
                    shape = RoundedCornerShape(100.dp),
                    border = BorderStroke(width = 1.dp, color = pomodoro.textColor)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.width(22.dp),
                            painter = painterResource(id = pomodoro.icon),
                            contentDescription = null,
                            tint = pomodoro.textColor
                        )
                        Text(
                            modifier = Modifier.padding(start = 6.dp),
                            text = pomodoro.title,
                            color = pomodoro.textColor,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = timeText,
                    color = pomodoro.textColor,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 165.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 140.sp,
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            expanded = !expanded
                        },
                        modifier = Modifier.size(65.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pomodoro.buttonColorSecondary,
                        )

                    ) {
                        Image(
                            painter = painterResource(R.drawable.solar__menu_dots_bold),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(pomodoro.iconColor),
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(150.dp)
                        ,
                        offset = DpOffset(x = 0.dp, y = 500.dp),
                        containerColor = pomodoro.buttonColorSecondary
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                timer = PomodoroTimer(
                                    focus = 25 * 60,
                                    shortBreak = 5 * 60,
                                    longBreak = 15 * 60
                                )
                                timerLeft = when (pomodoro) {
                                    Pomodoro.FOCUS -> timer.focus
                                    Pomodoro.SHORT_BREAK -> timer.shortBreak
                                    Pomodoro.LONG_BREAK -> timer.longBreak
                                }
                            },
                            text = {
                                Text(
                                    text = "25 / 5 -> 15",
                                    color = pomodoro.textColor,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                timer = PomodoroTimer(
                                    focus = 45 * 60,
                                    shortBreak = 15 * 60,
                                    longBreak = 30 * 60
                                )
                                timerLeft = when (pomodoro) {
                                    Pomodoro.FOCUS -> timer.focus
                                    Pomodoro.SHORT_BREAK -> timer.shortBreak
                                    Pomodoro.LONG_BREAK -> timer.longBreak
                                }
                            },
                            text = {
                                Text(
                                    text = "45 / 15 -> 30",
                                    color = pomodoro.textColor,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                timer = PomodoroTimer(
                                    focus = 5,
                                    shortBreak = 5,
                                    longBreak = 5
                                )
                                timerLeft = when (pomodoro) {
                                    Pomodoro.FOCUS -> timer.focus
                                    Pomodoro.SHORT_BREAK -> timer.shortBreak
                                    Pomodoro.LONG_BREAK -> timer.longBreak
                                }
                            },
                            text = {
                                Text(
                                    text = "1 / 1 -> 1",
                                    color = pomodoro.textColor,
                                    fontFamily = FontFamily.SansSerif,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                    }
                    Button(
                        onClick = {
                            isPlayPomodoro = !isPlayPomodoro
                        },
                        modifier = Modifier
                            .size(width = 120.dp, height = 72.dp)
                            .padding(horizontal = 14.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pomodoro.buttonColorPrimary,
                        )

                    ) {
                        Image(
                            painter = if (isPlayPomodoro) painterResource(R.drawable.material_symbols_light__pause) else painterResource(
                                R.drawable.line_md__play_filled
                            ),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(pomodoro.iconColor),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Button(
                        onClick = {
                            isPlayPomodoro = false
                            pomodoro = when (pomodoro) {
                                Pomodoro.FOCUS -> Pomodoro.SHORT_BREAK
                                Pomodoro.SHORT_BREAK -> Pomodoro.FOCUS
                                Pomodoro.LONG_BREAK -> Pomodoro.FOCUS
                            }
                            timerLeft = when (pomodoro) {
                                Pomodoro.FOCUS -> timer.focus
                                Pomodoro.SHORT_BREAK -> timer.shortBreak
                                Pomodoro.LONG_BREAK -> timer.longBreak
                            }
                        },
                        modifier = Modifier.size(65.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = pomodoro.buttonColorSecondary,
                        )

                    ) {
                        Image(
                            painter = painterResource(R.drawable.tabler__player_track_next_filled),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(pomodoro.iconColor),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PomodoroPreview() {
    PomodoroScreen()
}