package com.android.example.forecast.database;

import com.android.example.forecast.model.CardItem;

/**
 * Created by Mohamed Habib on 7/1/2016.
 */
public interface Model {
    /**
     * Saves the object in the DB.
     * checks if exists, if true update, else insert
     */
    void save(CardItem cardItem);

    /**
     * Deletes the object in the DB
     */
    void delete(CardItem cardItem);

    /**
     * Inserts the object into the DB
     */
    void insert(CardItem cardItem);

    void update(CardItem cardItem);


    /**
     * @return true if this object exists in the DB. It combines all of it's primary key fields
     * into a SELECT query and checks to see if any results occur.
     */
    boolean exists(CardItem cardItem);
}
