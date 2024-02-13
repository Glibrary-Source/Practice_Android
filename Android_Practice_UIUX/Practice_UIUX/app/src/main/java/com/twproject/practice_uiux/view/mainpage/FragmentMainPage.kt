package com.twproject.practice_uiux.view.mainpage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentMainPageBinding
import com.twproject.practice_uiux.view.mainpage.adapter.ImageListAdapter
import com.twproject.practice_uiux.view.viewpager.ViewPagerAdapter

class FragmentMainPage : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private lateinit var mContext: Context
    private lateinit var activity: MainActivity

    private val tabTextList = listOf("Tab1", "Tab2")
    private val tabIconList = listOf(R.drawable.icon_list_black, R.drawable.icon_email_black)

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


        binding.viewpager2.adapter = ViewPagerAdapter(activity)

        TabLayoutMediator(binding.tab, binding.viewpager2){ tab, pos ->
            tab.text = tabTextList[pos]
        }.attach()


//        setRcView()

        return binding.root
    }

//    private fun setRcView() {
//        val imgList = listOf(
//            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
//            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
//            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
//            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
//            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
//        )
//
//        binding.rcFragmentMainImgList.layoutManager = LinearLayoutManager(mContext)
//        binding.rcFragmentMainImgList.adapter = ImageListAdapter(mContext, imgList)
//    }
}