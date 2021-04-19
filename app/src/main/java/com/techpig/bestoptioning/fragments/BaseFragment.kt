package com.techpig.bestoptioning.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.techpig.bestoptioning.R
import kotlinx.android.synthetic.main.fragment_add_vehicle.*
import java.text.DecimalFormat
import java.util.*
import kotlin.math.pow

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

    fun getVin(
        mileAge: Long,
        odometerRead: Float,
        price: Float?,
        newPrice: Float?,
        equivalence: Float?
    ) {
        val value = if (!llOtherCurrencies.isVisible) {
            (10.0.pow(13.0) / (mileAge * odometerRead) * price!!)
        } else {
            ((10.0.pow(13.0) * equivalence!!) / (mileAge * odometerRead) * newPrice!!)
        }

        vin_value =
            if (Locale.getDefault().displayLanguage == Locale.getDefault().getDisplayLanguage(
                    Locale.forLanguageTag
                        ("es")
                )
            ) {
                formatNumber(replaceSymbol(value.toString())).toDouble()
            } else {
                formatNumber(value.toFloat()).toDouble()
            }
        Log.e("VIN", "$vin_value")
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