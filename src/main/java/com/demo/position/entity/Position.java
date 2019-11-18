package com.demo.position.entity;

import java.util.StringJoiner;

public class Position {
    private String currencyCode;
    private int amount;

    public Position(String currencyCode, int amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return currencyCode + " " + amount;
    }
}
