package com.techpig.bestoptioning.models

class DonateOptionModel(private var title: String) {

    fun setTitle(title: String) {
        this.title = title
    }

    fun getTitle(): String {
        return title
    }
}
