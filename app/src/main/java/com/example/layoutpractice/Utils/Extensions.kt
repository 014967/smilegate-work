package com.example.layoutpractice.Utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText


// 문자열이 json 형태인지, json 배열형태 인지 체크

fun String?.isJsonObject():Boolean {
    if(this?.startsWith("{") == true && this.endsWith("}"))
    {
        return true
    }else
    {
        return false
    }
}

fun String?.isJsonArray():Boolean{
    if(this?.startsWith("[") == true && this.endsWith("]"))
    {
        return true
    }else
    {
        return false
    }
}
fun String?.isCookie():Boolean{
    if(this?.contains("JSESSIONID=") == true)
    {
        return true
    }
    else {
        return false
    }
}




fun EditText.onTextChanged(completion : (Editable?) -> Unit)
{
    val TAG:String = "로그"
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.d(TAG," - beforeTextChanged() called")

            
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            
            Log.d(TAG," - onTextChanged() called")
        }

        override fun afterTextChanged(p0: Editable?) {
            //TODO("Not yet implemented")
            completion(p0)
        }
    })
}
