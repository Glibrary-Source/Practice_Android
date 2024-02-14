package com.twproject.practice_uiux.view.viewpager_tablayout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentMainPageBinding
import com.twproject.practice_uiux.view.mainpage.MainActivity

class FragmentMainPage : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private lateinit var mContext: Context
    private lateinit var activity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mContext = context
        activity = mContext as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPagerTabLayout()
    }

    private fun viewPagerTabLayout() {
        val tabTextList = listOf("Tab1", "Tab2")
        val tabIconList = listOf(R.drawable.icon_list_black, R.drawable.icon_email_black)

        val viewPager = binding.viewpager2
        val tabLayout = binding.tab

        viewPager.adapter = ViewPagerAdapter(activity)

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = tabTextList[position]
            tab.setIcon(tabIconList[position])
        }.attach()
    }
}