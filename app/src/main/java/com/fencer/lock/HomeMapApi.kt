package com.fencer.lock

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

import java.util.concurrent.TimeUnit

/**

@author  SZhua
Create at  2022/6/2
Description: 网络请求相关
 */
interface HomeMapApi {



    /**
     * 水情报表（手动）
     */
    @GET(value = "/lcyzt/app-AppYsq-querySdList.do")
    fun querySdList(@QueryMap params: Map<String, String>) :Observable<RiverCenterRootBean>

    /**
     * 水情报表（自动）
     */
    @GET(value =  "/lcyzt/app-AppYsq-queryZdList.do")
    fun queryZdList(@QueryMap params: Map<String, String>) :Observable<CommonListBean<AutoBean>>



    companion object{

        /**
         * 创建网络请求的单例
         */
        private var instance: HomeMapApi ?=null

        fun getInstance(): HomeMapApi {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = createRetrofit()
                    }
                }
            }
            return instance!!
        }
        private  fun createRetrofit() : HomeMapApi{
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            val mOkHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .callTimeout(120,TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .connectTimeout(120,TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl("http://120.224.28.51:8014")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
           return  retrofit.create(HomeMapApi::class.java)
        }
    }
}