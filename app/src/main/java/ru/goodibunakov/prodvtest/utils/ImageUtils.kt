package ru.goodibunakov.prodvtest.utils

import ru.goodibunakov.prodvtest.R
import java.util.*

object ImageUtils {

    private val DESCRIPTION: Map<String, Int>

    init {
        val map = HashMap<String, Int>()
        map["ясно"] = R.drawable.ic_sun
        map["пасмурно"] = R.drawable.ic_cloud
        map["дождь"] = R.drawable.ic_rain

        DESCRIPTION = Collections.unmodifiableMap(map)
    }

    fun getImageDrawable(description: String): Int {
        return if (DESCRIPTION.containsKey(description)) {
            DESCRIPTION[description]!!
        } else {
            R.drawable.ic_sun
        }
    }
}