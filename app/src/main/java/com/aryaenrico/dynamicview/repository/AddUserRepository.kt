package com.aryaenrico.dynamicview.repository
import com.aryaenrico.dynamicview.model.Message
import com.aryaenrico.dynamicview.retrofit.ApiService
import java.lang.Exception

class AddUserRepository(private val apiService: ApiService) {

   suspend fun addUserRepo(pNama:String,pAlamat:String,pPassword:String,ptelp:String):Message{
       var data = Message()
       try{
           data =apiService.addUser(nama = pNama,alamat =pAlamat,password = pPassword,no_telp = ptelp)
       }catch (e:Exception){
           data.pesan ="terdapat kesalan pada jaringan"
       }
       return data
   }

    companion object {
        @Volatile
        private var instance: AddUserRepository? = null
        fun getInstance(apiService: ApiService): AddUserRepository =
            instance ?: synchronized(this) {
                instance ?:AddUserRepository(apiService)
            }.also { instance = it }
    }

}