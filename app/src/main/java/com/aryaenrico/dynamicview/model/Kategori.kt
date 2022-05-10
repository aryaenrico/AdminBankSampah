package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Kategori(
    @field: SerializedName("id_kategori")
    var id_kategori: Int = 0,
    @field: SerializedName("deskripsi")
    var deskripsi: String = ""
)
