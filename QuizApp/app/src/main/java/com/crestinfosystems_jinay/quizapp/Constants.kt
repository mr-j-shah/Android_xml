package com.crestinfosystems_jinay.quizapp

object Constants {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                id = 0,
                question = "What country does this flag belong to?",
                option1 = "India",
                option2 = "Germany",
                option3 = "Australia",
                option4 = "New Zealand",
                correctOption = 0,
                image = R.drawable.ic_flag_of_india
            ),
            Question(
                id = 0,
                question = "What country does this flag belong to?",
                option1 = "India",
                option2 = "Germany",
                option3 = "Australia",
                option4 = "New Zealand",
                correctOption = 1,
                image = R.drawable.ic_flag_of_germany
            ),
            Question(
                id = 0,
                question = "What country does this flag belong to?",
                option1 = "India",
                option2 = "Germany",
                option3 = "Australia",
                option4 = "New Zealand",
                correctOption = 2,
                image = R.drawable.ic_flag_of_australia
            ),
            Question(
                id = 0,
                question = "What country does this flag belong to?",
                option1 = "India",
                option2 = "Germany",
                option3 = "Australia",
                option4 = "New Zealand",
                correctOption = 3,
                image = R.drawable.ic_flag_of_new_zealand
            )
        )
    }
}