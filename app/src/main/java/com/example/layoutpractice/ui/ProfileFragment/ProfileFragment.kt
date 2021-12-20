package com.example.layoutpractice.ui.ProfileFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.layoutpractice.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var mBinding : FragmentProfileBinding? = null

    companion object{
        const val TAG:String = "로그"

        fun newInstace() : ProfileFragment {
            return ProfileFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"ProfileFragment - onCreateView() called")
        val binding = FragmentProfileBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root

    }

    override fun onDestroy() {
        mBinding =null // 바인딩도 날라가게 해
        Log.d(TAG,"ProfileFragment - onDestroy() called")
        super.onDestroy() // 뷰가 사라지면 fragment가 메모리에서 사라짐
    }


}