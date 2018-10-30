package com.example.felipe.beerlist.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Beer: RealmObject(), Serializable {
    @PrimaryKey var id: Int? = null
    var name: String? = null
    var tagline: String? = null
    var description: String? = null
}