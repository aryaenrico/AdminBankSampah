package com.aryaenrico.dynamicview.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
                        dataSampah[i].nama_sampah,
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
            val check  =(binding.bobot.text.toString()).toFloat()

            if (check != 0.0f){
                model.setLoading(true)
                val nasabah = mapSampah.get(binding.namaSampah.text.toString())?.harga_nasabah ?:0
                val pengepul = mapSampah.get(binding.namaSampah.text.toString())?.harga_pengepul ?:0
                val bobot =(binding.bobot.text.toString()).toFloat()
                val paramNasabah =(nasabah*bobot).toInt()
                val paramPengepul =(pengepul*bobot).toInt()

                model.update(data.id_setor,mapSampah.get(binding.namaSampah.text.toString())?.id_sampah ?:"",data.id_nasabah,binding.bobot.text.toString(),paramNasabah,paramPengepul,data.harga)
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