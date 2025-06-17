package com.example.login

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ImageDetailActivity : AppCompatActivity() {

    private lateinit var saveButton: ImageButton
    private var imageResId: Int = -1
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        val imageView: ImageView = findViewById(R.id.fullscreenImage)
        val closeButton: ImageButton = findViewById(R.id.closeButton)
        val downloadButton: ImageButton = findViewById(R.id.downloadButton)
        saveButton = findViewById(R.id.saveButton)

        imageResId = intent.getIntExtra("image_res_id", -1)
        if (imageResId != -1) {
            imageView.setImageResource(imageResId)
            isSaved = SavedProjectsManager.isSaved(imageResId)
            updateSaveButtonIcon()
        }

        closeButton.setOnClickListener {
            finish()
        }

        downloadButton.setOnClickListener {
            saveImageToGallery(imageView)
        }

        saveButton.setOnClickListener {
            isSaved = SavedProjectsManager.toggleSave(imageResId)
            val message = if (isSaved) "Saved to Projects" else "Removed from Projects"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            updateSaveButtonIcon()
        }
    }

    private fun updateSaveButtonIcon() {
        val iconRes = if (isSaved) R.drawable.save else R.drawable.save1
        saveButton.setImageResource(iconRes)
    }

    private fun saveImageToGallery(imageView: ImageView) {
        val drawable = imageView.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap ?: return

        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        val mimeType = "image/jpeg"
        val relativeLocation = "Pictures/YourAppFolder"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        if (uri != null) {
            resolver.openOutputStream(uri).use { stream ->
                if (stream == null) {
                    Toast.makeText(this, "Failed to get output stream", Toast.LENGTH_SHORT).show()
                    return
                }
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.clear()
                contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                resolver.update(uri, contentValues, null, null)
            }
        } else {
            Toast.makeText(this, "Failed to create MediaStore entry", Toast.LENGTH_SHORT).show()
        }
    }
}
