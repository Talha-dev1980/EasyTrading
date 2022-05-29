package com.crypto.trading.bot.easytrading.repository;

import com.crypto.trading.bot.easytrading.models.Candle;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.models.CoinPriceChange;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SimpleAPI {
    @GET("api/v3/ticker/price")
    Call<List<Coin>> getPriceTicker(@Body String symbol);

    @GET("api/v3/ticker/price")
    Call<List<Coin>> getPriceTicker();

    @GET("api/v3/ticker/24hr")
    Call<List<CoinPriceChange>> getLastdayPrices();

    @GET("api/v3/ticker/24hr")
    Call<CoinPriceChange> getLastdayPrice(@Query("symbol") String symbol);

    @GET("api/v3/klines?symbol=BTCUSDT&interval=1h")
    Call<List<Candle>> get24HrCandles();

/*
 @GET("api/v3/klines")
    Call<List<Candle>> get24HrCandles(@Query("symbol") String symbol, @Query("startTime") long startTime
            , @Query("endTime") long endTime, @Query("interval") String interval);

*/

}
