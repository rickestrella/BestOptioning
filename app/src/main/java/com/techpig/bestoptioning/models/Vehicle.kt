package com.techpig.bestoptioning.models

class Vehicle {

    companion object {
        val vehicles: ArrayList<VehicleObject> = ArrayList()

        fun addVehicle(
            modelMake: String, selectedMonth: Long, selectedYear: Long,
            odometerReading: Int, price: Double, mkYearly: Int, mkEnd: Int, ucin: Float, vin: Float, cociente: Float, vu_relation: Float, isKmPicked: Boolean
        ): ArrayList<VehicleObject> {
            vehicles.add(
                VehicleObject(
                    modelMake,
                    selectedMonth,
                    selectedYear,
                    odometerReading,
                    price,
                    mkYearly,
                    mkEnd,
                    ucin,
                    vin,
                    cociente,
                    vu_relation,
                    isKmPicked
                )
            )
            return vehicles
        }
        fun removeVehicle () {
            vehicles.removeAll(vehicles)
        }
    }
}