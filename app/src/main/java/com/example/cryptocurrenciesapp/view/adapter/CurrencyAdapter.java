package com.example.cryptocurrenciesapp.view.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrenciesapp.R;
import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.utilities.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    private final List<Currency> list;
    private final CurrencyClickListener clickListener;

    public CurrencyAdapter(List<Currency> list, CurrencyClickListener clickListener) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = list.get(position);

        Picasso.get().load(Constants.CURRENCY_LOGO_SOURCE + currency.getImage()).into(holder);
        holder.id.setText(currency.getId());
        holder.name.setText(currency.getName());
        holder.price.setText(currency.getValue().getPrice());
        holder.marketCap.setText("MCap " + currency.getValue().getMarketCap());
        holder.percentChange.setText(currency.getValue().getChange24H());
        holder.percentChange.setTextColor((currency.getValue().getChange24H().contains("-")) ? Color.RED : Color.GREEN);
        holder.portfolio.setChecked(currency.isInPortfolio());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected class CurrencyViewHolder extends RecyclerView.ViewHolder implements Target {
        private final ImageView logo;
        private final TextView id;
        private final TextView name;
        private final TextView price;
        private final TextView marketCap;
        private final TextView percentChange;
        private final CheckBox portfolio;

        public CurrencyViewHolder(View view) {
            super(view);

            logo = view.findViewById(R.id.imageCurrency);
            id = view.findViewById(R.id.labelId);
            name = view.findViewById(R.id.labelName);
            price = view.findViewById(R.id.labelPrice);
            marketCap = view.findViewById(R.id.labelMarketCap);
            percentChange = view.findViewById(R.id.labelPercentChange);
            portfolio = view.findViewById(R.id.btnPortfolio);

            setupViewClick(view);
            setupPortfolioClick(portfolio);
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            logo.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            Log.d("getImage", e.getMessage());
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }

        public void setupViewClick(View view) {
            view.setOnClickListener(v -> {
                Currency currency = list.get(getAdapterPosition());
                clickListener.onCurrencyClick(currency);
            });
        }

        public void setupPortfolioClick(CheckBox portfolio) {
            portfolio.setOnClickListener((view) -> {
                Currency currency = list.get(getAdapterPosition());
                boolean isChecked = ((CheckBox) view).isChecked();
                clickListener.onPortfolioClick(currency, isChecked);
            });
        }

    }

    public interface CurrencyClickListener {
        void onCurrencyClick(Currency currency);

        void onPortfolioClick(Currency currency, boolean checked);
    }
}
