package com.crestinfosystems_jinay.trello.HomePage.subScreens

import MyBottomSheetFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crestinfosystems_jinay.trello.databinding.FragmentBoardBottomSheetBinding
import com.crestinfosystems_jinay.trello.databinding.FragmentScreen2Binding
import com.google.android.material.bottomsheet.BottomSheetDialog


class Screen_2 : Fragment() {
    var binding: FragmentScreen2Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentBoardBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(bottomSheetBinding.root)
        binding = FragmentScreen2Binding.inflate(inflater, container, false)
        binding?.fab?.setOnClickListener {

            dialog.show()
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}