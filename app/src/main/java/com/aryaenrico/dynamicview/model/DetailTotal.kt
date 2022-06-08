package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class DetailTotal(
    @field:SerializedName("harga")
    var harga: Int = 0
)