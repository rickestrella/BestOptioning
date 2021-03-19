package com.techpig.bestoptioning

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_item_layout.view.*
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs


class BestOptionAdapter(val context: Context, private val items: ArrayList<VehicleObject>) :
    RecyclerView.Adapter<BestOptionAdapter.ViewHolder>() {

    private var bestPossibleMatch = 0f
    private var highestV = 0f
    private var lowestUcn = 9999999f

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
            val df = DecimalFormat("######.####")
            return df.format(number.toDouble()).toFloat()
        }

        holder.cardTitle.text = item.getModelMake()
        val cardScoreFourDigits = formatNumber(item.getVu_relation())
        holder.cardScore.text = "$cardScoreFourDigits"


        // Best Choice = highest VIN
        // Best bet = lowest UCIN
        // BPM = highest vin / lowest ucin
        // Best Option =  Lo mas cercano a BPM

        fun highestVin() {
            for (i in Vehicle.vehicles) {
                if (i.getVin() > highestV) {
                    highestV = i.getVin()
                }
            }
        }

        fun lowestUcn() {
            for (i in Vehicle.vehicles) {
                if (i.getUcn() < lowestUcn) {
                    lowestUcn = i.getUcn()
                }
            }
        }

        highestVin()
        lowestUcn()

        bestPossibleMatch = highestV / lowestUcn

        val bestOptionList = arrayListOf<Float>()

        fun bestOption() {
            for (i in Vehicle.vehicles) {
                bestOptionList.add(i.getVu_relation())
            }
        }
        bestOption()

        bestOptionList.sort() //Ascending

        fun closestVal(bpr: Float): Float {
            var closestValue = bestOptionList[0]
            var subtractResult = abs(closestValue - bpr)

            for (i in 1 until bestOptionList.size) {

                if (abs(bestOptionList[i] - bpr) < abs(subtractResult)) {
                    subtractResult = abs(bestOptionList[i] - bpr)
                    closestValue = abs(bestOptionList[i])

                }
            }
            return closestValue
        }

        val closestV = closestVal(bestPossibleMatch)

        if ((context.applicationContext!!.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) == Configuration.UI_MODE_NIGHT_YES) {
            when (item.getVu_relation()) {
                closestV -> {
                    holder.containerCard.setCardBackgroundColor(Color.parseColor("#0E4D00"))
                    holder.cardTitle.setTextColor(Color.parseColor("#A0F779"))
                    holder.cardScore.setTextColor(Color.parseColor("#EAEAEA"))
                    holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    holder.containerCard.cardElevation = 12f
                }
                else -> {
                    holder.containerCard.setCardBackgroundColor(Color.parseColor("#002B4D"))
                    holder.cardTitle.setTextColor(Color.parseColor("#7FD7FF"))
                    holder.cardScore.setTextColor(Color.parseColor("#E3E3E3"))
                    holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    holder.containerCard.cardElevation = 5f
                }
            }
        } else {
            when (item.getVu_relation()) {
                closestV -> {
                    holder.containerCard.setCardBackgroundColor(Color.parseColor("#1DA000"))
                    holder.cardTitle.setTextColor(Color.parseColor("#FF9100"))
                    holder.cardScore.setTextColor(Color.parseColor("#EAEAEA"))
                    holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    holder.containerCard.cardElevation = 12f
                }
                else -> {
                    holder.containerCard.setCardBackgroundColor(Color.parseColor("#AB004D"))
                    holder.containerCard.setPadding(16,16,16,16)
                    holder.cardTitle.setTextColor(Color.parseColor("#FFC0D9"))
                    holder.cardScore.setTextColor(Color.parseColor("#EEDDDD"))
                    holder.cardScore.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
                    holder.containerCard.cardElevation = 5f
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}