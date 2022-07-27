package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Setoran(
    @field:SerializedName("id_setor")
    var id_setor:String = "",
    @field:SerializedName("tgl_setor")
    var tgl_setor:String="",
    @field:SerializedName("id_nasabah")
    var id_nasabah:String="",
    @field:SerializedName("id_admin")
    var id_admin:String="",
    @field:SerializedName("detil")
    var detil:ArrayList<DetilSetor> = ArrayList<DetilSetor>()
)

data class SetoranTambahan(
    @field:SerializedName("id_nasabah")
    var id_nasabah:String="",
    @field:SerializedName("id_setor")
    var id_setor:String = "",
    @field:SerializedName("detil")
    var detil:ArrayList<DetilSetor> = ArrayList<DetilSetor>()
)

data class DetilSetor(
    @field:SerializedName("id_sampah")
    var id_sampah:String="",
    @field:SerializedName("total")
    var total:Float =0F,
    @field:SerializedName("harga_nasabah")
    var harga_nasabah:Int =0,
    @field:SerializedName("harga_pengepul")
    var harga_pengepul:Int=0
)
