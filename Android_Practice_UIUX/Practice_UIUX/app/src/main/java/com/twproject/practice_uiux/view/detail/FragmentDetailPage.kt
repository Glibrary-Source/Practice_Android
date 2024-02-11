package com.twproject.practice_uiux.view.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentDetailPageBinding
import com.twproject.practice_uiux.view.MyGlobals
import com.twproject.practice_uiux.view.mainpage.MainActivity


class FragmentDetailPage : Fragment() {

    private lateinit var binding: FragmentDetailPageBinding
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
        binding = FragmentDetailPageBinding.inflate(inflater)


        // Inflate the layout for this fragment
        return binding.root
    }

}