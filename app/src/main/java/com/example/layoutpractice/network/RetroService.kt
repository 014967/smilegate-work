package com.example.layoutpractice.network

import com.example.layoutpractice.Utils.API
import com.example.layoutpractice.model.Status
import com.example.layoutpractice.model.Token
import com.google.gson.JsonArray
import com.google.gson.JsonObject

import java.util.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.*
import kotlin.collections.HashMap

interface RetroService {

    @Headers("Content-Type: application/json")
    @POST(API.JOIN)
    fun createUser(@Body param: Map<String,String>) : Observable<Status>



//    @Headers("Content-Type: application/json")
//    @POST("api/login")
//    @FormUrlEncoded
//    fun loginUser(@Field("email") email :String,
//                    @Field("password") password: String) : Observable<Token>

    @POST("api/login")
    fun loginUser(@Body body: RequestBody) : Observable<Response<Object>>

    @GET("api/users")
    fun getUserList(@Header("Authorization") authHeader : String) : Observable<ResponseBody>


    @HTTP(method="DELETE", hasBody = true, path = API.DELETE)
    fun deleteUserList(@Header("Authorization") authHeader: String, @Body body : JsonObject) : Observable<ResponseBody>
}