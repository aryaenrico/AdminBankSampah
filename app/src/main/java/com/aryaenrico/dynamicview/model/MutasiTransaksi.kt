package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class MutasiTransaksiData (
    @field:SerializedName("tgl_setor")
    var tanggal:String="",
    @field:SerializedName("nasabah")
    var hargaNasabah:Int=0,
    @field:SerializedName("pengepul")
    var hargaPengepul:Int=0
        )
