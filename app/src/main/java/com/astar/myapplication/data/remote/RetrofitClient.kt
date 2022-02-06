package com.astar.myapplication.data.remote

import com.astar.myapplication.data.remote.service.RadioServiceApi
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

//private const val BASE_URL = "https://radio-world-50-000-radios-stations.p.rapidapi.com/v1/"
//private const val API_KEY = "0f541b4108msh793f3bb9f4cc613p10bb20jsn8d8d5d2626ae"

private const val BASE_URL = "http://a0577461.xsph.ru/radios/"

object RetrofitClient {

    /*private val interceptor = Interceptor { chain ->
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader("x-rapidapi-key", API_KEY)
            .build()
        chain.proceed(newRequest)
    }*/

    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)


    fun create(): RadioServiceApi {
        val builder = OkHttpClient.Builder()
        // builder.interceptors().add(interceptor)
        builder.addInterceptor(interceptor)
        val client = builder.build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(RadioServiceApi::class.java)
    }
}