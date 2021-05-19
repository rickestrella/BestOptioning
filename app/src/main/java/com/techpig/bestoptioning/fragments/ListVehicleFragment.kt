package com.techpig.bestoptioning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.activities.ContainerActivity.Companion.chipNavBar
import com.techpig.bestoptioning.adapters.VehicleListAdapter
import com.techpig.bestoptioning.models.Vehicle

class ListVehicleFragment : Fragment() {

    private var vehicleAdapter: VehicleListAdapter? = null

    companion object {
        lateinit var cmpButton: MaterialButton
        lateinit var addVButton: MaterialButton
    }

    lateinit var vehicleListRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_list_vehicle, container, false)

        vehicleListRv = v.findViewById(R.id.vehicleListRv)

        cmpButton = v.findViewById(R.id.compareButton)
        addVButton = v.findViewById(R.id.addVehicle)

        setupVehicleRecyclerView()

        addVButton.setOnClickListener {
            chipNavBar.setItemSelected(R.id.add_menu, true)
            if (Vehicle.vehicles.size < 5) {
                chipNavBar.setItemEnabled(R.id.add_menu, true)
            }
            chipNavBar.setItemEnabled(R.id.result_menu, false)
        }

        cmpButton.setOnClickListener {
            val bundle = arguments
            if (bundle != null) {
                bundle.putFloat("bpm", BestOptionValue())
                bundle.putFloat("bestPm", bestPossibleMatch())
            }
            chipNavBar.setItemEnabled(R.id.add_menu, false)
            chipNavBar.setItemSelected(R.id.result_menu, true)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //When item swiped
                //Remove item from array
                Vehicle.vehicles.removeAt(viewHolder.adapterPosition)
                //Notify adapter
                vehicleAdapter!!.notifyDataSetChanged()
                if (Vehicle.vehicles.size <= 1) {
                    chipNavBar.setItemEnabled(R.id.result_menu, false)
                } else {
                    chipNavBar.setItemEnabled(R.id.result_menu, true)
                }

                Toast.makeText(
                    requireActivity().applicationContext,
                    getString(R.string.vehicle_removed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).attachToRecyclerView(vehicleListRv)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //When item swiped
                //Remove item from array
                Vehicle.vehicles.removeAt(viewHolder.adapterPosition)
                //Notify adapter
                vehicleAdapter!!.notifyDataSetChanged()
                if (Vehicle.vehicles.size <= 1) {
                    chipNavBar.setItemEnabled(R.id.result_menu, false)
                } else {
                    chipNavBar.setItemEnabled(R.id.result_menu, true)
                }
                Toast.makeText(
                    requireActivity().applicationContext,
                    getString(R.string.vehicle_removed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).attachToRecyclerView(vehicleListRv)

        return v
    }

    private fun bestPossibleMatch(): Float {
        var highestV = 0f
        var lowestUcn = 999999999f

        for (i in Vehicle.vehicles) {
            if (i.getVin() > highestV) {
                highestV = i.getVin()
            }
        }

        for (i in Vehicle.vehicles) {
            if (i.getUcn() < lowestUcn) {
                lowestUcn = i.getUcn()
            }
        }

        return highestV / lowestUcn
    }

    private fun BestOptionValue(): Float {

        val bestOptionList = arrayListOf<Float>()
        fun bestOption() {
            for (i in Vehicle.vehicles) {
                bestOptionList.add(i.getVu_relation())
            }
        }
        bestOption()

        fun binarySearch(list: List<Float>, valueToCompare: Float): Float {
            var central: Int
            var initialPosition = 0
            var lastPosition: Int
            var centralValue: Float
            lastPosition = list.size - 1

            while (initialPosition <= lastPosition) {
                central = (initialPosition + lastPosition) / 2
                centralValue = list[central]
                when {
                    valueToCompare == centralValue -> {
                        return centralValue
                    }
                    valueToCompare < centralValue -> {
                        lastPosition = central - 1
                    }
                    else -> {
                        initialPosition = central + 1
                    }
                }
            }
            return 0f
        }

        return binarySearch(bestOptionList, bestPossibleMatch())
    }

    private fun setupVehicleRecyclerView() {
        vehicleListRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        vehicleAdapter = VehicleListAdapter(context?.applicationContext!!, Vehicle.vehicles)
        vehicleListRv.adapter = vehicleAdapter
        cmpButton.isEnabled = Vehicle.vehicles.size >= 2
        if (Vehicle.vehicles.size >= 1) {
            chipNavBar.setItemEnabled(R.id.list_menu, true)
        }
    }
}