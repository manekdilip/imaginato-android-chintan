package com.example.mvvmstructurekotlin.ui.login


import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmstructurekotlin.R
import com.example.mvvmstructurekotlin.base.BaseActivity
import com.example.mvvmstructurekotlin.databinding.ActivityLoginsBinding
import com.example.mvvmstructurekotlin.repository.dbhandler.login.LoginTableModel
import com.example.mvvmstructurekotlin.ui.login.viewmodel.LoginViewModel
import com.example.mvvmstructurekotlin.util.CustomeProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginsBinding
    var viewmodel: LoginViewModel? = null
    var customeProgressDialog: CustomeProgressDialog? = null
    var xapi = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_logins)
        viewmodel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginviewmodel = viewmodel
        customeProgressDialog = CustomeProgressDialog(this)
        initObservables()
        checkInternet()
    }

    private fun initObservables() {
        viewmodel?.ProgressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()
        })
        viewmodel?.xapi?.observe(this, Observer {
            xapi = it
        })
        viewmodel?.invalidUsername?.observe(this, Observer {
            binding.edtUsername.setError(it)
        })
        viewmodel?.invalidPassword?.observe(this, Observer {
            binding.edtPassword.setError(it)
        })
        viewmodel?.errormsg?.observe(this, Observer {
            showDialog(it)
        })
        viewmodel?.UserLogin?.observe(this, Observer { it ->

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails =
                    LoginTableModel(
                        "" + it.user.userId,
                        it.user.userName,
                        xapi
                    )
                repo.loginDatabase.loginDao.insertLoginUser(loginDetails)
            }


            Toast.makeText(
                this, "Welcome  ${
                    it.user.userName
                }!!! ", Toast.LENGTH_LONG
            ).show()
        })
    }


}
