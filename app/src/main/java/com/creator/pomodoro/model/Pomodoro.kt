package com.example.pomodoro.model


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.creator.pomodoro.R

enum class Pomodoro(
    val title: String,
    val time: Int,
    val textColor: Color,
    val icon: Int,
    val iconColor: Color,
    val backgroundColor: Color,
    val buttonColorPrimary: Color,
    val buttonColorSecondary: Color,


    ) {

    FOCUS(
        title = "Focus",
//        time = 1500,
        time = 5,
        textColor =  Color(0xFF471515),
        icon = R.drawable.ph__brain,
        iconColor = Color(0xFF471515),
        backgroundColor = Color(0XFFFFF2F2),
        buttonColorPrimary = Color(0xFFFF7C7C),
        buttonColorSecondary = Color(0xFFFFD9D9),

    ),
    SHORT_BREAK(
        title = "Short Break",
//        time = 300,
        time = 5,
        textColor =  Color(0xFF14401D),
        icon = R.drawable.ph__coffee,
        iconColor = Color(0xFF14401D),
        backgroundColor = Color(0XFFF2FFF5),
        buttonColorPrimary = Color(0xFF4DDA6E).copy(0.62f),
        buttonColorSecondary = Color(0xFF4DDA6E).copy(0.15f),
    ),
    LONG_BREAK(
        title = "Long Break",
        time = 900,
        textColor =  Color(0xFF153047),
        icon = R.drawable.ph__coffee,
        iconColor = Color(0xFF153047),
        backgroundColor = Color(0XFFF2F9FF),
        buttonColorPrimary = Color(0xFF4CACFF).copy(0.62f),
        buttonColorSecondary = Color(0xFF4CACFF).copy(0.15f),
    )
}
class PomodoroTimer(
    var focus: Int = 1500,
    var shortBreak: Int = 300,
    var longBreak: Int = 900
)