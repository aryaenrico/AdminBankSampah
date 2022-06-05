package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Nasabah(
    @field:SerializedName("nama")
    var nama: String ="",
    @field:SerializedName("id_nasabah")
    var id_nasabah: String ="",
    @field:SerializedName("alamat")
    var alamat: String =""
)