package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.DaftarAjuan
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class DaftarAjuanRepository(private val apiService: ApiService) {

    suspend fun getAjuan():ArrayList<DaftarAjuan>{
        var data = ArrayList<DaftarAjuan>()
        try{
            data = apiService.getAjuan()
        }catch (e:Exception){
            data.add(DaftarAjuan())
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: DaftarAjuanRepository? = null
        fun getInstance(
            apiService: ApiService
        ): DaftarAjuanRepository =
            instance ?: synchronized(this) {
                instance ?:DaftarAjuanRepository(apiService)
            }.also { instance = it }
    }
}