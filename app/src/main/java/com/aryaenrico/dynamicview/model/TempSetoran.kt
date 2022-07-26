package com.aryaenrico.dynamicview.model

data class TempSetoran(
    var nama_nasabah:String="",
    var id_nasabah:String="",
    var data_setor:HashMap<String,TempData> = hashMapOf<String,TempData>()

)
data class TempData(
    var id_sampah:String="",
    var bobot:Float=0f,
    var satuan:Int=0
)
