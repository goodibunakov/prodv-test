package ru.goodibunakov.prodvtest.utils

import android.content.Context
import com.orhanobut.hawk.Hawk
import ru.goodibunakov.prodvtest.model.CityModel

class HawkHelper {

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

    companion object {

        const val ITEMS = "items"

        private val instance: HawkHelper? = null

        fun getInstance(): HawkHelper {
            return instance ?: HawkHelper()
        }
    }
}