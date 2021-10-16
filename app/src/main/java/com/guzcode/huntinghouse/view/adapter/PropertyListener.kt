package com.guzcode.huntinghouse.view.adapter

import com.guzcode.huntinghouse.model.Property
import java.text.FieldPosition

interface PropertyListener {
    fun onHouseClick(property: Property, position: Int)
}