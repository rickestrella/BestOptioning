package com.techpig.bestoptioning

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.title = "Best Optioning"

        val mainLogo = findViewById<ImageView>(R.id.mainLogo)
        val main_descriprion = findViewById<TextView>(R.id.main_descriprion)
        val addVehicleMainButton = findViewById<MaterialButton>(R.id.addVehicleMainButton)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        main_descriprion.text =
            Html.fromHtml("It's a way to help car buyers decide<br>which car to buy.<br>After the Required VEHICLE<br>and USAGE information of 2 or<br>more comparable vehicles has been<br>entered, this app will generate<br>numerical indicators for each car<br>to compare those values and reflect<br>which one could be your <b><i>best-choice</b>,<br><b><i>best-bet</b> and <b><i>BEST-OPTION!</b>.<br><br> <p style='text-align: center'><u>It's easier and it works!</p>")
        addVehicleMainButton.setOnClickListener {
            val i = Intent(this@HomeActivity, ContainerActivity::class.java)
            startActivity(i)
        }

        when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                homeLayout.setBackgroundColor(Color.parseColor("#000000"))
                mainLogo.setBackgroundResource(R.drawable.car_logo_night)
                main_descriprion.setTextColor(Color.parseColor("#FFFFFF"))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                homeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mainLogo.setBackgroundResource(R.drawable.car_logo_day)
                main_descriprion.setTextColor(Color.parseColor("#000000"))
            }
        }
    }
}