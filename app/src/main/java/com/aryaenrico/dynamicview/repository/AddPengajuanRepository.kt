package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.SaldoNasabah
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception


class AddPengajuanRepository(private val apiService: ApiService){

    suspend fun addPengajuan(id:String,status:String,jumlah:String,id_nasabah:String,id_admin:String):Message{
        var data = Message()
        try{
            data =apiService.insertPengajuan(id,status,jumlah,id_nasabah,id_admin)
        }catch (e: Exception){
            data.pesan =e.message.toString()
        }
        return data
    }
    suspend fun saldoUser(id_nasabah: String):SaldoNasabah{
        var data =SaldoNasabah()
        try{
            data =apiService.getSaldoNasabah(id_nasabah)
        }catch (e:Exception){
            data.saldo =e.message.toString()
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