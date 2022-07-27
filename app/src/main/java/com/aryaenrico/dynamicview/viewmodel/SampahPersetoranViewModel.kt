package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.SetoranTambahan
import com.aryaenrico.dynamicview.repository.SampahPersatuanRepository
import kotlinx.coroutines.launch

class SampahPersetoranViewModel(private  val  sampahPersatuanRepository: SampahPersatuanRepository):ViewModel() {

    private var _data = MutableLiveData<ArrayList<Sampah>>()
    val  data: LiveData<ArrayList<Sampah>> =_data

    private var _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> =_loading

    private var _pesan = MutableLiveData<Message>()
    val pesan:LiveData<Message> =_pesan
    fun getDataSampah(param:String){
        _loading.value =true
        viewModelScope.launch {
            _data.value = sampahPersatuanRepository.getDataSampah(param)
            _loading.value =false
        }
    }

    fun addSetoranTambahan(setoranTambahan: SetoranTambahan){
        viewModelScope.launch {
            _pesan.value =sampahPersatuanRepository.setoranTambahan(setoranTambahan)
            _loading.value =false
        }
    }

}
class ViewModelFactorySampahPersetoran(private val sampahPersatuanRepository: SampahPersatuanRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SampahPersetoranViewModel::class.java) -> {
                return SampahPersetoranViewModel(sampahPersatuanRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactorySampahPersetoran? = null
        fun getInstance(sampahPersatuanRepository: SampahPersatuanRepository =Injection.provideSampahPersetoranRepository()): ViewModelFactorySampahPersetoran = instance ?: synchronized(this) {
            instance ?: ViewModelFactorySampahPersetoran(sampahPersatuanRepository) }.also { instance = it }
    }
}