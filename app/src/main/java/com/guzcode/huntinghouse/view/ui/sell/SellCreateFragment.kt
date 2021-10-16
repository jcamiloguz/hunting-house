package com.guzcode.huntinghouse.view.ui.sell

import android.os.Bundle
import com.guzcode.huntinghouse.model.Property
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.local.ReferenceSet
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.network.FirestoreService
import kotlinx.android.synthetic.main.fragment_sell_create.*
import kotlinx.android.synthetic.main.fragment_sell_edit.*
import kotlinx.android.synthetic.main.fragment_sell_edit.tbEdit

class SellCreateFragment : DialogFragment() {
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
        return inflater.inflate(R.layout.fragment_sell_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbCreate.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.close)
        tbCreate.setNavigationOnClickListener{
            dismiss()
        }
        btnCreatePublish.setOnClickListener{
            var doc =fireStoreService.firebaseFireStore.document("users/3i7q3geApTqTpGBYqLEE")
            property.userID=doc
            property.description=etCreateDescription.text.toString()
            property.title=etCreateTitle.text.toString()
            property.area=etCreateArea.text.toString()
            property.createdAt =Timestamp.now()

            property.location=GeoPoint(1.2,0.2)

            fireStoreService.createProperty(property)
            dismiss()
        }

    }
}