package com.example.login

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class UploadProjectActivity : AppCompatActivity() {

    private lateinit var imageUploadView: ImageView
    private lateinit var homeButton: ImageButton
    private lateinit var saveButton: Button

    // Registers the image picker launcher
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUploadView.setImageURI(it)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_project)

        imageUploadView = findViewById(R.id.imageUploadView)
        homeButton = findViewById(R.id.homeButton)
        saveButton = findViewById(R.id.saveButton)

        val titleEditText = findViewById<EditText>(R.id.projectTitle)
        val resourcesEditText = findViewById<EditText>(R.id.resourcesNeeded)
        val tutorialEditText = findViewById<EditText>(R.id.writtenTutorial)

        var selectedImageUri: Uri? = null

        imageUploadView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        homeButton.setOnClickListener {
            finish()
        }

        // Save selected image URI

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val resources = resourcesEditText.text.toString()
            val tutorial = tutorialEditText.text.toString()

            if (title.isEmpty() || selectedImageUri == null) {
                Toast.makeText(
                    this,
                    "Please upload an image and fill out the title",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Send data to GalleryActivity
            val intent = Intent(this, GalleryActivity::class.java).apply {
                putExtra("imageUri", selectedImageUri.toString())
                putExtra("title", title)
                putExtra("resources", resources)
                putExtra("tutorial", tutorial)
            }

            startActivity(intent)
            Toast.makeText(this, "Project saved successfully!", Toast.LENGTH_SHORT).show()
        }
    }
}
