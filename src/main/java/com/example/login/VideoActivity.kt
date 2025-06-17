package com.example.login

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class VideoActivity : AppCompatActivity() {

    private val videoList = listOf(
        R.raw.video_1,
        R.raw.video_2,
        R.raw.video_3,
        R.raw.video_4
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val container = findViewById<LinearLayout>(R.id.videoContainer)

        for (videoRes in videoList) {
            val videoView = VideoView(this).apply {
                setVideoURI("android.resource://${packageName}/$videoRes".toUri())
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    600
                ).apply {
                    bottomMargin = 24
                }
                setOnPreparedListener { mediaPlayer ->
                    mediaPlayer.isLooping = true
                    val mediaController = MediaController(this@VideoActivity)
                    mediaController.setAnchorView(this)
                    this.setMediaController(mediaController)
                }
            }
            container.addView(videoView)
        }

        //  button click to go back to HomeActivity
        val homeButton = findViewById<ImageButton>(R.id.homeButton)
        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
