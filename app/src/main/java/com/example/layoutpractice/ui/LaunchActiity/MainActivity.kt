package com.example.layoutpractice.ui.LaunchActiity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.layoutpractice.Utils.SharedPrefManager
import com.example.layoutpractice.Utils.onTextChanged
import com.example.layoutpractice.ui.NavigationActivity.NavigationActivity
import com.example.layoutpractice.ui.SignUpActivity.SignUpActivity
import com.example.layoutpractice.databinding.ActivityMainBinding
import com.example.layoutpractice.model.Token
import com.example.layoutpractice.ui.ManagerActivity.ManagerActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityMainBinding
    lateinit var mainActivityViewModel: MainActivityViewModel

    val TAG:String = "로그"

    //뷰가 생성이 되었을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.etId.onTextChanged{
            mainActivityViewModel.updateValue(actionType = ActionType.EMAIL, it.toString())
        }
        binding.etPassword.onTextChanged {
            mainActivityViewModel.updateValue(actionType = ActionType.PASSWORD, it.toString())
        }

        mainActivityViewModel.status.observe(this, Observer {
            if (it == 1)
            {
                //매니저
                Log.d(TAG,"MainActivity - manager() called")
                val intent = Intent(this,ManagerActivity::class.java)
                startActivity(intent)

            }else if(it == 2){
                val intent = Intent(this, NavigationActivity::class.java)
                startActivity(intent)
            }
            else if(it != 0 ){
               // Log.d(TAG,"MainActivity - onCreate() called : $it")
                Toast.makeText(this,"잘못된 접근입니다" ,Toast.LENGTH_SHORT).show()
            }

        })
        binding.btLogin.setOnClickListener(this)
        binding.tvSignUp.setOnClickListener(this)

    }


    fun onLoginButtonClicked(){
        Log.d(TAG,"MainActivity - onLoginButtonClicked() called")



        val intent = Intent(this, NavigationActivity::class.java)
        startActivity(intent)

    }

    override fun onClick(view: View?) {
        when(view)
        {
            binding.btLogin ->
            {
                mainActivityViewModel.loginClick()
            }
            binding.tvSignUp ->
            {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}