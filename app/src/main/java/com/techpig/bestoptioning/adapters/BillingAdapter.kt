package com.techpig.bestoptioning.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.models.DonateOptionModel

class BillingAdapter(
    var context: Context,
    var listOfItems: ArrayList<DonateOptionModel>,
) :
    RecyclerView.Adapter<BillingAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button = itemView.findViewById<Button>(R.id.purchaseButton)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.purchase_buttons_rv_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val buttons = listOfItems[position]

        holder.button.text = buttons.getTitle()
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}