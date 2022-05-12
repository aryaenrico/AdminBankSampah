package com.aryaenrico.dynamicview.retrofit

import com.aryaenrico.dynamicview.model.*
import retrofit2.http.*

interface ApiService {

    @GET("getSampahAll.php")
    suspend fun getSampah(): ArrayList<Sampah>

    @POST("setoran.php")
    suspend fun setoran(@Body setoran: Setoran): Message

    @FormUrlEncoded
    @POST("insert_kategori.php")
    suspend fun insertKategori(@Field("kategori") kategori: String): Message

    @GET("get_pengajuan.php")
    suspend fun getAjuan(): ArrayList<DaftarAjuan>

    @GET("getMutasi_Transaksi.php")
    suspend fun getMutasiTransaksi(
        @Query("tanggal_awal") awal: String,
        @Query("tanggal_akhir") akhir: String
    ): ArrayList<MutasiTransaksiData>

    @FormUrlEncoded
    @POST("insert_nasabah.php")
    suspend fun addUser(
        @Field("nama") nama: String,
        @Field("alamat") alamat: String,
        @Field("password") password: String,
        @Field("id") username: String
    ): Message

    @FormUrlEncoded
    @POST("insert_pengajuan.php")
    suspend fun insertPengajuan(
        @Field("id") id: String,
        @Field("status") status: String
    ): Message

    @GET("getKategoriAll.php")
    suspend fun getKategoriSampah(
    ): ArrayList<Kategori>

    @GET("getCount_Sampah.php")
    suspend fun getCountSampah(
    ): Message

    @FormUrlEncoded
    @POST("insert_sampah.php")
    suspend fun addSampah(
        @Field("id") id: String,
        @Field("nama") nama: String,
        @Field("nasabah") nasabah: Int,
        @Field("pengepul") pengepul: Int,
        @Field("kategori") kategori: Int,
        @Field("tanggal") tanggal: String,
        @Field("admin") admin: String
    ): Message

    @FormUrlEncoded
    @POST("updateHarga_Sampah.php")
    suspend fun updatePriceSampah(
        @Field("id") id: String,
        @Field("nasabah") nasabah: Int,
        @Field("pengepul") pengepul: Int,
        @Field("tanggal") tanggal: String,
        @Field("admin") admin: String,
        @Field("nasabah_lama") nasabahLama: Int,
    @Field("pengepul_lama") pengepulLama: Int
    ): Message


}