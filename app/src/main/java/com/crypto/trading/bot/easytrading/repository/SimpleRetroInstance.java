package com.crypto.trading.bot.easytrading.repository;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SimpleRetroInstance {

    public static String BASE_URL = "https://api3.binance.com/";
    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit;
    }
}
