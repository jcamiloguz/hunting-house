package com.guzcode.huntinghouse.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guzcode.huntinghouse.model.Property
import com.guzcode.huntinghouse.network.Callback
import com.guzcode.huntinghouse.network.FirestoreService
import java.lang.Exception

class PropertyByUserViewModel: ViewModel() {
    val fireStoreService = FirestoreService()
    var listProperties: MutableLiveData<List<Property>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    fun refresh(){
        getPropertiesFromFirebase()
    }
    fun getPropertiesFromFirebase(){
        fireStoreService.getPropertiesByUser(object: Callback<List<Property>>{
            override fun onSuccess(result: List<Property>?) {
                listProperties.postValue(result)
                proccessFinished()
            }

            override fun onFailed(exception: Exception) {
                proccessFinished()
            }
        },"3i7q3geApTqTpGBYqLEE")
    }
    fun proccessFinished(){
        isLoading.value =true
    }
}