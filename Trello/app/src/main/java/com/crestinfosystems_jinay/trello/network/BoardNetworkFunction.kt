package com.crestinfosystems_jinay.trello.network

import com.crestinfosystems_jinay.trello.data.Board
import com.crestinfosystems_jinay.trello.data.UserData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

val database = FirebaseFirestore.getInstance();
fun createNewBoard(board: Board, onTap: () -> Unit) {
    if (board.name != null) {
        database.collection("Boards").document(board.name.toString())
            .set(board, SetOptions.merge()).addOnSuccessListener {
                onTap()
            }
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