package com.example.mvvmstructurekotlin.base

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mvvmstructurekotlin.repository.RepoModel
import com.example.mvvmstructurekotlin.util.Util
import com.example.mvvmstructurekotlin.util.extensions.isInternetAvailable
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity() {

    val repo: RepoModel by inject()
    val READ_PHONE_STATE_CODE: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = arrayOf<String>(Manifest.permission.READ_PHONE_STATE)
            if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(permissions, READ_PHONE_STATE_CODE)
            } else {
                getDeviceIds()
            }
        } else {
            getDeviceIds()
        }


    }

    @SuppressLint("MissingPermission")
    private fun getDeviceIds() {
        try {
            val telephonyManager =
                    getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            Util.IMEI = telephonyManager.deviceId
            println("imei: ${Util.IMEI}")

            Util.IMSI = telephonyManager.subscriberId
            println("msi: ${Util.IMSI}")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //TODO Check Internet Connectivity
    fun checkInternet(): Boolean {

        val available = isInternetAvailable()
        if (!available) {
            showInternetDialog()
        }
        return available
    }


    //TODO For Adding New Fragment
    fun addFragment(
            @NonNull fragment: Fragment,
            backStackName: Boolean = false,
            aTAG: String = "",
            @IdRes containerViewId: Int
    ) {

        val transition = supportFragmentManager.beginTransaction()

        if (backStackName)
            transition.addToBackStack(aTAG)

        transition.add(containerViewId, fragment).commit()
    }


    //TODO Show Dialog
    @Synchronized
    fun showDialog(msg: String) {
        val msgDialog = MessageDialog.getInstance()
        val bundle = Bundle()
        bundle.putString(Util.TEXT_MESSAGES, msg)
        msgDialog.setArguments(bundle)
        msgDialog.show(supportFragmentManager, "")
        msgDialog.setListener(object : MessageDialog.OnClick {
            override fun set(ok: Boolean) {
                msgDialog.dismiss()
            }
        })
    }


    //TODO Show Internet Dialog
    fun showInternetDialog() {
        val msgDialog = MessageDialog.getInstance()
        val bundle = Bundle()
        bundle.putString(Util.TEXT_MESSAGES, "Please Check Internet Connection")
        bundle.putString(Util.OK, "OK")
        msgDialog.setArguments(bundle)
        msgDialog.show(supportFragmentManager, "")
        msgDialog.setListener(object : MessageDialog.OnClick {
            override fun set(ok: Boolean) {
                msgDialog.dismiss()
            }
        })
    }
}