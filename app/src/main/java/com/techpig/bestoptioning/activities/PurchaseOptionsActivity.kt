package com.techpig.bestoptioning.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.billingclient.api.*
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.adapters.BillingAdapter
import com.techpig.bestoptioning.models.DonateOptionModel
import kotlinx.android.synthetic.main.purchase_buttons_rv_layout.*

class PurchaseOptionsActivity : AppCompatActivity() {

    companion object {
        const val PREF_FILE = "pref"
        lateinit var billingClient: BillingClient

        val purchaseItemIDs: ArrayList<DonateOptionModel> =
            object : ArrayList<DonateOptionModel>() {
                init {
                    add(DonateOptionModel("one_dollar"))
                    add(DonateOptionModel("three_dollars"))
                    add(DonateOptionModel("five_dollars"))
                    add(DonateOptionModel("ten_dollars"))
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_options)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        purchase_rv.layoutManager = GridLayoutManager(applicationContext, 2, GridLayoutManager.HORIZONTAL, false)
        val adapter = BillingAdapter(this@PurchaseOptionsActivity, purchaseItemIDs)
        purchase_rv.adapter = adapter

    }
}