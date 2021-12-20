package com.example.layoutpractice.network

import com.example.layoutpractice.Utils.API
import com.google.gson.JsonElement
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    @GET(API.SESSION)
    fun getSession() : Call<JsonElement>

    @GET(API.USERNAME)
    fun getUserName() : Call<JsonElement>


    @FormUrlEncoded
    @POST(API.JOIN)
    fun postUser(@Body param: RequestBody) : Call<String>



}