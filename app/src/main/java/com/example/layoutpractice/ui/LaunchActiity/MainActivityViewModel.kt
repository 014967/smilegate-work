package com.example.layoutpractice.ui.LaunchActiity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.layoutpractice.Utils.API.ROLE_MANAGER
import com.example.layoutpractice.Utils.API.ROLE_USER

import com.example.layoutpractice.Utils.SharedPrefManager
import com.example.layoutpractice.model.Token
import com.example.layoutpractice.network.RetroInstance
import com.example.layoutpractice.network.RetroService
import com.example.layoutpractice.ui.SignUpActivity.SignUpActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.internal.http2.Http2Writer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import kotlin.math.log


enum class ActionType {

    EMAIL,
    PASSWORD
}

class MainActivityViewModel : ViewModel() {
    companion object {
        val TAG: String = "로그"
    }

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private var _status = MutableLiveData<Int>()

    var compositeDisposable = CompositeDisposable()

    val email: LiveData<String>
        get() = _email
    val password: LiveData<String>
        get() = _password

    val status: LiveData<Int>
        get() = _status

    init {
        _email.value = ""
        _password.value = ""
        _status.value = 0

    }

    fun updateValue(actionType: ActionType, input: String) {

        when (actionType) {
            ActionType.EMAIL -> {
                _email.value = input
            }
            ActionType.PASSWORD -> {
                _password.value = input
            }
        }

    }


    fun loginClick() {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)


        var requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", _email.value.toString())
            .addFormDataPart("password", _password.value.toString()).build()



//        Log.d(TAG, "email : ${_email.value.toString()}")
//        Log.d(TAG, "password : ${_password.value.toString()}")
        //Log.d(TAG,"email : $email password : $password")
        compositeDisposable.add(retroInstance.loginUser(requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {


//                    Log.d(TAG,"it : $it")
//                    // map의 형태로 전달 받는다.
//                    Log.d(TAG,it.body().toString())
//                    var gson : Gson? = GsonBuilder().create()
//                    var token : Token? = gson?.fromJson(it.body().toString(), Token::class.java)
//                    Log.d(TAG,"token : ${token?.access_token}")
//                    Log.d(TAG,"token : ${token?.refresh_token}")

                    //var sharedPf : SharedPrefManager? = SharedPrefManager.storeToken(it.body().toString())
                    Log.d(TAG, "it : ${it.code()}")
                    Log.d(TAG, "it : ${it.body()}")

                    var gson : Gson? = GsonBuilder().create()
                    var token : Token? = gson?.fromJson(it.body().toString(), Token::class.java)
                    var role = token?.roles

                    Log.d(TAG,"role : $role")
                    val check = role?.find{it.contains("ADMIN")}
                    if(it.code() == 200)
                    {

                        if(check != null)
                        {
                            // 매니저
                            SharedPrefManager.storeToken(it.body().toString())
                            _status.value = ROLE_MANAGER
                        }
                        else {
                            SharedPrefManager.storeToken(it.body().toString())
                            _status.value= ROLE_USER

                        }

                        compositeDisposable.dispose()

                    }




                }, {


                    Log.d(TAG,"it : ${it.message}")
                    Log.d(TAG,"called :404")
                    _status.value = 404
                    compositeDisposable.dispose()
                }

            )
        )





    }

}