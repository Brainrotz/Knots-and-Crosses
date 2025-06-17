package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.login.com.example.login.ImageAdapter

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView: RecyclerView = findViewById(R.id.imageGrid)

        val imageList = listOf(
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_6,
            R.drawable.img_7, R.drawable.img_8, R.drawable.img_9,
            R.drawable.img_10, R.drawable.img_11, R.drawable.img_12,
            R.drawable.img_13, R.drawable.img_14, R.drawable.img_15,
            R.drawable.img_16, R.drawable.img_17, R.drawable.img_18,
            R.drawable.img_19, R.drawable.img_20, R.drawable.img_21, R.drawable.img_22
        ).shuffled()

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ImageAdapter(imageList) { imageResId ->
            val intent = Intent(this, ImageDetailActivity::class.java)
            intent.putExtra("image_res_id", imageResId)
            startActivity(intent)
        }

        val playButton: ImageView = findViewById(R.id.nav_play)
        playButton.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }


        val profileButton: ImageView = findViewById(R.id.top_nav_icon)
        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileSettingsActivity::class.java)
            startActivity(intent)
        }

        // ❤️ Heart menu popup
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
            val yOffset = -popupHeight - 40

            popupWindow.showAtLocation(heartButton, 0, location[0] + xOffset, location[1] + yOffset)

            popupView.findViewById<TextView>(R.id.savedProjects).setOnClickListener {
                popupWindow.dismiss()
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
