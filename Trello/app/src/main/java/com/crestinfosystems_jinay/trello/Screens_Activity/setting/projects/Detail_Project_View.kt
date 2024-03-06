package com.crestinfosystems_jinay.trello.Screens_Activity.setting.projects

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.databinding.ActivityDetailProjectViewBinding
import com.crestinfosystems_jinay.trello.databinding.AddTaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Detail_Project_View : AppCompatActivity() {
    var binding: ActivityDetailProjectViewBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailProjectViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val board: Board = intent.getParcelableExtra("board") ?: Board(name = "Trello")
        binding?.toolbarExercise?.title = board.name
        setContentView(binding?.root)
        realTimeDataChange(board)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.floatingActionButton?.setOnClickListener {
            Dialogfunction()
        }
    }

    private fun realTimeDataChange(board: Board) {
        FirebaseDatabase.getInstance().reference.child("Projects").child(board.name)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue(false) as Map<*, *>
                    Log.d(
                        "Task List",
                        "Task List for ${board.name} :: ${value["task"].toString()}"
                    )
                    binding?.datafromdb?.text = value.toString()
                    println("Data from Firebase: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                    println("Error reading data: ${error.message}")
                }
            })
    }

    private fun Dialogfunction() {
//        var user = Firebase.auth.currentUser
//        var suggestions: List<String> = listOf()
        val bottomSheetBinding = AddTaskBottomSheetBinding.inflate(layoutInflater)
//        CoroutineScope(Dispatchers.IO).launch {
//            var data = readUserAllUserOnApplication()
//            Log.d("User Data", data.toString())
//            withContext(Dispatchers.Main) {
//                isDataFetched = true;
//                bottomSheetBinding.bottomBarContent.visibility = View.VISIBLE
//                if (data != null) {
//                    suggestions = data
//                }
//                suggestions -= user?.email!!
//                val customAdapter = activity?.let {
//                    AutoCompleteViewAd(it, suggestions) { selectedSuggestion ->
//                        // Handle the custom event when a suggestion is tapped
//                        Toast.makeText(it, "User: $selectedSuggestion", Toast.LENGTH_SHORT).show()
//                        if (!assignTo.contains(selectedSuggestion)) {
//                            addChip(selectedSuggestion, bottomSheetBinding)
//                        }
//                        bottomSheetBinding.autoCompleteTextView.setText("")
//                    }
//                }
//                bottomSheetBinding.autoCompleteTextView.setAdapter(customAdapter)
//            }
//        }
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetBinding.root)
//        bottomSheetBinding.updateBtn.setOnClickListener {
//            createNewBoard(
//                board = Board(
//                    name = bottomSheetBinding.textInputLayoutBoardnameEdit.text.toString(),
//                    des = bottomSheetBinding.textInputLayoutBoarddescEdit.text.toString(),
//                    createdBy = user?.email!!,
//                    assignedTo = assignTo as ArrayList<String>
//                )
//            ) {
//                binding?.progressCircular?.visibility = View.VISIBLE
//                dialog.dismiss()
//                fetchData()
//                Toast.makeText(activity, "Board Created Successfully", Toast.LENGTH_SHORT).show()
//            }
//        }

        dialog.show()
    }
}