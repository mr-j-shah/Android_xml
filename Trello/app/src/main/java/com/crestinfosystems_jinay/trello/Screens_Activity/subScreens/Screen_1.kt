package com.crestinfosystems_jinay.trello.Screens_Activity.subScreens

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.crestinfosystems_jinay.trello.data.State
import com.crestinfosystems_jinay.trello.data.Task
import com.crestinfosystems_jinay.trello.databinding.FragmentScreen1Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Screen_1 : Fragment() {

    var lastUpdateTask: Task? = null
    var binding: FragmentScreen1Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScreen1Binding.inflate(inflater, container, false)
        getLastEditTask()
        return binding?.root
    }

    fun jsonStringToMap(jsonString: String): Map<String, Any> {
        val type = object : TypeToken<Map<String, Any>>() {}.type
        return Gson().fromJson(jsonString, type) ?: emptyMap()
    }

    private fun getLastEditTask() {
        val applicationContext: Context = requireContext()
        val preferences =
            applicationContext.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val lastedit = preferences.getString("lastedit", "")
        val resultMap: Map<String, Any> = jsonStringToMap(lastedit!!)
        lastUpdateTask = Task(resultMap)
        binding?.lastEditBy?.boardTitle?.text = lastUpdateTask?.title
        binding?.lastEditBy?.boardDesc?.text = lastUpdateTask?.desc
        binding?.lastEditBy?.lastEditBy?.visibility = View.GONE
        binding?.lastEditBy?.state?.text = lastUpdateTask?.state.toString().uppercase()
        when (lastUpdateTask?.state) {
            State.todo -> binding?.lastEditBy?.projectCard?.setCardBackgroundColor(
                Color.parseColor(
                    "#FFEB3B"
                )
            )

            State.doing -> binding?.lastEditBy?.projectCard?.setCardBackgroundColor(
                Color.parseColor(
                    "#4CAF50"
                )
            )

            State.done -> binding?.lastEditBy?.projectCard?.setCardBackgroundColor(
                Color.parseColor(
                    "#B0BEC5"
                )
            )

            else -> {}
        }
    }

}