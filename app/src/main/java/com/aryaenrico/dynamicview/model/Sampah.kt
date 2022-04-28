package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Sampah(
     @field:SerializedName("id_sampah")
     var id_sampah:String="",
     @field:SerializedName("nama_sampah")
     var nama_sampah:String="",
     @field:SerializedName("harga_nasabah")
     var harga_nasabah:Int=0,
     @field:SerializedName("harga_pengepul")
     var harga_pengepul:Int=0
 )
