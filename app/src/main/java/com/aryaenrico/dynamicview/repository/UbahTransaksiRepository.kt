package com.aryaenrico.dynamicview.repository


import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class UbahTransaksiRepository (private val apiService: ApiService)
{

    suspend fun searchNasabah(nama:String):ArrayList<Nasabah>{
        var data:ArrayList<Nasabah>
        try{
            data =apiService.searchNasbah(nama)
        }catch (e: Exception){
            data = arrayListOf(Nasabah("Eror","Eror","Eror"))
        }
        if (data.size>0){
            return data
        }else{
            return arrayListOf(Nasabah())
        }
    }

    suspend fun getMutasi(awal:String,akhir:String,id:String):ArrayList<Mutasi>{
        var data:ArrayList<Mutasi>
        try{
            data =apiService.getMutasiNasabah(awal,akhir,id)
        }catch (e:Exception){
            data = arrayListOf(Mutasi(id_setor = "eror"))
            throw Exception(e)
        }
        if (data.size>0){
            return data
        }else{
            return arrayListOf(Mutasi())
        }
    }

    suspend fun getTransaction(id:String):ArrayList<DetilMutasi>{
        var data:ArrayList<DetilMutasi>
        try{
            data =apiService.transaction(id)
        }catch (e:Exception){
            data = arrayListOf(DetilMutasi())
            throw Exception(e)
        }
        if (data.size>0){
            return data
        }else{
            return arrayListOf(DetilMutasi())
        }
    }
    suspend fun update(id_setor:String,id_sampah:String,id_nasabah:String,total:String,nasabah:Int,pengepul:Int,total_setor:Int): Message {
        var data: Message
        try{
            data =apiService.updateTransaction(id_setor,id_sampah,id_nasabah,total,nasabah,pengepul,total_setor)
        }catch (e:Exception){
            data = Message("Eror")
        }
        return data
    }

    suspend fun getDataSampah():ArrayList<Sampah>{
        var data = ArrayList<Sampah>()
        try{
            data =apiService.getSampah()
        }catch (e:Exception){
            data = arrayListOf(Sampah("null","null",0,0)) as ArrayList<Sampah>
        }
        return data
    }


    suspend fun getDetailTotal(id_setor:String):DetailTotal{
        var data:DetailTotal
        try{
            data = apiService.getTotalMutasiNasabah(id_setor)
        }catch (e:Exception){
            data = DetailTotal()
        }
        return data
    }

    companion object {
        @Volatile
        private var instance: UbahTransaksiRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UbahTransaksiRepository =
            instance ?: synchronized(this) {
                instance ?:UbahTransaksiRepository(apiService)
            }.also { instance = it }
    }
}