package com.example.cryptocurrenciesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cryptocurrenciesapp.adapters.NewsAdapter;
import com.example.cryptocurrenciesapp.model.NewsArticle;
import com.example.cryptocurrenciesapp.viewmodel.NewsViewModel;


public class NewsFragment extends Fragment {
    private NewsViewModel viewModel;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(){
        return new NewsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = view.findViewById(R.id.newsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getNews().observe(getViewLifecycleOwner(), data -> {
            adapter = new NewsAdapter(data, new NewsAdapter.NewsClickListener() {
                @Override
                public void onNewsClicked(NewsArticle article) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    System.out.println(article.getUrl());
                    intent.setData(Uri.parse(article.getUrl()));
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        });
        return view;
    }
}