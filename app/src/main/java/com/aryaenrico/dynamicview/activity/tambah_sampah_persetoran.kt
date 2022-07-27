package com.aryaenrico.dynamicview.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityTambahSampahPersetoranBinding
import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.SampahPersetoranViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactorySampahPersetoran

class tambah_sampah_persetoran : AppCompatActivity() {
    private var mapSampah = HashMap<String, Sampah>()
    private var sampah = ArrayList<String>()
    private lateinit var dataSampah: ArrayList<Sampah>
    private lateinit var binding: ActivityTambahSampahPersetoranBinding
    private lateinit var model:SampahPersetoranViewModel
    private lateinit var id_setor:String
    private lateinit var id_nasabah:String
    override fun onCreate(savedInstanceState: Bundle?) {

        binding =ActivityTambahSampahPersetoranBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent = intent
        id_setor =intent.getStringExtra(DetailUbahTransaksi.KEY)?:""
        id_nasabah =intent.getStringExtra(DetailUbahTransaksi.KEY2)?:""
        val hashMap = intent.getSerializableExtra("map") as HashMap<String, DetilMutasi>
        model = ViewModelProvider(this,ViewModelFactorySampahPersetoran.getInstance()).get(SampahPersetoranViewModel::class.java)
        model.getDataSampah(Utils.getTanggalLengkap())

        model.loading.observe(this){
            showLoading(it)
        }
        model.pesan.observe(this){
            showToast(it.pesan)
            finish()
        }

        model.data.observe(this) {
            this.dataSampah = it
            binding.parentLinearLayout.removeAllViews()
            if (dataSampah[0].id_sampah.equals("null")) {
                showToast("Terdapat kesalahan pada server")
                finish()
            } else if (dataSampah[0].id_sampah.equals("Tidak ada data")) {
                addNewView(dataSampah[0].nama_sampah, "0", "")
            } else {
                for (i in dataSampah.indices) {
                    this.mapSampah.set(
                        dataSampah[i].id_sampah,
                        Sampah(
                            id_sampah = dataSampah[i].id_sampah,
                            nama_sampah = dataSampah[i].nama_sampah,
                            harga_nasabah = dataSampah[i].harga_nasabah,
                            harga_pengepul = dataSampah[i].harga_pengepul,
                            satuan = dataSampah[i].satuan

                        )
                    )
                    this.sampah.add(dataSampah[i].nama_sampah)
                    var boolean =hashMap?.containsKey(dataSampah[i]?.id_sampah)

                    if(boolean ==  false){
                        addNewView(
                            dataSampah[i].nama_sampah,
                            dataSampah[i].id_sampah,
                            dataSampah[i].satuan
                        )
                    }else{
                        continue
                    }
                }
            }
        }
        binding.btnTambahSampah.setOnClickListener {
            if (checkEror()){
                showLoading(true)
                showToast("Proses sedang berlangsung")
                var data =process()
                model.addSetoranTambahan(data)

            }else{
                showToast("pastikan semua data sudah terisi")
            }
        }
    }

    private fun addNewView(param: String, sampahId: String, satuan: String) {
        // membuat objek view dari hasil inflate layout xml
        val view: View = layoutInflater.inflate(R.layout.item_sampah, null, false)
        val sampahEditText = view.findViewById<EditText>(R.id.inputNamaSampah)
        val idSampah = view.findViewById<TextView>(R.id.id_sampah)
        val spin = view.findViewById<Spinner>(R.id.spinner_masa)
        var data = listOf("KG", "G")
        if (satuan.contains("Liter")) {
            data = listOf("Liter")
        }
        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin.adapter = arrayAdapter
        idSampah.text = sampahId
        sampahEditText.setText(param)
        binding.parentLinearLayout.addView(view, binding.parentLinearLayout.childCount)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showLoading(isLoad: Boolean) {
        if (isLoad) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
    private fun checkEror(): Boolean {
        if (this.id_setor.isEmpty()){
            return false
        }
        val count = binding.parentLinearLayout.childCount
        var v: View?
        var flag = 0
        for (i in 0 until count) {
            v = binding.parentLinearLayout.getChildAt(i)
            val bobot: EditText = v.findViewById(R.id.inputbobotSampah)
            if (bobot.text.toString().equals("0")) {
                continue
            } else {
                flag++
            }
            if (flag > 0) {
                return true
            }
        }
        return false
    }

    private fun process(): SetoranTambahan {
        val count = binding.parentLinearLayout.childCount
        var v: View?
        val paramDetilSetor = ArrayList<DetilSetor>()
        // objek setoran
        val setoran = SetoranTambahan()
        setoran.id_setor =this.id_setor
        setoran.id_nasabah =this.id_nasabah

        for (i in 0 until count) {
            val detilSetoran = DetilSetor()
            v = binding.parentLinearLayout.getChildAt(i)

            val id_sampah: TextView = v.findViewById(R.id.id_sampah)
            val bobot: EditText = v.findViewById(R.id.inputbobotSampah)
            val masa: Spinner = v.findViewById(R.id.spinner_masa)

            val paramSampah = id_sampah.text.toString()
            val parambobot1 = bobot.text.toString()
            var parambobot: Float = parambobot1.toFloat()
            when {
                masa.selectedItemId.toString().equals("1") -> {
                    parambobot /= 1000
                }
            }
            if (!parambobot1.equals("0")) {
                val paramNasabah = this.mapSampah.get(paramSampah)?.harga_nasabah ?: 0
                val paramPengepul = this.mapSampah.get(paramSampah)?.harga_pengepul ?: 0

                //val timbangan: Float = _timbangan.toFloat()
                val hargaNasabah = (paramNasabah * parambobot).toInt()
                val hargaPengepul = (paramPengepul * parambobot).toInt()
                val idSampah = this.mapSampah.get(paramSampah)?.id_sampah ?: ""

                detilSetoran.id_sampah = idSampah
                detilSetoran.total = parambobot
                detilSetoran.harga_nasabah = hargaNasabah
                detilSetoran.harga_pengepul = hargaPengepul
                paramDetilSetor.add(detilSetoran)
            } else {
                continue
            }
        }
        setoran.detil = paramDetilSetor
        return setoran
    }




}