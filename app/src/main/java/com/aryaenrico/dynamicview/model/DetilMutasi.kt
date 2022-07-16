package com.aryaenrico.dynamicview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetilMutasi(
    @field:SerializedName("nama_sampah")
    var sampah: String = "",
    @field:SerializedName("satuan")
    var satuan: String = "",
    @field:SerializedName("harga_nasabah")
    var harga_nasabah: Int = 0,
    @field:SerializedName("total")
    var total: Float = 0F,
    @field:SerializedName("id_sampah")
    var id_sampah: String = ""
) : Parcelable