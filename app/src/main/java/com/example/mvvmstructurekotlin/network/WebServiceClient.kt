package  com.example.mvvmstructurekotlin.network

import android.util.Log
import com.example.mvvmstructurekotlin.util.Util
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


object WebServiceClient {
    private lateinit var interceptor: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient
    private var retrofit: Retrofit? = null


    val client: Retrofit
        get() {

            val httpLogger = HttpLoggingInterceptor()
            httpLogger.setLevel(HttpLoggingInterceptor.Level.BODY)

            val timeout = TimeUnit.SECONDS.toSeconds(100)

            val Httpclient = OkHttpClient.Builder()
            Httpclient.readTimeout(timeout, TimeUnit.SECONDS)
            Httpclient.writeTimeout(timeout, TimeUnit.SECONDS)
            Httpclient.connectTimeout(timeout, TimeUnit.SECONDS)



            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    request = request.newBuilder().addHeader(
                            "IMSI",
                            Util.IMSI
                    ).build()
                    request = request.newBuilder().addHeader(
                            "IMEI",
                            Util.IMEI
                    ).build()

                    Log.e("headerInterceptor: ", "")

                    val response = chain.proceed(request)
                    if (response.code() == 509) {
                        Log.e("headerInterceptor: ", "return 509")
                        return response
                    } else {
                        return response
                    }
                }
            }

            Httpclient.addInterceptor(headerInterceptor)
            Httpclient.addInterceptor(httpLogger)

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(Httpclient.build())
                        .baseUrl(Util.baseURL)
                        .build()

            }
            return retrofit!!

        }


}
