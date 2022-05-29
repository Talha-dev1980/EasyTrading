package com.crypto.trading.bot.easytrading.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.crypto.trading.bot.easytrading.R;
import com.crypto.trading.bot.easytrading.Utils.TimeFormat;
import com.crypto.trading.bot.easytrading.databinding.ActivityCoinDetailsBinding;
import com.crypto.trading.bot.easytrading.models.Candle;
import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.models.CoinPriceChange;
import com.crypto.trading.bot.easytrading.viewModels.CoinDetailsViewModel;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CoinDetails extends AppCompatActivity {

    public ArrayList<CandleEntry> yValsCandleStick;
    ActivityCoinDetailsBinding binding;
    CoinDetailsViewModel viewModel;
    Context context;
    List<Candle> candlesParse;
    List<Double[]> candlesDouble;
    CoinPriceChange coinPriceChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCoinDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    public void init() {
        context = CoinDetails.this;
        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        String price = intent.getStringExtra("price");
        candlesParse = new ArrayList<>();
        candlesDouble = new ArrayList<>();
        binding.tvCoinPair.setText(symbol);
        Coin coin = new Coin(symbol, price);
        viewModel = new ViewModelProvider(this).get(CoinDetailsViewModel.class);
        viewModel.callPriceTiker(context, coin);
        viewModel.getCoinDetails().observe((LifecycleOwner) context, new Observer<CoinPriceChange>() {
            @Override
            public void onChanged(CoinPriceChange coins) {
                if (coins == null) {
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                    Log.d("coins", "Failed");
                } else {
                    setValues(coins);
                    Log.d("coins", "Successfully got tikers ");
                    loadChart(coins);

                }
            }
        });

    }

    private void loadChart(CoinPriceChange coin) {
        viewModel.call24Candles(context, coin);
        viewModel.get24Candles().observe((LifecycleOwner) context, new Observer<List<Double[]>>() {
            @Override
            public void onChanged(List<Double[]> candles) {
                if (candles.size() < 1) {
                    // Toast.makeText( context, "failed", Toast.LENGTH_SHORT ).show();
                    Log.d("Candles", "Failed");
                } else {
                    candlesDouble = candles;
                    Log.d("Candles", "Successfully got candles " + candlesDouble.size());
                    for (Double[] doubles : candlesDouble) {
                        candlesParse.add(new Candle(doubles));
                    }

                    Log.d("Candles", candlesDouble.size() + " " + candles.size() + "");
                    getData(candlesParse);

                }
            }
        });
    }

    private void setValues(CoinPriceChange coins) {
        TimeFormat timeFormat = new TimeFormat();
        binding.tvCoinPair.setText(coins.getSymbol());

        binding.tvCoinPrice.setText(coins.getLastPrice());
        binding.tvDurBehave.setText(timeFormat.twoDatesInterval(coins.getOpenTime(), coins.getCloseTime()));
        binding.tvMarketBehave.setText(coins.getPriceChangePercent());
        NumberFormat formatter = new DecimalFormat("#0.00");

        Double percentage = Double.parseDouble(coins.getPriceChangePercent());
        if (percentage > 0.0) {
            binding.tvMarketBehave.setTextColor(context.getResources().getColor(R.color.green));
            binding.tvMarketBehave.setText(formatter.format(percentage) + "" + context.getResources().getString(R.string.marketUp));

        } else if (percentage < 0.0) {
            binding.tvMarketBehave.setTextColor(context.getResources().getColor(R.color.red));
            binding.tvMarketBehave.setText(formatter.format(Math.abs(percentage)) + "" + context.getResources().getString(R.string.marketDown));

        } else {
            binding.tvMarketBehave.setTextColor(context.getResources().getColor(R.color.black));
            binding.tvMarketBehave.setText(formatter.format(Math.abs(percentage)) + "");

        }
        // setChart();

    }

    private void setChart() {
        binding.detailsChart.setHighlightPerDragEnabled(true);

        binding.detailsChart.setDrawBorders(true);

        binding.detailsChart.setBorderColor(getResources().getColor(R.color.white));
        YAxis yAxis = binding.detailsChart.getAxisLeft();
        YAxis rightAxis = binding.detailsChart.getAxisRight();
        yAxis.setDrawGridLines(false);
        rightAxis.setDrawGridLines(false);
        binding.detailsChart.requestDisallowInterceptTouchEvent(true);

        XAxis xAxis = binding.detailsChart.getXAxis();

        xAxis.setDrawGridLines(false);// disable x axis grid lines
        xAxis.setDrawLabels(false);
        rightAxis.setTextColor(Color.WHITE);
        yAxis.setDrawLabels(false);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = binding.detailsChart.getLegend();
        l.setEnabled(false);
        CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");
        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(getResources().getColor(com.google.android.material.R.color.design_default_color_primary));
        set1.setShadowWidth(0.8f);
        set1.setDecreasingColor(getResources().getColor(R.color.red));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(getResources().getColor(R.color.green));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);
        set1.setDrawValues(false);


// create a data object with the datasets
        CandleData data = new CandleData(set1);


// set data
        binding.detailsChart.setData(data);
        binding.detailsChart.invalidate();
    }

    private void getData(List<Candle> candleList) {
        yValsCandleStick = new ArrayList<CandleEntry>();
        int iterator=1;
        for (Candle candle : candleList) {
            yValsCandleStick.add(new CandleEntry(iterator, Float.parseFloat(candle.getHigh() + ""),
                    Float.parseFloat(candle.getLow() + ""), Float.parseFloat(candle.getOpen() + ""), Float.parseFloat(candle.getClose() + "")));
        iterator++;
        }
        setChart();/*yValsCandleStick.add(new CandleEntry(1, 228.35f, 222.57f, 223.52f, 226.41f));
        yValsCandleStick.add(new CandleEntry(2, 226.84f, 222.52f, 225.75f, 223.84f));
        yValsCandleStick.add(new CandleEntry(3, 222.95f, 217.27f, 222.15f, 217.88f));
        yValsCandleStick.add(new CandleEntry(4, 225.0f, 219.84f, 224.94f, 221.07f));
        yValsCandleStick.add(new CandleEntry(5, 228.35f, 222.57f, 223.52f, 226.41f));
        yValsCandleStick.add(new CandleEntry(6, 226.84f, 222.52f, 225.75f, 223.84f));
        yValsCandleStick.add(new CandleEntry(7, 222.95f, 217.27f, 222.15f, 217.88f));
        yValsCandleStick.add(new CandleEntry(8, 225.0f, 219.84f, 224.94f, 221.07f));
        yValsCandleStick.add(new CandleEntry(9, 228.35f, 222.57f, 223.52f, 226.41f));
        yValsCandleStick.add(new CandleEntry(10, 226.84f, 222.52f, 225.75f, 223.84f));
        yValsCandleStick.add(new CandleEntry(11, 222.95f, 217.27f, 222.15f, 217.88f));
*/
    }
}