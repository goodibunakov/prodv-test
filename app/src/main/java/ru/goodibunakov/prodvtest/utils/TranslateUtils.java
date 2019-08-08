package ru.goodibunakov.prodvtest.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.goodibunakov.prodvtest.model.CityModel;

public class TranslateUtils {

    private static final Map<String, String> CITIES;

    static {
        Map<String, String> map = new HashMap<>();
        map.put("Новосибирск", "Novosibirsk");
        map.put("Москва", "Moscow");
        map.put("Прага", "Prague");
        map.put("Кемерово", "Kemerovo");
        map.put("Париж", "Paris");
        map.put("Томск", "Tomsk");

        CITIES = Collections.unmodifiableMap(map);
    }


    public static String fromEngToRu(String engCity) {
        String ru = "";
        for (Map.Entry<String, String> entry : CITIES.entrySet()) {
            if (entry.getValue().equals(engCity)) {
                ru = entry.getKey();
            }
        }
        return ru;
    }

    public static String fromRuToEng() {
        String city = "";
        List<CityModel> items = HawkHelper.getInstance().getItem(HawkHelper.ITEMS);
        for (CityModel cityModel : items) {
            if (cityModel.isSelected()) {
                city = CITIES.get(cityModel.getCity());
            }
        }
        return city;
    }
}
