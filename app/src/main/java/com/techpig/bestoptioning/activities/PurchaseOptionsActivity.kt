package com.techpig.bestoptioning.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.*
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.adapters.BillingAdapter
import com.techpig.bestoptioning.models.DonateOptionModel
import kotlinx.android.synthetic.main.purchase_buttons_rv_layout.*

class PurchaseOptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase_options)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        purchase_rv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BillingAdapter(this@PurchaseOptionsActivity, DonateOptionModel.listOfIDs)
        purchase_rv.adapter = adapter
    }
}