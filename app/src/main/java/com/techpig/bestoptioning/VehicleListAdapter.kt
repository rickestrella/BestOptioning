package com.techpig.bestoptioning

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.techpig.bestoptioning.ContainerActivity.Companion.chipNavBar
import com.techpig.bestoptioning.ListVehicleFragment.Companion.addVButton
import com.techpig.bestoptioning.ListVehicleFragment.Companion.cmpButton
import kotlinx.android.synthetic.main.vehicles_list_layout.view.*
import java.text.DecimalFormat

class VehicleListAdapter(val context: Context, private val items: ArrayList<VehicleObject>) :
    RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vehicleMakeTitle = itemView.vehicleListTitle!!
        val vehicleDate = itemView.vehicleListManufacturedDate!!
        val vehiclePrice = itemView.vehicleListPrice!!
        val deleteVehicle = itemView.deleteCarListedButton!!
        val reading = itemView.vehicleListOdometerRead!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if ((context.applicationContext!!.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) == Configuration.UI_MODE_NIGHT_YES) {
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.vehicles_list_layout_night, parent, false))
        } else {
            ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.vehicles_list_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        fun trimTrailingZero(value: String?): String? {
            return if (!value.isNullOrEmpty()) {
                if (value.indexOf(".") < 0) {
                    value
                } else {
                    value.replace("0*$".toRegex(), "").replace("\\.$".toRegex(), "")
                }
            } else {
                value
            }
        }

        holder.vehicleMakeTitle.text = item.getModelMake()
        holder.vehicleDate.text = "${item.getYear()}/${item.getMonth()}"
        holder.reading.text = "${item.getOdometer()} km/mi"
        holder.vehiclePrice.text = "$ ${item.getPrice().toLong()}"

        holder.deleteVehicle.setOnClickListener {
            items.removeAt(holder.layoutPosition)
            notifyItemRemoved(holder.layoutPosition)
            cmpButton.isEnabled = items.size > 1
            addVButton.isEnabled = items.size < 5
            if (items.size >= 1) {
                chipNavBar.setItemEnabled(R.id.list_menu, true)
            }
        }
        addVButton.isEnabled = items.size < 5
    }

    override fun getItemCount(): Int {
        return items.size
    }
}