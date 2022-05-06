package com.aryaenrico.dynamicview.injection

import com.aryaenrico.dynamicview.repository.*
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
    fun provideRepositorAddUser():AddUserRepository {
        val apiService = ApiConfig.getApi()
        return AddUserRepository.getInstance(apiService)
    }


}