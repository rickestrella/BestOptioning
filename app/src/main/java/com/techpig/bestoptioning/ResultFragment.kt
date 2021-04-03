package com.techpig.bestoptioning

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import kotlin.math.abs

class ResultFragment : Fragment() {

    companion object {
        var boBpr: String = ""
        var boTitle: String = ""
        var boScore: String = ""
    }

    private var vehicleAdapter: BestChoiceAdapter? = null
    private var bbAdapter: BestBetAdapter? = null
    private var boAdapter: BestOptionAdapter? = null

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

        val bpm = String.format("%.4f", bestPossibleMatch())
        bpmScore.text = bpm

        boBpr = bpm
        getBOName()

        finish.setOnClickListener {
            Vehicle.removeVehicle()
            val i = Intent(context, HomeActivity::class.java)
            startActivity(i)
            Toast.makeText(
                activity?.applicationContext,
                "Thanks for using our app!",
                Toast.LENGTH_LONG
            ).show()
        }

        //TODO: Permitir editar el vehiculo ingresado, agregar undo cuando se borra un vehiculo, ver vehiculo (contextMenu).


        donateButton.setOnClickListener {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/")
            ) //TODO: Cambiar el URI por el boton de donar
            startActivity(webIntent)
        }

        popDialog()

        return v
    }

    private fun popDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val alertLayout: View = layoutInflater.inflate(R.layout.dialog_layout, null)

        builder.setView(alertLayout)
        val dialog = builder.create()
        dialog.show()
        val viewKonfetti = alertLayout.findViewById<KonfettiView>(R.id.viewKonfetti)

        viewKonfetti.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.BLUE, Color.RED, Color.CYAN)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(-20f, viewKonfetti.width + 20f, -20f, -50f)
            .burst(900)

        val dialogBpr = alertLayout.findViewById<TextView>(R.id.dialog_bpr)
        dialogBpr.text = boBpr

        val dialogMM = alertLayout.findViewById<TextView>(R.id.dialog_model_make)
        dialogMM.text = boTitle

        val dialogVS = alertLayout.findViewById<TextView>(R.id.dialog_vehicle_score)
        dialogVS.text = boScore

        val dialogButton = alertLayout.findViewById<Button>(R.id.dismiss_dialog_button)
        dialogButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun setupVehicleRecyclerViews() {
        bestChoiceRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        vehicleAdapter = BestChoiceAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestChoiceRv.adapter = vehicleAdapter

        bestBetRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        bbAdapter = BestBetAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestBetRv.adapter = bbAdapter

        bestOptionRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        boAdapter = BestOptionAdapter(activity?.applicationContext!!, Vehicle.vehicles)
        bestOptionRv.adapter = boAdapter
    }

    private fun highestVin(): Float {
        var highestV = 0f
        for (i in Vehicle.vehicles) {
            if (i.getVin() > highestV) {
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

    private fun bestPossibleMatch(): Float {
        return highestVin() / lowestUcn()
    }

    private fun getBOName() {
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

        for (i in Vehicle.vehicles) {
            if (i.getVu_relation() == closestVal(bestPossibleMatch())) {
                boTitle = i.getModelMake()
                boScore = i.getVu_relation().toString()
            }
        }
    }
}