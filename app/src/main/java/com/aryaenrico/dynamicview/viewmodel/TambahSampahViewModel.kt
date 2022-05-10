package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Kategori
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.repository.MutasiTransaksiRepository
import com.aryaenrico.dynamicview.repository.TambahSampahRepository
import kotlinx.coroutines.launch

class TambahSampahViewModel(private val tambahSampahRepository: TambahSampahRepository):ViewModel() {
    private var _data = MutableLiveData<ArrayList<Kategori>>()
    val data : LiveData<ArrayList<Kategori>> =_data

    private var _pesan = MutableLiveData<Message>()
    val pesan : LiveData<Message> =_pesan

    fun getKategori(){
        viewModelScope.launch {
            _data.value = tambahSampahRepository.getKategori()
        }
    }

    fun tambahSampah(id:String,nama:String,nasabah:Int,pengepul:Int,kategori:Int,tanggal:String,admin:String){
        viewModelScope.launch {
            _pesan.value = tambahSampahRepository.addSampah(id, nama, nasabah, pengepul, kategori, tanggal, admin)
        }
    }


}
class ViewModelFactoryTambahSampah(private val tambahSampahRepository: TambahSampahRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(TambahSampahViewModel::class.java) -> {
                return TambahSampahViewModel(tambahSampahRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryTambahSampah? = null
        fun getInstance(tambahSampahRepository: TambahSampahRepository = Injection.provideTambahSampahRepository()): ViewModelFactoryTambahSampah = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryTambahSampah(tambahSampahRepository) }.also { instance = it }
    }
}