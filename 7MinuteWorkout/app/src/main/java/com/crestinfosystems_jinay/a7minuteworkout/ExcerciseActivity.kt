package com.crestinfosystems_jinay.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crestinfosystems_jinay.a7minuteworkout.databinding.ActivityExcerciseBinding
import com.crestinfosystems_jinay.a7minuteworkout.databinding.DialogCustomBackConfirmationBinding

class ExcerciseActivity : AppCompatActivity() {
    private var binding: ActivityExcerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var mediaPlayer: MediaPlayer? = null
    private var currentEcercise: Int = 0

    private lateinit var rvAdapter: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarExercise)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarExercise?.setNavigationOnClickListener {
            customDialogForBackButton(onpressed = {
                if (restTimer != null) {
                    restTimer?.cancel()
                    restProgress = 0
                }
                binding = null
                onBackPressed()
            })
        }
        setRestView()
        val layoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerViewAdapter?.layoutManager = layoutManager
        binding?.recyclerViewAdapter?.scrollToPosition(currentEcercise);
        rvAdapter = RvAdapter(constant, currentEcercise)
        binding?.recyclerViewAdapter?.adapter = rvAdapter
    }
    private fun customDialogForBackButton(onpressed: () -> Unit) {
        val customDialog = Dialog(this)
        var dialogBinding: DialogCustomBackConfirmationBinding? = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding?.root!!)
        dialogBinding?.dialogBtnYes?.setOnClickListener {
            onpressed() 
        }
        dialogBinding?.dialogBtnNo?.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding = null
    }

    private fun setRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.exerciseImageview?.visibility = View.INVISIBLE
        binding?.nextExerciseTitle?.visibility = View.VISIBLE
        binding?.nextExerciseTitle?.text = "Next Exercise ${constant[currentEcercise].name}"
        setRestProgressBar(2000)
    }

    private fun setRestProgressBar(startTimer: Long) {
        binding?.progressbar?.progress = restProgress
        binding?.progressbar?.max = (startTimer / 1000).toInt()
        restTimer = object : CountDownTimer(startTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressbar?.progress = (startTimer / 1000).toInt() - restProgress
                binding?.textViewTimer?.text =
                    ((startTimer / 1000).toInt() - restProgress).toString()

            }

            override fun onFinish() {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(this@ExcerciseActivity, R.raw.beep)
                mediaPlayer?.start()
                binding?.getReadyTitle?.text = constant[currentEcercise].name.toString()
                Toast.makeText(
                    this@ExcerciseActivity,
                    "Here now we will start doing ${constant[currentEcercise].name}",
                    Toast.LENGTH_SHORT
                ).show()
                setExerciseProgressBar(5000)
                binding?.nextExerciseTitle?.visibility = View.INVISIBLE
                binding?.exerciseImageview?.visibility = View.VISIBLE
                binding?.exerciseImageview?.setImageResource(constant[currentEcercise].image)
            }
        }.start()
    }

    private fun setExerciseProgressBar(startTimer: Long) {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.progressbar?.progress = restProgress
        binding?.progressbar?.max = (startTimer / 1000).toInt()
        restTimer = object : CountDownTimer(startTimer, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressbar?.progress = (startTimer / 1000).toInt() - restProgress
                binding?.textViewTimer?.text =
                    ((startTimer / 1000).toInt() - restProgress).toString()

            }

            override fun onFinish() {
                mediaPlayer?.release()
                mediaPlayer = MediaPlayer.create(this@ExcerciseActivity, R.raw.beep)
                mediaPlayer?.start()
                currentEcercise++
                rvAdapter = RvAdapter(constant, currentEcercise)
                binding?.recyclerViewAdapter?.adapter = rvAdapter
                binding?.getReadyTitle?.text = "TAKE REST"
                if (currentEcercise < constant.size) {
                    setRestView()
                } else {
                    if (restTimer != null) {
                        restTimer?.cancel()
                        restProgress = 0
                    }
                    val intent = Intent(this@ExcerciseActivity, ResultActivity::class.java)
                    startActivity(intent)
                    this@ExcerciseActivity.finish()
                }
                Toast.makeText(
                    this@ExcerciseActivity,
                    "Take a rest and Ready for next Exercise.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }
}