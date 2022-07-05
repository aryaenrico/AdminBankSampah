package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Login(
    @field:SerializedName("id_admin")
    var id_admin: String = "",
    @field:SerializedName("alamat")
     var alamat: String = "",
    @field:SerializedName("nama")
     var nama: String = "",
    @field:SerializedName("no_telepon")
    var no_telepon: String = "",
    @field:SerializedName("message")
    var pesanEror: String = "berhasil"
)