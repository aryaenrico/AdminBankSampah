package com.aryaenrico.dynamicview.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryaenrico.dynamicview.adapter.DaftarMutasiAdapter
import com.aryaenrico.dynamicview.databinding.ActivityMutasiTransaksiBinding
import com.aryaenrico.dynamicview.model.MutasiTransaksiData
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.MutasiTransaksiViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryMutasiTransaksi
import java.text.SimpleDateFormat
import java.util.*

class MutasiTransaksi : AppCompatActivity() {
    private lateinit var binding : ActivityMutasiTransaksiBinding
    private lateinit var model:MutasiTransaksiViewModel
    private var tanggalAwal =""
    private var tanggalAkhir =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMutasiTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val factory:ViewModelFactoryMutasiTransaksi = ViewModelFactoryMutasiTransaksi.getInstance()
        model =ViewModelProvider(this@MutasiTransaksi,factory).get(MutasiTransaksiViewModel::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH,0)
        calendar.get(Calendar.YEAR)
        calendar.set(Calendar.DAY_OF_MONTH,1)
        binding.tanggalawal.text = Utils.getTanggalBulanShow(calendar)
        tanggalAwal =Utils.getTanggalBulanSend(calendar)

        val calendar1 = Calendar.getInstance()
        binding.tanggalakhir.text = Utils.getTanggalBulanShow(calendar1)
        tanggalAkhir =Utils.getTanggalBulanSend(calendar1)

        val datePicker = DatePickerDialog.OnDateSetListener{_,year,month,dayofmonth ->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month)
            calendar.set(Calendar.DAY_OF_MONTH,dayofmonth)
            binding.tanggalawal.text = Utils.getTanggalBulanShow(calendar)
            tanggalAwal =Utils.getTanggalBulanSend(calendar)
        }

        val datePicker2 = DatePickerDialog.OnDateSetListener{_,year,month,dayofmonth ->
            calendar1.set(Calendar.YEAR,year)
            calendar1.set(Calendar.MONTH,month)
            calendar1.set(Calendar.DAY_OF_MONTH,dayofmonth)
            binding.tanggalakhir.text = Utils.getTanggalBulanShow(calendar1)
            tanggalAkhir =Utils.getTanggalBulanSend(calendar1)
        }

        binding.tanggalawal.setOnClickListener {
            DatePickerDialog(this,datePicker,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.tanggalakhir.setOnClickListener {
            DatePickerDialog(this,datePicker2,calendar1.get(Calendar.YEAR),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.cariRiwayat.setOnClickListener {
                val tanggalAwalInt = dateTomilis(tanggalAwal)
                val tanggalAkhirInt = dateTomilis(tanggalAkhir)
                if (tanggalAwalInt > tanggalAkhirInt){
                    showToast("Format Tanggal salah")
                }else{
                    model.getMutasiTransaksi(tanggalAwal,tanggalAkhir)
                }
        }

        model.data.observe(this){data->
            if (data.isEmpty()){
                showToast("data tidak ada")
            }
            showData(data)
        }
    }

    private fun dateTomilis(message: String):Long{
        val myFormat ="yyyy-MM-dd"
        val sdf =SimpleDateFormat(myFormat)
        val date: Date = sdf.parse(message)

        val calendar = Calendar.getInstance()

        calendar.time = date

        return calendar.timeInMillis
    }
    private fun showToast(message:String){
        Toast.makeText(this@MutasiTransaksi,message,Toast.LENGTH_SHORT).show()
    }

    private fun showData(value: ArrayList<MutasiTransaksiData>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = DaftarMutasiAdapter(this@MutasiTransaksi)
        adapter.setData(value)
        binding.recyclerView.adapter = adapter

    }
}