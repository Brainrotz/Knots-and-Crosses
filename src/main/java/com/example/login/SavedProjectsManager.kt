package com.example.login

object SavedProjectsManager {
    private val savedImages = mutableSetOf<Int>()

    fun isSaved(resId: Int): Boolean = savedImages.contains(resId)

    fun toggleSave(resId: Int): Boolean {
        return if (savedImages.contains(resId)) {
            savedImages.remove(resId)
            false
        } else {
            savedImages.add(resId)
            true
        }
    }

    fun getSavedImages(): List<Int> = savedImages.toList()
}
