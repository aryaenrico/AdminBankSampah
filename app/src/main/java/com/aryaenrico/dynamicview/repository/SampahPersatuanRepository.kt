package com.aryaenrico.dynamicview.repository

import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.model.SetoranTambahan
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class SampahPersatuanRepository(private val apiService: ApiService) {

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

    suspend fun setoranTambahan(setoranTambahan: SetoranTambahan): Message {
        var data = Message()
        try {
            data = apiService.setoranTambahan(setoranTambahan)
        } catch (e: Exception) {
            data.pesan = "kesalahan dalam memuat server"
        }
        return data
    }



    companion object {
        @Volatile
        private var instance: SampahPersatuanRepository? = null
        fun getInstance(
            apiService: ApiService
        ): SampahPersatuanRepository =
            instance ?: synchronized(this) {
                instance ?:SampahPersatuanRepository(apiService)
            }.also { instance = it }
    }
}