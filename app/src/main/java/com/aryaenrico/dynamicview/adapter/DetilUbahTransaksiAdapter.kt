package com.aryaenrico.dynamicview.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.activity.DetailUbahTransaksi
import com.aryaenrico.dynamicview.databinding.ActivityDetailUbahTransaksiBinding
import com.aryaenrico.dynamicview.databinding.ItemDetailMutasiBinding
import com.aryaenrico.dynamicview.databinding.ItemUbahTransaksiBinding
import com.aryaenrico.dynamicview.model.DetilMutasi
import com.aryaenrico.dynamicview.model.Mutasi
import com.aryaenrico.dynamicview.model.Nasabah

class DetilUbahTransaksiAdapter: RecyclerView.Adapter<DetilUbahTransaksiAdapter.Holder>() {
    private val daftarMutasi = ArrayList<DetilMutasi>()

    private lateinit var item: itemClickCallback

    fun setItemClickCallback(itemClickCallback: itemClickCallback){
        this.item =itemClickCallback
    }

    fun setData(param:ArrayList<DetilMutasi>){
        daftarMutasi.clear()
        daftarMutasi.addAll(param)
    }
    class Holder(var data: ItemDetailMutasiBinding): RecyclerView.ViewHolder(data.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = ItemDetailMutasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.data.namaUser.text =daftarMutasi[position].sampah
        holder.data.jumlahPengaju.text =""+daftarMutasi[position].harga_nasabah
        holder.data.tanggalPengajuan.text =""+daftarMutasi[position].total

        holder.itemView.setOnClickListener {
            this.item.onitemClicked(daftarMutasi[position])
        }

        }


    override fun getItemCount(): Int {
        return daftarMutasi.size
    }
    companion object{
        const val DATA ="data"
    }

    interface itemClickCallback{
        fun onitemClicked(param: DetilMutasi)
    }
}
