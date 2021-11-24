package com.guzcode.huntinghouse.view.ui.buy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.network.FirestoreService
import kotlinx.android.synthetic.main.fragment_buy_details.*
import kotlinx.android.synthetic.main.fragment_sell_edit.*
import kotlinx.android.synthetic.main.fragment_sell_edit.tbEdit
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage


class BuyDetailsFragment : DialogFragment(), OnMapReadyCallback  {
    var property = Property()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.fullscreenDialogStyle)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        tbEdit.navigationIcon= ContextCompat.getDrawable(view.context, R.drawable.close)
        tbEdit.setNavigationOnClickListener{
            dismiss()
        }
        val property =arguments?.getSerializable("property") as Property
        tvDetailTitle.setText(property.title)
        tvDetailDescription.setText(property.description)
        tvDetailSize.setText("Size: "+property.area)
        val storageReference = FirebaseStorage.getInstance().getReference("images/${property.image}")
        storageReference.downloadUrl.addOnSuccessListener {
        Log.e("image",it.toString())
            Glide.with(this).load(it).placeholder(R.drawable.placeholder_image).into(ivDetailImage)

        }
        btnDetailUserCall.setOnClickListener{

            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:3163619550")
            startActivity(intent)
            dismiss()
        }
        val map =childFragmentManager.findFragmentById(R.id.detailsMap) as SupportMapFragment
        map.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val zoom = 16f
        val property =arguments?.getSerializable("property") as Property
        Log.e("TEST",property.location.latitude.toString())
        val centerMap = LatLng(property.location.latitude,property.location.longitude)

        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

        val mark = LatLng(property.location.latitude,property.location.longitude)

        val markerOption = MarkerOptions()
        markerOption.position(mark)
        markerOption.title(property.title)
        markerOption.visible(true)
        googleMap.addMarker(markerOption)
    }
}