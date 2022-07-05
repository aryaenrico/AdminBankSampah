package com.aryaenrico.dynamicview.adapter

import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.activity.DetailUbahTransaksi
import com.aryaenrico.dynamicview.databinding.ItemUbahTransaksiBinding
import com.aryaenrico.dynamicview.model.Mutasi
import com.aryaenrico.dynamicview.model.Nasabah
import com.aryaenrico.dynamicview.util.FormatAngka
import com.aryaenrico.dynamicview.util.Utils

class UbahTransaksiAdapter(private var context:Context): RecyclerView.Adapter<UbahTransaksiAdapter.Holder>() {
    private val daftarMutasi = ArrayList<Mutasi>()
    //private lateinit var item: itemClickCallback

    fun setData(param:ArrayList<Mutasi>){
        daftarMutasi.clear()
        daftarMutasi.addAll(param)
    }
    class Holder(var data: ItemUbahTransaksiBinding):RecyclerView.ViewHolder(data.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UbahTransaksiAdapter.Holder {
        val view = ItemUbahTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.data.etNamaAdmin.text =daftarMutasi[position].nama_admin
        holder.data.tvtanggal.text =Utils.getTanggalBulanAdapter(Utils.longToDate(Utils.stringToLong(daftarMutasi[position].tanggal)))
        holder.data.totalSetor.text =FormatAngka.token(FormatAngka.getCurrency(daftarMutasi[position].harga))
        holder.itemView.setOnClickListener {
            val intent =Intent(context,DetailUbahTransaksi::class.java)
            //intent.putExtra(DATA,daftarMutasi[position])
            Utils.mutasi =daftarMutasi[position]
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return daftarMutasi.size
    }
    companion object{
        const val DATA ="data"
    }



}