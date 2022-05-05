package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class InputKategoriRepository (private val apiService: ApiService) {

    suspend fun inputKategori(kategori:String):Message{
        var data = Message()
        try{
            data =apiService.insertKategori(kategori)
        }catch (e: Exception){
            data.pesan ="kesalahan dalam memuat server"
        }
        return data
    }


    companion object {
        @Volatile
        private var instance: InputKategoriRepository? = null
        fun getInstance(
            apiService: ApiService
        ): InputKategoriRepository =
            instance ?: synchronized(this) {
                instance ?:InputKategoriRepository(apiService)
            }.also { instance = it }
    }
}