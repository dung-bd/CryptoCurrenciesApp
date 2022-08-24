package com.example.cryptocurrenciesapp.service;

import com.example.cryptocurrenciesapp.model.Currency;
import com.example.cryptocurrenciesapp.model.Detail;
import com.example.cryptocurrenciesapp.model.NewsArticle;
import com.example.cryptocurrenciesapp.model.Value;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("all/coinlist?summary=true")
    Call<List<Currency>> getCurrencySummary(@Query("fsym") String id);

    @GET("pricemultifull")
    Call<Value> getCurrencyValue(@Query("fsyms") String id, @Query("tsyms") String conversionCurrency);

    @GET("top/mktcapfull")
    Call<List<Currency>> getToplistByMarketCap(@Query("tsym") String conversionCurrency, @Query("limit") int count);

    @GET("v2/histominute")
    Call<List<Detail>> getDetailMinute(@Query("fsym") String id, @Query("tsym") String conversionCurrency, @Query("limit") int count);

    @GET("v2/histohour")
    Call<List<Detail>> getDetailHourly(@Query("fsym") String id, @Query("tsym") String conversionCurrency, @Query("limit") int count);

    @GET("v2/histoday")
    Call<List<Detail>> getDetailDaily(@Query("fsym") String id, @Query("tsym") String conversionCurrency, @Query("limit") int count);

    @GET("v2/news/")
    Call<List<NewsArticle>> getLatestNews(@Query("lang") String language);

    class Client {
        private static final String BASE_URL = "https://min-api.cryptocompare.com/data/";

        private static Converter.Factory createGsonConverter(Type type, Object typeAdapter) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(type, typeAdapter);
            Gson gson = gsonBuilder.create();

            return GsonConverterFactory.create(gson);
        }

        public static ApiService getService(Type type, Object typeAdapter) {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(createGsonConverter(type, typeAdapter))
                    .build()
                    .create(ApiService.class);
        }
    }
}
