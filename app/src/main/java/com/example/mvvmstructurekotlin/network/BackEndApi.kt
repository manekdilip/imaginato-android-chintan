package  com.example.mvvmstructurekotlin.network

import com.example.mvvmstructurekotlin.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import io.reactivex.Observable

interface BackEndApi {
    @FormUrlEncoded
    @POST("login")
    fun LOGIN(@Field("username") username: String, @Field("password") password: String): Call<User>



}

