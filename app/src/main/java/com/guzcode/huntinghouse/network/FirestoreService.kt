package com.guzcode.huntinghouse.network

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.guzcode.huntinghouse.model.Property

const val PROPERTIES_COLLECTION_NAME = "properties"
const val USERS_COLLECTION_NAME = "users"

class FirestoreService{
    val firebaseFireStore =FirebaseFirestore.getInstance()
    val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()
    init {
        firebaseFireStore.firestoreSettings = settings
    }
    fun getProperties(callback: Callback<List<Property>>){
        firebaseFireStore.collection(PROPERTIES_COLLECTION_NAME)
            .orderBy("createdAt")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result){
                    val list = result.toObjects(Property::class.java)
                    list
                    callback.onSuccess(list)
                    break

                }
            }
    }
    fun getPropertiesByUser(callback: Callback<List<Property>>,userID: String){
        var userPath=firebaseFireStore.collection(USERS_COLLECTION_NAME)
            .document(userID)

        firebaseFireStore.collection(PROPERTIES_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for ((index, doc) in result.withIndex()){
                    val list = result.toObjects(Property::class.java)
                    callback.onSuccess(list)
                    break

                }
            }
    }
    fun putPropertyByUser(property: Property){
        var title = property.title
        var userPath=firebaseFireStore.collection(PROPERTIES_COLLECTION_NAME).document(property.documentId.toString())
            .set(property)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written ")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }
    fun deleteProperty(property: Property){
        firebaseFireStore.collection(PROPERTIES_COLLECTION_NAME).document(property.documentId.toString())
            .delete()

    }
    fun createProperty(property: Property){
        firebaseFireStore.collection(PROPERTIES_COLLECTION_NAME)
            .add(property)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
//    fun getUsers(callback: Callback<List<User>>){
//
//    }
}
