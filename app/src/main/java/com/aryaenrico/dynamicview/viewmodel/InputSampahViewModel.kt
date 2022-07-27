package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.*
import com.aryaenrico.dynamicview.repository.InputSampahRepository
import kotlinx.coroutines.launch

class InputSampahViewModel(private val sampahRepository: InputSampahRepository,private val profileadmin: profileAdmin):ViewModel() {

    private var _data = MutableLiveData<ArrayList<Sampah>>()
    val  data:LiveData<ArrayList<Sampah>> =_data



    private var _tglSetor = MutableLiveData<Long>()
    val  tglSetor:LiveData<Long> =_tglSetor

    private var _dataTempSetoran = MutableLiveData<TempSetoran>()
    val  dataTempSetoran:LiveData<TempSetoran> =_dataTempSetoran




    init {
     getTglSetor()
    }

    private var _pesan = MutableLiveData<Message>()
    val pesan :LiveData<Message> =_pesan

    private var _loading = MutableLiveData<Boolean>()
    val loading :LiveData<Boolean> =_loading


    private var _nasabah = MutableLiveData<ArrayList<Nasabah>>()
    val  nasabah:LiveData<ArrayList<Nasabah>> =_nasabah


    fun setTempSetoran(data:TempSetoran){
        _dataTempSetoran.value =data
    }


    fun getProfileAdmin():LiveData<Admin>{
        return profileadmin.getProfile().asLiveData()
    }

    fun getData(param:String){
        viewModelScope.launch {
            _data.value =sampahRepository.getDataSampah(param)
            _loading.value =false

        }
    }
    fun getTglSetor(){
        _tglSetor.value =System.currentTimeMillis()
    }

    fun setTglSetor(param:Long){
        _tglSetor.value =param
        _loading.value =false
    }

    fun setor(setoran: Setoran){
        viewModelScope.launch {
            _pesan.value =sampahRepository.setoran(setoran)
            _loading.value =false
        }
    }

    fun getNasabah(nama:String){
        viewModelScope.launch {
            _nasabah.value = sampahRepository.searchNasabah(nama)
            _loading.value =false
        }
    }

    fun setLoading(param:Boolean){
        _loading.value =param
    }

}
class ViewModelFactory(private val sampahRepository:InputSampahRepository,private val profileadmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(InputSampahViewModel::class.java) -> {
                return InputSampahViewModel(sampahRepository,profileadmin) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(sampahRepository: InputSampahRepository = Injection.provideRepository(),profileadmin: profileAdmin): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(sampahRepository,profileadmin)
            }.also { instance = it }
    }
}