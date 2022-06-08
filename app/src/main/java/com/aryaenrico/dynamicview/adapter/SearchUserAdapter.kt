package com.aryaenrico.dynamicview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryaenrico.dynamicview.databinding.ItemNasabahBinding
import com.aryaenrico.dynamicview.model.Nasabah

class SearchUserAdapter:RecyclerView.Adapter<SearchUserAdapter.SearchHolder>() {

    private val daftarNasbah = ArrayList<Nasabah>()
   private lateinit var item: itemClickCallback

    fun setItemClickCallback(itemClickCallback: itemClickCallback){
        this.item =itemClickCallback
    }

    fun setData(param:ArrayList<Nasabah>){
        daftarNasbah.clear()
        daftarNasbah.addAll(param)
        notifyDataSetChanged()
    }
    class SearchHolder(var data:ItemNasabahBinding):RecyclerView.ViewHolder(data.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHolder {
        val view = ItemNasabahBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.data.tvnama.text   = daftarNasbah[position].nama
        holder.data.tvalamat.text = daftarNasbah[position].alamat
        holder.data.etHargaPenge.setOnClickListener {
            item.onitemClicked(daftarNasbah[position])
        }
    }

    override fun getItemCount(): Int {
     return daftarNasbah.size
    }

    interface itemClickCallback{
        fun onitemClicked(param:Nasabah)
    }
}