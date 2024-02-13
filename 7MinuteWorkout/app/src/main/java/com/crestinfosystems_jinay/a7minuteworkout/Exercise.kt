package com.crestinfosystems_jinay.a7minuteworkout

data class Exercise(
    var id: Int,
    var name: String,
    var image: Int
)

val constant: MutableList<Exercise> = mutableListOf(
    Exercise(
        id = 1,
        name = "Abdominal Crunch",
        image = R.drawable.ic_abdominal_crunch
    ),
    Exercise(
        id = 2,
        name = "Jumping Jack",
        image = R.drawable.ic_jumping_jacks
    ),
    Exercise(
        id = 3,
        name = "Lunge",
        image = R.drawable.ic_lunge
    ),
    Exercise(
        id = 4,
        name = "High Knees Running in Place",
        image = R.drawable.ic_high_knees_running_in_place
    ),
    Exercise(
        id = 5,
        name = "Push up",
        image = R.drawable.ic_push_up
    ),

    Exercise(
        id = 6,
        name = "Push up and Rotation",
        image = R.drawable.ic_push_up_and_rotation
    ),
    Exercise(
        id = 7,
        name = "Plank",
        image = R.drawable.ic_plank
    ),
    Exercise(
        id = 8,
        name = "Side Plank",
        image = R.drawable.ic_side_plank
    ),
    Exercise(
        id = 9,
        name = "Squat",
        image = R.drawable.ic_squat
    ),
    Exercise(
        id = 10,
        name = "Step up onto Chair",
        image = R.drawable.ic_step_up_onto_chair
    ),
    Exercise(
        id = 11,
        name = "Triceps Dip on Chair",
        image = R.drawable.ic_triceps_dip_on_chair
    ),
    Exercise(
        id = 12,
        name = "High Knees Running in Place",
        image = R.drawable.ic_wall_sit
    ),
)
