package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.com.example.login.ImageAdapter

class SavedProjectsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_projects)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val savedImages = SavedProjectsManager.getSavedImages()

        adapter = ImageAdapter(savedImages) { imageResId ->

        }

        recyclerView.adapter = adapter

        // Home button navigation
        val homeButton: ImageButton = findViewById(R.id.nav_home)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
// Heart menu popup
        val heartButton: ImageView = findViewById(R.id.nav_love)
        heartButton.setOnClickListener {
            val inflater = LayoutInflater.from(this)
            val parentViewGroup = heartButton.parent as? ViewGroup
            val popupView = inflater.inflate(R.layout.popup_menu, parentViewGroup, false)


            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)


            val popupWidth = (popupView.measuredWidth * 1.3).toInt()
            val popupHeight = (popupView.measuredHeight * 1.3).toInt()

            val popupWindow = PopupWindow(
                popupView,
                popupWidth,
                popupHeight,
                true
            )
            popupWindow.elevation = 10f

            val location = IntArray(2)
            heartButton.getLocationOnScreen(location)

            val xOffset = heartButton.width / 2 - popupWidth / 2
            val yOffset = -popupHeight - 40 // move popup higher

            popupWindow.showAtLocation(heartButton, 0, location[0] + xOffset, location[1] + yOffset)

            // Click listeners
            popupView.findViewById<TextView>(R.id.savedProjects).setOnClickListener {
                popupWindow.dismiss()
                // Launch SavedProjectsActivity
                startActivity(Intent(this, SavedProjectsActivity::class.java))
            }


            popupView.findViewById<TextView>(R.id.uploadProject).setOnClickListener {
                popupWindow.dismiss()
                startActivity(Intent(this, UploadProjectActivity::class.java))
            }

            popupView.findViewById<TextView>(R.id.openGallery).setOnClickListener {
                popupWindow.dismiss()
                startActivity(Intent(this, GalleryActivity::class.java))
            }
        }
    }
}
