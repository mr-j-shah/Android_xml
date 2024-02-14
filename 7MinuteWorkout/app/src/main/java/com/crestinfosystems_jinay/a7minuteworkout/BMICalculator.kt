package com.crestinfosystems_jinay.a7minuteworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityBmicalculatorBinding

class BMICalculator : AppCompatActivity() {

    private var binding: ActivityBmicalculatorBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.calculateButton?.setOnClickListener {
            var heightValue = 0.0
            var weightValue = 0.0

            if (binding?.editFieldHeight?.text.toString().isNotEmpty()) {
                heightValue = binding?.editFieldHeight?.text.toString().toDouble()
            }
            if (binding?.editFieldWeight?.text.toString().isNotEmpty()) {
                weightValue = binding?.editFieldWeight?.text.toString().toDouble()
            }
            if (weightValue > 0.0 && heightValue > 0.0) {
                var bmiValue = (weightValue / (heightValue * heightValue) * 10000)
                bmiValue = Math.round(bmiValue * 100.0) / 100.0
                binding?.BMIScoreTitle?.visibility = View.VISIBLE
                binding?.BMIScoreValue?.text = bmiValue.toString()
                binding?.BMIScoreValue?.visibility = View.VISIBLE
                binding?.BMIScoreComment?.text = calculateComment(bmiValue.toFloat())
                binding?.BMIScoreComment?.visibility = View.VISIBLE
                //TODO Need to add comment function based on price.
            } else {
                Toast.makeText(
                    this, "Please input Weight and Height Values greater than 0",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun calculateComment(score: Float): String {
        if (score < 16) {
            return "Severe Thinness"
        } else if (score >= 16 && score < 17) {
            return "Moderate Thinness"
        } else if (score >= 17 && score < 18.5) {
            return "Mild Thinness"
        } else if (score >= 18.5 && score < 25) {
            return "Normal"
        } else if (score >= 25 && score < 30) {
            return "Overweight"
        } else if (score >= 30 && score < 35) {
            return "Obese Class I"
        } else if (score >= 35 && score < 40) {
            return "Obese Class II"
        } else {
            return "Obese Class III"
        }
    }
}