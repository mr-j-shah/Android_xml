package com.crestinfosystems_jinay.trello.Screens_Activity.setting.projects

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.trello.adapter.TaskAdapter
import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.data.State
import com.crestinfosystems_jinay.trello.data.Task
import com.crestinfosystems_jinay.trello.databinding.ActivityDetailProjectViewBinding
import com.crestinfosystems_jinay.trello.databinding.AddTaskBottomSheetBinding
import com.crestinfosystems_jinay.trello.network.addNewTask
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Detail_Project_View : AppCompatActivity() {
    var list = mutableListOf<Task>()
    var binding: ActivityDetailProjectViewBinding? = null
    var baoardAdapter = TaskAdapter(
        arrayListOf()
    )

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
            Dialogfunction(board)
        }
        setAdpater()
    }

    private fun setAdpater() {
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerViewAdapter?.layoutManager = layoutManager
        binding?.recyclerViewAdapter?.adapter = baoardAdapter
    }

    private fun realTimeDataChange(board: Board) {
        FirebaseDatabase.getInstance().reference.child("Projects").child(board.name)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    val value = snapshot.getValue(false) as Map<*, *>
                    if (value["task"] != null) {
                        val taskList = value["task"] as Map<*, *>
                        for ((key, value) in taskList) {
                            Log.d(
                                "Task List",
                                "Task List for ${key} :: ${
                                    Task.toObj(value as Map<String, Any>)
                                }"
                            )
                            list.add(Task.toObj(value as Map<String, Any>))
                        }
                    }
                    baoardAdapter.submitList(list as ArrayList<Task>)
                    println("Data from Firebase: $value")
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle errors
                    println("Error reading data: ${error.message}")
                }
            })
    }

    private fun Dialogfunction(board: Board) {
        var isCorrectName = false
        var isCorrectDes = false
        var user = Firebase.auth.currentUser
        val bottomSheetBinding = AddTaskBottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.textInputLayoutBoardnameEdit.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 2) {
                    bottomSheetBinding.textInputLayoutBoardnameEdit.error = null
                    isCorrectName = true
                } else {
                    bottomSheetBinding.textInputLayoutBoardnameEdit.error =
                        "Enter Correct Task Name"
                    isCorrectName = false
                }
            }
        })
        bottomSheetBinding.textInputLayoutBoarddescEdit.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    bottomSheetBinding.textInputLayoutBoarddescEdit.error = null
                    isCorrectDes = true
                } else {
                    bottomSheetBinding.textInputLayoutBoarddescEdit.error =
                        "Add Description"

                    isCorrectDes = false
                }
            }
        })
        bottomSheetBinding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        bottomSheetBinding.addBtn.setOnClickListener {
            if (isCorrectName && isCorrectDes) {
                addNewTask(
                    task = Task(
                        title = bottomSheetBinding.textInputLayoutBoardnameEdit.text.toString(),
                        desc = bottomSheetBinding.textInputLayoutBoarddescEdit.text.toString(),
                        lastEdit = user?.email.toString(),
                        state = State.todo
                    ),
                    board = Board(
                        name = board.name,
                        des = board.des,
                        createdBy = board.createdBy,
                        assignedTo = board.assignedTo
                    )
                ) {
                    dialog.dismiss()
                    Toast.makeText(this, "Task Created Successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Fill Data Properly", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }
}