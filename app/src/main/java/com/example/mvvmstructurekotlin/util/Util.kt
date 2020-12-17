package  com.example.mvvmstructurekotlin.util

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log
import java.util.regex.Pattern

class Util {

    companion object {
   /*     fun isEmailValid(email: String): Boolean {
            val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
            val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }*/

        fun isValidString(email: String): Boolean {
          if(email.length==0){
              return false
          }else{
              return true
          }
        }


        var IMEI = ""
        var IMSI = ""
        var baseURL = "http://private-222d3-homework5.apiary-mock.com/api/"
        const val TEXT_MESSAGES: String = "TEXT_MESSAGES"
        const val OK: String = "ok"


    }
    object REQUEST_PRMS {
        const val username = "username"
        const val password = "password"
    }


}