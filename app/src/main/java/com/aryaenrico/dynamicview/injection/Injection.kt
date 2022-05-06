package com.aryaenrico.dynamicview.injection

import com.aryaenrico.dynamicview.repository.DaftarAjuanRepository
import com.aryaenrico.dynamicview.repository.InputKategoriRepository
import com.aryaenrico.dynamicview.repository.InputSampahRepository
import com.aryaenrico.dynamicview.repository.MutasiTransaksiRepository
import com.aryaenrico.dynamicview.retrofit.ApiConfig

object Injection {
    fun provideRepository():InputSampahRepository {
        val apiService = ApiConfig.getApi()
        return InputSampahRepository.getInstance(apiService)
    }
    fun provideRepositoryInputKategori():InputKategoriRepository {
        val apiService = ApiConfig.getApi()
        return InputKategoriRepository.getInstance(apiService)
    }
    fun provideRepositoryDaftarAjuan():DaftarAjuanRepository {
        val apiService = ApiConfig.getApi()
        return DaftarAjuanRepository.getInstance(apiService)
    }
    fun provideRepositoryMutasiTransaksi():MutasiTransaksiRepository {
        val apiService = ApiConfig.getApi()
        return MutasiTransaksiRepository.getInstance(apiService)
    }


}