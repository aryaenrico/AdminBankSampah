package com.aryaenrico.dynamicview.retrofit
import com.aryaenrico.dynamicview.model.*
import retrofit2.http.*
interface ApiService {

   @GET("getSampahAll.php")
   suspend fun getSampah():ArrayList<Sampah>

   @POST("setoran.php")
   suspend  fun setoran(@Body setoran: Setoran):Message

   @FormUrlEncoded
   @POST("insert_kategori.php")
   suspend fun insertKategori (@Field("kategori") kategori:String):Message

   @GET("get_pengajuan.php")
   suspend fun getAjuan():ArrayList<DaftarAjuan>

   @GET("getMutasi_Transaksi.php")
   suspend fun getMutasiTransaksi(
      @Query("tanggal_awal") awal:String,
      @Query("tanggal_akhir") akhir:String
   ):ArrayList<MutasiTransaksiData>


}