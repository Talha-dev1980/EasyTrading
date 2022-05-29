package com.crypto.trading.bot.easytrading.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.crypto.trading.bot.easytrading.CurrencyInfo;
import com.crypto.trading.bot.easytrading.R;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.viewModels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel mainActivityViewModel;
    Context context;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( MainActivity.this, CurrencyInfo.class );
                startActivity( intent );
                finish();
            }
        }, 500 );//todo 3000

    }

    public void init() {
        context = MainActivity.this;
        mainActivityViewModel = new ViewModelProvider( this ).get( MainActivityViewModel.class );
        mainActivityViewModel.callForCoinTiker( context );
        mainActivityViewModel.getCoinsList().observe( (LifecycleOwner) context, new Observer<List<Coin>>() {
            @Override
            public void onChanged(List<Coin> coins) {
                if (coins == null) {
                    Toast.makeText( context, "failed", Toast.LENGTH_SHORT ).show();
                } else {
                    Toast.makeText( context, "Successfully got tikers " + coins.size(), Toast.LENGTH_SHORT ).show();

                }
            }
        } );
    }
}