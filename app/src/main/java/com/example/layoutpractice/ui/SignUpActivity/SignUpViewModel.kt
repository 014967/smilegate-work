package com.example.layoutpractice.ui.SignUpActivity

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.layoutpractice.model.Status


import com.example.layoutpractice.network.RetroInstance
import com.example.layoutpractice.network.RetroService
import com.example.layoutpractice.network.RetrofitManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers


enum class ActionType{
    NAME,
    ID,
    PASSWORD
}
class SignUpViewModel : ViewModel(){

    companion object{
        val TAG:String = "로그"
    }


    private val _name = MutableLiveData<String>()
    private val _id = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _status = MutableLiveData<Status>()

    var compositeDisposable = CompositeDisposable()



    val name : LiveData<String>
        get() = _name

    val id : LiveData<String>
        get() = _id

    val password : LiveData<String>
        get() = _password

    val status : LiveData<Status>
        get() = _status
    init {
        _name.value=""
        _id.value=""
        _password.value=""
    }


    fun updateValue(actionType : ActionType, input : String)
    {
        when(actionType)
        {
            ActionType.NAME ->
            {
                _name.value = input
            }
            ActionType.ID ->
            {
                _id.value = input
            }
            ActionType.PASSWORD ->
            {
                _password.value = input
            }
        }

    }



    fun createUser(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)


        var map : HashMap<String,String>? = null;
        map = HashMap<String,String>()
        map.put("name", name.value.toString())
        map.put("email", id.value.toString())
        map.put("password", password.value.toString())

        //Log.d(TAG,"name : $name , email : $id, password : $password")
        Log.d(TAG,"map : $map")
        var disposable : Disposable =  retroInstance.createUser(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d(TAG,"SignUpViewModel - sucess :${it.sucess}")
                    _status.value = it

                },{
                    Log.d(TAG,"SignUpViewModel - throw : ${it.message}")
                }

            )

        compositeDisposable.add(disposable)
        disposable.addTo(compositeDisposable)

        compositeDisposable.dispose()
    }







}