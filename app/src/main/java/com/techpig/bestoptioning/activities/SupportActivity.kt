package com.techpig.bestoptioning.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anjlab.android.iab.v3.BillingProcessor
import com.anjlab.android.iab.v3.TransactionDetails
import com.techpig.bestoptioning.Constants.Constants
import com.techpig.bestoptioning.R
import kotlinx.android.synthetic.main.activity_support.*
import java.util.*


class SupportActivity : AppCompatActivity(), BillingProcessor.IBillingHandler {

    private var bp: BillingProcessor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)
        supportActionBar!!.title = resources.getString(R.string.pay_as_you_wish)

        ll5.visibility = View.VISIBLE

        if (Locale.getDefault().displayLanguage == Locale.getDefault()
                .getDisplayLanguage(Locale.forLanguageTag("es"))
        ) {
            ll5.visibility = View.GONE
        }

        bp = BillingProcessor(this, Constants.PLAY_STORE_LICENCE_KEY, this)
        bp!!.initialize()
    }

    override fun onBillingInitialized() {
        Log.d("Support Activity", "onBillingInitialized")
        bp!!.getPurchaseListingDetails(Constants.PRODUCTS) //Transaction Details

        one_dollar_button.setOnClickListener { _ ->
            if (bp!!.isOneTimePurchaseSupported) {
                bp!!.purchase(this, "one_dollar")
                bp!!.consumePurchase("one_dollar")
            }
            Toast.makeText(
                applicationContext,
                "Purchase method not supported in your device.",
                Toast.LENGTH_SHORT
            ).show()

        }

        three_dollar_button.setOnClickListener { _ ->
            if (bp!!.isOneTimePurchaseSupported) {
                bp!!.purchase(this, "three_dollars")
                bp!!.consumePurchase("three_dollars")
            }
            Toast.makeText(
                applicationContext,
                "Purchase method not supported in your device.",
                Toast.LENGTH_SHORT
            ).show()

        }

        five_dollar_button.setOnClickListener { _ ->
            if (bp!!.isOneTimePurchaseSupported) {
                bp!!.purchase(this, "five_dollars")
                bp!!.consumePurchase("five_dollars")
            }
            Toast.makeText(
                applicationContext,
                "Purchase method not supported in your device.",
                Toast.LENGTH_SHORT
            ).show()

        }

        ten_dollar_button.setOnClickListener { _ ->
            if (bp!!.isOneTimePurchaseSupported) {
                bp!!.purchase(this, "ten_dollars")
                bp!!.consumePurchase("ten_dollars")
            }
            Toast.makeText(
                applicationContext,
                "Purchase method not supported in your device.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onProductPurchased(productId: String, details: TransactionDetails?) {
        Log.d("Support Activity", "onProductPurchased")
    }

    override fun onPurchaseHistoryRestored() {
        Log.d("Support Activity", "onPurchaseHistoryRestored")
    }

    override fun onBillingError(errorCode: Int, error: Throwable?) {
        Log.d("Support Activity", "onBillingError")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!bp!!.handleActivityResult(requestCode, resultCode, data)) {
            Toast.makeText(
                applicationContext,
                resources.getString(R.string.thank_you_for_your_support),
                Toast.LENGTH_SHORT
            ).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        if (bp != null) {
            bp!!.release()
        }
        super.onDestroy()
    }

}