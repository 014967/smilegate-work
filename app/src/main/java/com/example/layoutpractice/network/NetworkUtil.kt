package com.example.layoutpractice.network

import android.util.Log
import com.example.layoutpractice.Utils.isCookie
import com.example.layoutpractice.Utils.isJsonArray
import com.example.layoutpractice.Utils.isJsonObject
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.net.CookieManager
import java.net.CookiePolicy


object NetworkUtil{

    val TAG:String = "로그"
    private var retrofitClient : Retrofit? = null


    //레트로핏 클라이언트 가져오기
    fun getClient(baseUrl:String) : Retrofit? {
        Log.d(TAG,"NetworkUtil - getClient() called")


        val gson = GsonBuilder()
            .setLenient().create();

        //로깅 인터셉터 추가
        // Okhttp 인스턴스 생성
        val client = OkHttpClient.Builder()


        // 로그를 찍기 위해 인터셉처 추가

        val logging = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
/*                Log.d(TAG,"NetworkUtil - log() called / message : $message")
                //message -> get https://192.168.0.102:8080/session

                when{
                    message.isJsonObject() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    message.isJsonArray() ->
                        Log.d(TAG,JSONObject(message).toString(4))
                    else ->{
                        try{
                            Log.d(TAG,JSONObject(message).toString(4))
                        }catch(e: Exception)
                        {
                            Log.d(TAG,"error : $message")
                        }
                    }

                }

 */
//                when{
//                    message.isCookie() -> {
//                        Log.d(TAG, "SessionID : $message")
//                        var result = message.split(" ")
//                        var result2 = result[1].split("=")
//
//                        cookie = result2[1].substring(0 until (result2[1].length - 1))
//
//                    }
//                    else ->
//                    {
//
//                    }
//                }
            }

        })

        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        client.addInterceptor(logging)
            .cookieJar(JavaNetCookieJar(CookieManager()))

        if(retrofitClient == null)
        {
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}
