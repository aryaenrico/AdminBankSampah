package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.retrofit.ApiService

class ProsesUbahRepository(private var apiService: ApiService) {

//    suspend fun update(id_setor:String,id_sampah:String,total:String):Message{
//        var data:Message
//        try{
//            data =apiService.updateTransaction(id_setor,id_sampah,total)
//        }catch (e:Exception){
//            data = Message("Eror")
//        }
//        return data
//    }

    companion object {
        @Volatile
        private var instance: ProsesUbahRepository? = null
        fun getInstance(
            apiService: ApiService
        ): ProsesUbahRepository =
            instance ?: synchronized(this) {
                instance ?:ProsesUbahRepository(apiService)
            }.also { instance = it }
    }
}