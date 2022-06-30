package com.aryaenrico.dynamicview.util

import java.text.NumberFormat
import java.util.*

object FormatAngka {
    val locale = Locale("IND", "ID")
    val formatter = NumberFormat.getCurrencyInstance(locale)
    fun getCurrency(data:Int):String{
        return formatter.format(data)
    }
    fun token(data: String): String {
        val tokenizer = StringTokenizer(data, ",")
        return tokenizer.nextToken()
    }
}