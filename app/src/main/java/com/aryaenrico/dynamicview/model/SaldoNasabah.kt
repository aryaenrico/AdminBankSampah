package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class SaldoNasabah(
    @field:SerializedName("saldo")
    var saldo: String = ""
)