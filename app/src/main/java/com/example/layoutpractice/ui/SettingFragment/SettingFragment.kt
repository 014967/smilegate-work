package com.example.layoutpractice.ui.SettingFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.layoutpractice.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var mBinding : FragmentSettingBinding? = null

    companion object{
        const val TAG:String = "로그"

        fun newInstace() : SettingFragment {
            return SettingFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG,"SettingFragment - onCreateView() called")
        val binding = FragmentSettingBinding.inflate(inflater,container,false)
        mBinding = binding
        return mBinding?.root

    }

    override fun onDestroy() {
        mBinding =null // 바인딩도 날라가게 해
        Log.d(TAG,"SettingFragment - onDestroy() called")
        super.onDestroy() // 뷰가 사라지면 fragment가 메모리에서 사라짐
    }


}