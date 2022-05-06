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
        }

        binding.buttonTambah.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val password = binding.etPassword.text.toString().trim()
            val alamat = binding.etAlamat.text.toString()
            val username = binding.etUsername.text.toString().trim()

            when{
                username.isBlank()->{
                    binding.etNama.error ="kolom username wajib di isi"
                }
                nama.isBlank()->{
                    binding.etNama.error ="kolom nama wajib di isi"
                }

                alamat.isBlank()->{
                    binding.etNama.error ="kolom alamat wajib di isi"
                }
                password.isBlank()->{
                    binding.etNama.error ="kolom password wajib di isi"
                }

                else->{
                    model.inputKategori(nama, alamat, username, password)
                }

            }
        }
    }
    private fun showToast(message:String){
        Toast.makeText(this@TambahUser ,message, Toast.LENGTH_SHORT).show()
    }
}