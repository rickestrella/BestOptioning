package com.techpig.bestoptioning.adapters

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.models.VehicleObject
import kotlinx.android.synthetic.main.card_item_layout.view.*
import java.text.DecimalFormat

class BestBetAdapter(val context: Context, private val items: ArrayList<VehicleObject>) :
    RecyclerView.Adapter<BestBetAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //CardView
        val cardTitle = itemView.cardModelMake!!
        val cardScore = itemView.cardScore!!
        val containerCard = itemView.containerCard!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        fun formatNumber(number: Float): Float {
            val df = DecimalFormat("####.####")
            return df.format(number.toDouble()).toFloat()
        }

        holder.cardTitle.text = item.getModelMake()
        holder.cardTitle.isSelected = true
        holder.cardTitle.setSingleLine()
        holder.cardTitle.ellipsize = TextUtils.TruncateAt.MARQUEE
        val cardScoreFourDigits = formatNumber(item.getUcn())
        holder.cardScore.text = "$cardScoreFourDigits"

        val cardScoree = holder.cardScore.text.toString()
        var lowestUcn = 999999f

        fun lowestUcn() {
            for (i in items) {
                if (i.getUcn() < lowestUcn) {
                    lowestUcn = i.getUcn()
                }
            }
        }
        lowestUcn()

        val bbstr = formatNumber(lowestUcn).toString()

        if ((context.applicationContext!!.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) == Configuration.UI_MODE_NIGHT_YES) {
            if (cardScoree == bbstr) {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#0E4D00"))
                holder.cardTitle.setTextColor(Color.parseColor("#A0F779"))
                holder.cardScore.setTextColor(Color.parseColor("#EAEAEA"))
                holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                holder.containerCard.cardElevation = 10f
            } else {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#002B4D"))
                holder.cardTitle.setTextColor(Color.parseColor("#7FD7FF"))
                holder.cardScore.setTextColor(Color.parseColor("#E3E3E3"))
                holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                holder.containerCard.cardElevation = 6f
            }
        } else {
            if (cardScoree == bbstr) {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#348A36"))
                holder.cardTitle.setTextColor(Color.parseColor("#FFFFFF"))
                holder.cardScore.setTextColor(Color.parseColor("#EAEAEA"))
                holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                holder.containerCard.cardElevation = 12f
            } else {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#FFEEB6"))
                holder.containerCard.setPadding(8,8,8,8)
                holder.cardTitle.setTextColor(Color.parseColor("#000000"))
                holder.cardScore.setTextColor(Color.parseColor("#000000"))
                holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                holder.containerCard.cardElevation = 6f
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }
}