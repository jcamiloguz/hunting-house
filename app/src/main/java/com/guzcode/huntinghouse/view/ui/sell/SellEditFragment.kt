package com.guzcode.huntinghouse.view.ui.sell

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.network.FirestoreService
import kotlinx.android.synthetic.main.fragment_sell_create.*
import kotlinx.android.synthetic.main.fragment_sell_edit.*

class SellEditFragment : DialogFragment() {
    val fireStoreService = FirestoreService()
    var property =Property()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.fullscreenDialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbEdit.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.close)
        tbEdit.setNavigationOnClickListener{
            dismiss()
        }
        val property =arguments?.getSerializable("property") as Property
        etEditTitle.setText(property.title)
        etEditDescription.setText(property.description)
        etEditArea.setText(property.area)
        btnDeletePublish.setOnClickListener{
            fireStoreService.deleteProperty(property)
            dismiss()
        }
        btnEditPublish.setOnClickListener{
            property.description=etEditDescription.text.toString()
            property.title=etEditTitle.text.toString()
            property.area=etEditArea.text.toString()
            fireStoreService.putPropertyByUser(property)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }
}