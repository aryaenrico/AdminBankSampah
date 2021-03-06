package com.aryaenrico.dynamicview.util

import com.aryaenrico.dynamicview.model.Mutasi
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
    fun idSetor(calendar: Calendar):String{
        val myFormat ="yyyyMMdd"
        val sdf =SimpleDateFormat(myFormat)
        return sdf.format(calendar.time)
    }
    fun getTanggalBulanShow(calendar: Calendar):String{
        val myFormat ="dd - MMMM - yyyy"
        val locale =Locale("id","ID")
        val sdf =SimpleDateFormat(myFormat,locale)
       return sdf.format(calendar.time)
    }
    fun getTanggalBulanSend(calendar: Calendar):String{
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        return sdf.format(calendar.time)
    }

    fun longToCalendar(param:Long):Calendar{
        val calendar =Calendar.getInstance()
        calendar.timeInMillis =param
        return calendar

    }

   fun stringToLong(tanggal:String):Long{
        var calendar =java.util.Calendar.getInstance()
        val date =Utils.dateFormat.parse(tanggal)
        calendar.time = date
        return calendar.timeInMillis
    }

    fun longToDate(param:Long):Date{
        var calendar = Calendar.getInstance()
        calendar.timeInMillis =param
        val date = calendar.time
        return  date

    }
    fun getTanggalBulanAdapter(date: Date):String{
        val formater = "MMMM - yyyy"
        val timeStamp: String = SimpleDateFormat(formater).format(date)
        return timeStamp
    }
    fun getTanggalBulanDetailUbahTransaksi(date: Date):String{
        val formater = "dd - MMMM - yyyy"
        val timeStamp: String = SimpleDateFormat(formater).format(date)
        return timeStamp
    }


    var id_nasabah:String=""
    lateinit var mutasi:Mutasi



}