package ru.goodibunakov.prodvtest.utils

import java.util.*

object TranslateUtils {

    private val CITIES: Map<String, String>

    init {
        val map = HashMap<String, String>()
        map["Новосибирск"] = "Novosibirsk"
        map["Москва"] = "Moscow"
        map["Прага"] = "Prague"
        map["Кемерово"] = "Kemerovo"
        map["Париж"] = "Paris"
        map["Томск"] = "Tomsk"

        CITIES = Collections.unmodifiableMap(map)
    }


    fun fromEngToRu(engCity: String): String {
        var ru = ""
        for ((key, value) in CITIES) {
            if (value == engCity) {
                ru = key
            }
        }
        return ru
    }

    fun fromRuToEng(): String {
        var city = ""
        val items = HawkHelper.getInstance().getItem(HawkHelper.ITEMS)
        for (cityModel in items!!) {
            if (cityModel.isSelected) {
                city = CITIES[cityModel.city]!!
            }
        }
        return city
    }
}