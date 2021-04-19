package com.techpig.bestoptioning.fragments

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.tapadoo.alerter.Alerter
import com.techpig.bestoptioning.Constants.Constants
import com.techpig.bestoptioning.R
import com.techpig.bestoptioning.activities.ContainerActivity.Companion.chipNavBar
import com.techpig.bestoptioning.activities.ContainerActivity.Companion.frame_layout
import com.techpig.bestoptioning.models.Vehicle
import java.time.LocalDate
import java.util.*

class AddVehicleFragment : BaseFragment() {

    private val calendar = Calendar.getInstance()
    private val currentYear = calendar[Calendar.YEAR]
    private val currentMonth = calendar[Calendar.MONTH]

    private var chosenMonth: Long = 0
    private var bestPossibleMatch = 0f

    private var validYear = false

    private var months = ArrayList<String>()
    private var units = ArrayList<String>()

    private var fieldEmpty = true

    //SharedPreferences
    private var prefs: SharedPreferences? = null
    private var CHOSEN_CURRENCY = 0

    //Objects

    private lateinit var usdCheckBox: MaterialRadioButton
    private lateinit var cadCheckBox: MaterialRadioButton
    private lateinit var otherCheckBox: MaterialRadioButton
    private lateinit var monthPicker: TextInputLayout
    private lateinit var unitPicker: TextInputLayout
    private lateinit var price: TextInputLayout
    private lateinit var equivalencia: TextInputLayout
    private lateinit var manufacturingYear: TextInputLayout
    private lateinit var price_new: TextInputLayout
    private lateinit var modelMake: TextInputLayout
    private lateinit var odometerRead: TextInputLayout
    private lateinit var mkYear: TextInputLayout
    private lateinit var mkEnd: TextInputLayout
    private lateinit var priceInfo: TextView
    private lateinit var titleTv: TextView
    private lateinit var monthInfoTV: TextView
    private lateinit var manufacturingTv: TextView
    private lateinit var odometerInfoTV: TextView
    private lateinit var mkYearInfoTv: TextView
    private lateinit var mkEndInfoTv: TextView
    private lateinit var usageInformationTitleTV: TextView
    private lateinit var llOtherCurrencies: LinearLayout
    private lateinit var addVehicleButton: Button
    private lateinit var cancelButton: Button
    private lateinit var vehicleInfoCard: CardView
    private lateinit var usageInfoCard: CardView

    private lateinit var vO: TextInputEditText
    private lateinit var vP: TextInputEditText
    private lateinit var vPOC: TextInputEditText
    private lateinit var vPE: TextInputEditText

