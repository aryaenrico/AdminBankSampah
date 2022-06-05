package com.aryaenrico.dynamicview.util

import com.aryaenrico.dynamicview.model.Nasabah
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


object Utils {
    val dateFormat =SimpleDateFormat("yyyy-MM-dd")
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
    fun getTanggalBulanShow(calendar: Calendar):String{
        val myFormat ="dd-MMMM-yyyy"
        val locale =Locale("id","ID")
        val sdf =SimpleDateFormat(myFormat,locale)
       return sdf.format(calendar.time)
    }
    fun getTanggalBulanSend(calendar: Calendar):String{
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        return sdf.format(calendar.time)
    }
    var id_nasabah:String=""
   lateinit var searchNasabah:ArrayList<Nasabah>


}