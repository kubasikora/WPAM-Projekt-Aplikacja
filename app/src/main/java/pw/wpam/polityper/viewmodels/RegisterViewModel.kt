package pw.wpam.polityper.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.wpam.polityper.services.AuthService

class RegisterViewModel : ViewModel() {
    val registerSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val registerMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun reset(){
        registerSuccess.postValue(false)
        registerMessage.postValue("")
        loading.postValue(false)
    }

    fun register(email: String, username: String, password: String, password2: String) {
        Log.d("USER VM", "Registering new user")
        loading.postValue(true)
        AuthService.registerUser(email, username, password, password2) { success, message ->
            if(success){
                registerSuccess.postValue(true)
            } else {
                registerSuccess.postValue(false)
            }
            registerMessage.postValue(message)
            loading.postValue(false)
        }
    }

}