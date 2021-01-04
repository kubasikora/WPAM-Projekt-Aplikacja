package pw.wpam.polityper.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pw.wpam.polityper.models.User
import pw.wpam.polityper.services.AuthService

class UserViewModel : ViewModel() {
    val currentUser: MutableLiveData<User?> by lazy {
        MutableLiveData<User?>().also {
            loadCurrentUser()
        }
    }
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val loginFailedMessage: MutableLiveData<String> = MutableLiveData<String>("")
    val loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun login(username: String, password: String) {
        loading.postValue(true)
        loginFailedMessage.postValue("")
        AuthService.loginUser(username, password) { success, user ->
            if(success){
                loginSuccess.postValue(true)
                currentUser.postValue(user)
                loading.postValue(false)
            } else {
                loginSuccess.postValue( false)
                loginFailedMessage.postValue("Wrong password. Please try again")
                loading.postValue(false)
            }
        }
    }

    fun logout() {
        Log.d("USER VM", "Logging out")
        loading.postValue(true)
        AuthService.logout { _ ->
            loginSuccess.postValue(false)
            currentUser.postValue(null)
            loading.postValue(false)
        }
    }

    private fun loadCurrentUser() {
        AuthService.getCurrentUser { success, user ->
            if(success){
                currentUser.postValue(user)
            } else {
                currentUser.postValue(null)
            }
        }
    }
}