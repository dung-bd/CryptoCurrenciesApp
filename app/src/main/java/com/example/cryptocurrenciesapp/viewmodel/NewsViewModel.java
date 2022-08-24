package com.example.cryptocurrenciesapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cryptocurrenciesapp.data.NewsRepository;
import com.example.cryptocurrenciesapp.model.NewsArticle;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private NewsRepository repository;

    private MutableLiveData<List<NewsArticle>> news;

    public NewsViewModel(){
        repository = new NewsRepository();
    }

    public LiveData<List<NewsArticle>> getNews(){
        if(news == null){
            news = repository.getNews();
        }
        return news;
    }
}
