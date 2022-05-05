package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class DaftarAjuan(
    @field:SerializedName("jumlah")
    var jumlah:Int =0,
    @field:SerializedName("tanggal_pengajuan")
    var tanggal_pengajuan:String ="",
    @field:SerializedName("status")
    var status:String ="",
    @field:SerializedName("nama")
    var nama_nasabah:String ="",
    @field:SerializedName("id_nasabah")
    var id_nasabah:String ="",
)
