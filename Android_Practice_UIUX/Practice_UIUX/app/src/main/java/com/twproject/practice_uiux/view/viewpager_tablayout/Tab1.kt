package com.twproject.practice_uiux.view.viewpager_tablayout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentTab1Binding
import com.twproject.practice_uiux.view.mainpage.MainActivity
import com.twproject.practice_uiux.view.mainpage.adapter.ImageListAdapter

class Tab1 : Fragment() {

    private lateinit var binding: FragmentTab1Binding
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
        binding = FragmentTab1Binding.inflate(inflater)

        setRcView()

        return binding.root
    }

    private fun setRcView() {
        val imgList = listOf(
            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
            ResourcesCompat.getDrawable(this.resources, R.drawable.refrigerator, null),
        )

        binding.rcFragmentMainImgList.layoutManager = LinearLayoutManager(mContext)
        binding.rcFragmentMainImgList.adapter = ImageListAdapter(mContext, imgList)
    }
}