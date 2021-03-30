package com.techpig.bestoptioning

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
import androidx.fragment.app.Fragment
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputLayout
import com.tapadoo.alerter.Alerter
import com.techpig.bestoptioning.ContainerActivity.Companion.chipNavBar
import com.techpig.bestoptioning.ContainerActivity.Companion.frame_layout
import java.time.LocalDate
import java.util.*
import kotlin.math.pow

class AddVehicleFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private val currentYear = calendar[Calendar.YEAR]
    private val currentMonth = calendar[Calendar.MONTH]
    private val currentDayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    private var chosenMonth: Long = 6
    private var bestPossibleMatch = 0f

    private var validYear = false

    private var months = ArrayList<String>()
    private var units = ArrayList<String>()

    private var fieldEmpty = true

    private var CHOSEN_CURRENCY = 0

    //SharedPreferences
    private var prefs: SharedPreferences? = null
    private val PREFS_FILENAME = "com.techpig.bestoptioning.prefs"

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

        months = arrayListOf(
            "JANUARY",
            "FEBRUARY",
            "MARCH",
            "APRIL",
            "MAY",
            "JUNE",
            "JULY",
            "AUGUST",
            "SEPTEMBER",
            "OCTOBER",
            "NOVEMBER",
            "DECEMBER",
        )


        units = arrayListOf("km", "mi")

        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {

                usageInfoCard.setCardBackgroundColor(Color.parseColor("#141414"))
                vehicleInfoCard.setCardBackgroundColor(Color.parseColor("#141414"))

                titleTv.setTextColor(Color.parseColor("#FFDA5A00"))
                usageInformationTitleTV.setTextColor(Color.parseColor("#FFDA5A00"))

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

                (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        activity?.applicationContext!!,
                        R.layout.spinner_items_night,
                        units
                    )
                )
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

                (unitPicker.editText as? AutoCompleteTextView)?.setAdapter(
                    ArrayAdapter(
                        activity?.applicationContext!!,
                        R.layout.spinner_items,
                        units
                    )
                )
            }
        }

        usdCheckBox.isChecked = true

        prefs = activity?.applicationContext!!.getSharedPreferences(
            PREFS_FILENAME,
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = prefs!!.edit()

        when (prefs!!.getInt("CHOSEN_CURRENCY", 0)) {
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

                editor!!.putInt("CHOSEN_CURRENCY", 0)
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

                editor!!.putInt("CHOSEN_CURRENCY", 1)
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

        //TODO Mantener millas o km con sharedPreferences

        if (CHOSEN_CURRENCY == 2) {
            val sharedPreferencesSavedValue = prefs!!.getFloat("CURRENCY_EQ", 0f)
            if (prefs!!.getFloat("CURRENCY_EQ", 0f).toString().isNotEmpty()) {
                equivalencia.editText?.setText("${trimTrailingZero(sharedPreferencesSavedValue.toString())}")
            }
        }
        otherCheckBox.setOnCheckedChangeListener { _, _ ->
            // Responds to checkbox being checked/unchecked
            if (otherCheckBox.isChecked) {
                CHOSEN_CURRENCY = 2

                llOtherCurrencies.visibility = View.VISIBLE
                price.visibility = View.GONE

                editor!!.putInt("CHOSEN_CURRENCY", 2)
                editor.apply()
                if (prefs!!.getFloat("CURRENCY_EQ", 0f) != 0.0f) {
                    equivalencia.editText!!.text =
                        SpannableStringBuilder("${prefs!!.getFloat(" CURRENCY_EQ ", 0f)}")
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
            checkFields()
            chosenMonth = monthNumber(monthPicker.editText!!.text.toString())
            if (manufacturingYear.editText?.text.isNullOrEmpty()) {
                manufacturingYear.requestFocus()
                val alert = Alerter.create(requireActivity())
                alert.setTitle("Error")
                alert.setText("You must provide the manufacturing year")
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
                        alert.setTitle("Error")
                        alert.setText("You must provide a price")
                        alert.setIcon(R.drawable.ic_error)
                        when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                            Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                            Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                        }
                        alert.setDuration(4500)
                        alert.enableSwipeToDismiss()
                        alert.show()
                        frame_layout.animation = shake
                    } else if (equivalencia.editText?.text.isNullOrEmpty() || equivalencia.editText?.text!!.toString() == "0.0") {
                        equivalencia.error
                        equivalencia.requestFocus()
                        val alert = Alerter.create(requireActivity())
                        alert.setTitle("Error")
                        alert.setText("You must provide the equivalence of a dollar in your currency")
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
                } else if (llOtherCurrencies.isVisible && price_new.editText?.text!!.isNotEmpty() && equivalencia.editText?.text!!.isNotEmpty()) {
                    newVehicle(
                        modelMake.editText!!.text.toString(),
                        chosenMonth,
                        manufacturingYear.editText!!.text.toString().toLong(),
                        odometerRead.editText?.text.toString().toInt(),
                        price_new.editText?.text.toString().toDouble(),
                        mkYear.editText?.text.toString().toInt(),
                        mkEnd.editText?.text.toString().toInt()
                    )
                }

                if (!llOtherCurrencies.isVisible) {
                    newVehicle(
                        modelMake.editText!!.text.toString(),
                        chosenMonth,
                        manufacturingYear.editText!!.text.toString().toLong(),
                        odometerRead.editText?.text.toString().toInt(),
                        Integer.parseInt(price.editText?.text.toString()).toDouble(),
                        mkYear.editText?.text.toString().toInt(),
                        mkEnd.editText?.text.toString().toInt(),
                    )
                }
                ContainerActivity.closest = bestOption()
                ContainerActivity.bestPM = bestPossibleMatch

                fragmentManager?.beginTransaction()!!
                    .replace(R.id.frameLayout, ListVehicleFragment()).addToBackStack("listVehicle")
                    .commit()

                val alert = Alerter.create(requireActivity())
                alert.setTitle("Success!")
                alert.setText("Your vehicle has been added")
                alert.setIcon(R.drawable.ic_done)
                when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.green_hard)
                    Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.green_A700)
                }
                alert.setDuration(4500)
                alert.enableSwipeToDismiss()
                alert.show()
                chipNavBar.setItemSelected(R.id.list_menu, true)
            }
        }

        cancelButton.setOnClickListener {
            if (Vehicle.vehicles.size == 0) {
                activity?.finish()
            } else {
                fragmentManager?.beginTransaction()!!
                    .replace(R.id.frameLayout, ListVehicleFragment())
                    .addToBackStack("vehicleList").commit()
            }
        }

        return v
    }

    private fun newVehicle(
        modelMake: String, selectedMonth: Long, selectedYear: Long,
        odometerReading: Int, price: Double, mkYearly: Int, mkEnd: Int
    ) {

        if (CHOSEN_CURRENCY == 2 || prefs!!.getInt("CHOSEN_CURRENCY", 0) == 2) {
            putSavedCurrency(
                equivalencia.editText?.text.toString().toFloat(),
                price_new.editText?.text.toString().toFloat()
            )
        }

        vin()
        ContainerActivity.ucn_value = ucn(
            odometerReading,
            price,
            mkEnd.toFloat(),
            mkYearly.toFloat()
        )
        cocienteVehiculos(
            ContainerActivity.vin_value, ucn(
                odometerReading,
                price,
                mkEnd.toFloat(),
                mkYearly.toFloat()
            )
        )
        vuRelation(
            ContainerActivity.vin_value, ucn(
                odometerReading,
                price,
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
            ucn(odometerReading, price, mkEnd.toFloat(), mkYearly.toFloat()),
            ContainerActivity.vin_value,
            ContainerActivity.cociente_temp,
            ContainerActivity.vu_relation_value
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
        if (prefs!!.getFloat("CURRENCY_EQ", 0f)
                .toString() != equivalencia.editText?.text.toString()
        ) {
            editor.putFloat("CURRENCY_EQ", equivalencia.editText?.text.toString().toFloat())
            editor.apply()
            return currencyConverter(equivalenc, thisPrice.toLong())
        }
        if (prefs!!.getFloat("CURRENCY_EQ", 0f).toString().isNotEmpty()) {
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
            alert.setTitle("Error")
            alert.setText("You must provide a year between 1920 and $currentYear")
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

    private fun monthNumber(monthName: String): Long {
        return when (monthName) {
            "JANUARY" -> {
                1L
            }
            "FEBRUARY" -> {
                2L
            }
            "MARCH" -> {
                3L
            }
            "APRIL" -> {
                4L
            }
            "MAY" -> {
                5L
            }
            "JUNE" -> {
                6L
            }
            "JULY" -> {
                7L
            }
            "AUGUST" -> {
                8L
            }
            "SEPTEMBER" -> {
                9L
            }
            "OCTOBER" -> {
                10L
            }
            "NOVEMBER" -> {
                11L
            }
            "DECEMBER" -> {
                12L
            }
            else -> {
                6
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
                    alert.setTitle("Error")
                    alert.setText("You must provide a price")
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
            } else if (llOtherCurrencies.visibility == View.VISIBLE && price_new.editText!!.text.isNotEmpty() && equivalencia.editText!!.text.isNotEmpty()) {
                fieldEmpty = false
                if (price_new.editText?.text!!.isEmpty()) {
                    price_new.requestFocus()
                    price_new.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle("Error")
                    alert.setText("You must provide a price")
                    alert.setIcon(R.drawable.ic_error)
                    when (context?.resources!!.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                        Configuration.UI_MODE_NIGHT_YES -> alert.setBackgroundColorRes(R.color.crimson)
                        Configuration.UI_MODE_NIGHT_NO -> alert.setBackgroundColorRes(R.color.crimson_day)
                    }
                    alert.setDuration(4500)
                    alert.enableSwipeToDismiss()
                    alert.show()
                    frame_layout.animation = shake
                } else if (equivalencia.editText?.text!!.isEmpty()) {
                    equivalencia.requestFocus()
                    equivalencia.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle("Error")
                    alert.setText("You must provide the equivalence of a dollar in your currency")
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
                    alert.setTitle("Error")
                    alert.setText("You must provide make and model")
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
                manufacturingYear.editText?.toString().isNullOrEmpty() -> {
                    manufacturingYear.requestFocus()
                    manufacturingYear.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle("Error")
                    alert.setText("You must provide the manufacturing year")
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
                price.editText?.text.isNullOrEmpty() -> {
                    price.requestFocus()
                    price.error
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle("Error")
                    alert.setText("You must provide the price")
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
                odometerRead.editText?.text.isNullOrEmpty() -> {
                    odometerRead.requestFocus()
                    val alert = Alerter.create(requireActivity())
                    alert.setTitle("Error")
                    alert.setText("You must provide the odometer reading")
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
                    alert.setTitle("Error")
                    alert.setText("You must fill all the fields")
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

        }
    }

    private fun vehicleInMonths(): Long {
        val date = LocalDate.of(currentYear, (currentMonth + 1), 2)
        val newDate = date.minusYears(manufacturingYear.editText!!.text.toString().toLong())
            .minusMonths((chosenMonth)).minusDays(0)

        return ((newDate.year * 12) + monthNumber(newDate.month.toString()))
    }

    private fun vin() {
        val value = if (!llOtherCurrencies.isVisible) {
            (10.0.pow(13.0) / (vehicleInMonths() * odometerRead.editText!!.text.toString()
                .toFloat() * price.editText!!.text.toString().toInt())).toFloat()
        } else {
            ((10.0.pow(13.0) * equivalencia.editText!!.text.toString()
                .toFloat()) / (vehicleInMonths() * odometerRead.editText!!.text.toString()
                .toFloat() *
                    price_new.editText!!.text.toString().toFloat()))
        }
        ContainerActivity.vin_value = String.format("%.6f", value).toFloat()
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

    private fun cocienteVehiculos(vin: Float, ucn: Float) {
        val tmp = vin / ucn
        ContainerActivity.cociente_temp = String.format("%.4f", tmp).toFloat()
    }

    private fun vuRelation(vin_val: Float, ucn_val: Float) {
        ContainerActivity.vu_relation_value = vin_val / ucn_val
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