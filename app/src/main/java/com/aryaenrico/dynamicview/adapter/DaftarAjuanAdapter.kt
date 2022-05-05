package com.aryaenrico.dynamicview.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.activity.DetailPengajuanActivity
import com.aryaenrico.dynamicview.databinding.ItemVerifikasiBinding
import com.aryaenrico.dynamicview.model.DaftarAjuan


class DaftarAjuanAdapter (private val context: Context) : RecyclerView.Adapter<DaftarAjuanAdapter.DaftarAjuanHolder>() {

    private val daftarAjuan = ArrayList<DaftarAjuan>()

    fun setData(param:ArrayList<DaftarAjuan>){
        daftarAjuan.clear()
        daftarAjuan.addAll(param)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarAjuanHolder {
        val view = ItemVerifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaftarAjuanHolder(view)
    }

    override fun onBindViewHolder(holder: DaftarAjuanHolder, position: Int) {
        holder.data.namaUser.text =daftarAjuan[position].nama_nasabah
        holder.data.tanggalPengajuan.text =daftarAjuan[position].tanggal_pengajuan
        holder.data.statusPengajuan.text =daftarAjuan[position].status
        holder.data.jumlahPengaju.text =""+daftarAjuan[position].jumlah

        holder.itemView.setOnClickListener {
            val intent =Intent(context,DetailPengajuanActivity::class.java)
            intent.putExtra(PARAM,daftarAjuan[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return  daftarAjuan.size
    }
    class DaftarAjuanHolder(val data:ItemVerifikasiBinding):RecyclerView.ViewHolder(data.root)


    companion object{
        const val PARAM="DETAIL"
    }

}