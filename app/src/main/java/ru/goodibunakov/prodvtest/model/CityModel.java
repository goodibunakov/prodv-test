package ru.goodibunakov.prodvtest.model;

public class CityModel {

    private String city;
    private boolean isSelected;

    public CityModel(String city, boolean isSelected) {
        this.city = city;
        this.isSelected = isSelected;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
