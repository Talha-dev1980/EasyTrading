package com.crypto.trading.bot.easytrading.viewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crypto.trading.bot.easytrading.models.Candle;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.models.CoinPriceChange;
import com.crypto.trading.bot.easytrading.models.Interval;
import com.crypto.trading.bot.easytrading.repository.SimpleAPI;
import com.crypto.trading.bot.easytrading.repository.SimpleRetroInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinDetailsViewModel extends ViewModel {
    private final MutableLiveData<CoinPriceChange> coinDetails;
    private final MutableLiveData<List<Candle>> candleList;

    public CoinDetailsViewModel() {
        this.coinDetails = new MutableLiveData<CoinPriceChange>();
        this.candleList = new MutableLiveData<List<Candle>>();
    }

    public void callPriceTiker(Context ctx, Coin coin) {
        SimpleAPI apiService = SimpleRetroInstance.getRetrofitClient().create(SimpleAPI.class);
        Call<CoinPriceChange> call = apiService.getLastdayPrice(coin.getSymbol());
        call.enqueue(new Callback<CoinPriceChange>() {
            @Override
            public void onResponse(Call<CoinPriceChange> call, Response<CoinPriceChange> response) {
                if (response.code() == 200) {
                    coinDetails.postValue(response.body());
                } else {
                    coinDetails.postValue(null);
                }
                // Toast.makeText( ctx, "response.code()"+response.code(), Toast.LENGTH_SHORT ).show();
                Log.d("APIFaliure", response.code() + "" + response.message());
                if (response.errorBody() != null) {
                    Log.e("APIFaliure", response.message() + "");
                }/* else if (response.body().getMessage() != null) {
                    Toast.makeText( ctx, response.body().getMessage() + "", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText( ctx, response.body().isSuccess() + "", Toast.LENGTH_LONG ).show();
                }*/
            }

            @Override
            public void onFailure(Call<CoinPriceChange> call, Throwable t) {
                Log.e("APIFaliure", "GetPriceTicker" + t.getMessage());
            }
        });

    }

    public MutableLiveData<CoinPriceChange> getCoinDetails() {
        return coinDetails;
    }

    public void call24Candles(Context ctx, CoinPriceChange coin) {
        SimpleAPI apiService = SimpleRetroInstance.getRetrofitClient().create(SimpleAPI.class);
        Interval[] interval = Interval.values();
        Call<List<Candle>> call = apiService.get24HrCandles();
        /*Call<List<Candle>> call = apiService.get24HrCandles(coin.getSymbol(), coin.getOpenTime()
                , coin.getCloseTime(), "1h");*/
        call.enqueue(new Callback<List<Candle>>() {
            @Override
            public void onResponse(Call<List<Candle>> call, Response<List<Candle>> response) {
                if (response.code() == 200) {
                    candleList.postValue(response.body());

                } else {
                    coinDetails.postValue(null);
                }
                Log.d("APIFaliure-candles", response.code() + "" + response);
                if (response.errorBody() != null) {
                    Log.e("APIFaliure-candles", response.errorBody() + "");
                }
            }

            @Override
            public void onFailure(Call<List<Candle>> call, Throwable t) {
                Log.e("APIFaliure-candles", "GetCandles24Hr" + t.getMessage());
            }
        });

    }

    public MutableLiveData<List<Candle>> get24Candles() {
        return candleList;
    }

    public enum INTERVAL {
        H, D, M


    }
}
