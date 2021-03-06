package com.aryaenrico.dynamicview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.adapter.DetilUbahTransaksiAdapter
import com.aryaenrico.dynamicview.adapter.UbahTransaksiAdapter
import com.aryaenrico.dynamicview.databinding.ActivityDetailUbahTransaksiBinding
import com.aryaenrico.dynamicview.model.DetilMutasi
import com.aryaenrico.dynamicview.model.Mutasi
import com.aryaenrico.dynamicview.util.FormatAngka
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.UbahTransaksiViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahTransaksi

class DetailUbahTransaksi : AppCompatActivity() {
    private lateinit var binding:ActivityDetailUbahTransaksiBinding
    private lateinit var model: UbahTransaksiViewModel
    private lateinit var data:Mutasi
    private var hash:HashMap<String,DetilMutasi> =HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUbahTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = Utils.mutasi
        model = ViewModelProvider(this, ViewModelFactoryUbahTransaksi.getInstance()).get(UbahTransaksiViewModel::class.java)
        model.setLoading(true)
        model.loading.observe(this){
            showLoading(it)
        }
        model.getDetailTotal(data.id_setor)
        model.total.observe(this){
            binding.total.text =FormatAngka.token(FormatAngka.getCurrency(it.harga))
        }

        binding.namaPengaju.text = data.nama_admin
        binding.tanggalTransaksi.text =Utils.getTanggalBulanDetailUbahTransaksi(Utils.longToDate(Utils.stringToLong(data.tanggal)))

        binding.btnTambahSampahBaru.setOnClickListener {
            val move =Intent(this,tambah_sampah_persetoran::class.java)
            move.putExtra(KEY,data.id_setor)
            move.putExtra(KEY2,data.id_nasabah)
            move.putExtra("map",hash)
            startActivity(move)
        }
        model.getTransaction(data.id_setor)
        model.detilMutasi.observe(this){
            if (it[0].harga_nasabah >= 0){
                showData(it)
                for (i in it.indices){
                    this.hash.put(it[i].id_sampah,it[i])
                }

            }
        }
    }

    private fun showData( param :ArrayList<DetilMutasi>){
        binding.rvDetilUbahTransaksi.layoutManager = LinearLayoutManager(this)
        val adapter = DetilUbahTransaksiAdapter()
        adapter.setData(param)
        binding.rvDetilUbahTransaksi.adapter = adapter
        adapter.setItemClickCallback(object :DetilUbahTransaksiAdapter.itemClickCallback{
            override fun onitemClicked(param: DetilMutasi) {
                var intent = Intent (this@DetailUbahTransaksi,ProsesUbahActivity::class.java)
                intent.putExtra("Data",data)
                intent.putExtra("sampah",param)
                startActivity(intent)
            }

        })
    }

    override fun onStart() {
        super.onStart()
        this.data = Utils.mutasi
        model.getDetailTotal(data.id_setor)
        model.total.observe(this){
            if (it.harga>0){
                binding.total.text =FormatAngka.token(FormatAngka.getCurrency(it.harga))
            }
        }
        model.getTransaction(data.id_setor)
        model.detilMutasi.observe(this){
            if (it[0].harga_nasabah >= 0){
                showData(it)
                for (i in it.indices){
                    this.hash.put(it[i].id_sampah,it[i])
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        this.data = Mutasi()
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object{
        val KEY ="param_id_setor"
        val KEY2 ="param_id_nasabah"
    }




}