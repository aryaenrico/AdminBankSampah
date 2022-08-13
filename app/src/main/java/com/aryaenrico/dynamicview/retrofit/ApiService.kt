package com.aryaenrico.dynamicview.retrofit

import com.aryaenrico.dynamicview.model.*
import retrofit2.http.*

interface ApiService {

    @GET("getSampahAll.php")
    suspend fun getSampah( @Query("tanggal") awal: String,): ArrayList<Sampah>

    @GET("get_saldo2.php")
    suspend fun getSaldoNasabah( @Query("id_nasabah") id_nasabah: String,): SaldoNasabah

    @GET("get_total.php")
    suspend fun transaction(@Query("id_setoran")id:String):ArrayList<DetilMutasi>

    @GET("get_mutasi.php")
    suspend fun getMutasiNasabah(
        @Query("tanggal_awal") awal: String,
        @Query("tanggal_akhir") akhir: String,
        @Query("id_nasabah")  id_nasabah:String
    ): ArrayList<Mutasi>

    @GET("get_total_mutasi.php")
    suspend fun getTotalMutasiNasabah(
        @Query("id_setoran")  id_nasabah:String
    ):DetailTotal

    @POST("setoran.php")
    suspend fun setoran(@Body setoran: Setoran): Message

    @POST("setoran_tambahan.php")
    suspend fun setoranTambahan(@Body setoranTambahan: SetoranTambahan): Message

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
        @Field("no_telp") no_telp: String,
    ): Message

    @FormUrlEncoded
    @POST("update_transaction.php")
    suspend fun updateTransaction(
        @Field("id_setor") id_setor: String,
        @Field("id_sampah") id_sampah: String,
        @Field("id_nasabah") id_nasabah: String,
        @Field("total") total: String,
        @Field("harga_nasabah") hrg_nasabah: Int,
        @Field("harga_pengepul") hrg_pengepul: Int,
        @Field("total_setor") total_setor: Int
    ): Message


    @FormUrlEncoded
    @POST("hapus_setoran.php")
    suspend fun deleteItem(
        @Field("id_setor") id_setor: String,
        @Field("id_sampah") id_sampah: String,
        @Field("id_nasabah") id_nasabah: String,
        @Field("harga_nasabah") hrg_nasabah: Int

    ): Message

    @FormUrlEncoded
    @POST("insert_pengajuan.php")
    suspend fun insertPengajuan(
        @Field("id") id: String,
        @Field("status") status: String,
        @Field("jumlah") jumlah: String,
        @Field("id_nasabah") id_nasabah: String,
        @Field("id_admin") id_admin: String
    ): Message

    @FormUrlEncoded
    @POST("search_nasabah.php")
    suspend fun searchNasbah(
        @Field("nama") nama: String
    ):ArrayList<Nasabah>

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
        @Field("admin") admin: String,
        @Field("satuan") satuan: String,
    ): Message

    @FormUrlEncoded
    @POST("updateHarga_Sampah.php")
    suspend fun updatePriceSampah(
        @Field("id") id: String,
        @Field("nasabah") nasabah: Int,
        @Field("pengepul") pengepul: Int,
        @Field("tanggal_awal") tanggal_awal: String,
        @Field("admin") admin: String,
        @Field("nasabah_lama") nasabahLama: Int,
        @Field("pengepul_lama") pengepulLama: Int,
        @Field("tanggal_akhir") tanggal_akhir: String
    ): Message

    @FormUrlEncoded
    @POST("loginAdmin.php")
    suspend fun login(
        @Field("no_telepon") noTelepon: String,
        @Field("password") password: String
    ): Login


}