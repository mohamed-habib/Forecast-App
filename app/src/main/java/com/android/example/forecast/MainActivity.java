package com.android.example.forecast;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.example.forecast.database.CardItemTableOperations;
import com.android.example.forecast.model.CardItem;
import com.android.example.forecast.model.ForecastDate;
import com.android.example.forecast.model.ForecastPeriod;
import com.android.example.forecast.model.SimpleForecastDay;
import com.android.example.forecast.util.CardItemAdapter;
import com.android.example.forecast.util.ConstantsClass;
import com.android.example.forecast.util.UtilitiesClass;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    UtilitiesClass utilitiesClass;
    Gson gson;
    CardItemTableOperations cardItemTableOperations;
    CardItemAdapter cardItemAdapter;
    LinearLayoutManager linearLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    @Bind(R.id.root_view)
    RelativeLayout rootView;
    @Bind(R.id.activity_main_rv)
    RecyclerView cardItemsRV;
    @Bind(R.id.activity_main_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        cardItemsRV.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        cardItemsRV.setLayoutManager(linearLayoutManager);
        cardItemTableOperations = new CardItemTableOperations();
        this.utilitiesClass = new UtilitiesClass(this);
        gson = new Gson();

        List<CardItem> cardItems = new ArrayList<>();
        cardItemAdapter = new CardItemAdapter(MainActivity.this, cardItems);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        sendForecastRequest();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendForecastRequest();
            }
        });

    }

    private void sendForecastRequest() {
        final List<CardItem> cardItemList = new ArrayList<>();
        if (this.utilitiesClass.isNetworkAvailable()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ConstantsClass.FORECAST_API, null, new Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //get the periods, will be the simple info on the card
                        JSONArray forecastPeriodJsonArr = response.getJSONObject(ConstantsClass.FORECAST).getJSONObject(ConstantsClass.TXT_FORECAST).getJSONArray(ConstantsClass.FORECASTDAY);
                        for (int i = 0; i < forecastPeriodJsonArr.length() - 2; i++) {//get 3 days only, every day has 2 periods, exclude last 2 periods of the 4th day
                            JSONObject forecastPeriodJsonObj = forecastPeriodJsonArr.getJSONObject(i);

                            ForecastPeriod forecastPeriod = gson.fromJson(forecastPeriodJsonObj.toString(), ForecastPeriod.class);
                            CardItem cardItem = new CardItem();
                            cardItem.setIconUrl(forecastPeriod.icon_url);
                            cardItem.setTitle(forecastPeriod.title);
                            cardItem.setDescription_mile(forecastPeriod.fcttext);
                            cardItem.setDescription_metric(forecastPeriod.fcttext_metric);
                            cardItemList.add(cardItem);
                        }

                        //get the simple forecast day, will be the detailed info of each period
                        JSONArray forecastDayJsonArr = response.getJSONObject(ConstantsClass.FORECAST).getJSONObject(ConstantsClass.SIMPLEFORECAST).getJSONArray(ConstantsClass.FORECASTDAY);
                        for (int i = 0; i < forecastDayJsonArr.length() - 1; i++) {//get 3 days only
                            JSONObject forecastDayJsonObj = forecastDayJsonArr.getJSONObject(i);

                            //todo: replace this with cardItem SimpleForecastDay is useless
                            SimpleForecastDay simpleForecastDay = getSimpleForecastDay(forecastDayJsonObj);

                            //for each simple forecast day, create 2 cards, the day and night
                            CardItem cardItemDay = cardItemList.get(i + i);
                            cardItemDay.setDate(simpleForecastDay.getDate());
                            cardItemDay.setTz_long(simpleForecastDay.tz_long);
                            cardItemDay.setTempCelsius(simpleForecastDay.celsiusHighTemp);
                            cardItemDay.setTempFahrenheit(simpleForecastDay.fahrenheitHighTemp);
                            cardItemDay.setMaxWindMPH(simpleForecastDay.maxWindMPH);
                            cardItemDay.setMaxWindKPH(simpleForecastDay.maxWindKPH);
                            cardItemDay.setMaxWindDir(simpleForecastDay.maxWindDir);
                            cardItemDay.setMaxWindDegrees(simpleForecastDay.maxWindDegrees);
                            cardItemDay.setAveWindMPH(simpleForecastDay.aveWindMPH);
                            cardItemDay.setAveWindKPH(simpleForecastDay.aveWindKPH);
                            cardItemDay.setAveWindDir(simpleForecastDay.aveWindDir);
                            cardItemDay.setAveWindDegrees(simpleForecastDay.aveWindDegrees);
                            cardItemDay.setAveHumidity(simpleForecastDay.aveHumidity);
                            if (cardItemTableOperations.exists(cardItemDay)) {
                                cardItemTableOperations.update(cardItemDay);
                            } else
                                cardItemTableOperations.insert(cardItemDay);

                            CardItem cardItemNight = cardItemList.get(i + i + 1);
                            cardItemNight.setDate(simpleForecastDay.getDate());
                            cardItemNight.setTz_long(simpleForecastDay.tz_long);
                            cardItemNight.setTempCelsius(simpleForecastDay.celsiusLowTemp);
                            cardItemNight.setTempFahrenheit(simpleForecastDay.fahrenheitLowTemp);
                            cardItemNight.setMaxWindMPH(simpleForecastDay.maxWindMPH);
                            cardItemNight.setMaxWindKPH(simpleForecastDay.maxWindKPH);
                            cardItemNight.setMaxWindDir(simpleForecastDay.maxWindDir);
                            cardItemNight.setMaxWindDegrees(simpleForecastDay.maxWindDegrees);
                            cardItemNight.setAveWindMPH(simpleForecastDay.aveWindMPH);
                            cardItemNight.setAveWindKPH(simpleForecastDay.aveWindKPH);
                            cardItemNight.setAveWindDir(simpleForecastDay.aveWindDir);
                            cardItemNight.setAveWindDegrees(simpleForecastDay.aveWindDegrees);
                            cardItemNight.setAveHumidity(simpleForecastDay.aveHumidity);
                            //if there's a card item with the same title update it, else insert it
                            if (cardItemTableOperations.exists(cardItemNight)) {
                                cardItemTableOperations.update(cardItemNight);
                            } else
                                cardItemTableOperations.insert(cardItemNight);

                        }

                        showCardItems(cardItemList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Snackbar.make(rootView, "Error happened while getting data", Snackbar.LENGTH_LONG).show();
                }
            });
            AppClass.getInstance().getRequestQueue().add(jsonObjectRequest);
        } else {
            //if local db contains items, then add them to recycler view, else, tell the user that i need internet connection
            List<CardItem> cashedCardItems = cardItemTableOperations.getAllCardItems();
            if (cashedCardItems.size() < 1) {
                Snackbar.make(rootView, "Check Internet Connection", Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendForecastRequest();
                    }
                }).show();
            } else {
                //add the card items to the recycler view
                showCardItems(cashedCardItems);
            }
        }
    }

    /**
     * adds cardItems to recycler view adapter and stops refreshing mSwipeRefreshLayout
     *
     * @param cardItems
     */
    private void showCardItems(List<CardItem> cardItems) {
        mSwipeRefreshLayout.setRefreshing(false);
        cardItemAdapter = new CardItemAdapter(MainActivity.this, cardItems);
        cardItemsRV.setAdapter(cardItemAdapter);
    }

    @NonNull
    private SimpleForecastDay getSimpleForecastDay(JSONObject forecastDayJsonObj) throws JSONException {
        ForecastDate forecastDate = gson.fromJson(forecastDayJsonObj.getJSONObject(ConstantsClass.DATE).toString(), ForecastDate.class);
        int period = forecastDayJsonObj.getInt(ConstantsClass.PERIOD);
        JSONObject highTemp = forecastDayJsonObj.getJSONObject(ConstantsClass.HIGH);
        String fahrenheitHighTemp = highTemp.getString(ConstantsClass.FAHRENHEIT);
        String celsiusHighTemp = highTemp.getString(ConstantsClass.CELSIUS);
        JSONObject lowTemp = forecastDayJsonObj.getJSONObject(ConstantsClass.LOW);
        String fahrenheitLowTemp = lowTemp.getString(ConstantsClass.FAHRENHEIT);
        String celsiusLowTemp = lowTemp.getString(ConstantsClass.CELSIUS);
        String conditions = forecastDayJsonObj.getString(ConstantsClass.CONDITIONS);
        String icon_url = forecastDayJsonObj.getString(ConstantsClass.ICON_URL);
        JSONObject maxWind = forecastDayJsonObj.getJSONObject(ConstantsClass.MAXWIND);
        String maxWindMPH = maxWind.getString(ConstantsClass.MPH);
        String maxWindKPH = maxWind.getString(ConstantsClass.KPH);
        String maxWindDir = maxWind.getString(ConstantsClass.DIR);
        String maxWindDegrees = maxWind.getString(ConstantsClass.DEGREES);
        JSONObject aveWind = forecastDayJsonObj.getJSONObject(ConstantsClass.AVEWIND);
        String aveWindMPH = aveWind.getString(ConstantsClass.MPH);
        String aveWindKPH = aveWind.getString(ConstantsClass.KPH);
        String aveWindDir = maxWind.getString(ConstantsClass.DIR);
        String aveWindDegrees = maxWind.getString(ConstantsClass.DEGREES);
        String aveHumidity = forecastDayJsonObj.getString(ConstantsClass.AVEHUMIDITY);

        return new SimpleForecastDay(forecastDate, period, fahrenheitHighTemp, celsiusHighTemp, fahrenheitLowTemp, celsiusLowTemp,
                conditions, icon_url, maxWindMPH, maxWindKPH, maxWindDir, maxWindDegrees, aveWindMPH, aveWindKPH, aveWindDir, aveWindDegrees, aveHumidity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.view_style_menu_item: {
                String gridViewStr = getResources().getString(R.string.view_grid_menu_item_str);
                String listViewStr = getResources().getString(R.string.view_list_menu_item_str);
                if (item.getTitle().equals(gridViewStr)) {
                    //show items as list staggered grid
                    cardItemsRV.setLayoutManager(staggeredGridLayoutManager);
                    //show the list icon
                    item.setIcon(getResources().getDrawable(R.mipmap.ic_view_stream));
                    item.setTitle(listViewStr);
                } else if (item.getTitle().equals(listViewStr)) {
                    //show items as list
                    cardItemsRV.setLayoutManager(linearLayoutManager);
                    //show the grid icon
                    item.setIcon(getResources().getDrawable(R.mipmap.ic_view_grid));
                    item.setTitle(gridViewStr);
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }


}