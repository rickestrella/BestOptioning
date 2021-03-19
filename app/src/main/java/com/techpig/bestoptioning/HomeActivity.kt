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
            Html.fromHtml("I'ts a way to help car buyers decide which car to buy.<br>After the Required VEHICLE and USAGE information of 2 or more comparable vehicles has been entered, this app will generate numerical indicators for each car to compare their values and reflect which one could be your <b><i>best-choice</b>, <b><i>best-bet</b> and <b><i>BEST-OPTION</b>.<br><br> <p style='text-align: center'><u>It's easier and it works!</p>")
        addVehicleMainButton.setOnClickListener {
            val i = Intent(this@HomeActivity, ContainerActivity::class.java)
            startActivity(i)
        }

        when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                homeLayout.setBackgroundColor(Color.parseColor("#000000"))
                mainLogo.setBackgroundResource(R.drawable.logo_night)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                homeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mainLogo.setBackgroundResource(R.drawable.logo_day)
            }
        }
    }
}