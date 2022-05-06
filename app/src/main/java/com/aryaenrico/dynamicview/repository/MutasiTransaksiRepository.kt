package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.MutasiTransaksiData
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class MutasiTransaksiRepository(private val apiService: ApiService) {

    suspend fun getMutasiTransaksi(awal:String,akhir:String):ArrayList<MutasiTransaksiData>{

        var data = ArrayList<MutasiTransaksiData>()
        try{
            data =apiService.getMutasiTransaksi(awal,akhir)
        }catch (e: Exception){
            data = listOf<MutasiTransaksiData>(MutasiTransaksiData("null",0,0)) as ArrayList<MutasiTransaksiData>
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: MutasiTransaksiRepository? = null
        fun getInstance(
            apiService: ApiService
        ): MutasiTransaksiRepository =
            instance ?: synchronized(this) {
                instance ?:MutasiTransaksiRepository(apiService)
            }.also { instance = it }
    }
}