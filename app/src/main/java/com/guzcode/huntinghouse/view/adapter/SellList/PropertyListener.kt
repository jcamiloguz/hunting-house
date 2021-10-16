package com.guzcode.huntinghouse.view.adapter.SellList

import com.guzcode.huntinghouse.model.Property

interface PropertyListener {
    fun onHouseClicked(property: Property, position: Int)
}