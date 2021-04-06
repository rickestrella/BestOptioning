package com.techpig.bestoptioning

class VehicleObject(
    private var modelMake: String,
    private var month: Long,
    private var year: Long,
    private var odometer: Int,
    private var price: Double,
    private var mkYear: Int,
    private var mkEnd: Int,
    private var ucn: Float,
    private var vin: Float,
    private var cociente: Float,
    private var vu_relation: Float,
    private var isKmPicked: Boolean
) {

    fun getModelMake(): String {
        return modelMake
    }

    fun setModelMake(modelMake: String) {
        this.modelMake = modelMake
    }

    fun getMonth(): Long {
        return month
    }

    fun setMonth(month: Long) {
        this.month = month
    }

    fun getYear(): Long {
        return year
    }

    fun setYear(year: Long) {
        this.year = year
    }

    fun getOdometer(): Int {
        return odometer
    }

    fun setOdometer(odometer: Int) {
        this.odometer = odometer
    }

    fun getPrice(): Double {
        return price
    }

    fun setPrice(price: Double) {
        this.price = price
    }

    fun getUcn(): Float {
        return ucn
    }

    fun setUcn(ucn: Float) {
        this.ucn = ucn
    }

    fun getVin(): Float {
        return vin
    }

    fun setVin(vin: Float) {
        this.vin = vin
    }

    fun getVu_relation(): Float {
        return vu_relation
    }

    fun setVu_relation(vu_relation: Float) {
        this.vu_relation = vu_relation
    }

    fun getIsKmPicked(): Boolean {
        return isKmPicked
    }

    fun setIsKmPicked(isKmPicked: Boolean) {
        this.isKmPicked = isKmPicked
    }
}