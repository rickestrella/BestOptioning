package com.techpig.bestoptioning

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    fun showLongToast (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showShortToast (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}