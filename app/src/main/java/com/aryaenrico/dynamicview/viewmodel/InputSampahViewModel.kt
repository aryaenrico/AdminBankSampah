package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.model.Sampah
import com.aryaenrico.dynamicview.model.Setoran
import com.aryaenrico.dynamicview.repository.InputSampahRepository
import kotlinx.coroutines.launch

class InputSampahViewModel(private val sampahRepository: InputSampahRepository):ViewModel() {

    private var _data = MutableLiveData<ArrayList<Sampah>>()
    val  data:LiveData<ArrayList<Sampah>> =_data

    private var _pesan = MutableLiveData<Message>()
    val pesan :LiveData<Message> =_pesan

    fun getData(){
        viewModelScope.launch {
            _data.value =sampahRepository.getDataSampah()
        }
    }

    fun setor(setoran: Setoran){
        viewModelScope.launch {
            _pesan.value =sampahRepository.setoran(setoran)
        }
    }

}
class ViewModelFactory(private val sampahRepository:InputSampahRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(InputSampahViewModel::class.java) -> {
                return InputSampahViewModel(sampahRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(sampahRepository: InputSampahRepository = Injection.provideRepository()): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(sampahRepository)
            }.also { instance = it }
    }
}