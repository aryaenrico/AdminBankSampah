package com.aryaenrico.dynamicview.viewmodel

import androidx.lifecycle.*
import com.aryaenrico.dynamicview.dataStore.profileAdmin
import com.aryaenrico.dynamicview.injection.Injection
import com.aryaenrico.dynamicview.model.Admin
import com.aryaenrico.dynamicview.model.Login
import com.aryaenrico.dynamicview.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository,private val profileadmin: profileAdmin):ViewModel() {
    private var _loginResult = MutableLiveData<Login>()
    val loginResult : LiveData<Login> =_loginResult

    fun login (notelp:String,password:String){
        viewModelScope.launch {
            _loginResult.value = loginRepository.login(notelp,password)
        }
    }
    fun setDataAdmin(admin: Admin){
        viewModelScope.launch {
            profileadmin.setProfile(admin)
        }
    }

    fun getDataAdmin():LiveData<Admin>{
      return  profileadmin.getProfile().asLiveData()
    }
}
class ViewModelFactoryLogin(private val loginRepository: LoginRepository,private val profileAdmin: profileAdmin) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(loginRepository,profileAdmin) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactoryLogin? = null

        fun getInstance(loginRepository: LoginRepository= Injection.provideLoginRepository(),profileadmin: profileAdmin): ViewModelFactoryLogin = instance ?: synchronized(this) {
            instance ?: ViewModelFactoryLogin(loginRepository,profileadmin) }.also { instance = it }
    }
}