package com.aryaenrico.dynamicview.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.R
import com.aryaenrico.dynamicview.databinding.ActivityInputSampahBinding
import com.aryaenrico.dynamicview.model.DetilSetor
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.util.Utils
import com.aryaenrico.dynamicview.viewmodel.InputSampahViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactory


class InputSampah : AppCompatActivity() {

    private lateinit var binding: ActivityInputSampahBinding
    private lateinit var model: InputSampahViewModel
    private var mapSampah = HashMap<String, Sampah>()
    private var sampah = ArrayList<String>()


    private lateinit var dataSampah: ArrayList<Sampah>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityInputSampahBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        val factory:ViewModelFactory = ViewModelFactory.getInstance()
        model = ViewModelProvider(this@InputSampah,factory).get(InputSampahViewModel::class.java)

        model.getData()

        model.data.observe(this) {
            this.dataSampah = it

            if (dataSampah[0].id_sampah.equals("null")){
                showToast("Terdapat kesalahan")
                finish()
            }else{
                for (i in 0..dataSampah.size - 1) {
                    this.mapSampah.set(
                        dataSampah[i].nama_sampah,
                        Sampah(
                            id_sampah = dataSampah[i].id_sampah,
                            nama_sampah = dataSampah[i].nama_sampah,
                            harga_nasabah = dataSampah[i].harga_nasabah,
                            harga_pengepul = dataSampah[i].harga_pengepul
                        )
                    )
                    this.sampah.add(dataSampah[i].nama_sampah)

                }
            }

        }

        binding.buttonAdd.setOnClickListener {
            addNewView()
        }


        // This Submit Button is used to store all the
        // data entered by user in arraylist
        binding.buttonSubmitList.setOnClickListener {
            showData()
        }

        model.pesan.observe(this){
            if (it.pesan.isNotBlank()){
                showToast(it.pesan)
            }
            if (it.pesan.equals("sukses")){
                binding.parentLinearLayout.removeAllViews()

            }
        }

    }

    // This function is called after
    // user clicks on addButton
    private fun addNewView() {
        // this method inflates the single item layout
        // inside the parent linear layout

        // membuat objek view dari hasil inflate layout xml
        var view: View = layoutInflater.inflate(R.layout.row_add_language, null, false)
        var spin = view.findViewById<Spinner>(R.id.exp_spinner)
        var button = view.findViewById<Button>(R.id.bt_cancel)
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            sampah
        )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spin.adapter = arrayAdapter

        binding.parentLinearLayout.addView(view, binding.parentLinearLayout.childCount)
        button.setOnClickListener {
            remove(view)
        }
    }

    private fun remove(data:View){
        binding.parentLinearLayout.removeView(data)
    }


    // This function is called after user
    // clicks on Submit Button
    private fun saveData(): Setoran {

        // this counts the no of child layout
        // inside the parent Linear layout
        val count = binding.parentLinearLayout.childCount
        var v: View?

        // array list detilsetor
        val paramDetilSetor = ArrayList<DetilSetor>()
        // objek setoran
        val setoran = Setoran()

        // objek Stringbuilder
        val builder = StringBuilder()


        for (i in 0 until count) {
            val detilSetoran = DetilSetor()

            v = binding.parentLinearLayout.getChildAt(i)

            val bobot: EditText = v.findViewById(R.id.et_name)
            val sampah: Spinner = v.findViewById(R.id.exp_spinner)
            val masa: Spinner = v.findViewById(R.id.exp_spinner_masa)

            val paramSampah = sampah.selectedItem.toString()

            // create an object of Language class
            var _timbangan = bobot.text.toString()
            when {
                masa.selectedItemId.toString().equals("1") -> {
                    _timbangan = (_timbangan.toFloat() / 1000).toString()
                }
            }
            val paramNasabah = this.mapSampah.get(paramSampah)?.harga_nasabah ?: 0
            val paramPengepul = this.mapSampah.get(paramSampah)?.harga_nasabah ?: 0

            val timbangan: Float = _timbangan.toFloat()
            val hargaNasabah = (paramNasabah * timbangan).toInt()
            val hargaPengepul = (paramPengepul * timbangan).toInt()
            val idSampah = this.mapSampah.get(paramSampah)?.id_sampah ?: ""



            detilSetoran.id_sampah = idSampah
            detilSetoran.total = timbangan
            detilSetoran.harga_nasabah = hargaNasabah
            detilSetoran.harga_pengepul = hargaPengepul

            paramDetilSetor.add(detilSetoran)
        }
        builder.append(binding.etUsername.text.toString().trim())
            .append(Utils.getTanggalBulan())

        setoran.id_admin = "a001"
        setoran.detil = paramDetilSetor
        setoran.id_nasabah = binding.etUsername.text.toString().trim()
        setoran.id_setor = builder.toString()
        setoran.tgl_setor = Utils.getTanggalLengkap()

        return setoran
    }

    private fun checkEror():Boolean{

        if (binding.etUsername.text.toString().trim().isEmpty()){
            return false
        }
        val count = binding.parentLinearLayout.childCount
        var v: View?
        for (i in 0 until count) {
            v = binding.parentLinearLayout.getChildAt(i)
            val bobot: EditText = v.findViewById(R.id.et_name)
            if (bobot.text.toString().isBlank()){
                return false
            }

        }
        return true
    }

    // This function is called after user
    // clicks on Show List data button
    private fun showData() {
        if (checkEror()){
            val data = saveData()
            model.setor(data)
            val count = binding.parentLinearLayout.childCount
            for (i in 0 until count) {
                Toast.makeText(
                    this,
                    "id_sampah $i is  = ${data.detil[i].id_sampah} harga pengepul =  ${data.detil[i].harga_pengepul} harga nasabah = ${data.detil[i].harga_nasabah} Bobot = ${data.detil[i].total} ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else{
            showToast("Pastikan semua data telah terisi ")
        }
    }

    private fun showToast(message:String){
        Toast.makeText(this@InputSampah ,message,Toast.LENGTH_SHORT).show()
    }
}