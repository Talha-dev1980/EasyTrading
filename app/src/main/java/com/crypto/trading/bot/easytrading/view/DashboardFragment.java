package com.crypto.trading.bot.easytrading.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crypto.trading.bot.easytrading.TickerAdapter;
import com.crypto.trading.bot.easytrading.databinding.FragmentDashboardBinding;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.viewModels.DashboardViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    DashboardViewModel viewModel;
    Context context;
    TickerAdapter tickerAdapter;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        context = getActivity();
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        viewModel.callForCoinTiker(context);
        viewModel.getCoinsList().observe((LifecycleOwner) context, new Observer<List<Coin>>() {
            @Override
            public void onChanged(List<Coin> coins) {
                if (coins == null) {
                    Log.d("coins", "Failed");
                } else {
                    Log.d("coins", "Successfully got tikers " + coins.size());
                    tickerAdapter = new TickerAdapter(context, coins);

                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(context);
                    binding.listTickers.setLayoutManager(layoutManager);
                    binding.listTickers.setHasFixedSize(true);
                    binding.listTickers.setAdapter(tickerAdapter);
                }
            }
        });/*
        viewModel.call24HrChange( context );
        viewModel.getCoinsListLast24hr().observe( (LifecycleOwner) context, new Observer<List<CoinPriceChange>>() {
            @Override
            public void onChanged(List<CoinPriceChange> coins) {
                if (coins == null) {
                   // Toast.makeText( context, "failed", Toast.LENGTH_SHORT ).show();
                    Log.d( "coins", "Failed" );
                } else {
                   // Toast.makeText( context, "Successfully got tikers " + coins.size(), Toast.LENGTH_SHORT ).show();

                    Log.d( "coins", "Successfully got tikers " + coins.size() );
                    tickerAdapter = new TickerAdapter(context, coins);

                    RecyclerView.LayoutManager layoutManager =
                            new LinearLayoutManager(context);
                    binding.listTickers.setLayoutManager(layoutManager);
                    binding.listTickers.setHasFixedSize(true);
                    binding.listTickers.setAdapter(tickerAdapter);
                }
            }
        } );*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}