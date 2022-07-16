package com.aryaenrico.dynamicview.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.databinding.ActivityUbahSampahBinding
import com.aryaenrico.dynamicview.model.DetilMutasi
import com.aryaenrico.dynamicview.model.Mutasi
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.viewmodel.UbahTransaksiViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryUbahTransaksi

class ProsesUbahActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUbahSampahBinding
    private lateinit var data:Mutasi
    private lateinit var detil:DetilMutasi
    private lateinit var model: UbahTransaksiViewModel
    private var mapSampah = HashMap<String, Sampah>()
    private lateinit var dataSampah: ArrayList<Sampah>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUbahSampahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model = ViewModelProvider(this, ViewModelFactoryUbahTransaksi.getInstance()).get(UbahTransaksiViewModel::class.java)
        data = intent.getParcelableExtra<Mutasi>("Data") as Mutasi

        detil = intent.getParcelableExtra<DetilMutasi>("sampah") as DetilMutasi
        model.setLoading(true)
        model.loading.observe(this){
            showLoading(it)
        }

        model.getDataSampah(data.tanggal)
        model.dataSampah.observe(this){
            this.dataSampah = it

            if (dataSampah[0].id_sampah.equals("null")) {
               // showToast("Terdapat kesalahan pada server")
                finish()
            } else {
                for (i in dataSampah.indices) {
                    this.mapSampah.set(
                        dataSampah[i].id_sampah,
                        Sampah(
                            id_sampah = dataSampah[i].id_sampah,
                            nama_sampah = dataSampah[i].nama_sampah,
                            harga_nasabah = dataSampah[i].harga_nasabah,
                            harga_pengepul = dataSampah[i].harga_pengepul
                        )
                    )
                }
            }

        }
        binding.namaSampah.setText(detil.sampah)
        binding.bobot.setText(""+detil.total)
        val myString =detil.satuan


        var dataAdapter = listOf("KG","G")
        if (detil.satuan.contains("Liter")){
            dataAdapter = listOf("Liter")
        }
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            dataAdapter
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMasa.adapter = arrayAdapter

         //the value you want the position for


        val spinnerPosition =arrayAdapter.getPosition(myString)


       binding.spinnerMasa.setSelection(spinnerPosition)

        model.message.observe(this){
            showToast(it.pesan)
            if (it.pesan.contains("sukses")){
                val intent = Intent(this@ProsesUbahActivity, DetailUbahTransaksi::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }

        binding.btnubahTransaksi.setOnClickListener {
            var check  =(binding.bobot.text.toString()).toFloat()
            val param =binding.spinnerMasa.selectedItemId.toString()
            if (param.equals("1")){
                check/=1000
            }


            if (check != 0.0f){
                model.setLoading(true)
                val nasabah = mapSampah.get(detil.id_sampah)?.harga_nasabah ?:0
                val pengepul = mapSampah.get(detil.id_sampah)?.harga_pengepul ?:0
                val paramNasabah =(nasabah*check).toInt()
                val paramPengepul =(pengepul*check).toInt()
                model.update(this.data.id_setor,detil.id_sampah,this.data.id_nasabah,check.toString(),paramNasabah,paramPengepul,data.harga)
            }else{
                showToast("Bobot tidak boleh 0")
            }

        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this@ProsesUbahActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}