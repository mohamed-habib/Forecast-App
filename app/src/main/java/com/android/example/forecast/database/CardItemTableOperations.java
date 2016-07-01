package com.android.example.forecast.database;

import com.android.example.forecast.model.CardItem;
import com.android.example.forecast.model.CardItem_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

/**
 * Created by Mohamed Habib on 7/1/2016.
 */
public class CardItemTableOperations implements Model {
    @Override
    public void save(CardItem cardItem) {
        cardItem.save();
    }

    @Override
    public void delete(CardItem cardItem) {

    }

    @Override
    public void insert(CardItem cardItem) {
        cardItem.insert();
    }

    @Override
    public void update(CardItem cardItem) {
        cardItem.update();
    }

    @Override
    public boolean exists(CardItem cardItem) {
        CardItem cardItem1 = SQLite.select().from(CardItem.class).where(CardItem_Table.title.eq(cardItem.getTitle())).querySingle();
        return cardItem1 != null;
    }

    public List<CardItem> getAllCardItems() {
        return SQLite.select().from(CardItem.class).queryList();
    }

}
