package com.bb.movieapi

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class VideoPlayer : YouTubeBaseActivity() {

    lateinit var ytbPlayer: YouTubePlayerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_video_player)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ytbPlayer = findViewById(R.id.youtubePlayer)
        ytbPlayer.initialize("AIzaSyBa8tqHrxlJlH1w2htWHHXhW6qd8-V_2m0",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubePlayer?,
                    p2: Boolean
                ) {
                    p1?.loadVideo(intent.extras!!.getString("id"), 1)
                    p1?.setFullscreen(true)
                }

                override fun onInitializationFailure(
                    p0: YouTubePlayer.Provider?,
                    p1: YouTubeInitializationResult?
                ) {
                    Log.i("youtube", "initialized Failed - ${p1?.name}")
                }


            })
    }

}