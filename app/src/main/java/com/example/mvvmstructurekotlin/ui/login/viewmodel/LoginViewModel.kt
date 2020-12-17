package com.example.mvvmstructurekotlin.ui.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.mvvmstructurekotlin.model.User
import com.example.mvvmstructurekotlin.network.BackEndApi
import com.example.mvvmstructurekotlin.network.WebServiceClient
import com.example.mvvmstructurekotlin.util.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application), Callback<User> {


    var UserName: ObservableField<String>? = null
    var Password: ObservableField<String>? = null
    var ProgressDialog: SingleLiveEvent<Boolean>? = null
    var UserLogin: MutableLiveData<User>? = null
    var xapi: MutableLiveData<String>? = null
    var errormsg: MutableLiveData<String>? = null
    var invalidUsername: MutableLiveData<String>? = null
    var invalidPassword: MutableLiveData<String>? = null

    init {
        ProgressDialog = SingleLiveEvent<Boolean>()
        UserName = ObservableField("")
        Password = ObservableField("")
        UserLogin = MutableLiveData<User>()
        invalidPassword = MutableLiveData<String>()
        invalidUsername = MutableLiveData<String>()
        xapi = MutableLiveData<String>()
        errormsg = MutableLiveData<String>()
    }


    fun login() {
        if(UserName?.get()!!.length==0){
            invalidUsername?.value="Enter User Name"
        }else if(Password?.get()!!.length==0){
            invalidPassword?.value="Enter Password"
        }else{
                ProgressDialog?.value = true
                WebServiceClient.client.create(BackEndApi::class.java).LOGIN(username = UserName?.get()!!, password = Password?.get()!!)
                    .enqueue(this)


        }

    }

    override fun onResponse(call: Call<User>?, response: Response<User>?) {
        ProgressDialog?.value = false
        if (response?.body()!!.errorCode.equals("00")) {
            val headers = response.headers()
            val xAccToken = headers["X-Acc"]
            xapi?.value = xAccToken
            UserLogin?.value = response?.body()
        }else if(response?.body()!!.errorCode.equals("01") ){
            errormsg?.value = response?.body()!!.errorMessage


        }


    }

    override fun onFailure(call: Call<User>?, t: Throwable?) {
        ProgressDialog?.value = false

    }

}
