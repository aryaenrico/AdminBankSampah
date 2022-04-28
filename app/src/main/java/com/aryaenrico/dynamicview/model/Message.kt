package com.aryaenrico.dynamicview.model

import com.google.gson.annotations.SerializedName

data class Message(
    @field:SerializedName("message")
    var pesan:String = ""
)
