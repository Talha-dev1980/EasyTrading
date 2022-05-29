package com.crypto.trading.bot.easytrading.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.crypto.trading.bot.easytrading.repository.SimpleAPI;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.repository.SimpleRetroInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityViewModel extends ViewModel {
    private MutableLiveData<List<Coin>> coinsList;

    public MainActivityViewModel() {
        this.coinsList = new MutableLiveData<List<Coin>>();
    }
    public void callForCoinTiker(Context ctx){
        SimpleAPI apiService = SimpleRetroInstance.getRetrofitClient().create( SimpleAPI.class );
        Call<List<Coin>> call = apiService.getPriceTicker( );
        call.enqueue( new Callback<List<Coin>>() {
            @Override
            public void onResponse(Call<List<Coin>> call, Response<List<Coin>> response) {
                if (response.code()==200){
                    coinsList.postValue( response.body() );
                }else{
                    coinsList.postValue( null );
                }
                Toast.makeText( ctx, "response.code()"+response.code(), Toast.LENGTH_SHORT ).show();
               Log.d("APIFaliure",response.code()+"");
                if (response.errorBody()!=null ) {
                    Log.e("APIFaliure", response.errorBody().toString()+"");
                }/* else if (response.body().getMessage() != null) {
                    Toast.makeText( ctx, response.body().getMessage() + "", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText( ctx, response.body().isSuccess() + "", Toast.LENGTH_LONG ).show();
                }*/
            }

            @Override
            public void onFailure(Call<List<Coin>> call, Throwable t) {
                Log.e("APIFaliure","GetPriceTicker"+t.getMessage());
            }
        } );

    }
    public MutableLiveData<List<Coin>> getCoinsList(){
        return coinsList;
    }
}
