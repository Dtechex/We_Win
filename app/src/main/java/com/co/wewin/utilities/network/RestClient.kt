package com.co.wewin.utilities.network

import com.co.wewin.WeWinApplication.AppConstants
import com.co.wewin.utilities.StorageUtils2
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


class RestClient {
    companion object{
        private var retrofit:Retrofit?=null
        fun getClient():Retrofit{
            val logging = HttpLoggingInterceptor()
            logging.level = (HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)


            if (retrofit == null){
                retrofit=Retrofit.Builder()
                    .baseUrl(NetworkConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build()
            }
            return retrofit!!
        }


        fun getAuthenticatedClient():Retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(getTrustKit())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getTrustKit():OkHttpClient{
            val clientBuilder=OkHttpClient.Builder()
            clientBuilder.connectTimeout(1, TimeUnit.MINUTES)
            clientBuilder.readTimeout(1, TimeUnit.MINUTES)
            clientBuilder.writeTimeout(1, TimeUnit.MINUTES)

//            if (BuildConfig.DEBUG) {
//                val httpLoggingInterceptor = HttpLoggingInterceptor()
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//                clientBuilder.addInterceptor(httpLoggingInterceptor)
//            }

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(httpLoggingInterceptor)

            HeaderInterceptor.addPostLoginHeaders(clientBuilder)

            return clientBuilder.build()
        }


        internal object HeaderInterceptor{
            fun addPostLoginHeaders(clientBuilder: OkHttpClient.Builder){
                clientBuilder.addInterceptor {
                    var request=it.request()
                    val headerBuilder=request.headers.newBuilder()
                    headerBuilder.add("Authorization","Bearer " +StorageUtils2.fetchString(AppConstants.StorageUtilKeys.userToken,"")!!)
                    request=request.newBuilder().headers(headerBuilder.build()).build()
                    it.proceed(request)
                }
            }
        }
    }
}