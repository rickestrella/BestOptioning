package com.techpig.bestoptioning.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.techpig.bestoptioning.R
import java.text.DecimalFormat
import java.util.*

open class BaseFragment : Fragment() {

    var closest = 0f
    var bestPM = 0f
    var ucn_value = 0f
    var vin_value: Double = 0.0
    var cociente_temp = 0f
    var vu_relation_value = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun formatNumber(number: Float): Float {
        val df = DecimalFormat("####.####")
        return df.format(number.toDouble()).toFloat()
    }

    fun cocienteVehiculos(vin: Float, ucn: Float) {
        val tmp = replaceSymbol(vin.toString()) / replaceSymbol(ucn.toString())

        cociente_temp =
            if (Locale.getDefault().displayLanguage == Locale.getDefault().getDisplayLanguage(
                    Locale.forLanguageTag
                        ("es")
                )
            ) {
                replaceSymbol(tmp.toString())
            } else {
                tmp
            }
    }

    fun replaceSymbol(value: String): Float {
        return value.replace(",", ".").toFloat()
    }

    fun replaceSymboltoInt(value: String): Int {
        return value.replace(",", ".").toInt()
    }

    fun replaceSymboltoDouble(value: String): Double {
        return value.replace(",", ".").toDouble()
    }

}