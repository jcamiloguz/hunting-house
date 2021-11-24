package com.guzcode.huntinghouse.view.ui.sell

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.guzcode.huntinghouse.model.Property
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.local.ReferenceSet
import com.google.firebase.storage.FirebaseStorage
import com.guzcode.huntinghouse.R
import com.guzcode.huntinghouse.network.FirestoreService
import kotlinx.android.synthetic.main.fragment_sell_create.*
import kotlinx.android.synthetic.main.fragment_sell_edit.*
import kotlinx.android.synthetic.main.fragment_sell_edit.tbEdit
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class SellCreateFragment : DialogFragment(), OnMapReadyCallback {
    val fireStoreService = FirestoreService()
    var property =Property()
    lateinit var imageUri : Uri
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.fullscreenDialogStyle)

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getActivity())


    }
    private  fun checkLocationPermission(): Boolean {

        if (getContext()?.let { ActivityCompat.checkSelfPermission(it, android.Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED &&
            getContext()?.let { ActivityCompat.checkSelfPermission(it, android.Manifest.permission.ACCESS_COARSE_LOCATION) }!= PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions( //Method of Fragment
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101
            );
        }
        return true
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



                val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                val now = Date()
                var fileName = property.title + "-"+ formatter.format(now)
                val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
                storageReference.putFile(imageUri).
                addOnSuccessListener {
                    Toast.makeText(context,"Succesful upload",Toast.LENGTH_SHORT).show()
                    dismiss()
                    Log.e("TEST",storageReference.name.toString())
                    property.image=storageReference.name.toString()
                    fireStoreService.createProperty(property)
                }.addOnFailureListener{
                    Toast.makeText(context,"Error upload",Toast.LENGTH_SHORT).show()
                }




        }
        bntAddImage.setOnClickListener{
            choosePicture()
        }
        val map =childFragmentManager.findFragmentById(R.id.createMap) as SupportMapFragment
        map.getMapAsync(this)

    }
    private fun choosePicture(){
        val  intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent,"Select Image from here..."),100)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==100 && resultCode==RESULT_OK && data != null){
            imageUri=data?.data!!
            Log.e("IMAGE", imageUri.toString())
            imagePreviewCreate.setImageURI(imageUri)
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        btnLocationCreate.setOnClickListener{
            if(checkLocationPermission()){
            fusedLocationProviderClient.lastLocation.addOnCompleteListener() {
                if(it!=null){
                    Log.e("TEST", it.result.latitude.toString())
                    Log.e("TEST", it.result.longitude.toString())
                    Toast.makeText(context, "Location get it", Toast.LENGTH_SHORT).show()
                    val zoom = 16f
                    val centerMap = LatLng(it.result.latitude,it.result.longitude)

                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom))

                    val mark = LatLng(it.result.latitude,it.result.longitude)

                    val markerOption = MarkerOptions()
                    markerOption.position(mark)
                    markerOption.title("current location")
                    markerOption.visible(true)
                    googleMap.addMarker(markerOption)
                    property.location= GeoPoint(it.result.latitude,it.result.longitude)

                }
            }
            }
        }
    }

}