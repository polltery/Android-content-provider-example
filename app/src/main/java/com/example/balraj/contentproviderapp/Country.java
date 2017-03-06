package com.example.balraj.contentproviderapp;

/**
 * Created by Balraj on 12/2/16.
 */

public class Country {
    private int _id;
    private String name;
    private String capital;
    private String population;
    private String area;
    private String lang;

    public Country(int _id, String area, String capital, String lang, String name, String population) {
        this._id = _id;
        this.area = area;
        this.capital = capital;
        this.lang = lang;
        this.name = name;
        this.population = population;
    }


    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
