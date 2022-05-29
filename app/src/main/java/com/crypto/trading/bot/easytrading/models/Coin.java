package com.crypto.trading.bot.easytrading.models;

public class Coin {
    String symbol;
    String price;

    public Coin(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;
    }

    public Coin() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
