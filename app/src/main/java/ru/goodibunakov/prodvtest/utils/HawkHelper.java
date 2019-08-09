package ru.goodibunakov.prodvtest.utils;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

import java.util.List;

import ru.goodibunakov.prodvtest.model.CityModel;

public class HawkHelper {

    public static final String ITEMS = "items";

    private static HawkHelper instance;

    public void init(Context context) {
        Hawk.init(context).build();
    }

    public List<CityModel> getItem(String key) {
        if (Hawk.contains(key))
            return Hawk.get(key);
        return null;
    }

    public boolean setItem(String key, List<CityModel> obj) {
        return Hawk.put(key, obj);
    }

    public boolean checkIfContain(String key) {
        return Hawk.contains(key);
    }

    public static HawkHelper getInstance() {
        if (instance != null)
            return instance;
        else return new HawkHelper();
    }
}