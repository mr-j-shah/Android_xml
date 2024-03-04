package com.crestinfosystems_jinay.trello.Screens_Activity.subScreens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crestinfosystems_jinay.trello.adapter.AutoCompleteViewAd
import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.databinding.FragmentBoardBottomSheetBinding
import com.crestinfosystems_jinay.trello.databinding.FragmentScreen2Binding
import com.crestinfosystems_jinay.trello.network.createNewBoard
import com.crestinfosystems_jinay.trello.network.readUserAllUserOnApplication
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Screen_2 : Fragment() {
    var binding: FragmentScreen2Binding? = null
    var assignTo: MutableList<String> = mutableListOf<String>()
    var isDataFetched: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreen2Binding.inflate(inflater, container, false)
        binding?.fab?.setOnClickListener {
            Dialogfunction()
        }
        return binding?.root
    }

    private fun Dialogfunction() {
        var user = Firebase.auth.currentUser
        var suggestions: List<String> = listOf()
        val bottomSheetBinding = FragmentBoardBottomSheetBinding.inflate(layoutInflater)
        CoroutineScope(Dispatchers.IO).launch {
            var data = readUserAllUserOnApplication()
            isDataFetched = false
            Log.d("User Data", data.toString())
            withContext(Dispatchers.Main) {
                isDataFetched = true;
                bottomSheetBinding.bottomBarContent.visibility = View.VISIBLE
                if (data != null) {
                    suggestions = data
                }
                suggestions -= user?.email!!
                val customAdapter = activity?.let {
                    AutoCompleteViewAd(it, suggestions) { selectedSuggestion ->
                        // Handle the custom event when a suggestion is tapped
                        Toast.makeText(it, "User: $selectedSuggestion", Toast.LENGTH_SHORT).show()
                        if (!assignTo.contains(selectedSuggestion)) {
                            addChip(selectedSuggestion, bottomSheetBinding)
                        }
                        bottomSheetBinding.autoCompleteTextView.setText("")
                    }
                }
                bottomSheetBinding.autoCompleteTextView.setAdapter(customAdapter)
            }
        }
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.updateBtn.setOnClickListener {
            createNewBoard(
                board = Board(
                    name = bottomSheetBinding.textInputLayoutBoardnameEdit.text.toString(),
                    des = bottomSheetBinding.textInputLayoutBoarddescEdit.text.toString(),
                    createdBy = user?.email!!,
                    assignedTo = assignTo as ArrayList<String>
                )
            ) {
                dialog.dismiss()
                Toast.makeText(activity, "Board Created Successfully", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    fun addChip(text: String, bottomSheetBinding: FragmentBoardBottomSheetBinding) {
        assignTo.add(text)
        val chip = Chip(bottomSheetBinding.chipGroup.context)
        chip.text = text
        chip.isClickable = true
        chip.isCloseIconVisible = true
        // Handle chip removal
        chip.setOnCloseIconClickListener {
            bottomSheetBinding.chipGroup.removeView(chip)
        }
        // Add chip to the ChipGroup
        bottomSheetBinding.chipGroup.addView(chip)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}