package com.guzcode.huntinghouse.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.view.adapter.PropertyListener

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
        val storageReference = FirebaseStorage.getInstance().getReference("images/${property.image}")
        storageReference.downloadUrl.addOnSuccessListener {
            Log.e("image",it.toString())
            Glide.with( holder.tvHouseId.context).load(it).placeholder(R.drawable.placeholder_image).into(holder.ivItemHouse)

        }


        holder.itemView.setOnClickListener{
            propertyListener.onHouseClick(property,position)
        }
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
        val ivItemHouse = itemView.findViewById<ImageView>(R.id.ivItemHouse)
    }
}