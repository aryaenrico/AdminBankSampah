package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Nasabah
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception


class InputSampahRepository(private val apiService: ApiService) {

    suspend fun getDataSampah(param: String): ArrayList<Sampah> {
        var data = ArrayList<Sampah>()
        try {
            data = apiService.getSampah(param)
        } catch (e: Exception) {
            data = arrayListOf(Sampah("null", "null", 0, 0)) as ArrayList<Sampah>
        }
        return if (data.size > 0) {
            data
        } else {
            arrayListOf(Sampah("Tidak ada data", "Tidak ada data"))
        }

    }

    suspend fun searchNasabah(nama: String): ArrayList<Nasabah> {
        var data: ArrayList<Nasabah>
        try {
            data = apiService.searchNasbah(nama)
        } catch (e: Exception) {
            data = arrayListOf(Nasabah("Eror", "Eror", "Eror"))
        }
        if (data.size > 0) {
            return data
        } else {
            return arrayListOf(Nasabah("", "", ""))
        }

    }


    suspend fun setoran(setoran: Setoran): Message {
        var data = Message()
        try {
            data = apiService.setoran(setoran)
        } catch (e: Exception) {
            data.pesan = "kesalahan dalam memuat server"
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
                instance ?: InputSampahRepository(apiService)
            }.also { instance = it }
    }
}