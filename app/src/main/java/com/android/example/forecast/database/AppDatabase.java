package com.android.example.forecast.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Mohamed Habib on 7/1/2016.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "ForecastDatabase"; // we will add the .db extension

    public static final int VERSION = 1;
}
