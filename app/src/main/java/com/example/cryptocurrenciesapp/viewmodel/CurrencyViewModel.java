package com.example.cryptocurrenciesapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cryptocurrenciesapp.data.CurrencyRepository;
import com.example.cryptocurrenciesapp.data.Database;
import com.example.cryptocurrenciesapp.data.PortfolioRepository;
import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.model.Detail;

import java.util.List;

public class CurrencyViewModel extends ViewModel {
    private CurrencyRepository currencyRepository;
    private PortfolioRepository portfolioRepository;

    private MutableLiveData<Currency> currency;
    private MutableLiveData<List<Detail>> detail;
    private MutableLiveData<List<Currency>> portfolio;

    public CurrencyViewModel(Currency currency, Database database) {
        this.currencyRepository = new CurrencyRepository(currency);
        this.portfolioRepository = PortfolioRepository.getInstance(database);
        this.currency = currencyRepository.getCurrencyValue();
        this.detail = currencyRepository.getDetail(currency.getId(), "hourly", 24);
        this.portfolio = portfolioRepository.getCurrenciesFromPortfolio();
    }

    public LiveData<Currency> getCurrency() {
        return currency;
    }

    public LiveData<List<Detail>> getDetail() {
        return detail;
    }

    public void fetchDetail(String type, int count) {
        currencyRepository.getDetail(currency.getValue().getId(), type, count);
    }

    public void handlePortfolioChange(Currency currency, boolean inPortfolio) {
        if (inPortfolio) {
            portfolioRepository.insertCurrency(currency);
        } else {
            portfolioRepository.deleteCurrency(currency);
        }
    }

    public boolean isInPortfolio(Currency currency) {
        if (portfolio.getValue() == null) {
            return false;
        }

        return portfolio.getValue().stream().anyMatch(c -> c.getId().equals(currency.getId()));
    }
}
