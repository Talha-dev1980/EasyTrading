package com.crypto.trading.bot.easytrading.models;

import java.math.BigDecimal;

public class Candle {

    private Double openTime;

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    private Double volume;

    private Double closeTime;

    private Double quoteAssetVolume;

    private Double numTrades;

    private Double takerBuyBaseAssetVolume;

    private Double takerBuyQuoteAssetVolume;

    private Double ignore;

    public Candle(Double[] candleAttributes) {
        this.openTime = candleAttributes[0];
        this.open = candleAttributes[1];
        this.high = candleAttributes[2];
        this.low = candleAttributes[3];
        this.close = candleAttributes[4];
        this.volume = candleAttributes[5];
        this.closeTime = candleAttributes[6];
        this.quoteAssetVolume = candleAttributes[7];
        this.numTrades = candleAttributes[8];
        this.takerBuyBaseAssetVolume = candleAttributes[9];
        this.takerBuyQuoteAssetVolume = candleAttributes[10];
        this.ignore = candleAttributes[11];
    }

    public Double getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Double openTime) {
        this.openTime = openTime;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Double closeTime) {
        this.closeTime = closeTime;
    }

    public Double getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    public void setQuoteAssetVolume(Double quoteAssetVolume) {
        this.quoteAssetVolume = quoteAssetVolume;
    }

    public Double getNumTrades() {
        return numTrades;
    }

    public void setNumTrades(Double numTrades) {
        this.numTrades = numTrades;
    }

    public Double getTakerBuyBaseAssetVolume() {
        return takerBuyBaseAssetVolume;
    }

    public void setTakerBuyBaseAssetVolume(Double takerBuyBaseAssetVolume) {
        this.takerBuyBaseAssetVolume = takerBuyBaseAssetVolume;
    }

    public Double getTakerBuyQuoteAssetVolume() {
        return takerBuyQuoteAssetVolume;
    }

    public void setTakerBuyQuoteAssetVolume(Double takerBuyQuoteAssetVolume) {
        this.takerBuyQuoteAssetVolume = takerBuyQuoteAssetVolume;
    }

    public Double getIgnore() {
        return ignore;
    }

    public void setIgnore(Double ignore) {
        this.ignore = ignore;
    }
}
