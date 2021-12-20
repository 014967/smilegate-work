package com.example.layoutpractice.Utils

import android.content.Context
import android.content.SharedPreferences
import com.example.layoutpractice.App
import com.example.layoutpractice.model.Token
import com.google.gson.Gson
import com.google.gson.GsonBuilder



object SharedPrefManager {

    private const val TOKEN_HISTORY = "token_history"
    private const val SHARED_ACCESS = "access_token"
    private const val SHARED_REFRESH = "refresh_token"

    fun storeToken(response: String)
    {
        var gson : Gson? = GsonBuilder().create()
        var token : Token? = gson?.fromJson(response, Token::class.java)

        val shared: SharedPreferences = App.instance.getSharedPreferences(TOKEN_HISTORY, Context.MODE_PRIVATE)

        val editor : SharedPreferences.Editor = shared.edit()
        editor.putString(SHARED_ACCESS, token?.access_token)
        editor.putString(SHARED_REFRESH ,token?.refresh_token)



        editor.apply()
    }
     //토큰 가져오기
    fun getToken(): Token? {
        val shared: SharedPreferences = App.instance.getSharedPreferences(TOKEN_HISTORY, Context.MODE_PRIVATE)
        val storedAccessToken : String? = shared.getString(SHARED_ACCESS, "")
        val storedReFreshToken : String? = shared.getString(SHARED_REFRESH, "")

        var storedTokenList : Token? = null
//         var map : HashMap<String, String>? = null
//         map = HashMap<String,String>()
//         map.put("access_token", storedAccessToken.toString())
//         map.put("refresh_token", storedReFreshToekn.toString())

         storedTokenList = Token(storedAccessToken.toString(),storedReFreshToken.toString(), null)

        return storedTokenList

    }

}