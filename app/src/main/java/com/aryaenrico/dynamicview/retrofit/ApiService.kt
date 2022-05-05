package com.aryaenrico.dynamicview.retrofit



import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import retrofit2.http.*


interface ApiService {

   @GET("getSampahAll.php")
   suspend fun getSampah():ArrayList<Sampah>

   @POST("setoran.php")
   suspend  fun setoran(@Body setoran: Setoran):Message

   @FormUrlEncoded
   @POST("insert_kategori.php")
   suspend fun insertKategori (@Field("kategori") kategori:String):Message


}