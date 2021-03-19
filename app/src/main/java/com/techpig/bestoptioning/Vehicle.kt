package com.techpig.bestoptioning

class Vehicle(vehicleObject: VehicleObject) {

    companion object {
        val vehicles: ArrayList<VehicleObject> = ArrayList()

        fun addVehicle(
            modelMake: String, selectedMonth: Long, selectedYear: Long,
            odometerReading: Int, price: Double, mkYearly: Int, mkEnd: Int, ucin: Float, vin: Float, cociente: Float, vu_relation: Float
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
                    vu_relation
                )
            )
            return vehicles
        }
        fun removeVehicle () {
            vehicles.removeAll(vehicles)
        }
    }
}