package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.adapter.SearchUserAdapter
import com.aryaenrico.dynamicview.adapter.UbahTransaksiAdapter
import com.aryaenrico.dynamicview.databinding.ActivityUbahTransaksiBinding
import com.aryaenrico.dynamicview.model.Mutasi
import com.aryaenrico.dynamicview.model.Nasabah
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.UbahTransaksiViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahTransaksi
import java.util.*
import kotlin.collections.ArrayList

class UbahTransaksi : AppCompatActivity() {
    private lateinit var binding: ActivityUbahTransaksiBinding
    private lateinit var model:UbahTransaksiViewModel
    private var tanggalAkhir =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        model =ViewModelProvider(this,ViewModelFactoryUbahTransaksi.getInstance()).get(UbahTransaksiViewModel::class.java)

        model.loading.observe(this){
            showLoading(it)
        }

        binding.cariUsername.setOnClickListener {
            val nama = binding.etUsername.text.toString()
            if (nama.isNotBlank()){
                model.setLoading(true)
                model.getNasabah(nama)
                model.dataNasabah.observe(this){data ->
                    if (data[0].nama.isNotBlank()){
                        binding.rvUserEdit.visibility = View.VISIBLE
                        showNasabah(data)
                        binding.notFound1.visibility = View.GONE

                    }else{
                        binding.rvUserEdit.visibility   = View.GONE
                        binding.notFound1.visibility = View.VISIBLE
                        binding.notFound1.setText("Username tidak ditemukan")

                    }
                }
            }else{
                showToast("Harap Masukan Nama user terlebih dahulu")
            }
        }

        val calendar1 = Calendar.getInstance()
        binding.cariTanggal.text = Utils.getTanggalBulanShow(calendar1)
        tanggalAkhir =Utils.getTanggalBulanSend(calendar1)

        val datePicker2 = DatePickerDialog.OnDateSetListener{ _, year, month, dayofmonth ->
            calendar1.set(Calendar.YEAR,year)
            calendar1.set(Calendar.MONTH,month)
            calendar1.set(Calendar.DAY_OF_MONTH,dayofmonth)
            binding.cariTanggal.text = Utils.getTanggalBulanShow(calendar1)
            tanggalAkhir =Utils.getTanggalBulanSend(calendar1)
        }
        binding.cariTanggal.setOnClickListener {
            DatePickerDialog(this,datePicker2,calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.buttonCari.setOnClickListener {
            if (Utils.id_nasabah.isNotBlank()){
                model.setLoading(true)
                model.getMutasi(Utils.getTanggalBulan(),tanggalAkhir,Utils.id_nasabah)
                model.dataMutasi.observe(this){ data ->
                    if (data[0].harga>0){
                        showMutasi(data)
                        binding.notFound.visibility =View.GONE
                    }else{
                        binding.recyclerView.visibility =View.GONE
                        binding.notFound.visibility =View.VISIBLE
                        binding.notFound.setText("Tidak Ada Riwayat Transaksi")
                    }
                }
            }else{
                showToast("Harap Pilih nama user terlebih dahulu")
            }
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this@UbahTransaksi, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNasabah(param: ArrayList<Nasabah>) {
        binding.rvUserEdit.layoutManager = LinearLayoutManager(this)
        val adapter = SearchUserAdapter()
        adapter.setData(param)
        binding.rvUserEdit.adapter = adapter
        adapter.setItemClickCallback(object : SearchUserAdapter.itemClickCallback {
            override fun onitemClicked(param: Nasabah) {
                binding.etUsername.setText(param.nama)
                binding.rvUserEdit.visibility = View.GONE
                Utils.id_nasabah = param.id_nasabah
            }
        })

    }

    private fun showMutasi(param:ArrayList<Mutasi>){
        binding.recyclerView.visibility =View.VISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = UbahTransaksiAdapter(this@UbahTransaksi)
        adapter.setData(param)
        binding.recyclerView.adapter = adapter

    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        Utils.id_nasabah =""
        showMutasi(arrayListOf(Mutasi()))
        binding.recyclerView.visibility =View.GONE
        binding.etUsername.setText("")
    }

    override fun onRestart() {
        super.onRestart()
        Utils.mutasi = Mutasi()
    }

    override fun onDestroy() {
        super.onDestroy()
        Utils.id_nasabah = ""
    }
}