package com.guzcode.huntinghouse.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property

class PropertyAdapter(val propertyListener: PropertyListener) : RecyclerView.Adapter<PropertyAdapter.ViewHolder>() {
    var listProperty = ArrayList<Property>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(LayoutInflater.from(parent.context).inflate(
R.layout.item_house,parent,false))

    override fun onBindViewHolder(holder: PropertyAdapter.ViewHolder, position: Int) {
        val property = listProperty[position] as Property

        holder.tvHouseTitle.text = property.title
        holder.tvHouseResume.text = property.description
        holder.tvHouseArea.text = property.area
        holder.tvHouseId.text = property.documentId
    }

    override fun getItemCount() = listProperty.size
    fun updateData(data: List<Property>){
        listProperty.clear()
        listProperty.addAll(data)
        notifyDataSetChanged()

    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvHouseTitle = itemView.findViewById<TextView>(R.id.tvItemHouseTitle)
        val tvHouseResume = itemView.findViewById<TextView>(R.id.tvItemHouseResume)
        val tvHouseArea = itemView.findViewById<TextView>(R.id.tvItemHouseArea)
        val tvHouseId = itemView.findViewById<TextView>(R.id.tvItemHouseId)
    }
}