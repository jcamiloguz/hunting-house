package com.guzcode.huntinghouse.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.util.*

class Property: Serializable {
    @DocumentId
    lateinit var documentId: String

    lateinit var title: String
    lateinit var description: String
    lateinit var createdAt: Timestamp
    lateinit var userID: DocumentReference
    lateinit var location: GeoPoint
    lateinit var area: String
}