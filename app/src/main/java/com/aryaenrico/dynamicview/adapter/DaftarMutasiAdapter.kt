package com.aryaenrico.dynamicview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.databinding.ItemMutasiTransaksiBinding
import com.aryaenrico.dynamicview.model.MutasiTransaksiData

class DaftarMutasiAdapter (private val context: Context) : RecyclerView.Adapter<DaftarMutasiAdapter.Holder>() {

    private val daftarMutasi = ArrayList<MutasiTransaksiData>()

    fun setData(param:ArrayList<MutasiTransaksiData>){
        daftarMutasi.clear()
        daftarMutasi.addAll(param)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = ItemMutasiTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.data.etHargaNasabah.text =""+daftarMutasi[position].hargaNasabah
        holder.data.etHargaPengepul.text =""+daftarMutasi[position].hargaPengepul
        holder.data.tvtanggal.text =daftarMutasi[position].tanggal

    }

    override fun getItemCount(): Int {
        return  daftarMutasi.size
    }
    class Holder(val data: ItemMutasiTransaksiBinding): RecyclerView.ViewHolder(data.root)

}