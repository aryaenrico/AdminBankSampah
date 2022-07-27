package com.aryaenrico.dynamicview.injection

import com.aryaenrico.dynamicview.repository.*
import com.aryaenrico.dynamicview.retrofit.ApiConfig

object Injection {

    fun provideLoginRepository():LoginRepository {
        val apiService = ApiConfig.getApi()
        return LoginRepository.getInstance(apiService)
    }
    fun provideSampahPersetoranRepository():SampahPersatuanRepository{
        val apiService = ApiConfig.getApi()
        return SampahPersatuanRepository.getInstance(apiService)
    }

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
    fun provideTambahSampahRepository():TambahSampahRepository{
        val apiService = ApiConfig.getApi()
        return TambahSampahRepository.getInstance(apiService)
    }
    fun provideAddPengajuanRepository():AddPengajuanRepository{
        val apiService = ApiConfig.getApi()
        return AddPengajuanRepository.getInstance(apiService)
    }
    fun provideUbahHargaSampahRepository():UbahHargaSampahRepository{
        val apiService = ApiConfig.getApi()
        return UbahHargaSampahRepository.getInstance(apiService)
    }

    fun provideUbahTransaksiRepository():UbahTransaksiRepository{
        val apiService = ApiConfig.getApi()
        return UbahTransaksiRepository.getInstance(apiService)
    }



}