package com.crestinfosystems_jinay.kidscanvasdrawingapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.Image

import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private var drawingView: DrawingView? = null
    private var brushSizeButton: ImageButton? = null
    private var brushColorButton: ImageButton? = null
    private var clearButton: ImageButton? = null
    private var addBackgroundImageBtn: ImageButton? = null
    private var imageView: ImageView? = null

    private var mediaImageAcessLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Gallery Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "To add Background Image from Gallery need to Grant Permission.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_DENIED
        ) {
            mediaImageAcessLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
        }
        drawingView = findViewById(R.id.drawing_view)
        brushSizeButton = findViewById(R.id.ib_brush)
        brushSizeButton?.setOnClickListener {
            showBrushSizeDialog()
        }
        brushColorButton = findViewById(R.id.ib_color)
        brushColorButton?.setOnClickListener {
            changeBrushColor()
        }
        clearButton = findViewById(R.id.ib_undo)
        clearButton?.setOnClickListener {
            drawingView?.undoPaint()
        }
        addBackgroundImageBtn = findViewById(R.id.ib_gallary)
        addBackgroundImageBtn?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_MEDIA_IMAGES
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //TODO Add file selection function
                openImagePicker()
            } else {
                Toast.makeText(
                    this,
                    "Please allow to Access Image from Gallery from Permission",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        imageView = findViewById(R.id.iv_background)
        drawingView?.setSizeForBrush(20.toFloat())
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imageView?.setImageURI(selectedImageUri)
        }
    }

    private fun showBrushSizeDialog() {
        var brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush Size")
        brushDialog.show()
        val smallBtn = brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView?.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumBtn = brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener {
            drawingView?.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largeBtn = brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        largeBtn.setOnClickListener {
            drawingView?.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }

    }

    private fun changeBrushColor() {
        var brushColorDialog = Dialog(this)
        brushColorDialog.setContentView(R.layout.color_brush)
        brushColorDialog.show()
        val greenBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_green)
        greenBtn.setOnClickListener {
            drawingView?.setColor("#FF00FF00")
            brushColorDialog.dismiss()
        }
        val blackBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_black)
        blackBtn.setOnClickListener {
            drawingView?.setColor("#FF000000")
            brushColorDialog.dismiss()
        }
        val blueBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_blue)
        blueBtn.setOnClickListener {
            drawingView?.setColor("#FF0000FF")
            brushColorDialog.dismiss()
        }
        val pinkBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_pink)
        pinkBtn.setOnClickListener {
            drawingView?.setColor("#FFFF00FF")
            brushColorDialog.dismiss()
        }
        val redBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_red)
        redBtn.setOnClickListener {
            drawingView?.setColor("#FFFF0000")
            brushColorDialog.dismiss()
        }
        val yellowBtn = brushColorDialog.findViewById<ImageButton>(R.id.ib_color_yellow)
        yellowBtn.setOnClickListener {
            drawingView?.setColor("#FFFFFF00")
            brushColorDialog.dismiss()
        }
    }
}
