package com.techpig.bestoptioning.models

class DonateOptionModel(private var id: String, private var title: String) {

    companion object {
        val listOfIDs: ArrayList<DonateOptionModel> = arrayListOf(
            DonateOptionModel("one_dollar", "$1 USD"),
            DonateOptionModel("three_dollars", "$3 USD"),
            DonateOptionModel("five_dollars", "$5 USD"),
            DonateOptionModel("ten_dollars", "$10 USD")
        )
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String {
        return id
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getTitle(): String {
        return title
    }
}
