package com.example.layoutpractice.ui.ManagerActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.layoutpractice.R
import com.example.layoutpractice.Utils.API
import com.example.layoutpractice.Utils.SharedPrefManager
import com.example.layoutpractice.databinding.ActivityManagerBinding
import com.example.layoutpractice.model.User
import com.example.layoutpractice.network.RetroInstance
import com.example.layoutpractice.network.RetroService
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import java.nio.charset.Charset
import java.text.Normalizer

class ManagerActivity : AppCompatActivity(), ManagerActivityAdapter.AdapterCallback,
    View.OnClickListener {
    lateinit var binding: ActivityManagerBinding
    lateinit var managerActivityViewModel: ManagerActivityViewModel
    private var managerToken: String? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: ManagerActivityAdapter? = null
    private var user: ArrayList<User>? = ArrayList()

    var compositeDisposable = CompositeDisposable()
    var userList = ArrayList<User>()

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managerToken = SharedPrefManager.getToken()?.access_token
        recyclerView = binding.recyclerview

        managerActivityViewModel = ViewModelProvider(this).get(ManagerActivityViewModel::class.java)


        managerToken?.let { getUserList(it) }


        managerActivityViewModel.userListListData?.observe(this, Observer {


            adapter = ManagerActivityAdapter(this, it, this)
            recyclerView!!.layoutManager = LinearLayoutManager(this)
            recyclerView!!.adapter = adapter
        })

        binding.btnDeleteUser.setOnClickListener(this)


    }


    fun getUserList(accessToken: String) {

        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)

        compositeDisposable.add(retroInstance.getUserList(API.str + accessToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    //managerActivityViewModel.getUserListResponse(it.body())
                    //Log.d(TAG,"it - $it")
                    //  managerActivityViewModel.getUserListResponse(it.body().toString())
                    //  BufferedSource source = responseBody.source();

                    var source: BufferedSource = it.source()
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    var buffer: Buffer = source.buffer
                    var responseBodyString: String =
                        buffer.clone().readString(Charset.forName("UTF-8"))
                    Log.d("TAG", responseBodyString);
                    managerActivityViewModel.getUserListResponse(responseBodyString)

                },
                {

                }

            ))


    }

//    fun deleteUserList(accessToken: String)
//    {
//        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
//
////        var requestBody: RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
////            .addFormDataPart("email", _email.value.toString())
////            .addFormDataPart("password", _password.value.toString()).build()
//
//        var requestBody : ArrayList<ResponseBody>? = null;
//        requestBody
//        compositeDisposable.add(retroInstance.deleteUserList(API.str + accessToken, ))
//    }

    override fun onItemClicked(user: ArrayList<User>) {
        Log.d(TAG,"map - $user")
        this.user = user
    }

    override fun onClick(view: View?) {
        when (view) {

            binding.btnDeleteUser -> {

                var accessToken : String = SharedPrefManager.getToken()!!.access_token
                user?.let { managerActivityViewModel.deleteUser(accessToken, user!!) }
            }
            binding.btnAddRole -> {}
            binding.btnDummy3 -> {

            }
        }
    }
}