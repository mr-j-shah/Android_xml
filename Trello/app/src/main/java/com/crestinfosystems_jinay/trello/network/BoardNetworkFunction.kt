package com.crestinfosystems_jinay.trello.network

import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.data.UserData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

val database = FirebaseFirestore.getInstance();
val realTimeDatabase = FirebaseDatabase.getInstance()
fun createNewBoard(board: Board, onTap: () -> Unit) {
    if (board.name != null) {
        database.collection("Boards").document(board.name.toString())
            .set(board, SetOptions.merge()).addOnSuccessListener {
                onTap()
            }
        realTimeDatabase.reference.child("Projects").child(board.name).setValue(board)
    }
}

suspend fun readUserAllUserOnApplication(): List<String>? {
    return suspendCancellableCoroutine { continuation ->
        val ref: CollectionReference = database.collection("Users")
        ref.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userListEmail: MutableList<String> = mutableListOf()
                    for (document in task.result!!) {
                        val userData = UserData.toObj(document.data)
                        userListEmail.add(userData.email!!)
                    }
                    continuation.resume(userListEmail)
                } else {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
    }
}

suspend fun readProjectsByUserEmail(email: String): List<Board>? {
    return suspendCancellableCoroutine { continuation ->
        val ref: CollectionReference = database.collection("Boards")
        ref.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userListEmail: MutableList<Board> = mutableListOf()
                    for (document in task.result!!) {
                        val boardData: MutableMap<String, Any> = document.data
                        if ((boardData["assignedTo"] as List<String>).contains(email)) {
                            userListEmail.add(Board.toObj(boardData))
                        }
                    }
                    continuation.resume(userListEmail)
                } else {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
    }
}

suspend fun readAllProjectsByUserEmail(email: String): List<Board>? {
    return suspendCancellableCoroutine { continuation ->
        val ref: CollectionReference = database.collection("Boards")
        ref.get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userListEmail: MutableList<Board> = mutableListOf()
                    for (document in task.result!!) {
                        val boardData: MutableMap<String, Any> = document.data
                        if ((boardData["assignedTo"] as List<String>).contains(email)) {
                            userListEmail.add(Board.toObj(boardData))
                        }
                        if (
                            (boardData["createdBy"] as String) == email
                        ) {
                            userListEmail.add(Board.toObj(boardData))
                        }
                    }
                    continuation.resume(userListEmail)
                } else {
                    continuation.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                continuation.resume(null)
            }
    }
}