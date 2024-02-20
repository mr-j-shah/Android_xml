package com.crestinfosystems_jinay.happyplaces.addPlaces

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.crestinfosystems_jinay.happyplaces.database.HappyPlaceDatabase
import com.crestinfosystems_jinay.happyplaces.databinding.ActivityAddPlacesBinding
import com.crestinfosystems_jinay.happyplaces.databinding.SelectImageDialogViewBinding
import com.crestinfosystems_jinay.happyplaces.model.HappyPlace
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.datepicker.MaterialDatePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.Calendar
import java.util.UUID


class AddPlaces : AppCompatActivity() {
    private var binding: ActivityAddPlacesBinding? = null
    private var selectedDate: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var position: Location
    private lateinit var imageUri: Uri;
    private var mHappyPlace: HappyPlace? = null

    val FROM_FILE_REQUEST = 2
    val CAMERA_REQUEST_CODE = 1
    val IMAGE_DIREC = "HappyPlacesImages"
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Image selection successful
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                binding?.imageSelected?.setImageURI(selectedImageUri)
                binding?.imageSelected?.visibility = View.VISIBLE
                binding?.imagePlaceholder?.visibility = View.GONE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddPlacesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .build()
        setSupportActionBar(binding?.applicationToolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.applicationToolbar?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.textEditFieldCalender?.setOnClickListener {
            showDatePicker()
        }
        binding?.textEditFieldLocation?.setOnClickListener {
            requestPermissions()
        }
        binding?.submitButton?.setOnClickListener {
            val happyPlaceDatabase = HappyPlaceDatabase(this, null)
            val place = HappyPlace(
                id = if (mHappyPlace == null) 0 else mHappyPlace!!.id,
                title = binding?.textEditFieldName?.text.toString(),
                description = binding?.textEditFieldDescription?.text.toString(),
                date = binding?.textEditFieldCalender?.text.toString(),
                location = binding?.textEditFieldLocation?.text.toString(),
                imageURL = imageUri.toString(),
            )
            if (happyPlaceDatabase.addPlace(place) > 0) {
                Toast.makeText(this@AddPlaces, "Place Added Successfully", Toast.LENGTH_SHORT)
                    .show()
                setResult(Activity.RESULT_OK)
                this@AddPlaces.finish()
            }
        }
        binding?.selectImageBtn?.setOnClickListener {
//            requestCameraPermissions()
            openDialog()
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Handle the selected date
                selectedDate = "$dayOfMonth-${month + 1}-$year"
                binding?.textEditFieldCalender?.setText(selectedDate)
                // You can perform any action with the selected date here
                // For example, update a TextView with the selected date
                // textView.text = selectedDate
            },
            currentYear,
            currentMonth,
            currentDay
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun requestCameraPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this) // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.CAMERA,
            ) // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        if (ActivityCompat.checkSelfPermission(
                                this@AddPlaces,
                                Manifest.permission.CAMERA
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(
                                applicationContext,
                                "Need Permission to fetch Location",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (intent.resolveActivity(packageManager) != null) {
                                // Start the camera activity
                                startActivityForResult(intent, CAMERA_REQUEST_CODE)
                            }

                        }

                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }

    @Suppress("DEPRECATION")
    private fun requestImageFromFilePermissions() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/*"
        startActivityForResult(intent, FROM_FILE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The image capture was successful

            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageUri = saveImageToInternalStorage(imageBitmap)
            Log.e("Saved Image", "Path :: ${imageUri}")
            binding?.imageSelected?.setImageBitmap(imageBitmap)
            binding?.imageSelected?.visibility = View.VISIBLE
            binding?.imagePlaceholder?.visibility = View.GONE
            // Process the captured image as needed
        }
        if (requestCode == FROM_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data!!.data
//            imageUri = copyImageToPrivateExternalStorage(selectedImageUri!!)!!
//            Log.e("Saved Image", "Path :: ${imageUri}")
//            try {
//                val sealectedImage =
//                    MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
//                imageUri = saveImageToInternalStorage(sealectedImage)
//                Log.e("Saved Image", "Path :: ${imageUri}")
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Toast.makeText(
//                    this@AddPlaces,
//                    "Failed to load the Image from Gallery!",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
            val contentURI = data.data
            try {
                // Here this is used to get an bitmap from URI
                @Suppress("DEPRECATION")
                val selectedImageBitmap =
                    MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)

                imageUri =
                    saveImageToInternalStorage(selectedImageBitmap)
                Log.e("Saved Image : ", "Path :: $imageUri")

                // Set the selected image from GALLERY to imageView.
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this@AddPlaces, "Failed!", Toast.LENGTH_SHORT)
                    .show()
            }
            binding?.imageSelected?.setImageURI(selectedImageUri)
            binding?.imageSelected?.visibility = View.VISIBLE
            binding?.imagePlaceholder?.visibility = View.GONE
        }
    }

    private fun requestPermissions() {
        // below line is use to request permission in the current activity.
        // this method is use to handle error in runtime permissions
        Dexter.withActivity(this) // below line is use to request the number of permissions which are required in our app.
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) // after adding permissions we are calling an with listener method.
            .withListener(object : MultiplePermissionsListener {

                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        if (ActivityCompat.checkSelfPermission(
                                this@AddPlaces,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this@AddPlaces,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            Toast.makeText(
                                applicationContext,
                                "Need Permission to fetch Location",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        fusedLocationClient.lastLocation
                            .addOnSuccessListener { location: Location? ->
                                if (location != null) {
                                    // Handle the fetched location (latitude, longitude, etc.)
                                    binding?.textEditFieldLocation?.setText("Lat :: ${location?.latitude}, Long :: ${location?.longitude}")
                                    position = location
                                    // Do something with the location data
                                } else {
                                    // Location is null, prompt the user to turn on location services
                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivity(intent)
                                }

                            }
                    }
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener { error: DexterError? ->
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }


    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AddPlaces)

        builder.setTitle("Need Permissions")

        // below line is our message for our dialog
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            // this method is called on click on positive button and on clicking shit button
            // we are redirecting our user from our app to the settings page of our app.
            dialog.cancel()
            // below is the intent from which we are redirecting our user.
            val intent: Intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.setData(uri)
            startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel") { dialog, which ->
            // this method is called when user click on negative button.
            dialog.cancel()
        }
        // below line is used to display our dialog
        builder.show()
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        val dialogBinding: SelectImageDialogViewBinding = SelectImageDialogViewBinding.inflate(
            layoutInflater
        )
        dialog.setContentView(dialogBinding.root)
        dialog.show()
        dialogBinding.dialogOptionCaptureFromCamera.setOnClickListener {
            requestCameraPermissions()
            dialog.dismiss()
        }
        dialogBinding.dialogOptionFromFile.setOnClickListener {
            requestImageFromFilePermissions()
            dialog.dismiss()
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIREC, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                this@AddPlaces,
                "Failed to store image locally",
                Toast.LENGTH_SHORT
            ).show()
        }
        return Uri.parse(file.absolutePath)
    }

    private fun copyImageToPrivateExternalStorage(selectedImageUri: Uri): Uri? {
        try {
            val inputStream: InputStream? = contentResolver.openInputStream(selectedImageUri)
            if (inputStream != null) {
                val wrapper = ContextWrapper(applicationContext)


                val outputDir = wrapper.getDir(IMAGE_DIREC, Context.MODE_PRIVATE)


                // Generate a unique filename using UUID
                val outputFile = File(outputDir, "${UUID.randomUUID()}.jpg")

                val outputStream: OutputStream = FileOutputStream(outputFile)
                inputStream.copyTo(outputStream)

                // Close the streams
                inputStream.close()
                outputStream.close()

                // Notify the system that a new file has been created
                MediaScannerConnection.scanFile(
                    applicationContext,
                    arrayOf(outputFile.absolutePath),
                    null
                ) { _, _ -> }

                // Return the Uri of the copied image in private external storage
                return Uri.parse(outputFile.absolutePath)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                this@AddPlaces,
                "Failed to copy image to private storage",
                Toast.LENGTH_SHORT
            ).show()
        }
        return null
    }


}