package com.techpig.bestoptioning.activities

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.techpig.bestoptioning.R
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar!!.title = "Best Optioning"

        val mainLogo = findViewById<ImageView>(R.id.mainLogo)
        val main_descriprion = findViewById<TextView>(R.id.main_descriprion)
        val addVehicleMainButton = findViewById<MaterialButton>(R.id.addVehicleMainButton)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //Detect system language
        if (Locale.getDefault().displayLanguage == Locale.getDefault()
                .getDisplayLanguage(Locale.forLanguageTag("es"))
        ) {
            @Suppress("DEPRECATION")
            main_descriprion.text =
                Html.fromHtml("Es una forma de ayudar a las personas que están<br>buscando un vehículo a decidir<br>cual es aquel que se debe comprar.<br>Una vez ingresada la información individual de dos o más VEHICULOS similares y su USO asociado,<br>la aplicación generará indicadores numéricos<br>para cada vehículo y que, al comparar esos valores,<br>se refleje cual sería su <b><i>mejor-elección,</b><br><b><i>mejor-selección</b> y <b><i>¡MEJOR OPCION!</b><br> <p style='text-align: center'><u>¡Es muy fácil y funciona!</p>")
        } else {
            @Suppress("DEPRECATION")
            main_descriprion.text =
                Html.fromHtml("It's a way to help car buyers decide<br>which car to buy.<br>After some VEHICLE and USAGE<br>information of 2 or more comparable<br>vehicles has been entered,<br>this app will generate<br>numerical indicators for each car<br>to compare those values and reflect<br>which one could be your <b><i>best-choice,</b><br><b><i>best-bet</b> and <b><i>BEST-OPTION!</b><br> <p style='text-align: center'><u>It's easier and it works!</p>")
        }
        ///////////////////

        addVehicleMainButton.setOnClickListener {
            val i = Intent(this@HomeActivity, ContainerActivity::class.java)
            startActivity(i)
        }

        when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                homeLayout.setBackgroundColor(Color.parseColor("#000000"))
                mainLogo.setBackgroundResource(R.drawable.car_logo_night)
                titleIV.setBackgroundResource(R.drawable.title_night)
                subtitleIV.setBackgroundResource(R.drawable.subtitle_night)

                mainLogo.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                mainLogo.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                titleIV.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                titleIV.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                subtitleIV.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                subtitleIV.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                main_descriprion.setTextColor(Color.parseColor("#FFFFFF"))
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                homeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mainLogo.setBackgroundResource(R.drawable.car_logo_day)
                titleIV.setBackgroundResource(R.drawable.title_day)
                subtitleIV.setBackgroundResource(R.drawable.subtitle_day)

                mainLogo.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                mainLogo.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                titleIV.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                titleIV.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                subtitleIV.minimumWidth = WindowManager.LayoutParams.WRAP_CONTENT
                subtitleIV.maxWidth = WindowManager.LayoutParams.WRAP_CONTENT

                main_descriprion.setTextColor(Color.parseColor("#000000"))
            }
        }
    }
}