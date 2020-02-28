package com.zhutian.baselibrary.basehttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager private constructor(){

    private var retrofit: Retrofit? = null
    companion object {
        private var retrofitManager : RetrofitManager? = null
        get() {
            if (field == null){
                field = RetrofitManager()
            }
            return field
        }
        @Synchronized fun get():RetrofitManager{
            return retrofitManager!!
        }
    }

    private fun initRetrofit(baseUrl:String){
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLogInterceptor())
            .connectTimeout(5,TimeUnit.SECONDS)
            .readTimeout(5,TimeUnit.SECONDS)
            .writeTimeout(5,TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private var baseUrl = "http://user.api.pandakill.live/"


    /**
     * 代理service接口
     */
    fun <T>createService(mClass:Class<T>):T{
        if (retrofit == null){
            initRetrofit(baseUrl)
        }
        return retrofit!!.create(mClass)
    }

}