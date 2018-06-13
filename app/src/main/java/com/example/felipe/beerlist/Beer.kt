package com.example.felipe.beerlist

import java.io.Serializable

data class Beer(
        val id: Int,
        val name: String,
        val tagline: String,
        val first_brewed: String,
        val description: String,
        val image_url: String,
        val abv: Float,
        val ibu: Float,
        val target_fg: Float,
        val target_og: Float,
        val ebc: Float,
        val srm: Float,
        val ph: Float,
        val attenuation_level: Float,
        val volume: Volume,
        val boil_volume: BoilVolume,
        val method: Method,
        val ingredients: Ingredients,
        val food_pairing: List<String>,
        val brewers_tips: String,
        val contributed_by: String
): Serializable

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