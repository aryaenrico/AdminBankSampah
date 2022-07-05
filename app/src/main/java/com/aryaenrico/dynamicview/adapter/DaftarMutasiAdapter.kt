package com.aryaenrico.dynamicview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.databinding.ItemMutasiTransaksiBinding
import com.aryaenrico.dynamicview.model.MutasiTransaksiData
import com.aryaenrico.dynamicview.util.FormatAngka
import com.aryaenrico.dynamicview.util.Utils

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
        holder.data.etHargaNasabah.text =FormatAngka.token(FormatAngka.getCurrency(daftarMutasi[position].hargaNasabah))
        holder.data.etHargaPengepul.text =FormatAngka.token(FormatAngka.getCurrency(daftarMutasi[position].hargaPengepul))
        holder.data.tvtanggal.text =Utils.getTanggalBulanAdapter(Utils.longToDate(Utils.stringToLong(daftarMutasi[position].tanggal)))

    }

    override fun getItemCount(): Int {
        return  daftarMutasi.size
    }
    class Holder(val data: ItemMutasiTransaksiBinding): RecyclerView.ViewHolder(data.root)

}