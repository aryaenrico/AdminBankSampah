package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Kategori
import com.aryaenrico.dynamicview.model.Message

import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class TambahSampahRepository(private val apiService: ApiService) {

    suspend fun getKategori():ArrayList<Kategori>{
        var data = ArrayList<Kategori>()
        try{
            data =apiService.getKategoriSampah()
        }catch (e: Exception){
            data = listOf<Kategori>(Kategori()) as ArrayList<Kategori>
        }
        return data
    }

    suspend fun addSampah(id:String,nama:String,nasabah:Int,pengepul:Int,kategori:Int,tanggal:String,admin:String):Message{
        var data = Message()
        try{
            data =apiService.addSampah(id,nama,nasabah,pengepul,kategori,tanggal,admin)
        }catch (e: Exception){
            data.pesan ="Kesalahan"
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: TambahSampahRepository? = null
        fun getInstance(
            apiService: ApiService
        ): TambahSampahRepository =
            instance ?: synchronized(this) {
                instance ?:TambahSampahRepository(apiService)
            }.also { instance = it }
    }
}