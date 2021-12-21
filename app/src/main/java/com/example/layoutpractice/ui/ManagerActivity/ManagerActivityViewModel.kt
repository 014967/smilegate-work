package com.example.layoutpractice.ui.ManagerActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.layoutpractice.Utils.API
import com.example.layoutpractice.Utils.API.str
import com.example.layoutpractice.model.User
import com.example.layoutpractice.network.RetroInstance
import com.example.layoutpractice.network.RetroInstance.Companion.gson
import com.example.layoutpractice.network.RetroService
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable


import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type
import java.nio.charset.Charset


class ManagerActivityViewModel : ViewModel() {
    companion object {
        val TAG: String = "로그"
    }


    private var _userListListData = MutableLiveData<ArrayList<User>>()
    private var _userList=  ArrayList<User>()
    var compositeDisposable = CompositeDisposable()


    val userListListData: LiveData<ArrayList<User>>
        get() = _userListListData



    fun getUserListResponse( response: String) {
        Log.d(TAG, "response : ${response}")

        var gson = GsonBuilder().create()
        var userList = gson.fromJson(response, Array<User>::class.java).toList()
        Log.d(TAG, "userlist - $userList() called")

        _userList = ArrayList<User>()
        for (u in 0 until userList.size) {

            var user = User(userList[u].name,userList[u].password,userList[u].email,userList[u].roles)
            _userList.add(user)

        }
        Log.d(TAG,"userList - $_userList() called")
        _userListListData.value = _userList
    }

    fun deleteUser(accessToken: String, user: Map<Int, User>)
    {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)




        var body = JsonObject()
        var nestBody = JsonArray()
        user.forEach({(k,v) ->
            run{
                var jsonObject = JsonObject()
                jsonObject.addProperty("email" ,v.email)
                nestBody.add(jsonObject)
            }
        })
        body.add("userList", nestBody)



        Log.d(TAG,"ManagerActivityViewModel - deleteUser() body : $body")
        compositeDisposable.add(retroInstance.deleteUserList(str + accessToken,body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG,"ManagerActivityViewModel - deleteUser() called: $it")
                    var source: BufferedSource = it.source()
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    var buffer: Buffer = source.buffer
                    var responseBodyString: String =
                        buffer.clone().readString(Charset.forName("UTF-8"))
                    getUserListResponse(responseBodyString)

                },
                {
                  Log.d(TAG,"ManagerActivityViewModel - deleteUser() error called :$it")
                }
            ))

    }


}