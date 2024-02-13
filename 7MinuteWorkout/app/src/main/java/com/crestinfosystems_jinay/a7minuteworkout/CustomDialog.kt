package com.crestinfosystems_jinay.a7minuteworkout

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button

class CustomDialog(context: Context, onConfirm: () -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_custom_back_confirmation)

        val yesButton: Button = findViewById(R.id.dialog_btn_yes)
        val noButton: Button = findViewById(R.id.dialog_btn_no)

        yesButton.setOnClickListener {

        }
        noButton.setOnClickListener {
            dismiss()
        }

    }

}
