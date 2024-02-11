package com.twproject.practice_uiux.view.mainpage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentMainPageBinding
import com.twproject.practice_uiux.view.detail.DetailPageActivity

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

        binding.btnMainPageGoDetail.setOnClickListener {
            val intent = Intent(mContext, DetailPageActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}