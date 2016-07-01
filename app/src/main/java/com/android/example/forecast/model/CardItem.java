package com.android.example.forecast.model;

import com.android.example.forecast.database.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Mohamed Habib on 7/1/2016.
 */
@Table(database = AppDatabase.class)
public class CardItem extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String iconUrl;
    @Column
    String title;
    @Column
    String date;
    @Column
    String tempFahrenheit;
    @Column
    String tempCelsius;
    @Column
    String description_mile;
    @Column
    String description_metric;
    @Column
    String tz_long;
    @Column
    public String maxWindMPH;
    @Column
    public String maxWindKPH;
    @Column
    public String maxWindDir;
    @Column
    public String maxWindDegrees;
    @Column
    public String aveWindMPH;
    @Column
    public String aveWindKPH;
    @Column
    public String aveWindDir;
    @Column
    public String aveWindDegrees;
    @Column
    public String aveHumidity;


    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTempFahrenheit(String tempFahrenheit) {
        this.tempFahrenheit = tempFahrenheit;
    }

    public void setTempCelsius(String tempCelsius) {
        this.tempCelsius = tempCelsius;
    }

    public void setDescription_mile(String description_mile) {
        this.description_mile = description_mile;
    }

    public void setTz_long(String tz_long) {
        this.tz_long = tz_long;
    }

    public void setDescription_metric(String description_metric) {
        this.description_metric = description_metric;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTempFahrenheit() {
        return tempFahrenheit;
    }

    public String getTempCelsius() {
        return tempCelsius;
    }


    public String getDescription_metric() {
        return description_metric;
    }

    public String getTz_long() {
        return tz_long;
    }

    public String getMaxWindMPH() {
        return maxWindMPH;
    }

    public String getMaxWindKPH() {
        return maxWindKPH;
    }

    public String getMaxWindDir() {
        return maxWindDir;
    }

    public String getMaxWindDegrees() {
        return maxWindDegrees;
    }

    public String getAveWindMPH() {
        return aveWindMPH;
    }

    public String getAveWindKPH() {
        return aveWindKPH;
    }

    public String getAveWindDir() {
        return aveWindDir;
    }

    public String getAveWindDegrees() {
        return aveWindDegrees;
    }

    public String getAveHumidity() {
        return aveHumidity;
    }

    public void setMaxWindMPH(String maxWindMPH) {
        this.maxWindMPH = maxWindMPH;
    }

    public void setMaxWindKPH(String maxWindKPH) {
        this.maxWindKPH = maxWindKPH;
    }

    public void setMaxWindDir(String maxWindDir) {
        this.maxWindDir = maxWindDir;
    }

    public void setMaxWindDegrees(String maxWindDegrees) {
        this.maxWindDegrees = maxWindDegrees;
    }

    public void setAveWindMPH(String aveWindMPH) {
        this.aveWindMPH = aveWindMPH;
    }

    public void setAveWindKPH(String aveWindKPH) {
        this.aveWindKPH = aveWindKPH;
    }

    public void setAveWindDir(String aveWindDir) {
        this.aveWindDir = aveWindDir;
    }

    public void setAveWindDegrees(String aveWindDegrees) {
        this.aveWindDegrees = aveWindDegrees;
    }

    public void setAveHumidity(String aveHumidity) {
        this.aveHumidity = aveHumidity;
    }

    /**
     * adds relevant unit to the temperature
     *
     * @param celsiusUnit
     * @param fahrenheitUnit
     * @return returns the temperature based on user preference
     */
    public String getTemp(String celsiusUnit, String fahrenheitUnit) {
        //todo: implement after taking user preference
        return tempCelsius + celsiusUnit;
    }

    /**
     * @return the description based on user preference
     */
    public String getDescription() {
        //todo: implement after taking user preference
        return description_metric;
    }

    /**
     * @return the value based on user preference
     */
    public String getWindMaxValue() {
        //todo: implement after taking user preference
        return maxWindKPH;
    }

    /**
     * @return the value based on user preference
     */
    public String getWindAvgValue() {
        //todo: implement after taking user preference
        return aveWindKPH;
    }
}