    private var vehicleOdometer: Int = 0
    private var vehiclePrice: Double = 0.0
    lateinit var vehiclePriceOtherCurrency: String
    lateinit var vehiclePriceEquivalence: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_add_vehicle, container, false)

        usdCheckBox = v.findViewById(R.id.usdCheckBox)
        monthPicker = v.findViewById(R.id.monthPicker)
        unitPicker = v.findViewById(R.id.unitPicker)
        llOtherCurrencies = v.findViewById(R.id.llOtherCurrencies)

        price = v.findViewById(R.id.price)

        priceInfo = v.findViewById(R.id.priceInfo)
        cadCheckBox = v.findViewById(R.id.cadCheckBox)
        otherCheckBox = v.findViewById(R.id.otherCheckBox)

        equivalencia = v.findViewById(R.id.equivalencia)

        addVehicleButton = v.findViewById(R.id.addVehicleButton)
        cancelButton = v.findViewById(R.id.cancelButton)
        manufacturingYear = v.findViewById(R.id.manufacturingYear)

        price_new = v.findViewById(R.id.price_new)

        modelMake = v.findViewById(R.id.modelMake)

        odometerRead = v.findViewById(R.id.odometerRead)

        mkEnd = v.findViewById(R.id.mkEnd)
        mkYear = v.findViewById(R.id.mkYear)
        titleTv = v.findViewById(R.id.titleTv)
        manufacturingTv = v.findViewById(R.id.manufacturingTv)
        monthInfoTV = v.findViewById(R.id.monthInfoTV)
        odometerInfoTV = v.findViewById(R.id.odometerInfoTV)
        usageInformationTitleTV = v.findViewById(R.id.usageInformationTitleTV)
        mkYearInfoTv = v.findViewById(R.id.mkYearInfoTv)
        mkEndInfoTv = v.findViewById(R.id.mkEndInfoTv)
        vehicleInfoCard = v.findViewById(R.id.vehicleInfoCard)
        usageInfoCard = v.findViewById(R.id.usageInfoCard)

        val mke = v.findViewById<TextInputEditText>(R.id.et_mke)
        val mky = v.findViewById<TextInputEditText>(R.id.et_mky)

        vO = v.findViewById(R.id.odometer_et)
        vP = v.findViewById(R.id.price_et)
        vPOC = v.findViewById(R.id.priceNewEt)
        vPE = v.findViewById(R.id.equivalencia_et)

        mkEnd.editText!!.text = SpannableStringBuilder(getString(R.string._250000))
        mkYear.editText!!.text = SpannableStringBuilder(getString(R.string._20000))

        prefs = activity?.applicationContext!!.getSharedPreferences(
            Constants.PREFS_FILENAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs!!.edit()


        months = arrayListOf(
            getString(R.string.january),
            getString(R.string.february),
            getString(R.string.march),
            getString(R.string.april),
            getString(R.string.may),
            getString(R.string.june),
            getString(R.string.july),
            getString(R.string.august),
            getString(R.string.september),
            getString(R.string.october),
            getString(R.string.november),
            getString(R.string.december),
        )

        units = arrayListOf(getString(R.string.km), getString(R.string.miles))

        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {

                usageInfoCard.setCardBackgroundColor(Color.parseColor("#141414"))
                vehicleInfoCard.setCardBackgroundColor(Color.parseColor("#141414"))

                titleTv.setTextColor(Color.parseColor("#7FD7FF"))
                usageInformationTitleTV.setTextColor(Color.parseColor("#7FD7FF"))

                monthInfoTV.setTextColor(Color.parseColor("#B5B5B5"))
                odometerInfoTV.setTextColor(Color.parseColor("#B5B5B5"))
                priceInfo.setTextColor(Color.parseColor("#B5B5B5"))
                mkYearInfoTv.setTextColor(Color.parseColor("#B5B5B5"))
                mkEndInfoTv.setTextColor(Color.parseColor("#B5B5B5"))
                manufacturingTv.setTextColor(Color.parseColor("#B5B5B5"))

                modelMake.boxBackgroundColor = Color.parseColor("#1C1C1C")
                manufacturingYear.boxBackgroundColor = Color.parseColor("#1C1C1C")
                monthPicker.boxBackgroundColor = Color.parseColor("#1C1C1C")
                unitPicker.boxBackgroundColor = Color.parseColor("#1C1C1C")
                odometerRead.boxBackgroundColor = Color.parseColor("#1C1C1C")
                price.boxBackgroundColor = Color.parseColor("#1C1C1C")
                price_new.boxBackgroundColor = Color.parseColor("#1C1C1C")
                equivalencia.boxBackgroundColor = Color.parseColor("#1C1C1C")
                mkYear.boxBackgroundColor = Color.parseColor("#1C1C1C")
                mkEnd.boxBackgroundColor = Color.parseColor("#1C1C1C")

                (monthPicker.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        activity?.applicationContext!!,
                        R.layout.spinner_items_night,
                        months
                    )
                )
                if (prefs!!.getString(Constants.READING_UNIT, "") == getString(R.string.miles)) {
                    unitPicker.editText!!.text = SpannableStringBuilder("mi")
                    (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                        ArrayAdapter(
                            activity?.applicationContext!!,
                            R.layout.spinner_items_night,
                            arrayOf(getString(R.string.miles), getString(R.string.km))
                        )
                    )
                } else {
                    unitPicker.editText!!.text = SpannableStringBuilder("km")
                    (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                        ArrayAdapter(
                            activity?.applicationContext!!,
                            R.layout.spinner_items_night,
                            units
                        )
                    )
                }
            }
            Configuration.UI_MODE_NIGHT_NO -> {

                usageInfoCard.setCardBackgroundColor(Color.parseColor("#FAFAFA"))
                vehicleInfoCard.setCardBackgroundColor(Color.parseColor("#FAFAFA"))

                titleTv.setTextColor(Color.parseColor("#000000")) //Por ver
                usageInformationTitleTV.setTextColor(Color.parseColor("#000000")) // Por ver

                monthInfoTV.setTextColor(Color.parseColor("#666666"))
                odometerInfoTV.setTextColor(Color.parseColor("#666666"))
                priceInfo.setTextColor(Color.parseColor("#666666"))
                mkYearInfoTv.setTextColor(Color.parseColor("#666666"))
                mkEndInfoTv.setTextColor(Color.parseColor("#666666"))
                manufacturingTv.setTextColor(Color.parseColor("#666666"))
//                manufacturingTv.setTextColor(Color.parseColor("#362967"))

                modelMake.boxBackgroundColor = Color.parseColor("#FFFFFF")
                manufacturingYear.boxBackgroundColor = Color.parseColor("#FFFFFF")
                monthPicker.boxBackgroundColor = Color.parseColor("#FFFFFF")
                unitPicker.boxBackgroundColor = Color.parseColor("#FFFFFF")
                odometerRead.boxBackgroundColor = Color.parseColor("#FFFFFF")
                price.boxBackgroundColor = Color.parseColor("#FFFFFF")
                price_new.boxBackgroundColor = Color.parseColor("#FFFFFF")
                equivalencia.boxBackgroundColor = Color.parseColor("#FFFFFF")
                mkYear.boxBackgroundColor = Color.parseColor("#FFFFFF")
                mkEnd.boxBackgroundColor = Color.parseColor("#FFFFFF")

                (monthPicker.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        activity?.applicationContext!!,
                        R.layout.spinner_items,
                        months
                    )
                )

                if (prefs!!.getString(Constants.READING_UNIT, "") == getString(R.string.miles)) {
                    unitPicker.editText!!.text = SpannableStringBuilder("mi")
                    (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                        ArrayAdapter(
                            activity?.applicationContext!!,
                            R.layout.spinner_items,
                            arrayOf(getString(R.string.miles), getString(R.string.km))
                        )
                    )
                } else {
                    unitPicker.editText!!.text = SpannableStringBuilder("km")
                    (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                        ArrayAdapter(
                            activity?.applicationContext!!,
                            R.layout.spinner_items,
                            units
                        )
                    )
                }
            }
        }

        usdCheckBox.isChecked = true

        when (prefs!!.getInt(Constants.CHOSEN_CURRENCY, 0)) {
            0 -> {
                usdCheckBox.isChecked = true
                CHOSEN_CURRENCY = 0
                llOtherCurrencies.visibility = View.GONE
                price.visibility = View.VISIBLE
                priceInfo.text = getString(R.string.costs)
            }
            1 -> {
                cadCheckBox.isChecked = true
                CHOSEN_CURRENCY = 1
                llOtherCurrencies.visibility = View.GONE
                price.visibility = View.VISIBLE
                priceInfo.text = getString(R.string.costs)
            }
            2 -> {
                otherCheckBox.isChecked = true
                CHOSEN_CURRENCY = 2
                llOtherCurrencies.visibility = View.VISIBLE
                price.visibility = View.GONE
                priceInfo.text = getString(R.string.costs_2)
            }
        }

        // To listen for a checkbox's checked/unchecked state changes
        usdCheckBox.setOnCheckedChangeListener { _, _ ->
            // Responds to checkbox being checked/unchecked
            if (usdCheckBox.isChecked) {
                CHOSEN_CURRENCY = 0

                editor!!.putInt(Constants.CHOSEN_CURRENCY, 0)
                editor.apply()

                llOtherCurrencies.visibility = View.GONE
                price.visibility = View.VISIBLE
                priceInfo.text = getString(R.string.costs)
            }
        }

        cadCheckBox.setOnCheckedChangeListener { _, _ ->
            // Responds to checkbox being checked/unchecked
            if (cadCheckBox.isChecked) {
                CHOSEN_CURRENCY = 1

                editor!!.putInt(Constants.CHOSEN_CURRENCY, 1)
                editor.apply()

                llOtherCurrencies.visibility = View.GONE
                price.visibility = View.VISIBLE
                priceInfo.text = getString(R.string.costs)
            }
        }

        //Remove trailing zeros
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

        if (llOtherCurrencies.isVisible && otherCheckBox.isChecked) {
            if (prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f) != 0.0f) {
                vPE.text =
                    SpannableStringBuilder("${prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f)}")
            } else {
                vPE.text =
                    SpannableStringBuilder("0")
            }
        }

        otherCheckBox.setOnCheckedChangeListener { _, _ ->
            // Responds to checkbox being checked/unchecked
            if (otherCheckBox.isChecked) {
                CHOSEN_CURRENCY = 2

                llOtherCurrencies.visibility = View.VISIBLE
                price.visibility = View.GONE

                editor!!.putInt(Constants.CHOSEN_CURRENCY, 2)
                editor.apply()
                if (prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f) != 0.0f) {
                    vPE.text =
                        SpannableStringBuilder("${prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f)}")
                } else {
                    vPE.text =
                        SpannableStringBuilder("0")
                }
                priceInfo.text = getString(R.string.costs_2)
            }
        }

        monthPicker.editText!!.setOnClickListener {
            hideKeyboard()
        }

        unitPicker.editText!!.setOnClickListener {
            hideKeyboard()
        }

        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        addVehicleButton.setOnClickListener {
            vehicleOdometer = vO.editableText.toString().toInt()

            checkFields()

            chosenMonth = if (monthPicker.editText!!.text.toString() == getString(R.string.june)) {
                6L
            } else {
                monthNumber(monthPicker.editText!!.text.toString())
            }

            if (manufacturingYear.editText?.text.isNullOrEmpty()) {
                manufacturingYear.requestFocus()
                val alert = Alerter.create(requireActivity())
                alert.setTitle(getString(R.string.error))
                alert.setText(getString(R.string.provide_year_of_manufacture))
                alert.setIcon(R.drawable.ic_error)
                when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                    Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                }
                alert.setDuration(4500)
                alert.enableSwipeToDismiss()
                alert.show()
                frame_layout.animation = shake
            } else {
                yearValidator()
            }
            if (!fieldEmpty && validYear) {
                if (llOtherCurrencies.isVisible && price_new.editText?.text.isNullOrEmpty() && equivalencia.editText?.text.isNullOrEmpty()) {
                    if (price_new.editText?.text.isNullOrEmpty()) {
                        price_new.error
                        price_new.requestFocus()
                        val alert = Alerter.create(requireActivity())
                        alert.setTitle(getString(R.string.error))
                        alert.setText(getString(R.string.provide_price))
                        alert.setIcon(R.drawable.ic_error)
                        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                            Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                        }
                        alert.setDuration(3000)
                        alert.enableSwipeToDismiss()
                        alert.show()
                        frame_layout.animation = shake
                    } else if (equivalencia.editText?.text.isNullOrEmpty() || equivalencia.editText?.text!!.toString() == "0.0") {
                        equivalencia.error
                        equivalencia.requestFocus()
                        val alert = Alerter.create(requireActivity())
                        alert.setTitle(getString(R.string.error))
                        alert.setText(getString(R.string.provide_equivalence))
                        alert.setIcon(R.drawable.ic_error)
                        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                            Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                        }
                        alert.setDuration(3000)
                        alert.enableSwipeToDismiss()
                        alert.show()
                        frame_layout.animation = shake
                    }
                } else if (llOtherCurrencies.isVisible && price_new.editText?.text!!.isNotEmpty() && equivalencia.editText?.text!!.isNotEmpty()) {
                    vehiclePriceOtherCurrency = vPOC.editableText.toString()
                    vehiclePriceEquivalence = vPE.editableText.toString()
                    newVehicle(
                        modelMake.editText!!.text.toString(),
                        chosenMonth,
                        manufacturingYear.editText!!.text.toString().toLong(),
                        replaceSymboltoInt(vehicleOdometer.toString()),
                        replaceSymboltoDouble(vehiclePriceOtherCurrency),
                        mky.editableText.toString().toInt(),
                        mke.editableText.toString().toInt()
                    )
                }

                if (!llOtherCurrencies.isVisible) {
                    vehiclePrice = vP.editableText.toString().toDouble()
                    newVehicle(
                        modelMake.editText!!.text.toString(),
                        chosenMonth,
                        manufacturingYear.editText!!.text.toString().toLong(),
                        replaceSymboltoInt(vehicleOdometer.toString()),
                        replaceSymboltoDouble(vehiclePrice.toString()),
                        mky.editableText.toString().toInt(),
                        mke.editableText.toString().toInt(),
                    )
                }

                closest = bestOption()
                bestPM = bestPossibleMatch

                val alert = Alerter.create(requireActivity())
                alert.setTitle(R.string.success)
                alert.setText(R.string.vehicle_added)
                alert.setIcon(R.drawable.ic_done)
                when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.green_hard)
                    Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.new_green)
                }
                alert.setDuration(1000)
                alert.enableSwipeToDismiss()
                alert.show()
                chipNavBar.setItemSelected(R.id.list_menu, true)
            }

            val unitPicked = unitPicker.editText!!.text.toString()

            when (unitPicked) {
                getString(R.string.km) -> {
                    editor!!.putString(Constants.READING_UNIT, getString(R.string.km))
                    editor.apply()
                }
                else -> {
                    editor!!.putString(Constants.READING_UNIT, getString(R.string.miles))
                    editor.apply()
                }
            }

        }

        cancelButton.setOnClickListener {
            if (Vehicle.vehicles.size == 0) {
                activity?.finish()
            } else {
                @Suppress("DEPRECATION")
                fragmentManager?.beginTransaction()!!
                    .replace(R.id.frameLayout, ListVehicleFragment())
                    .addToBackStack("vehicleList").commit()
            }
        }

        return v
    }

    private fun isKm(unitPicked: String): Boolean {
        return unitPicked == getString(R.string.km)
    }

    private fun newVehicle(
        modelMake: String, selectedMonth: Long, selectedYear: Long,
        odometerReading: Int, price: Double, mkYearly: Int, mkEnd: Int
    ) {

        if (CHOSEN_CURRENCY == 2 || prefs!!.getInt(Constants.CHOSEN_CURRENCY, 0) == 2) {
            putSavedCurrency(
                vPE.editableText.toString().toFloat(),
                vehiclePriceOtherCurrency.toFloat()
            )
        }

        val odometerReadEt = replaceSymbol(vehicleOdometer.toString())
        val priceET = replaceSymbol(vehiclePrice.toString().trim { it <= ' ' })

        if (llOtherCurrencies.isVisible) {
            val newPriceET = replaceSymbol(vehiclePriceOtherCurrency.trim { it <= ' ' })
            val equivalenceET = replaceSymbol(vehiclePriceEquivalence)
            getVin(vehicleInMonths(), odometerReadEt, 0f, newPriceET, equivalenceET)
        } else {
            getVin(vehicleInMonths(), odometerReadEt, priceET, 0f, 0f)
        }

        ucn_value = ucn(
            replaceSymboltoInt(odometerReading.toString()),
            replaceSymbol(price.toString()).toDouble(),
            mkEnd.toFloat(),
            mkYearly.toFloat()
        )
        cocienteVehiculos(
            vin_value.toFloat(), ucn(
                replaceSymboltoInt(odometerReading.toString()),
                replaceSymbol(price.toString()).toDouble(),
                mkEnd.toFloat(),
                mkYearly.toFloat()
            )
        )
        vuRelation(
            vin_value.toFloat(), ucn(
                replaceSymboltoInt(odometerReading.toString()),
                replaceSymbol(price.toString()).toDouble(),
                mkEnd.toFloat(),
                mkYearly.toFloat()
            )
        )

        Vehicle.addVehicle(
            modelMake,
            selectedMonth,
            selectedYear,
            odometerReading,
            price,
            mkYearly,
            mkEnd,
            ucn(
                replaceSymboltoInt(odometerReading.toString()),
                replaceSymbol(price.toString()).toDouble(), mkEnd.toFloat(), mkYearly.toFloat()
            ),
            vin_value.toFloat(),
            cociente_temp,
            vu_relation_value,
            isKm(unitPicker.editText!!.text.toString())
        )
    }

    private fun currencyConverter(equivalenc: Float, priceNew: Long): Double {
        return if (equivalenc >= 1) {
            (priceNew / equivalenc).toDouble()
        } else {
            (priceNew * equivalenc).toDouble()
        }
    }

    private fun putSavedCurrency(thisPrice: Float, equivalenc: Float): Double {
        var savedValue = 0.0
        val editor = prefs!!.edit()

        if (prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f)
                .toString() != vehiclePriceEquivalence
        ) {
            editor.putFloat(Constants.SAVED_CURRENCY, replaceSymbol(vehiclePriceEquivalence))
            editor.apply()
            return currencyConverter(equivalenc, thisPrice.toLong())
        }
        if (prefs!!.getFloat(Constants.SAVED_CURRENCY, 0f).toString().isNotEmpty()) {
            savedValue = currencyConverter(equivalenc, thisPrice.toLong())
        }
        return savedValue
    }

    private fun yearValidator() {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        if (manufacturingYear.editText!!.text.toString().toInt() in 1920..currentYear) {
            validYear = true
        } else {
            val alert = Alerter.create(requireActivity())
            alert.setTitle(getString(R.string.error))
            alert.setText(getString(R.string.must_provide_a_year_between) + currentYear)
            alert.setIcon(R.drawable.ic_error)
            when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
            }
            alert.setDuration(4500)
            alert.enableSwipeToDismiss()
            alert.show()
            manufacturingYear.requestFocus()
            frame_layout.animation = shake
        }
    }

    private fun hideKeyboard() {
        val parentLayout = activity?.findViewById<View>(android.R.id.content)
        val imm =
            activity?.applicationContext!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(parentLayout!!.windowToken, 0)
    }

    private fun monthTranslator(monthName: String): String {
        return when (monthName) {
            "JANUARY" -> "ENERO"
            "FEBRUARY" -> "FEBRERO"
            "APRIL" -> "ABRIL"
            "MARCH" -> "MARZO"
            "MAY" -> "MAYO"
            "JUNE" -> "JUNIO"
            "JULY" -> "JULIO"
            "AUGUST" -> "AGOSTO"
            "SEPTEMBER" -> "SEPTIEMBRE"
            "OCTOBER" -> "OCTUBRE"
            "NOVEMBER" -> "NOVIEMBRE"
            "DECEMBER" -> "DICIEMBRE"
            else -> "JUNIO"
        }
    }

    private fun monthNumber(monthName: String): Long {
        return when (monthName) {
            getString(R.string.january) -> {
                1L
            }
            getString(R.string.february) -> {
                2L
            }
            getString(R.string.march) -> {
                3L
            }
            getString(R.string.april) -> {
                4L
            }
            getString(R.string.may) -> {
                5L
            }
            getString(R.string.june) -> {
                6L
            }
            getString(R.string.july) -> {
                7L
            }
            getString(R.string.august) -> {
                8L
            }
            getString(R.string.september) -> {
                9L
            }
            getString(R.string.october) -> {
                10L
            }
            getString(R.string.november) -> {
                11L
            }
            getString(R.string.december) -> {
                12L
            }
            else -> {
                6L
            }
        }
    }

    private fun checkFields() {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)
        if (modelMake.editText!!.text.toString()
                .isNotEmpty() && manufacturingYear.editText!!.toString()
                .isNotEmpty() && odometerRead.editText!!.text.isNotEmpty()
        ) {
            if (llOtherCurrencies.visibility == View.GONE && price.editText!!.text.isNotEmpty()) {
                fieldEmpty = false
                if (price.editText?.text!!.isEmpty()) {
                    price.requestFocus()
                    price.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_price))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
            } else if (llOtherCurrencies.visibility == View.VISIBLE && price_new.editText!!.text.isNotEmpty() && equivalencia.editText!!.text.isNotEmpty()) {
                fieldEmpty = false
                if (price_new.editText?.text!!.isEmpty()) {
                    price_new.requestFocus()
                    price_new.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_price))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                } else if (equivalencia.editText?.text!!.isEmpty()) {
                    equivalencia.requestFocus()
                    equivalencia.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_equivalence))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(4500)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
            }
        } else {
            fieldEmpty = true
            when {
                modelMake.editText?.text.toString().isEmpty() -> {
                    modelMake.requestFocus()
                    modelMake.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_make_and_model))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
                manufacturingYear.editText?.toString().isNullOrEmpty() -> {
                    manufacturingYear.requestFocus()
                    manufacturingYear.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_year_of_manufacture))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
                price.editText?.text.isNullOrEmpty() -> {
                    price.requestFocus()
                    price.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_price))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
                odometerRead.editText?.text.isNullOrEmpty() -> {
                    odometerRead.requestFocus()
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.provide_odometer_reading))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(4500)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
                else -> {
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle(getString(R.string.error))
                    alert.setText(getString(R.string.fill_all_the_fields))
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(3000)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                }
            }

        }
    }

    private fun vehicleInMonths(): Long {
        val currentDate = LocalDate.now()

        val pickedYear = manufacturingYear.editText!!.text.toString().toInt()
        val pickedMonth = monthPicker.editText!!.text.toString()
        val deviceYear = currentDate.year
        val deviceMonth = when (Locale.getDefault().displayLanguage) {
            Locale.getDefault().getDisplayLanguage(Locale.forLanguageTag("es")) -> {
                monthTranslator(currentDate.month.toString())
            }
            else -> {
                currentDate.month
            }
        }
        return ((deviceYear * 12) + monthNumber(deviceMonth.toString())) -
                ((pickedYear * 12) + monthNumber(pickedMonth))
    }

    private fun ucn(odometerRead: Int, price: Double, mkEnd: Float, mkYearly: Float): Float {

        val usableKm = mkEnd - odometerRead
        val usableYears = usableKm / mkYearly
        val ucnYearly = price / usableYears
        val ucnMonth = ucnYearly / 12

        return if (!llOtherCurrencies.isVisible) {
            ucnMonth.toFloat()
        } else {
            (price / (equivalencia.editText!!.text.toString()
                .toFloat() * usableYears)).toFloat() / 12f
        }
    }

    private fun vuRelation(vin_val: Float, ucn_val: Float) {
        vu_relation_value = vin_val / ucn_val
    }

    private fun bestOption(): Float {
        var highestV = 0f
        var lowestUcn = 999999999f

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

        bestOptionList.sortDescending()

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
        return binarySearch(bestOptionList, bestPossibleMatch)
    }
}