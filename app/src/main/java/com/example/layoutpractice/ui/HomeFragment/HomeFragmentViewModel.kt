package com.example.layoutpractice.ui.HomeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ActionType{
    Photo,
    User
}
class HomeFragmentViewModel : ViewModel() {

    companion object{
        const val TAG: String = "로그"
    }

    private val _currentType = MutableLiveData<String>()
    private val _currentText = MutableLiveData<String>()

    val currentType : LiveData<String>
        get() = _currentType

    val currentText : LiveData<String>
        get() = _currentText

    init {
        Log.d(TAG,"HomeFragmentViewModel - 생성자 호출() called")
        _currentType.value = "사진"
        _currentText.value = ""
    }

    fun updateType(actionType: ActionType, input: String)
    {
        when(actionType){
            ActionType.Photo->
                _currentType.value = "사진"
            ActionType.User->
                _currentType.value ="유저"
        }
    }

    fun updateText(input : String)
    {
        _currentText.value = input
        Log.d(TAG,"HomeFragmentViewModel - updateText() ${_currentText.value}")
    }


}