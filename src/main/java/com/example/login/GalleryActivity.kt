package com.example.login

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        // Handle Home button click
        val homeButton: ImageButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            finish() // Go back to the previous screen
        }

    }
}
