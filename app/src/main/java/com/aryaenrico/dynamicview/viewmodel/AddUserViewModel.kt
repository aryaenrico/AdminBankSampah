package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.repository.AddUserRepository
import kotlinx.coroutines.launch


class AddUserViewModel (private val addUserRepository: AddUserRepository):ViewModel() {

    private var _pesan = MutableLiveData<Message>()
    val pesan : LiveData<Message> =_pesan

    fun inputUser(nama:String,alamat:String,password:String,notelp:String){
        viewModelScope.launch {
            _pesan.value =addUserRepository.addUserRepo(nama,alamat,password,notelp)
        }
    }
}

class ViewModelFactoryAddUser(private val addUserRepository: AddUserRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AddUserViewModel::class.java) -> {
                return AddUserViewModel(addUserRepository) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryAddUser? = null

        fun getInstance(addUserRepository: AddUserRepository =Injection.provideRepositorAddUser()): ViewModelFactoryAddUser = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryAddUser(addUserRepository) }.also { instance = it }
    }
}