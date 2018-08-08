package com.example.felipe.beerlist.model

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

data class Ingredients(
        val malt: List<Malt>,
        val hops: List<Hop>,
        val yeast: String
): Serializable

data class Hop(
        val name: String,
        val amount: Amount,
        val add: String,
        val attribute: String
): Serializable

data class Amount(
        val value: Float,
        val unit: String
): Serializable

data class Malt(
        val name: String,
        val amount: Amount
): Serializable

data class Volume(
        val value: Int,
        val unit: String
): Serializable

data class BoilVolume(
        val value: Int,
        val unit: String
): Serializable

data class Method(
        val mash_temp: List<MashTemp>,
        val fermentation: Fermentation,
        val twist: Any
): Serializable

data class Fermentation(
        val temp: Temp
): Serializable

data class MashTemp(
        val temp: Temp,
        val duration: Int
): Serializable

data class Temp(
        val value: Int,
        val unit: String
): Serializable