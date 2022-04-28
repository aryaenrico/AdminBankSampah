package com.aryaenrico.dynamicview.util

import java.text.SimpleDateFormat


object Utils {

    fun getTanggalLengkap():String{
       val formater = "yyyy-MM-dd"
        val timeStamp: String = SimpleDateFormat(formater).format(System.currentTimeMillis())
        return timeStamp
    }
    fun getTanggalBulan():String{
        val formater = "yyyyMM"
        val timeStamp: String = SimpleDateFormat(formater).format(System.currentTimeMillis())
        return timeStamp
    }


}