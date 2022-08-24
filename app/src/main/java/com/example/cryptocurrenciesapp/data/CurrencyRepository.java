package com.example.cryptocurrenciesapp.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.model.Detail;
import com.example.cryptocurrenciesapp.model.Value;
import com.example.cryptocurrenciesapp.service.ApiService;
import com.example.cryptocurrenciesapp.utilities.Constants;
import com.example.cryptocurrenciesapp.utilities.DetailListDeserializer;
import com.example.cryptocurrenciesapp.utilities.ValueDeserializer;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyRepository {
    private MutableLiveData<Currency> currency;
    private MutableLiveData<List<Detail>> detail;

    public CurrencyRepository(Currency curr){
        currency = new MutableLiveData<>(curr);
        detail = new MutableLiveData<>();
    }

    public MutableLiveData<Currency> getCurrencyValue(){
        ApiService service = ApiService.Client.getService(Value.class, new ValueDeserializer());

        Currency curr = currency.getValue();
        if(curr.getValue() != null){
            return currency;
        }

        Call<Value> call = service.getCurrencyValue(curr.getId(), Constants.CONVERSION_CURRENCY);

        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                curr.setValue(response.body());
                currency.setValue(curr);
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Log.d("getCurrencyValue", t.getMessage());
            }
        });

        return currency;
    }

    public MutableLiveData<List<Detail>> getDetail(String currencyId, String type, int count){
        ApiService service = ApiService.Client.getService(new TypeToken<List<Detail>>() {}.getType(), new DetailListDeserializer());

        Call<List<Detail>> call;

        switch (type){
            case "minute":
                call = service.getDetailMinute(currencyId, Constants.CONVERSION_CURRENCY, count);
                break;
            case "hourly":
                call = service.getDetailHourly(currencyId, Constants.CONVERSION_CURRENCY, count);
                break;
            case "daily":
                call = service.getDetailDaily(currencyId, Constants.CONVERSION_CURRENCY, count);
                break;
            default:
                return detail;
        }

        call.enqueue(new Callback<List<Detail>>() {
            @Override
            public void onResponse(Call<List<Detail>> call, Response<List<Detail>> response) {
                detail.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Detail>> call, Throwable t) {
                Log.d("getOhlc", t.getMessage());
            }
        });

        return detail;
    }
}
