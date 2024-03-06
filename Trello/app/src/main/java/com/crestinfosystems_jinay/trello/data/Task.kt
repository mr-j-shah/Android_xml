package com.crestinfosystems_jinay.trello.data

data class Task(
    var title: String = "",
    var desc: String = "",
    var lastEdit: String = "",
    var state: State = State.todo
) {
    fun toMap(): Map<String, Any> {
        return mapOf<String, Any>(
            "title" to title,
            "desc" to desc,
            "lastEdit" to lastEdit,
            "state" to state.toString()
        )
    }


    companion object {
        fun toObj(data: Map<String, Any>): Task {
            return Task(
                title = data["title"].toString() ?: "",
                desc = data["desc"].toString() ?: "",
                lastEdit = data["lastEdit"].toString() ?: "",
                state =  State.valueOf(data["state"].toString())
            )
        }
    }
}

enum class State { todo, doing, done }