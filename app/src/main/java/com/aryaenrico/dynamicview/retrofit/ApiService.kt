package com.aryaenrico.dynamicview.retrofit



import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

   @GET("getSampahAll.php")
   suspend fun getSampah():ArrayList<Sampah>

   @POST("setoran.php")
   suspend  fun setoran(@Body setoran: Setoran):Message
}