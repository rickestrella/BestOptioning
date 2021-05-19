package com.techpig.bestoptioning.adapters

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.fragments.BaseFragment
import com.techpig.bestoptioning.models.VehicleObject
import kotlinx.android.synthetic.main.card_item_layout.view.*
import java.text.DecimalFormat
import java.util.*

class BestChoiceAdapter(val context: Context, val items: ArrayList<VehicleObject>) :
    RecyclerView.Adapter<BestChoiceAdapter.ViewHolder>() {

    // VIN

    var highestV = 0f

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //CardView
        val cardTitle = itemView.cardModelMake
        val cardScore = itemView.cardScore
        val containerCard = itemView.containerCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if ((context.applicationContext!!.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) == Configuration.UI_MODE_NIGHT_YES) {
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.card_item_layout_night, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.card_item_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        // Vehicle Results

        fun formatNumber(number: Float): Float {
            val df = DecimalFormat("####.####")
            return df.format(number.toDouble()).toFloat()
        }

        val df = DecimalFormat("####.####")

        holder.cardTitle.text = item.getModelMake()
        holder.cardTitle.isSelected = true
        holder.cardTitle.setSingleLine()
        holder.cardTitle.ellipsize = TextUtils.TruncateAt.MARQUEE

        val value = BaseFragment().replaceSymbol(item.getVin().toString())
        val spanishValue = df.format(value).toString()

        val cardScoreFourDigits = if (Locale.getDefault().displayLanguage == Locale.getDefault()
                .getDisplayLanguage(
                    Locale.forLanguageTag("es")
                )
        ) {
            spanishValue
        } else {
            formatNumber(item.getVin()).toString()
        }

        holder.cardScore.text = cardScoreFourDigits

        fun highestVin() {
            for (i in items) {
                if (i.getVin() > highestV) {
                    highestV = i.getVin()
                }
            }
        }

        highestVin()

        highestV

        // Best Choice = highest VIN
        // Best bet = lowest UCIN
        // BPM = highest vin / lowest ucin
        // Best Option =  Lo mas cercano a BPM
        // val hs = getHighestScore(cardScoree)

        if ((context.applicationContext!!.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) == Configuration.UI_MODE_NIGHT_YES) {
            if (item.getVin() == highestV) {
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
            if (item.getVin() == highestV) {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#348A36"))
                holder.cardTitle.setTextColor(Color.parseColor("#FFFFFF"))
                holder.cardScore.setTextColor(Color.parseColor("#EAEAEA"))
                holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                holder.containerCard.cardElevation = 12f
            } else {
                holder.containerCard.setCardBackgroundColor(Color.parseColor("#FFEEB6"))
                holder.containerCard.setPadding(8, 8, 8, 8)
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