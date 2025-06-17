package com.example.login

import android.content.Context
import android.content.SharedPreferences

object SavedImagesManager {

    private const val PREFS_NAME = "saved_images_prefs"
    private const val KEY_SAVED_IMAGES = "saved_images"

    private lateinit var prefs: SharedPreferences

    // Initialize SharedPreferences (call once in Application or Activity)
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Save an image ID
    fun saveImage(imageId: Int) {
        val savedSet = prefs.getStringSet(KEY_SAVED_IMAGES, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        savedSet.add(imageId.toString())
        prefs.edit().putStringSet(KEY_SAVED_IMAGES, savedSet).apply()
    }

    // Remove an image ID
    fun removeImage(imageId: Int) {
        val savedSet = prefs.getStringSet(KEY_SAVED_IMAGES, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        if (savedSet.contains(imageId.toString())) {
            savedSet.remove(imageId.toString())
            prefs.edit().putStringSet(KEY_SAVED_IMAGES, savedSet).apply()
        }
    }

    // Get all saved image IDs as a List<Int>
    fun getSavedImages(): List<Int> {
        val savedSet = prefs.getStringSet(KEY_SAVED_IMAGES, emptySet()) ?: emptySet()
        return savedSet.mapNotNull {
            try {
                it.toInt()
            } catch (e: NumberFormatException) {
                null
            }
        }
    }

    // Check if an image is saved
    fun isImageSaved(imageId: Int): Boolean {
        val savedSet = prefs.getStringSet(KEY_SAVED_IMAGES, emptySet()) ?: emptySet()
        return savedSet.contains(imageId.toString())
    }
}
