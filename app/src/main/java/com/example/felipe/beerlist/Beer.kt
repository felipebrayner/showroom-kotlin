package com.example.felipe.beerlist

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Beer: RealmObject(), Serializable {
    @PrimaryKey var id: Int? = null
    var name: String? = null
    var tagline: String? = null
    var description: String? = null
    @Ignore val first_brewed: String? = null
    @Ignore val image_url: String? = null
    @Ignore val abv: Float? = null
    @Ignore val ibu: Float? = null
    @Ignore val target_fg: Float? = null
    @Ignore val target_og: Float? = null
    @Ignore val ebc: Float? = null
    @Ignore val srm: Float? = null
    @Ignore val ph: Float? = null
    @Ignore val attenuation_level: Float? = null
    @Ignore val volume: Volume? = null
    @Ignore val boil_volume: BoilVolume? = null
    @Ignore val method: Method? = null
    @Ignore val ingredients: Ingredients? = null
    @Ignore val food_pairing: List<String>? = null
    @Ignore val brewers_tips: String? = null
    @Ignore val contributed_by: String? = null
}

class Ingredients(
        val malt: List<Malt>,
        val hops: List<Hop>,
        val yeast: String
): Serializable

class Hop(
        val name: String,
        val amount: Amount,
        val add: String,
        val attribute: String
): Serializable

class Amount(
        val value: Float,
        val unit: String
): Serializable

class Malt(
        val name: String,
        val amount: Amount
): Serializable

class Volume(
        val value: Int,
        val unit: String
): Serializable

class BoilVolume(
        val value: Int,
        val unit: String
): Serializable

class Method(
        val mash_temp: List<MashTemp>,
        val fermentation: Fermentation,
        val twist: Any
): Serializable

class Fermentation(
        val temp: Temp
): Serializable

class MashTemp(
        val temp: Temp,
        val duration: Int
): Serializable

class Temp(
        val value: Int,
        val unit: String
): Serializable