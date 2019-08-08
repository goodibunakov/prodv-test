package ru.goodibunakov.prodvtest.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ru.goodibunakov.prodvtest.R;

public class ImageUtils {

    private static final Map<String, Integer> DESCRIPTION;

    static {
        Map<String, Integer> map = new HashMap<>();
        map.put("ясно", R.drawable.ic_sun);
        map.put("пасмурно", R.drawable.ic_cloud);
        map.put("дождь", R.drawable.ic_rain);

        DESCRIPTION = Collections.unmodifiableMap(map);
    }

    public static int getImageDrawable(String description){
        if (DESCRIPTION.containsKey(description)){
            return DESCRIPTION.get(description);
        } else {
            return R.drawable.ic_sun;
        }
    }
}
