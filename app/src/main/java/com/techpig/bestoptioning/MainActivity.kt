package com.techpig.bestoptioning

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var fromTop: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<LinearLayout>(R.id.mainTitle)
        val title = findViewById<ImageView>(R.id.titleIV)
        val subtitle = findViewById<ImageView>(R.id.subtitleIV)
        val techpigTV = findViewById<TextView>(R.id.techpigTV)
        val mainLayout = findViewById<RelativeLayout>(R.id.mainLayout)

        when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                mainLayout.setBackgroundColor(Color.parseColor("#000000"))
                container.setPadding(8, 8, 8, 8)
                title.setBackgroundResource(R.drawable.title_night)
                title.scaleType = ImageView.ScaleType.CENTER_INSIDE
                subtitle.setBackgroundResource(R.drawable.subtitle_night)
                subtitle.scaleType = ImageView.ScaleType.CENTER_INSIDE
                subtitle.maxHeight = 15
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                mainLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                container.setPadding(8, 8, 8, 8)
                title.setBackgroundResource(R.drawable.title_day)
                title.scaleType = ImageView.ScaleType.CENTER_INSIDE
                subtitle.setBackgroundResource(R.drawable.subtitle_day)
                subtitle.scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }

        fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top)
        title.animation = fromTop

        val fromRight: Animation = AnimationUtils.loadAnimation(this, R.anim.from_right)
        subtitle.animation = fromRight

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        techpigTV.animation = fadeIn

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
        }, 5000)
    }
}