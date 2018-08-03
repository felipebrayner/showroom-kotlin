package com.example.felipe.beerlist

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.PrimaryKey

@RealmClass
open class SavedBeer: RealmObject() {
    @PrimaryKey
    var id: Long = 0
}