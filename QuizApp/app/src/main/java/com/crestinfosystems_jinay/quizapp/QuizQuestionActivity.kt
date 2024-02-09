package com.crestinfosystems_jinay.quizapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

class QuizQuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var progressBar: ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var questionImage: ImageView? = null

    private var tvOption1: TextView? = null
    private var tvOption2: TextView? = null
    private var tvOption3: TextView? = null
    private var tvOption4: TextView? = null
    private var buttonSubmit: Button? = null

    val quizQuestionList = Constants.getQuestions()
    val currentQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        questionImage = findViewById(R.id.iv_image)

        tvOption1 = findViewById(R.id.tv_option_one)
        tvOption2 = findViewById(R.id.tv_option_two)
        tvOption3 = findViewById(R.id.tv_option_three)
        tvOption4 = findViewById(R.id.tv_option_four)
        buttonSubmit = findViewById(R.id.btn_submit)
        setQuestion(0)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)
        val questionList = Constants.getQuestions()

    }

    @SuppressLint("SetTextI18n")
    fun setQuestion(index: Int) {
        val question: Question =
            quizQuestionList[currentQuestion - 1] // Getting the question from the list with the help of current position.
//        defaultOptionsView()

        if (currentQuestion == quizQuestionList.size) {
            buttonSubmit?.text = "FINISH"
        } else {
            buttonSubmit?.text = "SUBMIT"
        }
        // END
        progressBar?.progress =
            currentQuestion // Setting the current progress in the progressbar using the position of question
        tvProgress?.text =
            "$currentQuestion" + "/" + progressBar?.max // Setting up the progress text

        // Now set the current question and the options in the UI
        tvQuestion?.text = question.question
        questionImage?.setImageResource(question.image)
        tvOption1?.text = question.option1
        tvOption2?.text = question.option2
        tvOption3?.text = question.option3
        tvOption4?.text = question.option4

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}