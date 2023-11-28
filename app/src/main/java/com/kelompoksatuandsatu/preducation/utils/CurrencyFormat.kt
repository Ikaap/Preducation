package com.kelompoksatuandsatu.preducation.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

fun Int.toCurrencyFormat(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance("IDR")
    return format.format(this)
}
