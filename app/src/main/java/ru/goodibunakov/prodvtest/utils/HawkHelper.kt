package ru.goodibunakov.prodvtest.utils

import android.content.Context
import com.orhanobut.hawk.Hawk
import ru.goodibunakov.prodvtest.presentation.model.CityModel

object HawkHelper {

    const val ITEMS = "items"

    fun init(context: Context) {
        Hawk.init(context).build()
    }

    fun getItem(key: String): List<CityModel>? {
        return if (Hawk.contains(key)) Hawk.get<List<CityModel>>(key) else null
    }

    fun setItem(key: String, obj: List<CityModel>): Boolean {
        return Hawk.put(key, obj)
    }

    fun checkIfContain(key: String): Boolean {
        return Hawk.contains(key)
    }
}