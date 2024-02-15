package com.twproject.practice_uiux.view.component_bottom_modal_sheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentPersistentBottomSheetBinding

class FragmentPersistentBottomSheet : Fragment() {
    private lateinit var binding: FragmentPersistentBottomSheetBinding
    private lateinit var behavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersistentBottomSheetBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        persistentBottomSheetEvent()

        binding.btnCollapsed.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        binding.btnExpanded.setOnClickListener {
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun persistentBottomSheetEvent() {
        behavior = BottomSheetBehavior.from(binding.persistentBottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 되는 도중 계속 호출
                // called continuously while dragging
                Log.d(TAG, "onStateChanged: 드래그 중")
            }
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_COLLAPSED-> {
                        Log.d(TAG, "onStateChanged: 접음")
                    }
                    BottomSheetBehavior.STATE_DRAGGING-> {
                        Log.d(TAG, "onStateChanged: 드래그")
                    }
                    BottomSheetBehavior.STATE_EXPANDED-> {
                        Log.d(TAG, "onStateChanged: 펼침")
                    }
                    BottomSheetBehavior.STATE_HIDDEN-> {
                        Log.d(TAG, "onStateChanged: 숨기기")
                    }
                    BottomSheetBehavior.STATE_SETTLING-> {
                        Log.d(TAG, "onStateChanged: 고정됨")
                    }
                }
            }
        })
    }

    private val TAG = "PersistentActivity"
}