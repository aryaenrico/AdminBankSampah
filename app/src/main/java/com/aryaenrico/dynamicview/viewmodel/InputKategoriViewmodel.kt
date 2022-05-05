package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.repository.InputKategoriRepository
import kotlinx.coroutines.launch

class InputKategoriViewmodel(private val inputKategoriRepository: InputKategoriRepository):ViewModel() {

    private var _pesan = MutableLiveData<Message>()
    val pesan : LiveData<Message> =_pesan

    fun inputKategori(kategori:String){
        viewModelScope.launch {
            _pesan.value = inputKategoriRepository.inputKategori(kategori)
        }
    }
}
class ViewModelFactoryInputKategori(private val inputKategoriRepository: InputKategoriRepository ) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(InputKategoriViewmodel::class.java) -> {
                return InputKategoriViewmodel(inputKategoriRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryInputKategori? = null

        fun getInstance(inputKategoriRepository: InputKategoriRepository = Injection.provideRepositoryInputKategori()): ViewModelFactoryInputKategori = instance ?: synchronized(this) {
                instance ?: ViewModelFactoryInputKategori(inputKategoriRepository) }.also { instance = it }
    }
}