package com.example.cryptocurrenciesapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryptocurrenciesapp.data.Database;

public class ListViewModelFactory implements ViewModelProvider.Factory {
    private Database database;

    public ListViewModelFactory(Database database) {
        this.database = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        return (T) new ListViewModel(database);
    }
}
