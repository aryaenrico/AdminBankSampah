package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.repository.UbahHargaSampahRepository
import kotlinx.coroutines.launch

class UbahSampahViewModel(private val ubahHargaSampahRepository: UbahHargaSampahRepository,private val profileadmin: profileAdmin):ViewModel() {
    private var _data = MutableLiveData<ArrayList<Sampah>>()
    val  data: LiveData<ArrayList<Sampah>> =_data

    private var _pesan = MutableLiveData<Message>()
    val pesan: LiveData<Message> =_pesan

    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> =_loading

    fun getData(param:String){
        viewModelScope.launch {
            _data.value = ubahHargaSampahRepository.getDataSampah(param)
            _loading.value =false
        }
    }
    fun updateHargaSampah(id:String,nasabah:Int,pengepul:Int,tanggal:String,admin:String,nasabahLama:Int,pengepulLama:Int,tangaalAkhir:String){
        viewModelScope.launch {
            _pesan.value =ubahHargaSampahRepository.addSampah(id, nasabah, pengepul, tanggal, admin, nasabahLama, pengepulLama,tangaalAkhir)
            _loading.value =false
        }

    }
    fun setLoading(param:Boolean){
        _loading.value =param
    }

    fun getProfileAdmin():LiveData<Admin>{
        return profileadmin.getProfile().asLiveData()
    }
}
class ViewModelFactoryUbahHargaSampah(private val ubahHargaSampahRepository: UbahHargaSampahRepository,private val profileadmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(UbahSampahViewModel::class.java) -> {
                return UbahSampahViewModel(ubahHargaSampahRepository,profileadmin) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactoryUbahHargaSampah? = null
        fun getInstance(ubahHargaSampahRepository: UbahHargaSampahRepository = Injection.provideUbahHargaSampahRepository(),profileadmin: profileAdmin): ViewModelFactoryUbahHargaSampah = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryUbahHargaSampah(ubahHargaSampahRepository,profileadmin) }.also { instance = it }
    }
}