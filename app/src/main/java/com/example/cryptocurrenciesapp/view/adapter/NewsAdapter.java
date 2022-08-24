package com.example.cryptocurrenciesapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrenciesapp.R;
import com.example.cryptocurrenciesapp.model.NewsArticle;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsArticle> data;
    private final NewsClickListener clickListener;

    public NewsAdapter(List<NewsArticle> data, NewsClickListener clickListener) {
        this.data = data;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = data.get(position);
        Picasso.get().load(article.getImage()).into(holder.image);
        holder.title.setText(article.getTitle());
        holder.source.setText(article.getSource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onNewsClicked(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView source;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageArticle);
            title = itemView.findViewById(R.id.labelTitle);
            source = itemView.findViewById(R.id.labelSource);
        }
    }

    public interface NewsClickListener {
        void onNewsClicked(NewsArticle article);
    }
}
