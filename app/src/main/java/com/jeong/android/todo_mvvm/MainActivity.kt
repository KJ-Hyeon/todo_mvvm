package com.jeong.android.todo_mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jeong.android.todo_mvvm.databinding.ActivityMainBinding
import com.jeong.android.todo_mvvm.ui.AfterFragment
import com.jeong.android.todo_mvvm.ui.BeforeFragment
import com.jeong.android.todo_mvvm.ui.MiddleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val pagerAdapter = PagerAdapter(this)
        val tabName = listOf<String>("시작 전", "진행 중", "완료")

        binding.viewpager.adapter = pagerAdapter
        // Tab과 Viewpager를 연결해줌
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = tabName[position]
        }.attach()
    }
    private inner class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BeforeFragment()
                1 -> MiddleFragment()
                else -> AfterFragment()
            }
        }
    }
}