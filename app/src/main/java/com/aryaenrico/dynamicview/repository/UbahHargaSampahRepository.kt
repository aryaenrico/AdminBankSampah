package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class UbahHargaSampahRepository(private val apiService: ApiService) {

    suspend fun getDataSampah():ArrayList<Sampah>{
        var data = ArrayList<Sampah>()
        try{
            data =apiService.getSampah()
        }catch (e: Exception){
            data = arrayListOf(Sampah("null","null",0,0)) as ArrayList<Sampah>
        }
        return data
    }

    suspend fun addSampah(id:String,nasabah:Int,pengepul:Int,tanggal:String,admin:String,nasabahLama:Int,pengepulLama:Int): Message {
        var data = Message()
        try{
            data =apiService.updatePriceSampah(id,nasabah,pengepul,tanggal,admin,nasabahLama,pengepulLama)
        }catch (e: Exception){
            data.pesan ="Kesalahan"
        }
        return data
    }


    companion object {
        @Volatile
        private var instance: UbahHargaSampahRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UbahHargaSampahRepository =
            instance ?: synchronized(this) {
                instance ?:UbahHargaSampahRepository(apiService)
            }.also { instance = it }
    }
}