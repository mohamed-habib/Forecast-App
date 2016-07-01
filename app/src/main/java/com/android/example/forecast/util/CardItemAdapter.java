package com.android.example.forecast.util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.example.forecast.R;
import com.android.example.forecast.model.CardItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Mohamed Habib on 7/1/2016.
 */
public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.CardViewHolder> {
    Context context;
    List<CardItem> cardItems;

    public CardItemAdapter(Context context, List<CardItem> cardItems) {
        this.context = context;
        this.cardItems = cardItems;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int position) {

        final CardItem cardItem = cardItems.get(position);
        final String cardItemTemperature = cardItem.getTemp(cardViewHolder.celsiusUnit, cardViewHolder.fahrenheitUnit);
        //use picasso to download the icon from the url and attach it to the imageView
        Picasso.with(context).load(cardItem.getIconUrl()).into(cardViewHolder.cardIconIV);
        cardViewHolder.cardTitleTV.setText(cardItem.getTitle());
        cardViewHolder.cardDateTV.setText(cardItem.getDate());
        cardViewHolder.cardTempTV.setText(cardItemTemperature);
        cardViewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, cardItem.getTitle() + " temperature is " + cardItemTemperature + " and humidity is: " + cardItem.aveHumidity + " and wind direction is: " + cardItem.aveWindDir);
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Send to: "));
            }
        });

        //show/hide card details with arrow
        cardViewHolder.arrowIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardViewHolder.cardDetailsRL.getVisibility() == View.VISIBLE) {
                    cardViewHolder.cardDetailsRL.setVisibility(View.GONE);
                    cardViewHolder.arrowIV.setImageResource(R.mipmap.arrow_down);
                } else {
                    cardViewHolder.arrowIV.setImageResource(R.mipmap.arrow_up);
                    cardViewHolder.cardDetailsRL.setVisibility(View.VISIBLE);

                }
            }
        });
        cardViewHolder.cardDescTV.setText(cardItem.getDescription());
        cardViewHolder.humidityTV.setText(cardItem.getAveHumidity());
        cardViewHolder.windMaxValueTV.setText(cardItem.getWindMaxValue());
        cardViewHolder.windAvgValueTV.setText(cardItem.getWindAvgValue());
        cardViewHolder.windMaxDirTV.setText(cardItem.getMaxWindDir());
        cardViewHolder.windAvgDirTV.setText(cardItem.getAveWindDir());
        cardViewHolder.windMaxDegreeTV.setText(cardItem.getMaxWindDegrees());
        cardViewHolder.windAvgDegreeTV.setText(cardItem.getAveWindDegrees());

    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_item_icon_iv)
        ImageView cardIconIV;
        @Bind(R.id.card_item_title_tv)
        TextView cardTitleTV;
        @Bind(R.id.card_item_date_tv)
        TextView cardDateTV;
        @Bind(R.id.card_item_temperature_tv)
        TextView cardTempTV;
        @Bind(R.id.card_item_share_btn)
        Button shareBtn;
        @Bind(R.id.card_item_arrow_down_ib)
        ImageButton arrowIV;
        @Bind(R.id.card_item_details_rl)
        RelativeLayout cardDetailsRL;
        @Bind(R.id.card_item_desc_tv)
        TextView cardDescTV;
        @Bind(R.id.card_item_humidity_tv)
        TextView humidityTV;
        @Bind(R.id.card_item_wind_max_value_tv)
        TextView windMaxValueTV;
        @Bind(R.id.card_item_wind_avg_value_tv)
        TextView windAvgValueTV;
        @Bind(R.id.card_item_wind_max_dir_tv)
        TextView windMaxDirTV;
        @Bind(R.id.card_item_wind_avg_dir_tv)
        TextView windAvgDirTV;
        @Bind(R.id.card_item_wind_max_degree_tv)
        TextView windMaxDegreeTV;
        @Bind(R.id.card_item_wind_avg_degree_tv)
        TextView windAvgDegreeTV;
        @BindString(R.string.celsius_unit)
        String celsiusUnit;
        @BindString(R.string.fahrenheit_unit)
        String fahrenheitUnit;


        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

}
