package com.example.cryptocurrenciesapp.view.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrenciesapp.R;
import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.utilities.Constants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {

    //private LayoutInflater inflater;
    private final List<Currency> list;
    private final List<Currency> filteredList;
    private final CurrencyClickListener clickListener;

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Currency> newFilteredList = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                newFilteredList.addAll(list);
            } else {
                String filter = charSequence.toString().toLowerCase().trim();
                for (Currency currency : list) {
                    if (currency.getId().toLowerCase().contains(filter) || currency.getName().toLowerCase().contains(filter)) {
                        newFilteredList.add(currency);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = newFilteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredList.clear();
            filteredList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public SearchAdapter(List<Currency> list, CurrencyClickListener clickListener) {
        this.list = list;
        this.filteredList = new ArrayList<>(list);
        this.clickListener = clickListener;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Currency currency = filteredList.get(position);

        Picasso.get().load(Constants.CURRENCY_LOGO_SOURCE + currency.getImage()).into(holder);
        holder.id.setText(currency.getId());
        holder.name.setText(currency.getName());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    protected class SearchViewHolder extends RecyclerView.ViewHolder implements Target {
        private final ImageView logo;
        private final TextView id;
        private final TextView name;

        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            logo = itemView.findViewById(R.id.imageCurrency);
            id = itemView.findViewById(R.id.labelId);
            name = itemView.findViewById(R.id.labelName);
            setupViewClick(itemView);
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
                Currency currency = filteredList.get(getAdapterPosition());
                clickListener.onCurrencyClick(currency);
            });
        }
    }

    public interface CurrencyClickListener {
        void onCurrencyClick(Currency currency);

        void onPortfolioClick(Currency currency, boolean checked);
    }
}
