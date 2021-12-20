package com.example.layoutpractice.ui.NavigationActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.layoutpractice.R
import com.example.layoutpractice.databinding.ActivityNavigationBinding


class NavigationActivity : AppCompatActivity() {
    lateinit var binding : ActivityNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 네비게이션을 담을 호스트를 가져온다
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바텀 네비게이션 뷰와 네비게이션뷰를 묶어준다
        NavigationUI.setupWithNavController(binding.nav, navController)



    }
}