package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class AddPengajuanRepository(private val apiService: ApiService){

    suspend fun addPengajuan(id:String,status:String):Message{
        var data = Message()
        try{
            data =apiService.insertPengajuan(id,status)
        }catch (e: Exception){
            data.pesan =e.message.toString()
        }
        return data
    }


    companion object {
        @Volatile
        private var instance: AddPengajuanRepository? = null
        fun getInstance(
            apiService: ApiService
        ): AddPengajuanRepository =
            instance ?: synchronized(this) {
                instance ?:AddPengajuanRepository(apiService)
            }.also { instance = it }
    }
}