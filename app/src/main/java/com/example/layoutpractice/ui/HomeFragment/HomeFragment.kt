package com.example.layoutpractice.ui.HomeFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.layoutpractice.R
import com.example.layoutpractice.Utils.SharedPrefManager
import com.example.layoutpractice.Utils.onTextChanged
import com.example.layoutpractice.databinding.FragmentHomeBinding
import com.example.layoutpractice.model.Token

class HomeFragment : Fragment() {

    private var mBinding : FragmentHomeBinding? = null  // -> optional
    lateinit var homeFragmentViewModel : HomeFragmentViewModel
    //mBinding = member 변수 Binding

    companion object{
        const val TAG:String = "로그"

        fun newInstace() : HomeFragment {
            return HomeFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"HomeFragment - onCreateView() called")
        val binding = FragmentHomeBinding.inflate(inflater,container,false)
        mBinding = binding
        homeFragmentViewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)




        return mBinding?.root



    }






    override fun onDestroy() {
        mBinding =null // 메모리에서 사라자기 전 바인딩도 날라가게 해
        Log.d(TAG,"HomeFragment - onDestroy() called")
        super.onDestroy() // 뷰가 사라지면 fragment가 메모리에서 사라짐
    }


}


