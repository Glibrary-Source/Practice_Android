package com.twproject.practice_uiux.view.component_bottom_modal_sheet

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.twproject.practice_uiux.R
import com.twproject.practice_uiux.databinding.FragmentBottomSheetBinding
import com.twproject.practice_uiux.view.mainpage.MainActivity


class FragmentBottomSheet : Fragment() {

    private lateinit var binding: FragmentBottomSheetBinding
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
        binding = FragmentBottomSheetBinding.inflate(inflater)


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        modalBottomSheet()
        modalBottomSheetTransparent()
    }

    private fun modalBottomSheet() {
        binding.btnShowBottomSheet.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }
    }

    private fun modalBottomSheetTransparent() {
        binding.btnShowBottomSheet.setOnClickListener {
            val modal = BottomSheetTopTransParentDialog()
            modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.TransParentBottomSheetDialogTheme)
            modal.show(childFragmentManager, modal.tag)
        }
    }

}