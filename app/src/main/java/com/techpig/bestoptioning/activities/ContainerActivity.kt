package com.techpig.bestoptioning.activities

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.ismaeldivita.chipnavigation.BuildConfig
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.fragments.AddVehicleFragment
import com.techpig.bestoptioning.fragments.ListVehicleFragment
import com.techpig.bestoptioning.fragments.ResultFragment
import com.techpig.bestoptioning.models.Vehicle.Companion.vehicles
import kotlin.system.exitProcess

class ContainerActivity : BaseActivity() {

    private var backPressedTime = 0L
    private var aClass: Class<*>? = null
    private var frag = ""


    companion object {
        lateinit var chipNavBar: ChipNavigationBar
        @SuppressLint("StaticFieldLeak")
        lateinit var frame_layout: FrameLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        chipNavBar = findViewById(R.id.chipNavBar)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        frame_layout = findViewById(R.id.frameLayout)

        when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                frame_layout.setBackgroundColor(Color.parseColor("#000000"))
                chipNavBar.setMenuResource(R.menu.bottom_bar_menu_night)
                chipNavBar.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.chip_night, null)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                frame_layout.setBackgroundColor(Color.parseColor("#F3F3F3"))
                chipNavBar.setMenuResource(R.menu.bottom_bar_menu)
                chipNavBar.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.chip_day, null)
            }
        }

        chipNavBar.setItemSelected(R.id.add_menu, true)
        aClass = AddVehicleFragment::class.java
        supportActionBar!!.title = getString(R.string.add_vehicle)
        openFragment()

        if (vehicles.size < 1) {
            chipNavBar.setItemEnabled(R.id.list_menu, false)
        }
        if (vehicles.size <= 2) {
            chipNavBar.setItemEnabled(R.id.result_menu, false)
        }

        chipNavBar.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {
                when (id) {
                    R.id.add_menu -> {
                        supportActionBar!!.title = getString(R.string.add_vehicle)
                        frag = "addVehicle"
                        aClass = AddVehicleFragment::class.java
                        openFragment()
                    }
                    R.id.list_menu -> {
                        supportActionBar!!.title = getString(R.string.vehicle_list)
                        frag = "listVehicle"
                        aClass = ListVehicleFragment::class.java
                        openFragment()
                    }
                    R.id.result_menu -> {
                        supportActionBar!!.title = getString(R.string.results)
                        frag = "results"
                        aClass = ResultFragment::class.java
                        openFragment()
                    }
                }
            }
        })
    }


    private fun openFragment() {
        try {
            //Initialize fragment
            val fragment: androidx.fragment.app.Fragment =
                aClass!!.newInstance() as androidx.fragment.app.Fragment
            //Open Fragment
            if (aClass == AddVehicleFragment::class.java) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.frameLayout, fragment).commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    .replace(R.id.frameLayout, fragment).addToBackStack(frag).commit()
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish()
            exitProcess(0)
        } else {
            Toast.makeText(
                applicationContext,
                "Press back again to exit the app",
                Toast.LENGTH_SHORT
            )
                .show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val emailList = arrayOf("bestoptioning@gmail.com")

        return when (item.itemId) {
            R.id.action_share -> {
                shareApp()
                true
            }
            R.id.rate_us -> {
                rateOnAppStore()
                true
            }
            R.id.support_action -> {
                suggestions(emailList)
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun shareApp() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
            var shareMessage = getString(R.string.email_message)
            shareMessage =
                """
                ${shareMessage}https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}
                """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.coose_one)))
        } catch (e: Exception) {
            e.toString()
        }
    }

    private fun suggestions(emails: Array<String>) {
        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.type = "plain/text"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
        emailIntent.putExtra(
            Intent.EXTRA_SUBJECT,
            getString(R.string.email_subject)
        )
        startActivity(emailIntent, null)
    }

    private fun rateOnAppStore() {
        val uri: Uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }
}