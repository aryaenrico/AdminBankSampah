package com.aryaenrico.dynamicview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aryaenrico.dynamicview.databinding.ActivityTambahUserBinding
import com.aryaenrico.dynamicview.viewmodel.AddUserViewModel
import com.aryaenrico.dynamicview.viewmodel.ViewModelFactoryAddUser

class TambahUser : AppCompatActivity() {
    private lateinit var binding:ActivityTambahUserBinding
    private lateinit var model:AddUserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val factory: ViewModelFactoryAddUser = ViewModelFactoryAddUser.getInstance()
        model = ViewModelProvider(this@TambahUser,factory).get(AddUserViewModel::class.java)

        model.pesan.observe(this){
            showToast(it.pesan)
            if (it.pesan.contains("Berhasil menambah user")){
                finish()
            }
        }

        binding.buttonTambah.setOnClickListener {
            val nama = binding.etname.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val notelp = binding.etNoTelp.text.toString().trim()
            val password ="12345"

            when{
                notelp.isBlank()->{
                    binding.etNoTelp.error ="kolom username wajib di isi"
                }
                nama.isBlank()->{
                    binding.etname.error ="kolom nama wajib di isi"
                }

                alamat.isBlank()->{
                    binding.etAlamat.error ="kolom alamat wajib di isi"
                }
                else->{
                    model.inputUser(nama, alamat, "12345",notelp)
                }

            }
        }
    }
    private fun showToast(message:String){
        Toast.makeText(this@TambahUser ,message, Toast.LENGTH_SHORT).show()
    }
}