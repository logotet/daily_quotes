package com.logotet.dailysmartsproject.data.remote;

import androidx.annotation.NonNull;

public class QuoteModel {

    private QuoteData quote;

    public QuoteData getQuote() {
        return quote;
    }

    public void setQuote(QuoteData quote) {
        this.quote = quote;
    }


    @NonNull
    @Override
    public String toString() {
        return quote.toString();
    }
}
