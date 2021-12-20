package com.example.layoutpractice.network

import android.content.Intent
import android.util.Log
import com.example.layoutpractice.Utils.API
import com.example.layoutpractice.Utils.Constants.TAG
import com.example.layoutpractice.model.User
import com.google.gson.JsonElement
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager() //싱클턴 적용


    }

    // http call create
    // 레트로핏 인터페이스 가져오기
    private val httpCall: RetrofitInterface? = NetworkUtil.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)




    // 세션 가져오기 api 호출
    fun takeSession(completion : (String) -> Unit){

        val call = httpCall?.getSession()?: return   //이게 spring security 안에 api를 호출
        Log.d(TAG,"RetrofitManager - takeSession() called")

        call.enqueue(object: retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"RetrofitManager - onResponse() called/ response: ${response.raw()}")
                completion(response.raw().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"RetrofitManager - onFailure() called t: $t")
            }

        })
    }

    fun getUserName(completion: (String) -> Unit){
        val call= httpCall?.getUserName()?: return
        Log.d(TAG,"RetrofitManager - getUserName() called")


        call.enqueue(object: retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"RetrofitManager - onResponse() called/ response: ${response.raw()}")
                completion(response.raw().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"RetrofitManager - onFailure() called t: $t")
            }

        })

    }


//    fun postUser(user : User){
//
//        //map = HashMap<String,String>();
//        var map : HashMap<String,String>? = null;
//        map = HashMap<String,String>()
//        map.put("name", user.username.toString())
//        map.put("email", user.email.toString())
//        map.put("password", user.password.toString())
//
//        //var body : RequestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(),map.toString());
//        val call = httpCall?.postUser(param = map)
//        Log.d(TAG,"RetrofitManager - postUser() called")
//
//        if (call != null) {
//            call.enqueue(object: retrofit2.Callback<ResponseBody>{
//                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                    Log.d(TAG,"RetrofitManager - onResponse() called : ${response.raw().code}")
//
//                }
//
//                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                    Log.d(TAG,"RetrofitManager - onFailure() called : ${t.cause}")
//                }
//
//            })
//        }
//
//
//    }



}