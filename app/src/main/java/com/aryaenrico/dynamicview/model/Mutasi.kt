package com.aryaenrico.dynamicview.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mutasi(
    @field:SerializedName("harga")
    var harga: Int = 0,
    @field:SerializedName("tgl_setor")
    var tanggal: String = "",
    @field:SerializedName("id_setor")
    var id_setor: String = "",
    @field:SerializedName("nama_nasabah")
    var nama_nasabah: String = "",
    @field:SerializedName("nama_admin")
    var nama_admin: String = "",
    @field:SerializedName("id_nasabah")
    var id_nasabah: String = "",
):Parcelable