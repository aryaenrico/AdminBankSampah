package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Login
import com.aryaenrico.dynamicview.retrofit.ApiService

class LoginRepository (private val apiService: ApiService){

    suspend fun login(notelp:String,password:String):Login{
        var data =Login()
        try {
           data =apiService.login(notelp,password)
        }catch (e:Exception){
            data.pesanEror =e.toString()
        }
        return data
    }
    companion object {
        @Volatile
        private var instance: LoginRepository? = null
        fun getInstance(
            apiService: ApiService
        ): LoginRepository =
            instance ?: synchronized(this) {
                instance ?:LoginRepository(apiService)
            }.also { instance = it }
    }
}