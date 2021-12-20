package com.example.layoutpractice.ui.SignUpActivity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.layoutpractice.Utils.onTextChanged
import com.example.layoutpractice.databinding.ActivitySignUpBinding
import com.example.layoutpractice.network.RetrofitManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.URL

class SignUpActivity : AppCompatActivity(),View.OnClickListener{

    lateinit var binding : ActivitySignUpBinding
    lateinit var signUpViewModel: SignUpViewModel
    lateinit var url : URL


    val TAG:String = "로그"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
//        binding.etId.addTextChangedListener { onChanged() }

        signUpViewModel.id.observe(this, Observer {
            Log.d(TAG,"SignUpActivity - id 값 변경 : $it ")
        })
        signUpViewModel.name.observe(this, Observer {
            Log.d(TAG,"SignUpActivity - name 값 변경 : $it ")
        })
        signUpViewModel.password.observe(this, Observer {
            Log.d(TAG,"SignUpActivity - password 값 변경 : $it ")
        })

        signUpViewModel.status.observe(this, Observer {
            if(it.sucess == 201)
            {
                onBackPressed()
            }
        })

        binding.etName.onTextChanged {

            signUpViewModel.updateValue(actionType = ActionType.NAME, it.toString())

        }

        binding.etId.onTextChanged {

            signUpViewModel.updateValue(actionType = ActionType.ID, it.toString())

        }
        binding.etPassword.onTextChanged {

            signUpViewModel.updateValue(actionType = ActionType.PASSWORD, it.toString())

        }

        binding.btnCreate.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view)
        {
            binding.btnCreate ->
            {
                //var response = signUpViewModel.postUser();
                //Log.d(TAG,"SignUpActivity - onClick() called $response");

                //signUpViewModel.postUser()
                //var res = signUpViewModel.postUser();
                //Log.d(TAG,"res : ${res}")
                signUpViewModel.createUser()


            }

        }

    }


}
