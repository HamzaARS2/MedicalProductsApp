package com.ars.data.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.convertToReadableDate(): String {
    val dateFormat = SimpleDateFormat("EEE, dd MMMM yyyy", Locale.getDefault())
    val date = Date(this)
    return dateFormat.format(date)
}