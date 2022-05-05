package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class InputSampahRepository(private val apiService: ApiService) {
    suspend fun getDataSampah():ArrayList<Sampah>{
        var data = ArrayList<Sampah>()
        try{
            data =apiService.getSampah()
        }catch (e:Exception){
           data = listOf<Sampah>(Sampah("null","null",0,0)) as ArrayList<Sampah>
        }
        return data
    }

    suspend fun setoran (setoran: Setoran):Message{
        var data = Message()
        try{
            data =apiService.setoran(setoran)
        }catch (e:Exception){
            data.pesan ="kesalahan dalam memuat server"
        }
        return data
    }


    companion object {
        @Volatile
        private var instance: InputSampahRepository? = null
        fun getInstance(
            apiService: ApiService
        ): InputSampahRepository =
            instance ?: synchronized(this) {
                instance ?:InputSampahRepository(apiService)
            }.also { instance = it }
    }
}