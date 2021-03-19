package com.techpig.bestoptioning

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrInterface

class ResultFragment : Fragment() {

    private var vehicleAdapter : BestChoiceAdapter? = null
    private var bbAdapter : BestBetAdapter? = null
    private var boAdapter : BestOptionAdapter? = null

    private var slidrInt: SlidrInterface? = null

    lateinit var result_layout: FrameLayout
    lateinit var bpmScore: TextView
    lateinit var donateButton: Button
    lateinit var finish: Button
    lateinit var bestChoiceRv: RecyclerView
    lateinit var bestBetRv: RecyclerView
    lateinit var bestOptionRv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_result, container, false)

        result_layout = v.findViewById(R.id.result_layout)
        bpmScore = v.findViewById(R.id.bpmScore)
        donateButton = v.findViewById(R.id.donateButton)
        finish = v.findViewById(R.id.finish)
        bestChoiceRv = v.findViewById(R.id.bestChoiceRv)
        bestBetRv = v.findViewById(R.id.bestBetRv)
        bestOptionRv = v.findViewById(R.id.bestOptionRv)

        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> result_layout.setBackgroundColor(Color.parseColor("#141414"))
            Configuration.UI_MODE_NIGHT_NO -> result_layout.setBackgroundColor(Color.WHITE)
        }

        setupVehicleRecyclerViews()

        finish.setOnClickListener {
            Vehicle.removeVehicle()
            activity?.finishAffinity()
        }

        val bpm = String.format("%.4f", bestPossibleMatch())
        bpmScore.text = bpm

        donateButton.setOnClickListener {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/")) //TODO: Cambiar el URI por el boton de donar
            startActivity(webIntent)
        }

        return v
    }

    private fun setupVehicleRecyclerViews() {
        bestChoiceRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vehicleAdapter = BestChoiceAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestChoiceRv.adapter = vehicleAdapter

        bestBetRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bbAdapter = BestBetAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestBetRv.adapter = bbAdapter

        bestOptionRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        boAdapter = BestOptionAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestOptionRv.adapter = boAdapter
    }

    private fun highestVin(): Float {
        var highestV = 0f
        for (i in Vehicle.vehicles) {
            if (i.getVin() > highestV){
                highestV = i.getVin()
            }
        }
        return highestV
    }

    private fun lowestUcn(): Float {
        var lowestUCN = 999999999f
        for (i in Vehicle.vehicles) {
            if (i.getUcn() < lowestUCN) {
                lowestUCN = i.getUcn()
            }
        }
        return lowestUCN
    }

    private fun bestPossibleMatch() : Float {
        return highestVin() / lowestUcn()
    }
}