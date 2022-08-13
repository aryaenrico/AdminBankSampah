package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.repository.UbahTransaksiRepository
import kotlinx.coroutines.launch

class UbahTransaksiViewModel(val ubahTransaksiRepository: UbahTransaksiRepository) : ViewModel() {


    private var _dataMutasi =MutableLiveData<ArrayList<Mutasi>>()
    var dataMutasi:LiveData<ArrayList<Mutasi>> =_dataMutasi

    private var _dataNasabah =MutableLiveData<ArrayList<Nasabah>>()
    var dataNasabah:LiveData<ArrayList<Nasabah>> =_dataNasabah

    private var _message =MutableLiveData<Message>()
    var message:LiveData<Message> =_message

    private var _loading =MutableLiveData<Boolean>()
    var loading :LiveData<Boolean> =_loading



    private var _detilMutasi =MutableLiveData<ArrayList<DetilMutasi>>()
    var detilMutasi:LiveData<ArrayList<DetilMutasi>> =_detilMutasi

    private var _dataSampah = MutableLiveData<ArrayList<Sampah>>()
    val  dataSampah:LiveData<ArrayList<Sampah>> =_dataSampah

    private var _total =MutableLiveData<DetailTotal>()
    var total:LiveData<DetailTotal> =_total

    fun getNasabah(nama: String) {
        viewModelScope.launch {
            _dataNasabah.value = ubahTransaksiRepository.searchNasabah(nama)
            _loading.value =false
        }
    }
    fun getMutasi(awal:String,akhir:String,id:String){
        viewModelScope.launch {
            _dataMutasi.value = ubahTransaksiRepository.getMutasi(awal,akhir,id)
            _loading.value =false
        }
    }
    fun getTransaction(id:String){
        viewModelScope.launch {
            _detilMutasi.value =ubahTransaksiRepository.getTransaction(id)
            _loading.value =false
        }
    }

    fun update(id_setor:String,id_sampah:String,id_nasabah:String,total:String,nasabah:Int,pengepul:Int,total_setor:Int){
        viewModelScope.launch {
            _message.value =ubahTransaksiRepository.update(id_setor, id_sampah, id_nasabah, total,nasabah,pengepul,total_setor
            )
            _loading.value =false
        }
    }

    fun delete(id_setor: String,id_sampah: String,id_nasabah: String,total: Int){
        viewModelScope.launch {
            _message.value =ubahTransaksiRepository.DeleteItem(id_setor, id_sampah,id_nasabah,total)
        }
        _loading.value =false
    }



    fun getDataSampah(param:String){
        viewModelScope.launch {
            _dataSampah.value =ubahTransaksiRepository.getDataSampah(param)
            _loading.value =false

        }
    }

    fun getDetailTotal(id_setor:String){
        viewModelScope.launch {
            _total.value =ubahTransaksiRepository.getDetailTotal(id_setor)
        }
    }

    fun setLoading(load:Boolean){
        _loading.value =load
    }


}

class ViewModelFactoryUbahTransaksi(private val ubahTransaksiRepository: UbahTransaksiRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UbahTransaksiViewModel::class.java) -> {
                return UbahTransaksiViewModel(ubahTransaksiRepository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryUbahTransaksi? = null
        fun getInstance(ubahTransaksiRepository: UbahTransaksiRepository = Injection.provideUbahTransaksiRepository()): ViewModelFactoryUbahTransaksi =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactoryUbahTransaksi(ubahTransaksiRepository)
            }.also { instance = it }
    }
}