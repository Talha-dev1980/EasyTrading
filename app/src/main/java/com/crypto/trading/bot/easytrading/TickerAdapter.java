package com.crypto.trading.bot.easytrading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.crypto.trading.bot.easytrading.models.Coin;
import com.crypto.trading.bot.easytrading.models.CoinPriceChange;
import com.crypto.trading.bot.easytrading.view.CoinDetails;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.CoinViewHolder> {
    private List<Coin> mCoins = new ArrayList<>();
    private Context mContext;

    public TickerAdapter(Context context, List<Coin> coins) {
        mContext = context;
        mCoins = coins;
    }

    @Override
    public TickerAdapter.CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.item_coinprice, parent, false);
        CoinViewHolder viewHolder = new CoinViewHolder( view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CoinViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bindCoin( mCoins.get( position));
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(mContext, CoinDetails.class);
                it.putExtra( "symbol",mCoins.get( position ).getSymbol()+"" );
                it.putExtra( "price",mCoins.get( position ).getPrice()+"" );
                mContext.startActivity( it );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public class CoinViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;
        public TextView tvTicker, tvPrice, tvChange, tvChngDur;

        public CoinViewHolder(View itemView) {
            super( itemView);

            mContext = itemView.getContext();
            tvTicker = (TextView) itemView.findViewById( R.id.tvCoinPair);
            tvPrice = (TextView) itemView.findViewById( R.id.tvCoinPrice);
            tvChange = (TextView) itemView.findViewById( R.id.tvMarketBehave);
            tvChngDur = (TextView) itemView.findViewById( R.id.tvDurBehave);
        }

        @SuppressLint("ResourceAsColor")
        public void bindCoin(Coin mCoin) {
            tvTicker.setText( mCoin.getSymbol() + "");
            tvPrice.setText( mCoin.getPrice() + "");
            NumberFormat formatter = new DecimalFormat( "#0.00");

            Double percentage = Double.parseDouble( mCoin.getPrice());
            tvChngDur.setText( mContext.getResources().getString( R.string.last) + " 24Hrs");
            if (percentage > 0.0) {
                tvChange.setTextColor( mContext.getResources().getColor( R.color.green));
                tvChange.setText( formatter.format( percentage) + "" + mContext.getResources().getString( R.string.marketUp));

            } else if (percentage < 0.0) {
                tvChange.setTextColor( mContext.getResources().getColor( R.color.red));
                tvChange.setText( formatter.format( Math.abs( percentage)) + "" + mContext.getResources().getString( R.string.marketDown));

            }else{
                tvChange.setTextColor( mContext.getResources().getColor( R.color.black));
                tvChange.setText( formatter.format( Math.abs( percentage)) + "" );

            }
        }
    }
}

