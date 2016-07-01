package com.android.example.forecast.model;

/**
 * Created by Mohamed Habib on 6/30/2016.
 */
public class SimpleForecastDay {
    //    ForecastDate date;
    ForecastDate forecastDate;

    public String epoch;
    public String pretty;
    public String day;
    public String month;
    public String year;
    public String hour;
    public String min;
    public String ampm;
    public String weekday;
    public String tz_long;
    public int period;
    public String fahrenheitHighTemp;
    public String celsiusHighTemp;
    public String fahrenheitLowTemp;
    public String celsiusLowTemp;
    public String conditions;
    public String icon_url;
    public String maxWindMPH;
    public String maxWindKPH;
    public String maxWindDir;
    public String maxWindDegrees;
    public String aveWindMPH;
    public String aveWindKPH;
    public String aveWindDir;
    public String aveWindDegrees;
    public String aveHumidity;

    private void setForecastDateValues(ForecastDate forecastDate) {
        this.forecastDate = forecastDate;
        this.epoch = forecastDate.epoch;
        this.pretty = forecastDate.pretty;
        this.day = forecastDate.day;
        this.month = forecastDate.month;
        this.year = forecastDate.year;
        this.hour = forecastDate.hour;
        this.min = forecastDate.min;
        this.ampm = forecastDate.ampm;
        this.weekday = forecastDate.weekday;
        this.tz_long = forecastDate.tz_long;
    }

    public SimpleForecastDay(ForecastDate forecastDate, int period, String fahrenheitHighTemp, String celsiusHighTemp, String fahrenheitLowTemp, String celsiusLowTemp,
                             String conditions, String icon_url, String maxWindMPH, String maxWindKPH, String maxWindDir, String maxWindDegrees, String aveWindMPH, String aveWindKPH,
                             String aveWindDir, String aveWindDegrees, String aveHumidity) {
        setForecastDateValues(forecastDate);
        this.period = period;
        this.fahrenheitHighTemp = fahrenheitHighTemp;
        this.celsiusHighTemp = celsiusHighTemp;
        this.fahrenheitLowTemp = fahrenheitLowTemp;
        this.celsiusLowTemp = celsiusLowTemp;
        this.conditions = conditions;
        this.icon_url = icon_url;
        this.maxWindMPH = maxWindMPH;
        this.maxWindKPH = maxWindKPH;
        this.maxWindDir = maxWindDir;
        this.maxWindDegrees = maxWindDegrees;
        this.aveWindMPH = aveWindMPH;
        this.aveWindKPH = aveWindKPH;
        this.aveWindDir = aveWindDir;
        this.aveWindDegrees = aveWindDegrees;
        this.aveHumidity = aveHumidity;
    }


    public String getDate() {
        return day + "/" + month + "/" + year;
    }
}
