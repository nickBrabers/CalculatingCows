package com.example.calculatingcows

import android.text.format.DateUtils
import android.util.Log
import androidx.core.util.TimeUtils
import org.intellij.lang.annotations.JdkConstants
import java.text.SimpleDateFormat
import java.util.*



private const val MONTH_LENGTH: Float = 30.44F

fun convert(age: Int): String {
    val timeNowMillis: Long = System.currentTimeMillis()
    val ageToFloat: Float = age.toFloat()

    val floatResult = ageToFloat * MONTH_LENGTH * 24.0F * 60.0F * 60.0F * 1000.0F
    Log.i("FloatResult", "result = $floatResult")
    val ageInMillis = floatResult.toLong()

    val math = timeNowMillis - ageInMillis

    val simpleDate = SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(math)

    return simpleDate
}

enum class Filter {
    SHOW_ASC,
    SHOW_DESC
}